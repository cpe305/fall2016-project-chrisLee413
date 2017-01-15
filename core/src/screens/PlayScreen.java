package screens;

import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.clee186.mariobros.MarioBros;

import scenes.Hud;
import sprites.Enemy;
import sprites.Item;
import sprites.ItemDefinition;
import sprites.Mario;
import sprites.Mushroom;
import tools.B2WorldCreator;
import tools.WorldContactListener;

public class PlayScreen implements Screen{
  
  //Reference to Game, used to set Screens
  private MarioBros game;
  private TextureAtlas atlas;
  public static boolean alreadyDestroyed = false;

  //basic playscreen variables
  private OrthographicCamera gamecam;
  private Viewport gamePort;
  private Hud hud;

  //Tiled map variables
  private TmxMapLoader maploader;
  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;

  //Box2d variables
  private World world;
  private Box2DDebugRenderer b2dr;
  private B2WorldCreator creator;

  //sprites
  private Mario mario;

  private Music music;

  private Array<Item> items;
  private LinkedBlockingQueue<ItemDefinition> itemsToSpawn;


  public PlayScreen(MarioBros game){
      atlas = new TextureAtlas("Mario_and_Enemies.pack.txt");

      this.game = game;
      
      //create cam used to follow mario through cam world
      gamecam = new OrthographicCamera();

      //create a FitViewport to maintain virtual aspect ratio despite screen size
      gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, gamecam);

      //create game HUD for scores/timers/level info
      hud = new Hud(game.batch);

      //Load map and setup renderer
      maploader = new TmxMapLoader();
      map = maploader.load("level1.tmx");
      renderer = new OrthogonalTiledMapRenderer(map, 1  / MarioBros.PPM);

      //initially set gamcam to be centered correctly at the start of of map
      gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

      //create Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
      world = new World(new Vector2(0, -10), true);
      
      //allows for debug lines of box2d world.
      //b2dr = new Box2DDebugRenderer();

      creator = new B2WorldCreator(this);

      mario = new Mario(this);

      world.setContactListener(new WorldContactListener());

      music = MarioBros.manager.get("audio/music/mario_music.ogg", Music.class);
      music.setLooping(true);
      music.play();

      items = new Array<Item>();
      itemsToSpawn = new LinkedBlockingQueue<ItemDefinition>();
  }

  public void spawnItem(ItemDefinition idef){
      itemsToSpawn.add(idef);
  }


  public void handleSpawningItems(){
      if(!itemsToSpawn.isEmpty()){
          ItemDefinition idef = itemsToSpawn.poll();
          if(idef.type == Mushroom.class){
              items.add(new Mushroom(this, idef.position.x, idef.position.y));
          }
      }
  }

  public TextureAtlas getAtlas(){
      return atlas;
  }

  @Override
  public void show() {

  }

  public void handleInput(float dt){
      //control our mario using immediate impulses
      if(mario.currentState != Mario.State.DEAD) {
          if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
              mario.jump();
          if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.b2body.getLinearVelocity().x <= 2)
              mario.b2body.applyLinearImpulse(new Vector2(0.1f, 0), mario.b2body.getWorldCenter(), true);
          if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.b2body.getLinearVelocity().x >= -2)
              mario.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), mario.b2body.getWorldCenter(), true);
      }

  }

  public void update(float dt){
    
      //handle user input first
      handleInput(dt);
      handleSpawningItems();

      world.step(1 / 60f, 6, 2);

      mario.update(dt);
      for(Enemy enemy : creator.getEnemies()) {
          enemy.update(dt);
          if(enemy.getX() < mario.getX() + 224 / MarioBros.PPM) {
              enemy.b2body.setActive(true);
          }
      }

      for(Item item : items)
          item.update(dt);

      hud.update(dt);

      //attach gamecam to mario.x coordinate
      if(mario.currentState != Mario.State.DEAD) {
          gamecam.position.x = mario.b2body.getPosition().x;
      }

      //update gamecam with correct coordinates after changes
      gamecam.update();
      
      //tell renderer to draw only what camera can see in game world.
      renderer.setView(gamecam);

  }


  @Override
  public void render(float delta) {
      update(delta);

      //Clear the game screen with Black
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      //render our game map
      renderer.render();

      //renderer Box2DDebugLines
      //b2dr.render(world, gamecam.combined);

      game.batch.setProjectionMatrix(gamecam.combined);
      game.batch.begin();
      mario.draw(game.batch);
      
      for (Enemy enemy : creator.getEnemies())
          enemy.draw(game.batch);
      for (Item item : items)
          item.draw(game.batch);
      
      game.batch.end();

      //Set batch to draw what the Hud camera sees.
      game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
      hud.stage.draw();

      if(gameOver()){
          game.setScreen(new GameOverScreen(game));
          dispose();
      }

  }

  public boolean gameOver(){
      if(mario.currentState == Mario.State.DEAD && mario.getStateTimer() > 3){
          return true;
      }
      return false;
  }

  @Override
  public void resize(int width, int height) {
      gamePort.update(width,height);

  }

  public TiledMap getMap(){
      return map;
  }
  public World getWorld(){
      return world;
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
      //dispose of all opened resources
      map.dispose();
      renderer.dispose();
      world.dispose();
      //b2dr.dispose();
      hud.dispose();
  }

  public Hud getHud() { 
    return hud; 
  }
}

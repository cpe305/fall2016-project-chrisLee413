package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.clee186.mariobros.MarioBros;

import scenes.Hud;
import sprites.Mario;
import tools.B2WorldCreator;

public class PlayScreen implements Screen {
  private MarioBros game;
  private Mario mario;
  private OrthographicCamera camera;
  private Viewport gamePort;
  private Hud hud;
  private TextureAtlas atlas;
  
  //Tiled map variables
  private TmxMapLoader maploader;
  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;
  
  //Box2d variables
  private World world;
  private Box2DDebugRenderer b2dr;
  
  public PlayScreen(MarioBros game){
    atlas = new TextureAtlas("Mario_and_Enemies.pack.txt");
    
    this.game = game;
    //create camera used to follow mario
    camera = new OrthographicCamera();
    
    //create a FitViewport to maintain virtual aspect ratio despite screen size
    gamePort = new FitViewport(MarioBros.V_WIDTH / MarioBros.PPM, MarioBros.V_HEIGHT / MarioBros.PPM, camera);
    
    //create the game HUD for scores/timers/level info
    hud = new Hud(game.batch);
    
    //Load our map and setup our map renderer
    maploader = new TmxMapLoader();
    map = maploader.load("level1.tmx");
    renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);
    
    //initially set our camera to be centered correctly at the 
    camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
    
    //create our Box2D world, setting no gravity in X, -10 in Y
    world = new World(new Vector2(0, -10), true);
    
    //allows for debug lines of our box2D world
    b2dr = new Box2DDebugRenderer();
    
    mario = new Mario(world, this);
    
    new B2WorldCreator(world, map);
  }

  @Override
  public void show() {
    // TODO Auto-generated method stub
    
  }
  
  public TextureAtlas getAtlas() {
    return atlas;
  }
  
  public void handleInput(float dt){
    
    if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
      mario.b2body.applyLinearImpulse(new Vector2(0, 4f), mario.b2body.getWorldCenter(), true);
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.b2body.getLinearVelocity().x <= 2)
      mario.b2body.applyLinearImpulse(new Vector2(0.1f, 0), mario.b2body.getWorldCenter(), true);
    if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.b2body.getLinearVelocity().x >= -2)
      mario.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), mario.b2body.getWorldCenter(), true);
  }
  
  public void update(float dt){
    handleInput(dt);
    
    world.step(1/60f, 6, 2);
    
    mario.update(dt);
    
    camera.position.x = mario.b2body.getPosition().x;
    
    camera.update();
    renderer.setView(camera);
  }

  @Override
  public void render(float delta) {
    //separate update logic from render
    update(delta);
    
    //clear game screen to black
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    
    //render game map
    renderer.render();
    
    //render Box2DDebugLines
    b2dr.render(world, camera.combined);
    
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();
    mario.draw(game.batch);
    game.batch.end();
    
    //set batch to draw what the Hud camera sees
    game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
    hud.stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    gamePort.update(width, height);
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void hide() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void dispose() {
    map.dispose();
    renderer.dispose();
    world.dispose();
    b2dr.dispose();
    hud.dispose();
  }

}

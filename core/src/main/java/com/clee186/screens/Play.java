package com.clee186.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.clee186.Characters.Link;

public class Play implements Screen {

  private TiledMap map;
  private OrthogonalTiledMapRenderer renderer;
  private OrthographicCamera camera;
  private Link hero;
  private float mapWidth;
  private float mapHeight;
  private float elapsedTime = 0f;
  private TextureAtlas atlas;
  private Animation animation;
  private SpriteBatch batch;

  public void show() {
    TmxMapLoader loader = new TmxMapLoader();
    map = loader.load("maps/LinksHouse.tmx");
    
    atlas = new TextureAtlas(Gdx.files.internal("LinkMovement/LinkMovement.pack"));
    animation = new Animation(1f/3f, atlas.getRegions());
    batch = new SpriteBatch();

    mapWidth = map.getProperties().get("width", Integer.class)
        * map.getProperties().get("tilewidth", Integer.class);
    mapHeight = map.getProperties().get("height", Integer.class)
        * map.getProperties().get("tileheight", Integer.class);

    renderer = new OrthogonalTiledMapRenderer(map);

    camera = new OrthographicCamera();

    hero = new Link(new Sprite(new Texture("img/player.png")),
        (TiledMapTileLayer) map.getLayers().get(0));
    hero.setSize(hero.getWidth() * .16f, hero.getHeight() * .16f);
    hero.setPosition(12 * hero.getCollisionLayer().getTileWidth(),
        (hero.getCollisionLayer().getHeight() - 21) * hero.getCollisionLayer().getTileHeight());

    Gdx.input.setInputProcessor(hero);
  }

  public void render(float delta) {
    elapsedTime = Gdx.graphics.getDeltaTime();
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

    camera.position.set(hero.getX() + (hero.getWidth() / 2), hero.getY() + (hero.getHeight() / 2),
        0);

    if (camera.position.x - camera.viewportWidth / 2 < 0)
      camera.position.x = camera.viewportWidth / 2;
    else if (camera.position.x + camera.viewportWidth / 2 > mapWidth)
      camera.position.x = mapWidth - camera.viewportWidth / 2;

    if (camera.position.y - camera.viewportHeight / 2 < 0)
      camera.position.y = camera.viewportHeight / 2;
    else if (camera.position.y + camera.viewportHeight / 2 > mapHeight)
      camera.position.y = mapHeight - camera.viewportHeight / 2;

    camera.update();

    renderer.setView(camera);
    renderer.render();

    renderer.getSpriteBatch().begin();
    hero.draw(renderer.getSpriteBatch());
    renderer.getSpriteBatch().end();
    
    batch.begin();
    batch.draw(animation.getKeyFrame(elapsedTime, true), 12 * hero.getCollisionLayer().getTileWidth(),
        (hero.getCollisionLayer().getHeight() - 21) * hero.getCollisionLayer().getTileHeight());
    batch.end();
  }

  public void resize(int width, int height) {
    camera.viewportWidth = width / 7;
    camera.viewportHeight = height / 6;
  }

  public void pause() {

  }

  public void resume() {

  }

  public void hide() {
    dispose();
  }

  public void dispose() {
    map.dispose();
    renderer.dispose();
    hero.getTexture().dispose();
    atlas.dispose();
  }

}

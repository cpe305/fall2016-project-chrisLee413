package com.clee186.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.clee186.entities.Player;

public class Play implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player hero;

	public void show() {
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load("maps/LinksHouse.tmx");

		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();

		hero = new Player(new Sprite(new Texture("img/player.png")), (TiledMapTileLayer) map.getLayers().get(0));
		hero.setSize(hero.getWidth() * .16f, hero.getHeight() * .16f);
		hero.setPosition(12 * hero.getCollisionLayer().getTileWidth(), (hero.getCollisionLayer().getHeight() - 21) * hero.getCollisionLayer().getTileHeight());

		Gdx.input.setInputProcessor(hero);

	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		if(camera.position.x != (hero.getX() + (hero.getWidth() / 2))){
			camera.position.set(hero.getX() + (hero.getWidth() / 2), hero.getY() + (hero.getHeight() / 2), 0);
			camera.update();
		}
		System.out.println("camera: " + camera.position.x);
		System.out.println("camera: " + camera.position.x);

		renderer.setView(camera);
		renderer.render();

		renderer.getSpriteBatch().begin();
		hero.draw(renderer.getSpriteBatch());
		renderer.getSpriteBatch().end();
	}

	public void resize(int width, int height) {
		camera.viewportWidth = width / 7;
		camera.viewportHeight = height / 6;
		camera.update();
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
	}

}

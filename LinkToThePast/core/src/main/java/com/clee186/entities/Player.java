package com.clee186.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite implements InputProcessor {

	// the movement velocity
	private Vector2 velocity = new Vector2();

	private float speed = 60 * 2;
	private TiledMapTileLayer collisionLayer;

	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		this.collisionLayer = collisionLayer;
	}

	public void draw(Batch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}

	public void update(float delta) {

		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);

	}

	public boolean keyDown(int keycode) {
		switch(keycode){
		case Keys.W:
			velocity.y = speed;
			break;
		case Keys.A:
			velocity.x = -speed;
			break;
		case Keys.S:
			velocity.y = -speed;
			break;
		case Keys.D:
			velocity.x = speed;
			break;
		}
		return true;
	}

	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.W:
			velocity.y = 0;
			break;
		case Keys.A:
			velocity.x = 0;
			break;
		case Keys.S:
			velocity.y = 0;
			break;
		case Keys.D:
			velocity.x = 0;
			break;
		}
		return true;
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public boolean keyTyped(char arg0) {
		return false;
	}

	public boolean mouseMoved(int arg0, int arg1) {
		return false;
	}

	public boolean scrolled(int arg0) {
		return false;
	}

	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		return false;
	}

}

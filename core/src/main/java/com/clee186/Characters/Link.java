package com.clee186.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

public class Link extends Sprite implements InputProcessor {

  // the movement velocity
  private Vector2 velocity = new Vector2();
  
  private boolean movingLeft = false;
  private boolean movingRight = false;
  private boolean movingUp = false;
  private boolean movingDown = false;

  private float speed = 60 * 2;
  private TiledMapTileLayer collisionLayer;

  public Link(Sprite sprite, TiledMapTileLayer collisionLayer) {
    super(sprite);
    this.collisionLayer = collisionLayer;
  }

  public void draw(Batch spriteBatch) {
    update(Gdx.graphics.getDeltaTime());
    super.draw(spriteBatch);
  }

  public void update(float delta) {
    
    float oldX = getX();
    float oldY = getY();
    
    boolean collisionX = false;
    boolean collisionY = false;

    setX(getX() + velocity.x * delta);
    
    //going left
    if (velocity.x < 0)
      collisionX = collidesLeft();

    //going right
    else if (velocity.x > 0)
      collisionX = collidesRight();
    
    if (collisionX) {
      setX(oldX);
      velocity.x = 0;
    }
    
    setY(getY() + velocity.y * delta);

    //going down
    if (velocity.y < 0)
      collisionY = collidesBottom();
    
    //going up
    else if (velocity.y > 0)
      collisionY = collidesTop();
    
    if (collisionY) {
      setY(oldY);
      velocity.y = 0;
    }
  }
  
  private boolean isCellBlocked(float x, float y) {
    Cell cell = collisionLayer.getCell((int)(x / collisionLayer.getTileWidth()), (int)(y / collisionLayer.getTileHeight()));
    return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
  }
  
  private boolean collidesRight() {
    for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2) {
      if(isCellBlocked(getX() + getWidth(), getY() + step))
        return true;
    }
    
    return false;
  }
  
  private boolean collidesLeft() {
    for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2) {
      if(isCellBlocked(getX(), getY() + step))
        return true;
    }
    
    return false; 
  }

  private boolean collidesTop() {
    for (float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2) {
      if(isCellBlocked(getX() + step, getY() + getHeight()))
        return true;
    }
    
    return false;
  }

  private boolean collidesBottom() {
    
    for (float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2) {
      if(isCellBlocked(getX() + step, getY()))
        return true;
    }
    
    return false;
  }

  public boolean keyDown(int keycode) {
    switch (keycode) {
      case Keys.W:
        velocity.y = speed;
        movingUp = true;
        break;
      case Keys.A:
        velocity.x = -speed;
        movingLeft = true;
        break;
      case Keys.S:
        velocity.y = -speed;
        movingDown = true;
        break;
      case Keys.D:
        velocity.x = speed;
        movingRight = true;
        break;
      default:
        break;
    }
    return true;
  }

  public boolean keyUp(int keycode) {
    switch (keycode) {
      case Keys.W:
        if(movingDown)
           velocity.y = -speed;
        else
          velocity.y = 0;
        
        movingUp = false;
        break;
      case Keys.A:
        if(movingRight)
          velocity.x = speed;
        else
          velocity.x = 0;
        
        movingLeft = false;
        break;
      case Keys.S:
        if(movingUp)
          velocity.y = speed;
        else
          velocity.y = 0;
        
        movingDown = false;
        break;
      case Keys.D:
        if(movingLeft)
          velocity.x = -speed;
        else
          velocity.x = 0;
        
        movingRight = false;
        break;
      default:
        break;
    }
    return true;
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

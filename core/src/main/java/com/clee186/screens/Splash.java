package com.clee186.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.clee186.Tweens.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class Splash implements Screen {
  
  private Sprite splash;
  private SpriteBatch batch;
  private TweenManager tweenManager;

  public void dispose() {
    batch.dispose();
    splash.getTexture().dispose();
  }

  @Override
  public void hide() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    
    tweenManager.update(delta);
    
    batch.begin();
    splash.draw(batch);
    batch.end();
  }

  @Override
  public void resize(int width, int height) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void show() {
    batch = new SpriteBatch();
    splash = new Sprite(new Texture("img/Title.png"));
    
    tweenManager = new TweenManager();
    Tween.registerAccessor(Sprite.class, new SpriteAccessor());
    
    splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    
    Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
    Tween.to(splash, SpriteAccessor.ALPHA, 3).target(1).repeatYoyo(1, 2).setCallback(new TweenCallback() {

      public void onEvent(int arg0, BaseTween<?> arg1) {
        ((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
      }
  }).start(tweenManager);
  }

}

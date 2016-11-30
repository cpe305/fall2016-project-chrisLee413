package com.clee186.core;

import com.badlogic.gdx.Game;
import com.clee186.screens.Play;
import com.clee186.screens.Splash;

public class ZeldaGame extends Game {

  public void create() {
    setScreen(new Play());
  }

  public void resize(int width, int height) {
    super.resize(width, height);
  }

  public void render() {
    super.render();
  }

  public void pause() {
    super.pause();
  }

  public void resume() {
    super.resume();
  }

  public void dispose() {
    super.dispose();
  }
}

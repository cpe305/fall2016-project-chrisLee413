package com.clee186.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.clee186.core.ZeldaGame;

public class ZeldaGameDesktop {
  public static void main(String[] args) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    config.title = "A Link to the Past";
    config.useGL30 = true;
    config.width = 1280;
    config.height = 720;

    new LwjglApplication(new ZeldaGame(), config);

  }
}

package com.clee186.html;

import com.clee186.core.ZeldaGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class ZeldaGameHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new ZeldaGame();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}

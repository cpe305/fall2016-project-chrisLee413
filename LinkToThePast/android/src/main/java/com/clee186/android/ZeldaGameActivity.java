package com.clee186.android;

import com.clee186.core.ZeldaGame;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class ZeldaGameActivity extends AndroidApplication {

	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
			initialize(new ZeldaGame(), config);
	}
}

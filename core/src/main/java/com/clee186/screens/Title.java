package com.clee186.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class Title implements Screen {
  
  private Stage stage;
  private TextureAtlas atlas;
  private Skin skin;
  private Table table;
  private TextButton buttonPlay, buttonExit;
  private BitmapFont white, black;
  private Label heading;

  @Override
  public void dispose() {
    // TODO Auto-generated method stub
    
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
    
    Table.drawDebug(stage);
    
    stage.act(delta);    
    stage.draw();
  }

  @Override
  public void resize(int arg0, int arg1) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void show() {
    stage = new Stage();
    
    atlas = new TextureAtlas("ui/button.pack");
    skin = new Skin(atlas);
    
    table = new Table(skin);
    table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    
    white = new BitmapFont(Gdx.files.internal("Fonts/white.fnt"));
    
    TextButtonStyle textButtonStyle = new TextButtonStyle();
    textButtonStyle.up = skin.getDrawable("button.up");
    textButtonStyle.down = skin.getDrawable("button.down");
    textButtonStyle.pressedOffsetX = 1;
    textButtonStyle.pressedOffsetY = -1;
    textButtonStyle.font = white;
    textButtonStyle.fontColor = Color.BLACK;
    
    buttonExit = new TextButton("EXIT", textButtonStyle);
    buttonExit.pad(20);
    
    table.add(buttonExit);
    table.debug();
    stage.addActor(table);    
  }

}

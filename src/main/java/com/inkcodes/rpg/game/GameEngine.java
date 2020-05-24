package com.inkcodes.rpg.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.inkcodes.rpg.Engine;
import com.inkcodes.rpg.graphics.GraphicsFactory;

public class GameEngine implements Engine {

  private final GraphicsFactory graphicsFactory;

  public GameEngine(final GraphicsFactory graphicsFactory) {
    this.graphicsFactory = graphicsFactory;
  }

  @Override
  public void tick(final KeyStroke keyStroke) {}
}

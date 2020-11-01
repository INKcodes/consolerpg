package com.inkcodes.rpg.graphics;

import com.googlecode.lanterna.input.KeyStroke;
import com.inkcodes.rpg.Engine;

public class GraphicsEngine implements Engine, GraphicsFactory {

  private final Canvas canvas;

  public static int SCREEN_WIDTH;
  public static int SCREEN_HEIGHT;

  public GraphicsEngine(final Canvas canvas) {
    this.canvas = canvas;
  }

  @Override
  public void tick(final KeyStroke keyStroke) {}

  @Override
  public Sprite createSprite(final char sym) {
    final Sprite sprite = new Sprite(sym);
    canvas.addSprite(sprite);
    return sprite;
  }
}

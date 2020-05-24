package com.inkcodes.rpg.graphics;

import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;
import com.inkcodes.rpg.Engine;

public class GraphicsEngine implements Engine, GraphicsFactory {

  private final Canvas canvas;

  public GraphicsEngine(final Window window) {
    final Panel contentPanel = new Panel(new LinearLayout().setSpacing(0));
    canvas = new Canvas();
    contentPanel.addComponent(canvas);
    window.setComponent(contentPanel);
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

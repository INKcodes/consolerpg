package com.inkcodes.rpg.graphics;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;

import java.util.ArrayList;
import java.util.List;

class Canvas extends AbstractComponent<Canvas> {

  private final List<Sprite> sprites = new ArrayList<>();

  public void addSprite(final Sprite sprite) {
    sprites.add(sprite);
    sprite.onChange((Void) -> this.invalidate());
    this.invalidate();
  }

  @Override
  protected ComponentRenderer<Canvas> createDefaultRenderer() {
    return new ComponentRenderer<>() {

      @Override
      public TerminalSize getPreferredSize(final Canvas component) {
        return new TerminalSize(80, 24);
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final Canvas component) {
        sprites.forEach(
            s -> graphics.putString(s.getPosX(), s.getPosY(), String.valueOf(s.getSymbol())));
      }
    };
  }
}

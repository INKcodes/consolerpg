package com.inkcodes.rpg.graphics;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;

class Canvas extends AbstractComponent<Canvas> {

  private int playerPosX;
  private int playerPosY;

  @Override
  protected ComponentRenderer<Canvas> createDefaultRenderer() {
    return new ComponentRenderer<>() {

      @Override
      public TerminalSize getPreferredSize(final Canvas component) {
        return new TerminalSize(80, 24);
      }

      @Override
      public void drawComponent(final TextGUIGraphics graphics, final Canvas component) {
        graphics.putString(playerPosX, playerPosY, String.valueOf(Symbols.CLUB));
      }
    };
  }

  public void setPlayerPosition(final int playerPosX, final int playerPosY) {
    this.playerPosX = playerPosX;
    this.playerPosY = playerPosY;
    this.invalidate();
  }
}

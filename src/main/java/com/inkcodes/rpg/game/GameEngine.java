package com.inkcodes.rpg.game;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.input.KeyStroke;
import com.inkcodes.rpg.Engine;
import com.inkcodes.rpg.graphics.GraphicsFactory;
import com.inkcodes.rpg.graphics.Sprite;

public class GameEngine implements Engine {

  private final GraphicsFactory graphicsFactory;
  private final Sprite playerSprite;

  public GameEngine(final GraphicsFactory graphicsFactory) {
    this.graphicsFactory = graphicsFactory;
    this.playerSprite = graphicsFactory.createSprite(Symbols.CLUB);
  }

  @Override
  public void tick(final KeyStroke keyStroke) {
    // TODO: still very empty here :(
    switch (keyStroke.getKeyType()) {
      case ArrowDown:
        playerSprite.setPosY(playerSprite.getPosY() + 1);
        break;
      case ArrowUp:
        playerSprite.setPosY(playerSprite.getPosY() - 1);
        break;
      case ArrowRight:
        playerSprite.setPosX(playerSprite.getPosX() + 1);
        break;
      case ArrowLeft:
        playerSprite.setPosX(playerSprite.getPosX() - 1);
        break;
    }
  }
}

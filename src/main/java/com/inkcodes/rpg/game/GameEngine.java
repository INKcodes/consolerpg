package com.inkcodes.rpg.game;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.input.KeyStroke;
import com.inkcodes.rpg.Engine;
import com.inkcodes.rpg.graphics.GraphicsFactory;
import com.inkcodes.rpg.graphics.Sprite;

public class GameEngine implements Engine {
  private Sprite playerSprite;

  public void init(final GraphicsFactory graphicsFactory) {
    playerSprite = graphicsFactory.createGraphics(Symbols.CLUB);
  }

  @Override
  public void tick(final KeyStroke keyStroke) {
      switch (keyStroke.getKeyType()) {
        case ArrowDown -> playerSprite.setPosY(playerSprite.getPosY()+1);
        case ArrowRight -> playerSprite.setPosX(playerSprite.getPosX()+1);
        case ArrowLeft -> playerSprite.setPosX(playerSprite.getPosX()-1);
        case ArrowUp -> playerSprite.setPosY(playerSprite.getPosY()-1);
      }
  }
}

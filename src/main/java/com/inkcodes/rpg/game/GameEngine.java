package com.inkcodes.rpg.game;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.input.KeyStroke;
import com.inkcodes.rpg.Engine;
import com.inkcodes.rpg.graphics.DialogFactory;
import com.inkcodes.rpg.graphics.GraphicsFactory;
import com.inkcodes.rpg.graphics.Sprite;

public class GameEngine implements Engine {

  private final GraphicsFactory graphicsFactory;
  private final Sprite playerSprite;
  private final Sprite schildSprite;
  private final DialogFactory dialogFactory;

  public GameEngine(final GraphicsFactory graphicsFactory, final DialogFactory dialogFactory) {
    this.graphicsFactory = graphicsFactory;
    this.playerSprite = graphicsFactory.createSprite(Symbols.CLUB);
    this.schildSprite = graphicsFactory.createSprite(Symbols.DIAMOND);
    this.dialogFactory = dialogFactory;
    this.schildSprite.setPosX(5);
    this.schildSprite.setPosY(5);
  }

  @Override
  public void tick(final KeyStroke keyStroke) {

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
    if (playerSprite.getPosX() == schildSprite.getPosX()
        && playerSprite.getPosY() == schildSprite.getPosY()) {
      dialogFactory.showDialog("Schild", "Hallo Welt!");
      System.out.println("test");
    }
  }
}

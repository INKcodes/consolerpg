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
    this.playerSprite.setPosZ(1000);
    this.schildSprite.setPosZ(0);
  }

  @Override
  public void tick(final KeyStroke keyStroke) {
    System.out.println(keyStroke);
    switch (keyStroke.getKeyType()) {
      case ArrowDown -> playerSprite.setPosY(playerSprite.getPosY() + 1);
      case ArrowUp -> playerSprite.setPosY(playerSprite.getPosY() - 1);
      case ArrowRight -> playerSprite.setPosX(playerSprite.getPosX() + 1);
      case ArrowLeft -> playerSprite.setPosX(playerSprite.getPosX() - 1);
      case Character -> {
        switch (keyStroke.getCharacter()) {
          case 's' -> playerSprite.setPosY(playerSprite.getPosY() + 1);
          case 'w' -> playerSprite.setPosY(playerSprite.getPosY() - 1);
          case 'd' -> playerSprite.setPosX(playerSprite.getPosX() + 1);
          case 'a' -> playerSprite.setPosX(playerSprite.getPosX() - 1);
        }
      }
    }
    if (playerSprite.getPosX() == schildSprite.getPosX()
        && playerSprite.getPosY() == schildSprite.getPosY()) {
      dialogFactory.showDialog("Schild", "Hallo Welt!");
    }
  }
}

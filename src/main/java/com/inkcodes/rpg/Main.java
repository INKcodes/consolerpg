package com.inkcodes.rpg;

import com.googlecode.lanterna.gui2.Window;
import com.inkcodes.rpg.game.GameEngine;
import com.inkcodes.rpg.graphics.GraphicsEngine;

public class Main {

  private static final Console console = new Console();

  public static void main(final String[] args) {
    final Window window = console.init();

    final var graphicsEngine = new GraphicsEngine(window);
    console.addEngine(graphicsEngine);

    final var gameEngine = new GameEngine(graphicsEngine);
    console.addEngine(gameEngine);

    console.run();
  }
}

package com.inkcodes.rpg;

import com.inkcodes.rpg.game.GameEngine;
import com.inkcodes.rpg.graphics.GraphicsEngine;

public class Main {

  private static final Console console = new Console();

  public static void main(final String[] args) {
    final var canvas = console.init();

    final var graphicsEngine = new GraphicsEngine(canvas);
    console.addEngine(graphicsEngine);

    final var gameEngine = new GameEngine(graphicsEngine, console);
    console.addEngine(gameEngine);

    console.run();
  }
}

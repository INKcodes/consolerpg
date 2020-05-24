package com.inkcodes.rpg;

import com.inkcodes.rpg.graphics.GraphicsEngine;

public class Main {

  public static void main(String[] args) {
    final var gEngine = new GraphicsEngine();
    gEngine.init();

    gEngine.drawWorld();
  }
}

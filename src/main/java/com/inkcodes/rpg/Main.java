package com.inkcodes.rpg;

import com.inkcodes.rpg.graphics.GraphicsEngine;

import java.util.ArrayDeque;
import java.util.Deque;

public class Main {

  private static Deque<Engine> engineStack;

  public static void main(final String[] args) {
    initEngines();

    mainLoop();

    shutdown();
  }

  private static void initEngines() {
    engineStack = new ArrayDeque<>();

    final var graphicsEngine = new GraphicsEngine();
    graphicsEngine.init();
    engineStack.add(graphicsEngine);
  }

  private static void mainLoop() {
    boolean running = true;
    while (running) {
      for (final Engine e : engineStack) {
        running = e.run();
        if (!running) {
          break;
        }
      }
    }
  }

  private static void shutdown() {
    engineStack.forEach(Engine::shutdown);
  }
}

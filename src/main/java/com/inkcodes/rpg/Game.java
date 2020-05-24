package com.inkcodes.rpg;

public class Game implements Runnable {

  @Override
  public void run() {
    init(); // initialisation of images, sound..etc. will be executed once only

    final int fps = 60; // number of update per second.
    final double tickPerSecond = 1000000000 / fps;
    final double delta = 0;
    long now;
    final long lastTime = System.nanoTime();
  }

  private void init() {}
}

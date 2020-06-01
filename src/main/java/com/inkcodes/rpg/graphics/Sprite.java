package com.inkcodes.rpg.graphics;

import java.util.function.Consumer;

public class Sprite {
  private int posX;
  private int posY;
  private final char symbol;
  private Consumer<Void> consumer;

  public Sprite(final char symbol) {
    this.symbol = symbol;
  }

  public int getPosX() {
    return posX;
  }

  public void setPosX(final int posX) {
    invalidate();
    this.posX = clamp(posX, 0, GraphicsEngine.SCREEN_WIDTH - 1);
  }

  private void invalidate() {
    this.consumer.accept(null);
  }

  public int getPosY() {
    return posY;
  }

  public void setPosY(final int posY) {
    invalidate();
    this.posY = clamp(posY, 0, GraphicsEngine.SCREEN_HEIGHT - 1);
  }

  private int clamp(final int value, final int min, final int max) {
    final int calcMax = Math.min(max, value);
    return Math.max(min, calcMax);
  }

  public char getSymbol() {
    return symbol;
  }

  public void onChange(final Consumer<Void> consumer) {
    this.consumer = consumer;
  }
}

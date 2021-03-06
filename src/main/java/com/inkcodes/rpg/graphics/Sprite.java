package com.inkcodes.rpg.graphics;

import java.util.function.Consumer;

public class Sprite implements Comparable<Sprite> {
  private int posX;
  private int posY;
  private int posZ;
  private final char symbol;
  private Consumer<Void> consumer;

  public Sprite(final char symbol) {
    this.symbol = symbol;
  }

  public int getPosX() {
    return posX;
  }

  public int getPosZ() {
    return posZ;
  }

  public void setPosZ(final int posZ) {
    this.posZ = posZ;
  }

  public void setPosX(final int posX) {
    invalidate();
    this.posX = posX;
  }

  private void invalidate() {
    this.consumer.accept(null);
  }

  public int getPosY() {
    return posY;
  }

  public void setPosY(final int posY) {
    invalidate();
    this.posY = posY;
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

  @Override
  public int compareTo(final Sprite other) {
    return this.posZ > other.posZ ? 1 : -1;
  }
}

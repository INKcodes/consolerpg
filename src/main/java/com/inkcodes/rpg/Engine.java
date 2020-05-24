package com.inkcodes.rpg;

import com.googlecode.lanterna.input.KeyStroke;

public interface Engine {
  void tick(KeyStroke keyStroke);
}

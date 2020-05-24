package com.inkcodes.rpg.graphics;

import com.google.common.base.Preconditions;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GraphicsEngine {

  private Screen screen;
  private boolean initialized;
  private boolean dirty;

  public void init() {
    try {
      DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
      defaultTerminalFactory.setAutoOpenTerminalEmulatorWindow(true);
      Terminal terminal = defaultTerminalFactory.createTerminal();
      screen = new TerminalScreen(terminal);
      screen.startScreen();
      screen.setCursorPosition(null);
      initialized = true;
    } catch (final IOException e) {
      throw new GraphicsException(e);
    }
  }

  public void drawWorld() {
    Preconditions.checkArgument(initialized);
    TerminalSize terminalSize = screen.getTerminalSize();
    drawBorderedBox(0, 0, terminalSize.getColumns(), terminalSize.getRows());
    refreshScreen();
  }

  private void refreshScreen() {
    if (dirty) {
      try {
        screen.refresh();
      } catch (IOException e) {
        throw new GraphicsException(e);
      }
      dirty = false;
    }
  }

  private void drawBorderedBox(int x, int y, int width, int height) {
    TextGraphics textGraphics = screen.newTextGraphics();

    // draw top left corner
    textGraphics.setCharacter(x, y, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);

    // draw top border
    for (int rx = x + 1; rx < x + width - 1; ++rx) {
      textGraphics.setCharacter(rx, y, Symbols.DOUBLE_LINE_HORIZONTAL);
    }

    // draw top right corner
    textGraphics.setCharacter(x + width - 1, y, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);

    // draw right border
    for (int ry = y + 1; ry < y + height - 1; ++ry) {
      textGraphics.setCharacter(x + width - 1, ry, Symbols.DOUBLE_LINE_VERTICAL);
    }

    // draw bottom right corner
    textGraphics.setCharacter(
        x + width - 1, y + height - 1, Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);

    // draw botton border
    for (int rx = x + 1; rx < x + width - 1; ++rx) {
      textGraphics.setCharacter(rx, y + height - 1, Symbols.DOUBLE_LINE_HORIZONTAL);
    }

    // draw bottom left corner
    textGraphics.setCharacter(x, y + height - 1, Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);

    // draw left border
    for (int ry = y + 1; ry < y + height - 1; ++ry) {
      textGraphics.setCharacter(x, ry, Symbols.DOUBLE_LINE_VERTICAL);
    }

    markDirty();
  }

  private void markDirty() {
    dirty = true;
  }
}

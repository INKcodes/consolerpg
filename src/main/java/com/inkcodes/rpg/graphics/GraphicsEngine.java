package com.inkcodes.rpg.graphics;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.inkcodes.rpg.Engine;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class GraphicsEngine implements Engine {

  private Screen screen;
  private boolean initialized;
  private boolean dirty;

  private int playerPosX = 0;
  private int playerPosY = 0;
  private WindowBasedTextGUI textGUI;

  public void init() {
    try {
      final DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
      defaultTerminalFactory.setAutoOpenTerminalEmulatorWindow(true);
      final Terminal terminal = defaultTerminalFactory.createTerminal();
      System.out.println("terminal size: " + terminal.getTerminalSize());
      screen = new TerminalScreen(terminal);
      screen.startScreen();
      textGUI = new MultiWindowTextGUI(screen);
      final Window window = new BasicWindow("My Root Window");
      window.setHints(Collections.singletonList(Window.Hint.FULL_SCREEN));
      final Panel contentPanel = new Panel(new LinearLayout().setSpacing(0));
      final Canvas canvas = new Canvas();
      contentPanel.addComponent(canvas);
      window.setComponent(contentPanel);
      window.addWindowListener(new WindowListener() {
        @Override
        public void onResized(final Window window, final TerminalSize oldSize, final TerminalSize newSize) {

        }

        @Override
        public void onMoved(final Window window, final TerminalPosition oldPosition, final TerminalPosition newPosition) {

        }

        @Override
        public void onInput(final Window basePane, final KeyStroke keyStroke, final AtomicBoolean deliverEvent) {
          System.out.println(keyStroke);
          if (keyStroke.getKeyType() == KeyType.Escape) {
          } else {
            switch (keyStroke.getKeyType()) {
              case ArrowDown -> playerPosY++;
              case ArrowRight -> playerPosX++;
              case ArrowLeft -> playerPosX--;
              case ArrowUp -> playerPosY--;
            }
          }
          canvas.setPlayerPosition(playerPosX, playerPosY);
        }

        @Override
        public void onUnhandledInput(final Window basePane, final KeyStroke keyStroke, final AtomicBoolean hasBeenHandled) {

        }
      });
      textGUI.addWindowAndWait(window);
      //screen.setCursorPosition(null);
      initialized = true;
    } catch (final IOException e) {
      throw new GraphicsException(e);
    }
  }

  @Override
  public boolean run() {
    //drawWorld();

    try {
      final KeyStroke stroke;
      stroke = screen.pollInput();
      if (stroke != null) {

      }
    } catch (final IOException e) {
      throw new GraphicsException(e);
    }

    //drawPlayer();

    //refreshScreen();
    return true;
  }

  private void drawPlayer() {
    screen.setCharacter(playerPosX, playerPosY, new TextCharacter(Symbols.CLUB));
    markDirty();
  }

  private void drawWorld() {
    final TerminalSize terminalSize = screen.getTerminalSize();
    drawBorderedBox(0, 0, terminalSize.getColumns(), terminalSize.getRows());
    refreshScreen();
  }

  private void refreshScreen() {
    if (dirty) {
      try {
        screen.refresh();
      } catch (final IOException e) {
        throw new GraphicsException(e);
      }
      dirty = false;
    }
  }

  private void drawBorderedBox(final int x, final int y, final int width, final int height) {
    final TextGraphics textGraphics = screen.newTextGraphics();

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

  @Override
  public void shutdown() {
    try {
      screen.close();
    } catch (final IOException e) {
      throw new GraphicsException(e);
    }
  }
}

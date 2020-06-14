package com.inkcodes.rpg;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import com.inkcodes.rpg.graphics.GraphicsEngine;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;

public class Console {
  private Window window;
  private WindowBasedTextGUI textGUI;
  private static final Deque<Engine> engineStack = new ArrayDeque<>();

  private void initWindow() throws IOException {
    final DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    defaultTerminalFactory.setAutoOpenTerminalEmulatorWindow(true);
    defaultTerminalFactory.setTerminalEmulatorTitle("ConsoleRPG");
    final Font font = new Font("Courier New", Font.PLAIN, 36);
    final var fontConfig = SwingTerminalFontConfiguration.newInstance(font);
    defaultTerminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
    final Terminal terminal = defaultTerminalFactory.createTerminal();
    final Screen screen = new TerminalScreen(terminal);
    screen.startScreen();
    textGUI = new MultiWindowTextGUI(screen);
    window = new BasicWindow();
    window.setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.NO_DECORATIONS));
    window.addWindowListener(
        new WindowListener() {
          @Override
          public void onResized(
              final Window window, final TerminalSize oldSize, final TerminalSize newSize) {
            GraphicsEngine.SCREEN_HEIGHT = newSize.getRows();
            GraphicsEngine.SCREEN_WIDTH = newSize.getColumns();
          }

          @Override
          public void onMoved(
              final Window window,
              final TerminalPosition oldPosition,
              final TerminalPosition newPosition) {}

          @Override
          public void onInput(
              final Window basePane, final KeyStroke keyStroke, final AtomicBoolean deliverEvent) {
            if (keyStroke.getKeyType() == KeyType.Escape) {
              try {
                terminal.close();
              } catch (final IOException e) {
                throw new RuntimeException(e);
              }
            } else if (keyStroke.getCharacter() != null && keyStroke.getCharacter() == 't') {
              new MessageDialogBuilder()
                  .setTitle("Schild")
                  .setText("Hallo Welt")
                  .build()
                  .showDialog(textGUI);
            } else {
              engineStack.forEach(e -> e.tick(keyStroke));
            }
          }

          @Override
          public void onUnhandledInput(
              final Window basePane,
              final KeyStroke keyStroke,
              final AtomicBoolean hasBeenHandled) {}
        });
  }

  public Window init() {
    try {
      initWindow();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
    return window;
  }

  public void addEngine(final Engine engine) {
    engineStack.add(engine);
  }

  public void run() {
    textGUI.addWindowAndWait(window); // this is blocking
  }
}

package com.inkcodes.rpg;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
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
import com.inkcodes.rpg.graphics.Canvas;
import com.inkcodes.rpg.graphics.DialogFactory;
import com.inkcodes.rpg.graphics.GraphicsEngine;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Console implements DialogFactory {
  private Window window;
  private WindowBasedTextGUI textGUI;
  private static final Deque<Engine> engineStack = new ArrayDeque<>();
  private Panel stats;
  private Panel mainPanel;

  private Canvas initWindow() throws IOException {
    final DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    defaultTerminalFactory.setAutoOpenTerminalEmulatorWindow(true);
    defaultTerminalFactory.setTerminalEmulatorTitle("ConsoleRPG");
    final Font font = new Font("Courier New", Font.PLAIN, 26);
    final var fontConfig = SwingTerminalFontConfiguration.newInstance(font);
    defaultTerminalFactory.setTerminalEmulatorFontConfiguration(fontConfig);
    defaultTerminalFactory.setInitialTerminalSize(new TerminalSize(110, 40));
    final Terminal terminal = defaultTerminalFactory.createTerminal();
    final Screen screen = new TerminalScreen(terminal);
    screen.startScreen();
    textGUI = new MultiWindowTextGUI(screen);
    window = new BasicWindow();
    window.setHints(
        List.of(
            Window.Hint.FULL_SCREEN, Window.Hint.NO_DECORATIONS, Window.Hint.NO_POST_RENDERING));
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

    mainPanel = new Panel(new BorderLayout());
    mainPanel.setSize(terminal.getTerminalSize());
    mainPanel.setTheme(
        new SimpleTheme(new TextColor.RGB(255, 255, 255), new TextColor.RGB(139, 69, 19)));
    final Canvas canvas = new Canvas();
    canvas.setLayoutData(BorderLayout.Location.CENTER);
    final var canvasPanel = new Panel(new LinearLayout().setSpacing(0));
    canvasPanel.addComponent(canvas);
    canvasPanel.setTheme(
        new SimpleTheme(new TextColor.RGB(255, 255, 255), new TextColor.RGB(0, 0, 0)));
    final var canvasBorder = Borders.singleLine("World");
    canvasBorder.setComponent(canvasPanel);
    mainPanel.addComponent(canvasBorder);

    final var rightPanel = new Panel(new LinearLayout(Direction.VERTICAL));
    rightPanel.addComponent(new Label("Stat 1: 42"));
    rightPanel.addComponent(new Label("Stat 2: 1337"));
    rightPanel.setPreferredSize(new TerminalSize(20, terminal.getTerminalSize().getRows()));
    rightPanel.setLayoutData(BorderLayout.Location.RIGHT);
    rightPanel.setTheme(
        new SimpleTheme(new TextColor.RGB(0, 0, 0), new TextColor.RGB(139, 69, 19)));
    final var rightBorder = Borders.singleLine("Stats");
    rightBorder.setComponent(rightPanel);
    mainPanel.addComponent(rightBorder);

    final var bottomPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
    bottomPanel.addComponent(new Label("Action 1"));
    bottomPanel.addComponent(new Label("Action 2"));
    bottomPanel.setPreferredSize(new TerminalSize(terminal.getTerminalSize().getColumns(), 1));
    bottomPanel.setLayoutData(BorderLayout.Location.BOTTOM);
    bottomPanel.setTheme(
        new SimpleTheme(new TextColor.RGB(0, 0, 0), new TextColor.RGB(139, 69, 19)));
    final var bottomBorder = Borders.singleLine("Actions");
    bottomBorder.setComponent(bottomPanel);
    mainPanel.addComponent(bottomBorder);

    window.setComponent(mainPanel);
    return canvas;
  }

  public Canvas init() {
    try {
      return initWindow();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void addEngine(final Engine engine) {
    engineStack.add(engine);
  }

  public void run() {
    textGUI.addWindow(window); // this is blocking
    textGUI.waitForWindowToClose(window);
  }

  @Override
  public void showDialog(final String title, final String text) {
    new MessageDialogBuilder().setTitle(title).setText(text).build().showDialog(textGUI);
  }
}

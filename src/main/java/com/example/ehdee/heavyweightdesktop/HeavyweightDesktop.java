package com.example.ehdee.heavyweightdesktop;

import com.example.ehdee.heavyweightdesktop.client.HeavyweightClient;
import com.example.ehdee.heavyweightdesktop.client.HeavyweightClientImpl;
import com.example.ehdee.heavyweightdesktop.config.HeavyweightDesktopConfig;
import com.example.ehdee.heavyweightdesktop.gui.GUIComponentFactoryImpl;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.regex.Pattern;

public class HeavyweightDesktop {
    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(HeavyweightDesktopConfig.class);
        final GUIComponentFactoryImpl g = ctx.getBean(GUIComponentFactoryImpl.class);
        final HeavyweightClient c = ctx.getBean(HeavyweightClientImpl.class);

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        final Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        final Panel results = new Panel();
        results.addComponent(g.buildResultsPanel());

        final Panel controls = new Panel();
        controls.setLayoutManager(new GridLayout(3).setVerticalSpacing(1));

        controls.addComponent(new Label("date"));
        final TextBox dateEntry = new TextBox().setValidationPattern(Pattern.compile("[0-9\\-]*")).addTo(controls);


        new Button("Get reigns", new Runnable() {
            @Override
            public void run() {
                results.removeAllComponents();
                results.addComponent(g.buildResultsPanel(c.getReignsByDate(dateEntry.getText())));
            }
        }).addTo(controls);

        final WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);

        new Button("Set port", new Runnable() {
            @Override
            public void run() {
                c.setPort(new TextInputDialogBuilder()
                        .setTitle("Port")
                        .setDescription("0 - whatever")
                        .setValidationPattern(Pattern.compile("[0-9]*"), "Enter a port number.")
                        .setInitialContent(c.getPort())
                        .build()
                        .showDialog(textGUI));
            }
        }).addTo(controls);

        new Button("Set IP", new Runnable() {
            @Override
            public void run() {
                c.setHost(new TextInputDialogBuilder()
                        .setTitle("IP")
                        .setDescription("127.0.0.1 is your localhost")
                        .setValidationPattern(Pattern.compile("[0-9\\.]*"), "Enter an IP.")
                        .setInitialContent(c.getHost())
                        .build()
                        .showDialog(textGUI));
            }
        }).addTo(controls);


        mainPanel.addComponent(controls);
        mainPanel.addComponent(results);

        BasicWindow window = new BasicWindow();
        window.setComponent(mainPanel);

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }
}

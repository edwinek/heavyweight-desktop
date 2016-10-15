package com.example.ehdee.heavyweightdesktop.gui;

import com.example.ehdee.heavyweightdesktop.model.HeavyweightResponse;
import com.example.ehdee.heavyweightdesktop.model.Reign;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class GUIComponentFactoryImpl {

    public Panel buildResultsPanel() {

        Panel resultsPanel = new Panel();
        resultsPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel tablePanel = new Panel();
        tablePanel.setPreferredSize(new TerminalSize(80, 10));

        Table<String> table = new Table<>("Champion", "Country", "Representing", "Reign begun", "Reign ended");
        table.getTableModel().addRow("<none>", "<none>", "<none>", "<none>", "<none>");

        tablePanel.addComponent(table);

        Panel messagePanel = new Panel();
        messagePanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        messagePanel.addComponent(new Label("message: "));
        messagePanel.addComponent(new Label("no results to show"));

        resultsPanel.addComponent(tablePanel);
        resultsPanel.addComponent(messagePanel);

        return resultsPanel;

    }

    public Panel buildResultsPanel(HeavyweightResponse heavyweightResponse) {

        Panel resultsPanel = new Panel();
        resultsPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel tablePanel = new Panel();
        tablePanel.setPreferredSize(new TerminalSize(80, 10));

        Table<String> table = new Table<>("Champion", "Country", "Representing", "Reign began", "Reign ended");


        if (heavyweightResponse.getReigns() != null && heavyweightResponse.getReigns().size() > 0) {
            for (Reign reign : heavyweightResponse.getReigns()) {
                String reignBegan = ISODateTimeFormat.date().print(new DateTime(reign.getReignBegan()));
                String reignEnded;
                if (StringUtils.isEmpty(reign.getReignEnded())) {
                    reignEnded = "N/A";
                } else {
                    reignEnded = ISODateTimeFormat.date().print(new DateTime(reign.getReignEnded()));
                }
                table.getTableModel().addRow(reign.getChampion(), reign.getNationality(), reign.getRecognition(), reignBegan, reignEnded);
            }
        } else {
            table.getTableModel().addRow("<none>", "<none>", "<none>", "<none>", "<none>");
        }

        tablePanel.addComponent(table);

        Panel messagePanel = new Panel();
        messagePanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        messagePanel.addComponent(new Label("message: "));
        if (!StringUtils.isEmpty(heavyweightResponse.getError())) {
            messagePanel.addComponent(new Label(heavyweightResponse.getError()));
        } else {
            messagePanel.addComponent(new Label(heavyweightResponse.getReigns().size() + " records returned."));
        }


        resultsPanel.addComponent(tablePanel);
        resultsPanel.addComponent(messagePanel);

        return resultsPanel;

    }


}

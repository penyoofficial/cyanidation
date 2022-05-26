// Copyright (c) Penyo. All rights reserved.

package pers.penyo.cyanidation;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class GUI {
    Core c = new Core();

    public void boot() {
        init();
        setLayout();
        actionRegist();
    }

    Frame frame = new Frame("Cyanidation");
    Panel csEntireBody = new Panel(null),
            xAxis = new Panel(new GridLayout(1, 7, 0, 0)),
            yAxis = new Panel(new GridLayout(5, 1, 0, 0)),
            csInfoBody = new Panel(new GridLayout(5, 7, 0, 0)),
            bottomBar = new Panel(new GridLayout(1, 3, 0, 0)),
            bottomCtrl = new Panel();
    Label weekNow = new Label("  Week " + (c.getWeek() + c.weekSkewing)),
            sentence = new Label(c.getSentence() + "  ", Label.RIGHT);
    Button lastWeek = new Button("Last Week"),
            reflash = new Button("Reflash"),
            nextWeek = new Button("Next Week");
    MenuBar topBar = new MenuBar();
    Menu file = new Menu("File"),
            help = new Menu("Help");
    MenuItem newCS = new MenuItem("New a CS", new MenuShortcut(KeyEvent.VK_N, false)),
            openStandardCS = new MenuItem("Open a CS", new MenuShortcut(KeyEvent.VK_O, false)),
            getSRC = new MenuItem("Get source code"),
            about = new MenuItem("About");
    Dialog newCSguide = new Dialog(frame, "New your own CS", false);
    FileDialog open = new FileDialog(frame, "Open your CS file", FileDialog.LOAD),
            saveAs = new FileDialog(frame, "Save your CS file", FileDialog.SAVE);

    Label upGuide = new Label("Please type the CS command as the given format below: ");
    TextArea typeArea = new TextArea("""
            Title@TermBeginningDate
            $Subject1@Teacher1
            DayOfWeek, PhaseOfDay - WeekBeginning, WeekEnd@Classroom1
            DOW, POD - WB, WE@C2
            DOW, POD - WB, WE@C3
            $S2@T2
            DOW, POD - WB, WE@C4
            DOW, POD - WB, WE@C5
            DOW, POD - WB, WE@C6
            $S3@T3
            DOW, POD - WB, WE@C7
            DOW, POD - WB, WE@C8
            DOW, POD - WB, WE@C9""");
    Button saveCSas = new Button("Save the CS as...");

    String path = null;

    public void init() {
        xAxis.add(new Label("Mon", Label.CENTER));
        xAxis.add(new Label("Tue", Label.CENTER));
        xAxis.add(new Label("Wes", Label.CENTER));
        xAxis.add(new Label("Thu", Label.CENTER));
        xAxis.add(new Label("Fri", Label.CENTER));
        xAxis.add(new Label("Sat", Label.CENTER));
        xAxis.add(new Label("Sun", Label.CENTER));

        yAxis.add(new Label("1", Label.CENTER));
        yAxis.add(new Label("2", Label.CENTER));
        yAxis.add(new Label("3", Label.CENTER));
        yAxis.add(new Label("4", Label.CENTER));
        yAxis.add(new Label("5", Label.CENTER));

        bottomCtrl.add(lastWeek);
        bottomCtrl.add(reflash);
        bottomCtrl.add(nextWeek);

        bottomBar.add(weekNow);
        bottomBar.add(bottomCtrl);
        bottomBar.add(sentence);

        reflash(null);

        csEntireBody.add(xAxis);
        csEntireBody.add(yAxis);
        csEntireBody.add(csInfoBody);

        frame.add(csEntireBody);
        frame.add(bottomBar, BorderLayout.SOUTH);

        file.add(newCS);
        file.add(openStandardCS);
        help.add(getSRC);
        help.add(about);
        topBar.add(file);
        topBar.add(help);
        frame.setMenuBar(topBar);

        newCSguide.add(upGuide, BorderLayout.NORTH);
        newCSguide.add(typeArea);
        newCSguide.add(saveCSas, BorderLayout.SOUTH);
    }

    public void setLayout() {
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setVisible(true);

        int edge = 10, axisShortEdge = 30, width = csEntireBody.getWidth(), height = csEntireBody.getHeight();
        xAxis.setBounds(edge + axisShortEdge, edge, width - edge * 2 - axisShortEdge, axisShortEdge);
        yAxis.setBounds(edge, edge + axisShortEdge, axisShortEdge, height - edge * 2 - axisShortEdge);
        csInfoBody.setBounds(edge + axisShortEdge, edge + axisShortEdge, width - edge * 2 - axisShortEdge,
                height - edge * 2 - axisShortEdge);

        csEntireBody.setBackground(Color.LIGHT_GRAY);
        bottomBar.setBackground(Color.GRAY);

        newCSguide.setSize(360, 640);
        newCSguide.setResizable(false);
    }

    public void actionRegist() {
        newCS.addActionListener(e -> newCSguide.setVisible(true));
        openStandardCS.addActionListener(e -> {
            open.setVisible(true);
            if (open.getFile() != null) {
                path = open.getDirectory() + open.getFile();
                reflash(path);
            }
        });
        lastWeek.addActionListener(e -> {
            if (c.getWeek() + c.weekSkewing > 1)
                c.weekSkewing--;
            reflash(path);
            weekNow.setText("  Week " + (c.getWeek() + c.weekSkewing));
        });
        reflash.addActionListener(e -> {
            c.weekSkewing = 0;
            reflash(path);
            weekNow.setText("  Week " + (c.getWeek() + c.weekSkewing));
        });
        nextWeek.addActionListener(e -> {
            if (c.getWeek() + c.weekSkewing < c.weekLimit)
                c.weekSkewing++;
            reflash(path);
            weekNow.setText("  Week " + (c.getWeek() + c.weekSkewing));
        });
        getSRC.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/penyoofficial/cyanidation"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        about.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/penyoofficial"));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        saveCSas.addActionListener(e -> {
            saveAs.setVisible(true);
            c.save(saveAs.getDirectory() + saveAs.getFile(), typeArea.getText());
            newCSguide.setVisible(false);
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        newCSguide.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                newCSguide.setVisible(false);
            }
        });
    }

    public void reflash(String path) {
        csInfoBody.removeAll();
        String[] standardCS = c.reflash(path);
        frame.setTitle("Cyanidation - " + c.csGist[0]);
        for (int i = 0; i < 35; i++) {
            TextArea classUnit = new TextArea(standardCS[i], 0, 0, TextArea.SCROLLBARS_NONE);
            classUnit.setEditable(false);
            csInfoBody.add(classUnit);
        }
        frame.setVisible(false);
        frame.setVisible(true);
    }
}

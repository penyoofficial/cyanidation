package pers.penyo.cyanidation;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.Arrays;

public class GUI {
    Core c = new Core();

    public void boot() {
        c.reflash();
        init();
        setLayout();
        actionRegist();
    }

    Frame frame = new Frame("Cyanidation");
    Panel csEntireBody = new Panel(null), xAxis = new Panel(new GridLayout(1, 7, 0, 0)),
            yAxis = new Panel(new GridLayout(5, 1, 0, 0)), csInfoBody = new Panel(new GridLayout(5, 7, 0, 0)),
            bottomBar = new Panel(new GridLayout(1, 3, 0, 0)), bottomCtrl = new Panel();
    Label dateNow = new Label("  Week " + c.getWeek() + ", " + c.getWeekDate()),
            sentence = new Label(c.getSentence() + "  ", Label.RIGHT);
    Button lastWeek = new Button("Last Week"), reflash = new Button("Reflash"), nextWeek = new Button("Next Week");
    MenuBar topBar = new MenuBar();
    Menu file = new Menu("File"), help = new Menu("Help");
    MenuItem newCS = new MenuItem("New a CS", new MenuShortcut(KeyEvent.VK_N, false)),
            openCS = new MenuItem("Open an standard CS", new MenuShortcut(KeyEvent.VK_O, false)),
            saveCS = new MenuItem("Save the CS", new MenuShortcut(KeyEvent.VK_S, false)),
            saveCSas = new MenuItem("Save the CS as...", new MenuShortcut(KeyEvent.VK_S, true)),
            getHelp = new MenuItem("Get official support"), about = new MenuItem("About");

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

        reflash();

        for (Button button : Arrays.asList(lastWeek, reflash, nextWeek)) {
            bottomCtrl.add(button);
        }

        bottomBar.add(dateNow);
        bottomBar.add(bottomCtrl);
        bottomBar.add(sentence);

        csEntireBody.add(xAxis);
        csEntireBody.add(yAxis);
        csEntireBody.add(csInfoBody);

        frame.add(csEntireBody);
        frame.add(bottomBar, BorderLayout.SOUTH);

        file.add(newCS);
        file.add(openCS);
        file.add(saveCS);
        file.add(saveCSas);
        help.add(getHelp);
        help.add(about);
        topBar.add(file);
        topBar.add(help);
        frame.setMenuBar(topBar);
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
    }

    public void actionRegist() {
        lastWeek.addActionListener(e -> {
            if (c.weekSkewing > 0)
                c.weekSkewing--;
            dateNow = new Label("  Week " + (c.getWeek() + c.weekSkewing));
            reflash();
        });
        reflash.addActionListener(e -> {
            c.weekSkewing = 0;
            reflash();
            dateNow = new Label("  Week " + c.getWeek() + ", " + c.getWeekDate());
        });
        nextWeek.addActionListener(e -> {
            if (c.weekSkewing < c.weekLimit)
                c.weekSkewing++;
            dateNow = new Label("  Week " + (c.getWeek() + c.weekSkewing));
            reflash();
        });
        getHelp.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI(
                        "https://qm.qq.com/cgi-bin/qm/qr?k=1NYJ9kl-yoTJVjBa3e1PgUrqA_flIdtW&authKey=yUZJhaqtkZCmyhJCA2qvLAJASlYgLcPEd3bSJO20BDERXrCmPfGG%2BVmXppSGKHF7&noverify=0&group_code=769409099"));
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
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void reflash() {
        csInfoBody = new Panel(new GridLayout(5, 7, 0, 0));
        String[] standardCS = c.reflash();
        frame.setTitle("Cyanidation - " + c.csGist[0]);
        for (int i = 0; i < 35; i++) {
            TextArea classUnit = new TextArea(standardCS[i], 0, 0, TextArea.SCROLLBARS_NONE);
            classUnit.setEditable(false);
            csInfoBody.add(classUnit);
        }
    }
}

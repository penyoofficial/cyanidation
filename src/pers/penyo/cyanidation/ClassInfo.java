package pers.penyo.cyanidation;

import java.util.ArrayList;

public class ClassInfo {
    String[] gist;
    ArrayList<TimeMoph> timeMophPack = new ArrayList<>();

    public class TimeMoph {
        int weekBegin, weekEnd, dayOfWeek, phaseOfDay;
        String spInfo;

        public TimeMoph(String infoCut) {
            String[] info = infoCut.split("@");
            dayOfWeek = info[0].charAt(0) - '0';
            phaseOfDay = info[0].charAt(3) - '0';
            weekBegin = Integer.parseInt(info[0].substring(7, 9));
            weekEnd = Integer.parseInt(info[0].substring(11, 13));
            try {
                spInfo = info[1];
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ClassInfo(String entireInfo) {
        String[] infoCut = entireInfo.split("\n");
        gist = infoCut[0].split("@");
        for (int i = 1; i < infoCut.length; i++)
            timeMophPack.add(new TimeMoph(infoCut[i]));
    }
}

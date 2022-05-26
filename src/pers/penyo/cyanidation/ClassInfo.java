package pers.penyo.cyanidation;

import java.util.ArrayList;

public class ClassInfo {
    String[] gist;
    ArrayList<TimeMoph> timeMophPack = new ArrayList<>();

    public static class TimeMoph {
        int weekBegin, weekEnd, dayOfWeek, phaseOfDay;

        public TimeMoph(String info) {
            dayOfWeek = info.charAt(0) - '0';
            phaseOfDay = info.charAt(3) - '0';
            weekBegin = Integer.parseInt(info.substring(7, 8));
            weekEnd = Integer.parseInt(info.substring(11, 12));
        }
    }

    public ClassInfo(String entireInfo) {
        String[] infoCut = entireInfo.split("\n");
        gist = infoCut[0].split("@");
        for (int i = 1; i < infoCut.length; i++)
            timeMophPack.add(new TimeMoph(infoCut[i]));
    }
}

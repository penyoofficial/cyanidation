package pers.penyo.cyanidation;

import java.io.InputStream;
import java.util.*;
import java.text.*;

public class Core {
    String[] csGist = new String[2];
    ArrayList<ClassInfo> cs = new ArrayList<>();
    int weekSkewing = 0, weekLimit = 20;
    boolean fromCore = false;

    public String[] reflash() {
        try {
            InputStream ioCS = Core.class.getResourceAsStream("cyanidation.pdp");
            return parse(ioCS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[35];
    }

    public String[] parse(InputStream ioCS) {
        try {
            byte[] parsedCS_I = new byte[ioCS.available()];
            ioCS.read(parsedCS_I);
            String[] parsedCS_II = new String(parsedCS_I).split("\\$");
            csGist = parsedCS_II[0].split("@");

            if (fromCore) {
                fromCore = false;
                return new String[35];
            }

            cs = new ArrayList<>();
            for (int i = 1; i < parsedCS_II.length; i++)
                cs.add(new ClassInfo(parsedCS_II[i]));
            String[] result = new String[35];
            int i = getWeek() + weekSkewing;
            for (ClassInfo thisCS : cs)
                for (ClassInfo.TimeMoph tm : thisCS.timeMophPack)
                    if (tm.weekBegin <= i && tm.weekEnd >= i)
                        result[(tm.phaseOfDay - 1) * 7 + (tm.dayOfWeek - 1)] = "" + thisCS.gist[0] + " / " + tm.spInfo
                                + "\n" + thisCS.gist[1];
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[35];
    }

    public int getWeek() {
        try {
            fromCore = true;
            reflash();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(csGist[1]), now = new Date();
            return (int) ((now.getTime() - start.getTime()) / 1000.0 / 60 / 60 / 24 / 7) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getWeekDate() {
        Calendar c = Calendar.getInstance();
        return switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1 -> "Sunday";
            case 2 -> "Monday";
            case 3 -> "Tuesday";
            case 4 -> "Wednesday";
            case 5 -> "Thursday";
            case 6 -> "Friday";
            case 7 -> "Saturday";
            default -> "Apresunday";
        };
    }

    public String getSentence() {
        int rd = (int) (Math.random() * 114514);
        return switch (rd % 7) {
            case 0 -> "I'll stand on my own two feet.";
            case 1 -> "Genius is an infinite capacity for taking pains.";
            case 2 -> "Fading is true while flowering is past.";
            case 3 -> "All or nothing, now or never.";
            case 4 -> "Every man is a poet when he is in love.";
            case 5 -> "Life is a journey, not the destination, but the scenery along.";
            case 6 -> "Truth needs no color.";
            default -> "One for all, all for one.";
        };
    }
}
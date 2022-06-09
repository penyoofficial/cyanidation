// Copyright (c) Penyo. All rights reserved.

package pers.penyo.cyanidation;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Core {
    String[] csGist = new String[2];
    ArrayList<ClassInfo> cs = new ArrayList<>();
    int weekSkewing = 0, weekLimit = 20;
    boolean fromCore = false;

    public String[] reflash(String path) {
        try {
            InputStream ioCS = path == null ?
                    Core.class.getResourceAsStream("default.pcs") : new FileInputStream(path);
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

    public void save(String path, String contain) {
        try {
            OutputStream f = new FileOutputStream(path);
            f.write(contain.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getWeek() {
        try {
            fromCore = true;
            reflash(null);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(csGist[1]), now = new Date();
            return (int) ((now.getTime() - start.getTime()) / 1000.0 / 60 / 60 / 24 / 7) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getSentence() {
        int rd = (int) (Math.random() * 114514);
        return switch (rd % 7) {
            case 0 -> "要在文化上有成绩，则非韧不可。 ——鲁迅";
            case 1 -> "倘只看书，便变成书橱。 ——鲁迅";
            case 2 -> "但我坦然，欣然，我将大笑，我将歌唱。 ——鲁迅";
            case 3 -> "改造自己，总比禁止别人来的难。 ——鲁迅";
            case 4 -> "心事浩茫连广宇，于无声处听惊雷。 ——鲁迅";
            case 5 -> "自由固不是钱所买到的，但能够为钱而卖掉。 ——鲁迅";
            case 6 -> "人生得一知已足矣，斯世当以同怀视之。 ——鲁迅";
            default -> "丢掉幻想，准备斗争。 ——毛泽东";
        };
    }
}
package pers.penyo.cyanidation;

public class ClassInfo {
    String name;
    int[] datetime = new int[2],
            weekRange = new int[2];

    public ClassInfo(String info) {
        datetime[0] = info.charAt(0) - '0';
        datetime[1] = info.charAt(3) - '0';
        weekRange[0] = Integer.parseInt(info.substring(7, 8));
        weekRange[1] = Integer.parseInt(info.substring(11, 12));
    }
}

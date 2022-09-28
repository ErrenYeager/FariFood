package ir.ac.kntu.helperclasses;

public class Time {
    private int hour;
    private int minute;

    public Time() {
    }

    public Time(int hour, int minute) {
        setHour(hour);
        setMinute(minute);
    }

    public void scanAndSetTime() {
        int scannedHour;
        int scannedMinute;
        System.out.print("Hour: ");
        scannedHour = MyScanner.getInstance().nextInt();
        System.out.print("Minute: ");
        scannedMinute = MyScanner.getInstance().nextInt();
        while (true) {
            if(scannedHour >= 24 || scannedHour < 0) {
                System.out.println("The hour you set is invalid!\nSet hour again: ");
                scannedHour = MyScanner.getInstance().nextInt();
            } else if(scannedMinute > 59 || scannedMinute < 0) {
                System.out.print("The minute you set is invalid!\nSet minute again: ");
                scannedMinute = MyScanner.getInstance().nextInt();
            } else {
                break;
            }
        }
        setHour(scannedHour);
        setMinute(scannedMinute);
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        if(hour >= 24) {
            hour -= 24;
        }
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        if(minute > 59) {
            minute = 0;
        }
        this.minute = minute;
    }

    @Override
    public String toString() {
        return hour + " : " + minute;
    }
}

import java.util.concurrent.Semaphore;

public class Alarm {
    private int hours, minutes, seconds;
    private boolean on;
    private Semaphore mutex;

    public Alarm(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.on = false;
        mutex = new Semaphore(1);
    }

    public void setAlarm(int hours, int minutes, int seconds) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        mutex.release();
    }

    public int getTimeAsSecs() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int tas = hours*3600 + minutes*60 + seconds;
        mutex.release();
        return tas;
    }

    public void toggle() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        on = !on;
        mutex.release();
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean isOn() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean o = on;
        mutex.release();
        return o;
    }

    public void setOn(boolean on) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.on = on;
        mutex.release();
    }
}

import java.util.concurrent.Semaphore;

public class ClockData {
    private int hours, minutes, seconds;
    private Semaphore mutex;

    public ClockData(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        mutex = new Semaphore(1);
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

    public void setTime(int hours, int minutes, int seconds) {
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

    public void increment() {
        try {
            mutex.acquire();
            seconds++;
            if (seconds > 59) {
                minutes++;
                if (minutes > 59) {
                    hours++;
                    if (hours > 23)
                        hours = 0;
                    minutes = 0;
                }
                seconds = 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mutex.release();
    }

}

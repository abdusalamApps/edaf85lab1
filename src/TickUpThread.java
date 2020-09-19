import clock.io.ClockOutput;

import java.util.concurrent.Semaphore;

public class TickUpThread extends Thread {
    private ClockData clock;
    private ClockOutput output;
    private Alarm alarm;
    private Semaphore alarmSemaphore;

    public TickUpThread(ClockData clock, Alarm alarm, Semaphore alarmSemaphore, ClockOutput output) {
        super();
        this.clock = clock;
        this.output = output;
        this.alarm = alarm;
        this.alarmSemaphore = alarmSemaphore;
    }

    @Override
    public void run() {
        super.run();
        long t, diff;
        t = System.currentTimeMillis();
        while (true) {
            try {
                clock.increment();
                int timeAsSeconds = clock.getTimeAsSecs();
                int hours = timeAsSeconds/3600;
                timeAsSeconds = timeAsSeconds%3600;
                int minutes = timeAsSeconds / 60;
                timeAsSeconds = timeAsSeconds%60;
                int seconds =  timeAsSeconds;
                output.displayTime(hours, minutes, seconds);
                if (alarm.getTimeAsSecs() == clock.getTimeAsSecs() & alarm.isOn()) {
                    alarmSemaphore.release();
                }
                t += 1000;
                diff = t - System.currentTimeMillis();
                if (diff > 0) sleep(diff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

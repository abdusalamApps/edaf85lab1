import clock.io.ClockOutput;

import java.util.concurrent.Semaphore;

public class AlarmThread extends Thread {
    private Semaphore alarmSemaphore;
    private ClockOutput output;
    private Alarm alarm;

    public AlarmThread(Semaphore alarmSemaphore, ClockOutput output, Alarm alarm) {
        super();
        this.alarmSemaphore = alarmSemaphore;
        this.output = output;
        this.alarm = alarm;
    }

    @Override
    public void run() {
        super.run();
        try {
            alarmSemaphore.acquire();
            int i = 0;
            while (alarm.isOn() && i < 20) {
                output.alarm();
                i++;
                sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

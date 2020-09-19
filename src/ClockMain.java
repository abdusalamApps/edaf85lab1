import clock.AlarmClockEmulator;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;

import java.util.concurrent.Semaphore;

public class ClockMain {

    public static void main(String[] args) throws InterruptedException {
        AlarmClockEmulator emulator = new AlarmClockEmulator();

        ClockInput in = emulator.getInput();
        ClockOutput out = emulator.getOutput();
        ClockData clock = new ClockData(1, 0, 0);
        Alarm alarm = new Alarm(1, 0, 10);
        Semaphore alarmSemaphore = new Semaphore(0);
        TickUpThread tickUpThread = new TickUpThread(clock, alarm, alarmSemaphore, out);
        AlarmThread alarmThread = new AlarmThread(alarmSemaphore, out, alarm);
        alarmThread.start();
        tickUpThread.start();


        while (true) {
            in.getSemaphore().acquire();
            UserInput userInput = in.getUserInput();
            int choice = userInput.getChoice();
            int h = userInput.getHours();
            int m = userInput.getMinutes();
            int s = userInput.getSeconds();
            switch (choice) {
                case ClockInput.CHOICE_SET_TIME:
                    clock.setTime(h, m, s);
                    alarm.setOn(false);
                    out.setAlarmIndicator(false);
                    break;
                case ClockInput.CHOICE_SET_ALARM:
                    alarm.setAlarm(h, m, s);
                    alarm.setOn(false);
                    out.setAlarmIndicator(false);
                    break;
                case ClockInput.CHOICE_TOGGLE_ALARM:
                    out.setAlarmIndicator(!alarm.isOn());
                    alarm.toggle();
                    break;
            }

            System.out.println("choice = " + choice + " and h = " + h + " m = " + m + " s = " + s);
        }
    }
}

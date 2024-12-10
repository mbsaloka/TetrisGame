package utility;

import java.util.Timer;
import java.util.TimerTask;

public class Time {
	private int currentTime;
	private int second;
	private int milisecond;

	public Time() {
		currentTime = 0;
		second = 0;
	}

	public void start() {
		Timer t = new Timer();
		t.scheduleAtFixedRate(
			new TimerTask() {
				public void run() {
					++currentTime;
					milisecond = currentTime % 100;
					if (currentTime % 100 == 0) {
						second = currentTime / 100;
					}
				}
			}, 0, 10);
	}

	public int getMilisecond() {
		return milisecond;
	}

	public int getSecond() {
		return second;
	}
}

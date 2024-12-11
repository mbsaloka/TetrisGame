package audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Objects;

public class AudioPlayer {
	private Clip clip;
	private int pausePosition;

	public AudioPlayer(String path) {
		try {
			AudioInputStream ais =
				AudioSystem.getAudioInputStream(
					Objects.requireNonNull(
						getClass().getResourceAsStream(path)
					)
				);
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais =
				AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if (clip == null) return;

		stop();
		pausePosition = 0;
		clip.setFramePosition(pausePosition);
		clip.start();
	}

	public void pause() {
		if (clip != null && clip.isRunning()) {
			pausePosition = clip.getFramePosition();
			clip.stop();
		}
	}

	public void resume() {
		if (clip != null && !clip.isRunning()) {
			clip.setFramePosition(pausePosition);
			clip.start();
		}
	}

	public void stop() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
		}
		pausePosition = 0;
	}

	public void close() {
		stop();
		if (clip != null) {
			clip.close();
		}
	}
}

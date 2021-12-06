package engine;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * ^^
 * Sound control SFX & BGM
 */
public class Sound {
    File file;
    Clip clip;

    /**
     * ^^
     *
     * @param path filepath
     */
    public Sound(String path) {
        file = new File(path);
    }

    public void setFile(File file) {
        this.file = file;
    }


    /**
     * ^^
     * infinite loop for music ! like bgm
     */
    public void playSoundLoop(int mode) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(this.file));
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (mode == 0) {
                return;
            } else if (mode == 1) {
                gainControl.setValue(-10.0f);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else if (mode == 2) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

        } catch (Exception e) {
            System.err.println("Put the music.wav file in the sound folder if you want to play background music, only optional!");
        }

    }


    /**
     * ^^
     * music play just once
     */
    public void playOnce(int mode) {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(this.file));
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (mode == 0) {
                return;
            } else if (mode == 1) {
                gainControl.setValue(-10.0f);
                clip.start();
            } else if (mode == 2) {
                clip.start();
            }
        } catch (Exception e) {
            System.err.println("Put the music.wav file in the sound folder if you want to play background music, only optional!");
        }
    }


    /**
     * ^^
     * music stop like change screen
     */
    public void pause() {
        clip.stop();
        clip.close();
    }

}

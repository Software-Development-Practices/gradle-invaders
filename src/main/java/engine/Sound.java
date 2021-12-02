package engine;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
// created by chanhyuk-park
// 2021-11-15
// thank you for all contributions^^

/**
 * ^^
 * Sound control SFX & BGM
 */
public class Sound {
    File file;
    Clip clip;

    /**
     * ^^
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
    public void playSoundLoop() {
        try{
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(this.file));
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            //Thread.sleep(clip.getMicrosecondLength()/1000);
        }
        catch(Exception e)
        {
            System.err.println("Put the music.wav file in the sound folder if you want to play background music, only optional!");
        }

    }


    /**
     * ^^
     * music play just once
     */
    public void playOnce(){
        try{
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(this.file));
            clip.start();

            //Thread.sleep(clip.getMicrosecondLength()/1000);
        }
        catch(Exception e) {
            System.err.println("Put the music.wav file in the sound folder if you want to play background music, only optional!");
        }
    }


    /**
     * ^^
     * music stop like change screen
     */
    public void pause(){
        clip.stop();
        clip.close();
    }

}

package testSoundPackage;

import engine.Sound;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screen.Screen;
import screen.TitleScreen;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class MusicTest {

    @Before
    public void setup() {
        //given
        System.out.println("before");
    }

    @DisplayName("음악 볼륨이 제대로 작동하는지 테스트")
    @Test
    public void testVolume() throws InterruptedException, LineUnavailableException, UnsupportedAudioFileException, IOException {
        Clip clip = AudioSystem.getClip();
        File file = new File("./src/main/resources/music/scoreScreenBgm.wav");
        clip.open(AudioSystem.getAudioInputStream(file));

        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        Sound backGroundMusic;
        int musicVolume = 2;
        backGroundMusic = new Sound("./src/main/resources/music/mainBgm.wav");
        backGroundMusic.playSoundLoop(musicVolume);
        assertTrue(backGroundMusic.clip.isActive(), "false");
    }

    @DisplayName("멈췄을 때 음악도 멈추는지 테스트")
    @Test
    public void testPause() {
        Sound backGroundMusic;
        int musicVolume = 2;
        backGroundMusic = new Sound("./src/main/resources/music/mainBgm.wav");
        backGroundMusic.playSoundLoop(musicVolume);
        assertTrue(backGroundMusic.clip.isOpen());

        backGroundMusic.pause();
        assertFalse(backGroundMusic.clip.isOpen());

    }

    @After
    public void teardown() {
        System.out.println("teardown");
    }
}

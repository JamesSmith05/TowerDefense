package logic;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){

        soundURL[0] = getClass().getResource("/resources/sound/8bitMusic.wav");
        soundURL[1] = getClass().getResource("/resources/sound/coin.wav");
        soundURL[2] = getClass().getResource("/resources/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/resources/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/resources/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/resources/sound/hitmonster.wav");
        soundURL[6] = getClass().getResource("/resources/sound/receivedamage.wav");
        soundURL[7] = getClass().getResource("/resources/sound/fireball.wav");
        soundURL[8] = getClass().getResource("/resources/sound/levelup.wav");
        soundURL[9] = getClass().getResource("/resources/sound/cursor.wav");
    }

    public void setFile(int i){

        try{

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch(Exception e){}

    }
    public void play(){

        try {
            clip.start();
        } catch (Exception e) {

        }


    }
    public void loop(){

        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {

        }

    }
    public void stop() {

        try {
            clip.stop();

        }catch (Exception e) {

        }
    }
}

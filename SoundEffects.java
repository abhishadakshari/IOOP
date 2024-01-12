package openworld;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class SoundEffects {

    private final String filepath;

    public SoundEffects(String filepath){

        this.filepath = filepath;

    }


    public void playSound() throws Exception{

        File soundFile = new File(this.filepath);
        AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);

        // load the sound into memory (a Clip)
        DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(sound);

        clip.addLineListener(new LineListener() {
            public void update(LineEvent event) {
                if (event.getType() == LineEvent.Type.STOP) {
                    event.getLine().close();
                }
            }
        });

        clip.start();

    }
}

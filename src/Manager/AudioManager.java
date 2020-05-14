package Manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioManager {
    
    private volatile static List<Clip> clips;
    
    public static int PLAYER_SHOOT = 0;
    public static int ENEMY_SHOOT = 1;
    public static int ENEMY_DEFEAT = 2;
    public static int ENEMY_XPLODE = 3;
    public static int BULLET_HIT = 4;
    public static int MUSIC = 5;
    public static int MUSIC_FINAL = 6;
    
    public boolean sfx_enable;
    public boolean music_enable;
    
    private static AudioManager instance;
    
    private AudioManager(){
        clips = new ArrayList<>();
        load("SFX/playershot.wav");
        load("SFX/enemyshotedited.mp3");
        load("SFX/enemydefeated1.mp3");
        load("SFX/enemyexplode1.mp3");
        load("SFX/hit.wav");
        load("Music/assault.mp3");
        load("Music/bossfight.mp3");
    }
    
    public static AudioManager getInstance(){
        if (instance == null){
            instance = new AudioManager();
        }
        return instance;
    }

    private void load(String s){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(AudioManager.class.getClassLoader().getResourceAsStream(s));
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
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            Clip clip = AudioSystem.getClip();
            clip.open(dais);
            clips.add(clip);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("AudioManager error: " + e);
        }
    }
    
    public void volume(int n, float decibels){
        FloatControl gainControl = 
        (FloatControl) clips.get(n).getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(decibels);
    }
    
    public void play(int n) {
        if(clips.get(n) == null) return;
//        if (!sfx_enable) return;
        stop(n);
        clips.get(n).setFramePosition(0);
        clips.get(n).start();
    }
    
    public void stop(int n) {
        if(clips.get(n).isRunning()) clips.get(n).stop();
    }
    
    public void close(int n) {
        stop(n);
        clips.get(n).close();
    }
    
    public void closeAll(){
        clips.forEach((clip)->{
            clip.close();
        });
    }
    
    public void loop(int n, int frame, int start, int end) {
        Clip c = clips.get(n);
        if(c == null) return;
//        if (!music_enable) return;
        if(c.isRunning()) c.stop();
        c.setLoopPoints(start, end);
        c.setFramePosition(frame);
        c.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public int getFrames(int n){
        return clips.get(n).getFrameLength();
    }
}
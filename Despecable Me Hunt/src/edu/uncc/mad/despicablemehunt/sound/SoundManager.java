/**
 * Homework #3
 * @author Ankur Huralikoppi, Vishwas Subramanian
 * 
 * NOTE: Please enable sounds on the emulator while testing the application.
 * 
 */

package edu.uncc.mad.despicablemehunt.sound;

import java.util.Random;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import edu.uncc.mad.despicablemehunt.R;

public class SoundManager {


	private static SoundManager soundManagerInstance;

	private SoundPool soundManager;
	private MediaPlayer mediaPlayer;
	
	private int currentPlayingId;
	
	private Context context;
	
	public static int MINION_LAUGH;
	public static int MINION_ELO;
	public static int MINION_SHORT_LAUGH;
	public static int MINION_WOOHAHA;
	public static int MINION_YMCA;
	public static int MINION_YOU_GOT_IT;
	public static int MINION_WRONG_TOUCH;
	public static int MINION_MAMBO;

	public static SoundManager getSoundManagerInstance(Context context){
		if(soundManagerInstance == null)
			soundManagerInstance = new SoundManager(context);
		
		return soundManagerInstance;
	}
	
	private SoundManager(Context context) {
		this.context = context;
		
		currentPlayingId = 0;
		
		soundManager = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		mediaPlayer = MediaPlayer.create(context, R.raw.minions_ymca);

		MINION_ELO = soundManager.load(context, R.raw.minion_elo, 1);
		MINION_MAMBO = soundManager.load(context, R.raw.minion_mambo, 1);
		MINION_SHORT_LAUGH = soundManager.load(context, R.raw.minion_short_laugh, 1);
		MINION_WOOHAHA = soundManager.load(context, R.raw.minions_woohaha, 1);
		MINION_YMCA = soundManager.load(context, R.raw.minions_ymca, 1);
		MINION_WRONG_TOUCH = soundManager.load(context, R.raw.wrong_touch, 1);
		MINION_YOU_GOT_IT = soundManager.load(context, R.raw.you_got_it, 1);
	}
	
	/**
	 * The YMCA sound track is greater than 1MB and sound pool cannot play sounds more than 1MB. 
	 * So invoking the song from a MediaPlayer object.
	 */
	public void playYMCA(){
		stopAnyPlayingSound();
		
		mediaPlayer.reset();
		mediaPlayer = MediaPlayer.create(context, R.raw.minions_ymca);
		mediaPlayer.start();
	}
	
	public void stopAnyPlayingSound(){
		if(mediaPlayer.isPlaying())
			mediaPlayer.stop();
		
		if(currentPlayingId > 0)
			soundManager.stop(currentPlayingId);
		
	}
	
	/**
	 * Plays a sound taken from the Sound Id pool
	 * @param soundID Id of the song that has to be played. Usage: SoundManager.<SOUND_ID>
	 * Doesnt work for MINION_YMCA sound. Use <link>SoundManager.playYMCA()</link>
	 */
	public void playSound(int soundID){
		
		if(soundID == MINION_YMCA)
			return;
		
		currentPlayingId = soundManager.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f);
	}
	
	public void playHappySound(){
		Random r = new Random();
		int soundId = r.nextInt(4);
		
		switch(soundId){
		case 0: 
			soundManager.play(MINION_YOU_GOT_IT, 1.0f, 1.0f, 1, 0, 1.0f);
			break;
			
		case 1: 
			soundManager.play(MINION_SHORT_LAUGH, 1.0f, 1.0f, 1, 0, 1.0f);
			break;
			
		case 2: 
			soundManager.play(MINION_MAMBO, 1.0f, 1.0f, 1, 0, 1.0f);
			break;
			
		case 3: 
			soundManager.play(MINION_WOOHAHA, 1.0f, 1.0f, 1, 0, 1.0f);
			break;
			
		}
	}
	
}

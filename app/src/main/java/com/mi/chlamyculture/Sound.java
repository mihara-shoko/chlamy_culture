package com.mi.chlamyculture;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

public class Sound {

    private static SoundPool soundPool;
    private static int hitSound;
    private static int starSound;
    private static int toxinSound;
    private static int startSound;

    private AudioAttributes andioAttributes;

    public Sound (Context context){
        soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        hitSound = soundPool.load(context, R.raw.coin03, 1);
        starSound = soundPool.load(context, R.raw.powerup03, 1);
        toxinSound = soundPool.load(context, R.raw.magic1, 1);
        startSound = soundPool.load(context, R.raw.reflection, 1);
    }

    public void playHitSound() {
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playStarSound() {
        soundPool.play(starSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playToxinSound() {
        soundPool.play(toxinSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playStartSound() {
        soundPool.play(startSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}

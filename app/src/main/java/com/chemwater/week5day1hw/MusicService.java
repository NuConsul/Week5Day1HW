package com.chemwater.week5day1hw;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import java.util.ArrayList ;
import android.content.ContentUris ;
import android.media.AudioManager ;
import android.media.MediaPlayer ;
import android.net.Uri ;
import android.os.Binder ;
import android.os.PowerManager ;
import android.util.Log ;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
 MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    //media player
    private MediaPlayer player ;
    //song list
    private ArrayList<Song> songs ;
    //current position
    private int songPosn ;


    public void onCreate() {
        //create the service
        super.onCreate() ;
        //initialize position
        songPosn = 0 ;
        //create player
        player = new MediaPlayer() ;
    }

    public void initMusicPlayer() {
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK) ;
        player.setAudioStreamType(AudioManager.STREAM_MUSIC) ;
    }


    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null ;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }
}

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

    private final IBinder musicBind = new MusicBinder();




    public void onCreate() {
        //create the service
        super.onCreate() ;
        //initialize position
        songPosn = 0 ;
        //create player
        player = new MediaPlayer() ;
        initMusicPlayer();
    }



    public void initMusicPlayer() {
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK) ;
        player.setAudioStreamType(AudioManager.STREAM_MUSIC) ;
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setList(ArrayList<Song> theSongs){
        songs=theSongs;
    }

    public class MusicBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }


    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind ;
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
        mediaPlayer.start() ;
    }


    public void setSong(int songIndex){
        songPosn=songIndex;
    }



    public int getPosn(){
        return player.getCurrentPosition();
    }

    public int getDur(){
        return player.getDuration();
    }

    public boolean isPng(){
        return player.isPlaying();
    }

    public void pausePlayer(){
        player.pause();
    }

    public void seek(int posn){
        player.seekTo(posn);
    }

    public void go(){
        player.start();
    }






    public void playSong(){
        //play a song
        player.reset() ;

        //get song
        Song playSong = songs.get(songPosn);
        //get id
        long currSong = playSong.getId();
        //set uri
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);

        try{
            player.setDataSource(getApplicationContext(), trackUri);
        }
        catch(Exception e){
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }

        player.prepareAsync();
    }



    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        return false;
    }


}

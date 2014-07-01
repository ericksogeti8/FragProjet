package app.myapplicationeen.enguehard.fr.myfragmenteen.Fragments;

// Declare les class using in this class
import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.IOException;

import app.myapplicationeen.enguehard.fr.myfragmenteen.Music;
import app.myapplicationeen.enguehard.fr.myfragmenteen.R;

/************************************************
 * Created by ENGUEHARD Erick on 23/06/2014.
 ************************************************
 *****************   MAIN ***********************
 */
public class MusicFragment extends Fragment {

    private TextView musicTitleTextView;
    private TextView musicArtisteTitleView;
    private TextView musicAlbumTextView;
    private TextView musicSongDurationTitleView;
    private RadioButton musicFavYesRB;
    private RadioButton musicFavNoRB;
    private Button action_Stream;
    private Button action_Deezer;
    private Button ButtonPreced;


    private Music music;
    private MediaPlayer player = new MediaPlayer();
    /*********************************************
     Fragment OnCreateView en mode fragment
     *****************************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //        setContentView(R.layout.fragment_music);
        // permet de gonfler/transformer un XML en objet
        View viewFragment = inflater.inflate(R.layout.activity_music, null);

        /* Declare : Text view */
        this.musicTitleTextView = (TextView) viewFragment.findViewById(R.id.action_Chanson);
        this.musicArtisteTitleView = (TextView) viewFragment.findViewById(R.id.action_Artiste);
        this.musicAlbumTextView = (TextView) viewFragment.findViewById(R.id.action_Album);
        this.musicSongDurationTitleView= (TextView) viewFragment.findViewById(R.id.song_Duration);
        /* Declare : RadioButton */
        this.musicFavNoRB = (RadioButton) viewFragment.findViewById(R.id.action_Non);
        this.musicFavYesRB = (RadioButton) viewFragment.findViewById(R.id.action_Oui);
        /* Declare  Button */
        this.action_Stream = (Button) viewFragment.findViewById(R.id.action_Stream);
        this.action_Deezer = (Button) viewFragment.findViewById(R.id.action_Deezer);
        this.ButtonPreced = (Button) viewFragment.findViewById(R.id.ButtonPreced);


        if (this.music == null) {// gerer si contenu est vide
            this.music = Music.getDefaultMusic();
        }

        // SET
        this.musicTitleTextView.setText(this.music.getTitle());
        this.musicArtisteTitleView.setText(this.music.getArtist());
        this.musicAlbumTextView.setText(this.music.getAlbum());
        this.musicFavYesRB.setChecked(this.music.isFavorite());
        this.musicFavNoRB.setChecked(this.music.isFavorite());
        this.musicSongDurationTitleView.setText(this.music.getDuration().toString());


        /*********************************************
         listener button PLAY : recovery data de la chanson
         *****************************************************/
        this.action_Stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        // Favori gestion OUI
        this.musicFavYesRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.setFavorite(true);
            }
        });
        // Favori gestion NON
        this.musicFavNoRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                music.setFavorite(false);
            }
        });
        /*********************************************************
         listener button action_Deezer  : display site Deezer on Click
         *****************************************************************/
        this.action_Deezer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchWebBrowser();
            }
        });

        return viewFragment;

    }   // accolade fin public View onCreateView

    /******************************************************************
        listener launchWebBrowser : action_Deezer : lançement Activity
    *********************************************************************/
    private void launchWebBrowser() {

       Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.music.getLink()));
       startActivity(intent);
    }
    /*
     class PLAY : recovery data de la chanson
    */
    private void play() {

        try {
            player.reset();
            player.setDataSource(getActivity(), Uri.parse(this.music.getSampleURL()));
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /******************************************************************
     listener methode set Music
     *********************************************************************/
    public void setMusic(Music music) {
        this.music = music;
    }
    /******************************************************************
     listener methode set Music
     *********************************************************************/
    public void refresh() {
        if(this.musicTitleTextView != null)
        {
        this.musicTitleTextView.setText("SONG  [ " + this.music.getTitle() + " ]");
        this.musicSongDurationTitleView.setText(this.music.getDuration().toString()+ " seconds");
        this.musicArtisteTitleView.setText(this.music.getArtist());
        this.musicAlbumTextView.setText(this.music.getAlbum());
        this.musicFavYesRB.setChecked(this.music.isFavorite());
        this.musicFavNoRB.setChecked(!this.music.isFavorite());
        }
    }
}

package app.myapplicationeen.enguehard.fr.myfragmenteen;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ENGUEHARD Erick on 23/06/2014.
 * Class de definition des variables chanson
 */
public class Music implements Serializable{
    public Music() {
    }

    @Override
    public String toString() {
        return title + " - " + artist;
    }
    private String title;
    private String artist;
    private String album;
    private Long duration;
    boolean favorite;
    private String sampleURL;
    private String link;
    private String coverURL;
    private String idJsonMusic;     //identifiant de la musique

//    =======================================================
    //CREATION DU SGBD a travers une classe fournie par Android :
    // SQLiteOpenHelper

    public String getIdJsonMusic() {
        return idJsonMusic;
    }
//        ; le new dans l'instruction "Music m = new Music("0")" fait un appel au constructeur
//          définit comem cela : Music(String idJsonMusic)

    public Music(String idJsonMusic) {
        this.idJsonMusic = idJsonMusic;
    }

//    =======================================================

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getSampleURL() {
        return sampleURL;
    }

    public void setSampleURL(String sampleURL) {
        this.sampleURL = sampleURL;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }


/** =======================================================
 *  Class static getDefaultMusic : inform song data
 ======================================================= */
    public static Music getDefaultMusic()
    {
        Music m = new Music("0");

        m.setTitle("That Was Just Your Life");
        m.setArtist("METALLICA");
        m.setAlbum("Death Magnetic");
        m.setFavorite(true);
        m.setDuration((long) 145);
        m.setSampleURL(null);
        m.setLink("http://google.com");
        m.setCoverURL(null);

        return m;
    }



}

package app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher;

import java.util.ArrayList;

import app.myapplicationeen.enguehard.fr.myfragmenteen.Music;

/**
 * Created by Fitec on 27/06/2014.
 */
public interface MusicFetcher {

    public void fetchMusicForArtist(String artistName, OnMusicFetcherResultListener listener);



}

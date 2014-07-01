package app.myapplicationeen.enguehard.fr.myfragmenteen.Fragments;

import java.util.ArrayList;

import app.myapplicationeen.enguehard.fr.myfragmenteen.Music;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher.MusicFetcher;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher.OnMusicFetcherResultListener;

/**
 * Created by Fitec on 27/06/2014.
 */
public class DummyMusicFectcher implements MusicFetcher
{
    @Override
    public void fetchMusicForArtist(String artistName, OnMusicFetcherResultListener listener) {

        ArrayList<Music> list = new ArrayList<Music>();
        // =========================================================================
        // ========= Nombre de chansons ramenees ==================================
        // =========================================================================
        for (int i = 0; i < 30; i++)
        {
            Music m = new Music();

            m.setTitle("Track : " + (i+1) );
            m.setArtist(artistName);
            m.setAlbum("Unknown Album");
            m.setFavorite(i % 2 ==0);
            m.setDuration((long) 0);
            m.setSampleURL(null);
            m.setLink("http://www.deezer.com");
            m.setCoverURL(null);

            list.add(m);
        }
        listener.onMusicFetcherResult(list, null);
    }
}

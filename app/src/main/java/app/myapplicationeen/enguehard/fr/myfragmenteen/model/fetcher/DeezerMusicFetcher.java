package app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.myapplicationeen.enguehard.fr.myfragmenteen.Music;

/**
 * Created by Fitec on 27/06/2014.
 */
public class DeezerMusicFetcher implements MusicFetcher
{
    // private ConnectionManager implémenté par Alt+Entrée
    private ConnectionManager connectionManager;
    //nous allons faire de l'injection de dépendances

    // MENU code : generate : ConnectionManager
    public DeezerMusicFetcher(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    // methode implémenté par Alt+Entrée sur MusicFetcher pour choisir : fetchMusicForArtist
    @Override
    public void fetchMusicForArtist(String artistName, final OnMusicFetcherResultListener listener)
    {
        //Construire l'url a appeler : http://api.deezer.com/search?q=eminem
        String urlDeezer = "http://api.deezer.com/search?q=" + artistName.replace(" ","+").trim();

        //demander au connection manager de construire la requête (Perfom
        //si pas d'erreur on doit recevoir un JSON parser
        this.connectionManager.performURLRequest(urlDeezer, new OnConnectionResultListener() {
            @Override
            public void OnConnectionResult(JSONObject result, Exception e) {

                ArrayList<Music> list = new  ArrayList<Music>();

//                int iSize = result.getJSONArray("data").length();
                JSONArray varTableau = null;
                try {
                    varTableau = result.getJSONArray("data");

                    for (int i = 0; i < varTableau.length(); i ++)
                    {
                        //Découpage des informations du JSON PARSE http://json.parser.online.fr/
                        JSONObject item = varTableau.getJSONObject(i);
                        Music m = new Music();

                        m.setTitle      (item.getString("title"));
                        m.setArtist     (item.getJSONObject("artist").getString("name"));
                        m.setAlbum      (item.getJSONObject("album").getString("title"));
                        m.setDuration   (item.getLong("duration"));
                        m.setLink       (item.getString("link"));
                        m.setSampleURL  (item.getString("preview"));
                        m.setFavorite   (false);
                        m.setCoverURL   (item.getJSONObject("album").getString("cover"));
//                        m.setIdJsonMusic (String.valueOf(item.getInt("id")));

                        list.add(m);
                    }
                    listener.onMusicFetcherResult(list, null);

                } catch (JSONException e1) {
                    listener.onMusicFetcherResult(null, e1);

                    e1.printStackTrace();
                }

            }
        });
    }
}

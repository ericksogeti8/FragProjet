package app.myapplicationeen.enguehard.fr.myfragmenteen.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.myapplicationeen.enguehard.fr.myfragmenteen.MainActivity;
import app.myapplicationeen.enguehard.fr.myfragmenteen.Music;
import app.myapplicationeen.enguehard.fr.myfragmenteen.R;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.Repository.DatabaseManager;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher.ConnectionManager;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher.DeezerMusicFetcher;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher.MusicFetcher;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher.OnImageReceiveListener;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher.OnMusicFetcherResultListener;
import app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher.VolleyConnectionManager;


// ========= Declaration de la class : MusicListFragment =========
public class MusicListFragment extends Fragment {

    // ========= Declaration of variable constant  =========
    private static final int ACTION_SELECT = 666;
    private static final int ACTION_FAVORI = 45;

    // Views
    private EditText artistEditText;
    private Button artistButtonSearch;
    private ListView listView;

    // Model
    private ArrayList<Music> musics;
    private MusicFetcher fetcher;
    private String viewById;
    private VolleyConnectionManager connectionManager;
    private DatabaseManager dbmgr;

//    private final ConnectionManager connectionManager = new VolleyConnectionManager((getActivity()));
//    private final MusicFetcher fetcher = new DeezerMusicFetcher(connectionManager); // CHANGE this later !! pour un new DeezerMusicFetcher

    public void setOnMusicSelectedListener(OnMusicSelectedListener onMusicSelectedListener) {
        this.onMusicSelectedListener = onMusicSelectedListener;
    }

    // Listeners  :: Définir un nouveau type 'OnMusicSelectedListener' de type Listener pour la gestion des fragments
    private OnMusicSelectedListener  onMusicSelectedListener;

    // ========= Methode : onCreate =========
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.connectionManager = new VolleyConnectionManager(getActivity());
        this.fetcher = new DeezerMusicFetcher(this.connectionManager);
        this.dbmgr = ((MainActivity) getActivity()).getDatabaseManager();

        // on remplace setContentView(R.layout.fragment_music); par le ViewFragment
        // permet de gonfler/transformer un XML en objet
        View ViewFragment = inflater.inflate(R.layout.activity_music_list, null);

        /* Binding Declarations  */
        this.artistButtonSearch = (Button) ViewFragment.findViewById(R.id.artistButtonSearch);
        this.artistEditText = (EditText) ViewFragment.findViewById(R.id.artistEditText);
        this.listView = (ListView) ViewFragment.findViewById(R.id.listView);
        this.artistButtonSearch = (Button) ViewFragment.findViewById(R.id.artistButtonSearch);

        /* Recover object avec le nombre de chansons en parameter */
        //        musics = Music.GetAllMusics(11);
        // Remplace le GetAllMusics par la nouvelle methode fetchMusicForArtist
//        this.musics = this.fetcher.fetchMusicForArtist("Metallica");
        this.musics = new ArrayList<Music>();

        this.artistButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //artistButtonSearch
                // =========================================================================
                loadMusicsASYNC();
                // =========================================================================
            }
        });


        final MusicAdapter adapter = new MusicAdapter(getActivity());
        this.listView.setAdapter(adapter);

        // AJOUT un menu contextuel aux items
        this.listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;
                Music musicSelected = musics.get(info.position);
                String favoriteMenuText = musicSelected.isFavorite() ? "Retirer des favoris" : "Ajouter aux favoris";

                contextMenu.setHeaderTitle(musicSelected.getTitle());
                contextMenu.add(contextMenu.NONE, ACTION_SELECT, 1, "Sélectionner");
                contextMenu.add(contextMenu.NONE, ACTION_FAVORI, 0, favoriteMenuText);
            }
        });

        // ========= Methode : On ItemClickListener =========
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Music musicSelected = musics.get(position);
                onMusicSelected(musicSelected); //  voir Methode Gestion par Intent
            }
        });

        return ViewFragment;
    }

    // =========================================================================
    // ========= Permet de raffraichir le radio bouton favori Oui/Non  =========
    // =========================================================================
    private void loadMusicsASYNC() {

        //        :Todo récupérer le contenu de la cellule Artist
        //String artist = "Iron Maiden";

        String artist = artistEditText.getText().toString();

        // Afficher le spinner (busy message)
        final ProgressDialog spinner = new ProgressDialog(getActivity());
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle("Loading songs of " + artist.toUpperCase());
        spinner.setMessage("Find songs in progress...");
        spinner.setCancelable(false);

        spinner.show();

        this.fetcher.fetchMusicForArtist(artist, new OnMusicFetcherResultListener() {
            @Override
            public void onMusicFetcherResult(ArrayList<Music> results, Exception e) {

                // suppression du spinner
                spinner.dismiss();

                // Mise à jour de la liste de music
                if(e == null)
                {
                    musics = results;
                    listView.setAdapter(new MusicAdapter(getActivity()));
                }

            }
        });
    }

    // ========= Permet de raffraichir le radio bouton favori Oui/Non  =========
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.listView.invalidateViews();
    }

    // ========= Methode : On Context Item =========
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Music musicSelected = musics.get(info.position);

        switch (item.getItemId())
        {
            case ACTION_SELECT:
                // Implementation
                onMusicSelected(musicSelected);
                break;

            case ACTION_FAVORI:
                musicSelected.setFavorite(!musicSelected.isFavorite());
//                this.listView.setAdapter(new MusicAdapter(getActivity()));
                this.listView.invalidateViews();
                Toast.makeText(getActivity(), "Duration track is "+musicSelected.getDuration()+" sec.", Toast.LENGTH_LONG).show();

                break;

            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    // ========= Methode : onMusicSelected =========
    private void onMusicSelected(Music musicSelected)
    {
        if(onMusicSelectedListener != null )
        {
            onMusicSelectedListener.onMusicSelected(musicSelected);
            Toast.makeText(getActivity(), " for lesson the song, click on [ Play ] ", Toast.LENGTH_LONG).show();
        }
    }

    // Inflate the menu;
    private class MusicAdapter extends BaseAdapter
    {
        //raccourci menu : / Code / Generate / Constructor
        private Context context;
        //Constructeur ; c'est dans cette classe que se lance le constructeur
        private MusicAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return musics.size();
        }

        @Override
        public Object getItem(int i) {
            return  musics.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            // Recover music with start to -1
//            int lastIndex = musics.size()-1;
//            Music musicToDisplay = (Music) getItem(lastIndex-1);
            // Recover music
            Music musicToDisplay = (Music) getItem(i);

            // Gonflage inflation
            // Transformer xml en objet Java = LayoutInflater

            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // Return dans une variable cell de type View
            // Si cellule est vidéo ou photo le layout est differently
            View cell = inflater.inflate(R.layout.music_cell, null);

            // Id included into activity music
            final ImageView coverImageView = (ImageView) cell.findViewById(R.id.CoverImageView);
            TextView titleTextView_1 = (TextView) cell.findViewById(R.id.TitleTextView_1);
            TextView albumTextView_2 = (TextView) cell.findViewById(R.id.AlbumTextView_2);
            ImageView starImageView = (ImageView) cell.findViewById(R.id.StarImageView);

            titleTextView_1.setText(musicToDisplay.getTitle());
            albumTextView_2.setText(musicToDisplay.getAlbum());
            starImageView.setAlpha(dbmgr.isFavorite(musicToDisplay) ? 1f : 0f);

            // 1er on affiche l'icone BugDroïd
            coverImageView.setImageResource(R.drawable.ucjapan);        //ic_launcher

            // 2éme On lance le téléchargement de la vignette de la pochette en ASYNC
            connectionManager.loadImageFromURL(musicToDisplay.getCoverURL(), new OnImageReceiveListener() {
                @Override
                public void onImageReceive(Bitmap bitmap, Exception e) {
                    if(e == null)
                    {
                        // 3éme Quand le téléchargement est terminé, on remplace le BugDroïd
                       coverImageView.setImageBitmap(bitmap);
                    }
                }
            });

            return cell;
        }
    }
}



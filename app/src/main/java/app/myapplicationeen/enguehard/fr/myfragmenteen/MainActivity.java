package app.myapplicationeen.enguehard.fr.myfragmenteen;

import android.app.Activity;
import android.os.Bundle;

import app.myapplicationeen.enguehard.fr.myfragmenteen.Fragments.MusicFragment;
import app.myapplicationeen.enguehard.fr.myfragmenteen.Fragments.MusicListFragment;
import app.myapplicationeen.enguehard.fr.myfragmenteen.Fragments.OnMusicSelectedListener;


public class MainActivity extends Activity {

    MusicListFragment list;
    MusicFragment detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.frameLayout) != null ) // Telephone GALAXIE NEXUS 4 si recherche par l'id est <> null
        {
            list = new MusicListFragment();
            detail = new MusicFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.frameLayout, list)
                    .commit();
        }
        else // Tablette NEXUS 10
        {
            // fragment list par Alt+Entrée pour caster
            list = (MusicListFragment) getFragmentManager().findFragmentById(R.id.fragment);
            // fragment detail par Alt+Entrée pour caster
            detail = (MusicFragment) getFragmentManager().findFragmentById(R.id.fragment2);
        }

        list.setOnMusicSelectedListener(new OnMusicSelectedListener() {
            @Override
            public void onMusicSelected(app.myapplicationeen.enguehard.fr.myfragmenteen.Music music) {
                if(findViewById(R.id.frameLayout) != null )
                {
                getFragmentManager().beginTransaction()
                        .hide(list)
                        .add(R.id.frameLayout, detail)
                        .commit();
                }
                detail.setMusic(music);
                detail.refresh();
            }


        });
    }

    //   boutton PRECEDENT sur Telephone GALAXIE NEXUS 4
    @Override
    public void onBackPressed()
    {
        boolean DeviceISPhone = (findViewById(R.id.frameLayout) != null);
        boolean detailFragmentVisible = detail.isAdded();

        if(DeviceISPhone && detailFragmentVisible)
        {
            getFragmentManager().beginTransaction()
                    .remove(detail)
                    .show(list)
                    .commit();
        }
        else
        {
            super.onBackPressed();
        }

    }
}

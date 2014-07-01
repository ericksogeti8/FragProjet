package app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher;

import android.graphics.Bitmap;

/**
 * Created by INTERFACE on 30/06/2014.
 */
public interface OnImageReceiveListener {

    void onImageReceive(Bitmap bitmap, Exception e); //on remplace le Object o par le Exception e
}

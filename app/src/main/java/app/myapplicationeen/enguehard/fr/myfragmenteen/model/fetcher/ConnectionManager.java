package app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher;

/**
 * Created by Fitec on 27/06/2014.
 */

// private ConnectionManager implémenté par Alt+Entrée puis création interface
    // OnConnectionResultListener  implémenté par Alt+Entrée puis création interfac

public interface ConnectionManager {

    public void performURLRequest(String url, OnConnectionResultListener listener); //en Asynchrone on prend toujours void et listener
}

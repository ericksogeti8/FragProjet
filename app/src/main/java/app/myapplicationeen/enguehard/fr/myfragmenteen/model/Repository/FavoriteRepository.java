package app.myapplicationeen.enguehard.fr.myfragmenteen.model.Repository;

import app.myapplicationeen.enguehard.fr.myfragmenteen.Music;

/**
 * Created by Fitec on 30/06/2014.
 */
// ajouter et supprimer des données dans la base locale du téléphone

public interface FavoriteRepository {

    void add(Music music);

    void remove(Music music);

    boolean isFavorite(Music music);


}

package app.myapplicationeen.enguehard.fr.myfragmenteen.model.Repository;

/**
 * Created by Fitec on 30/06/2014.
 */
// ajouter et supprimer des données dans la base locale du téléphone

public interface FavoriteRepository {

    void add(Musique musique);

    void remove(Musique musique);

    boolean isFavorite(Music music);


}

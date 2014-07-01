package app.myapplicationeen.enguehard.fr.myfragmenteen.model.Repository;

import java.util.ArrayList;

/**
 * Created by Fitec on 30/06/2014.
 */
public interface RecentRepository {

    void add(String request);

    ArrayList<String> getRequestMatching(String predicate);


}

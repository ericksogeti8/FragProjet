package app.myapplicationeen.enguehard.fr.myfragmenteen.model.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import app.myapplicationeen.enguehard.fr.myfragmenteen.Music;

/**
 * Created by Fitec on 01/07/2014.
 */
//================================================================================================
// depuis une application mobile il est extrêmement déconseillé de permettre
// un accès à une source de données quelconque pour des raisons évidentes de sécurité.
// création des 3 add/remove/IsFavorite public void par Alt+Entrée sur FavoriteRepository
//================================================================================================

public class DatabaseManager extends SQLiteOpenHelper implements FavoriteRepository, RecentRepository
{
    private static final String DATABASE_name ="mydb.sqlite";
    private static final int CURRENT_DB_VERSION = 1;
    //    Context = activity
    public DatabaseManager(Context context) {
    super(context, DATABASE_name, null, CURRENT_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    // ajout du sql pour créer la structure de la database

        sqLiteDatabase.execSQL("create table History\n" +
        "(\n" +
        " request \"VARCHAR()\" not null\n" +
                ");");

        sqLiteDatabase.execSQL("create table Favorite\n" +
                "(\n" +
                " identifier \"VARCHAR()\" not null\n" +
                ");");
    }

    //==============================================================================================

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // code sql de màj de la version i version i2 de la database

        switch (i)
        {
            case 1://code sql 1->vers 2 ALTER TABLE

            case 2://code sql 2->vers 3

            case 3://code sql 3->vers ...

            default:
        }

    }


    //==============================================================================================
    @Override   // vient de FavoriteRepository
    public void add(Music music) {

        if(!isFavorite(music))
        {
            ContentValues cv = new ContentValues();
            cv.put("identifier", music.getIdJsonMusic());
            this.getWritableDatabase().insert("Favorite", null, cv);
           // Log.d(" Tag ", music.getIdJsonMusic());

        }
    }

    //==============================================================================================
    @Override   // vient de FavoriteRepository
    public void remove(Music music) {
        // mise à jour de la base de données par db.update("table", rep, "id="+id,null)
        // la version par fonction
        this.getWritableDatabase().delete("Favorite", "identifier = "+ music.getIdJsonMusic(), null);

//        // la version par sql dynamique
//        String SqlState = "DELETE FROM Favorite WHERE identifier= \"" +
//                 music.getIdJsonMusic() + "\";";
//        this.getWritableDatabase().execSQL(SqlState);
    }

    //==============================================================================================
    @Override   // vient de FavoriteRepository
    public boolean isFavorite(Music music) {
        String table ="Favorite";
        String column = "identifier";
        String predicate = music.getIdJsonMusic();

        Cursor c;
        c = this.getReadableDatabase().rawQuery("SELECT " + column + " FROM " + table + " WHERE " + column + "= \"" + predicate +"\";", null);
        return c.getCount() != 0;
    }

    //==============================================================================================
    @Override   // vient de RecentRepository
    public void add(String request) {
        String reqLowerCase = request.toLowerCase();

        if(!existInHistory(reqLowerCase))
        {
            ContentValues cv = new ContentValues();
            cv.put("request", reqLowerCase);
            this.getWritableDatabase().insert("History", null, cv);
        }

    }

    //==============================================================================================
    private boolean existInHistory(String reqLowerCase) {
        String table ="History";
        String[] columns = {"request"};
        String predicate =reqLowerCase;

        Cursor h;
        h = this.getReadableDatabase().query(false, table, columns, "request = " + predicate,
                null, null, null, null, null);

//        h = this.getReadableDatabase().rawQuery("SELECT " + column + " FROM " + table + " WHERE " + column + "= \"" + predicate +"\";", null);
        return h.getCount() == 1;

    }

    //==============================================================================================
    @Override   // vient de RecentRepository
    public ArrayList<String> getRequestMatching(String predicate) {
        return null;
    }
}

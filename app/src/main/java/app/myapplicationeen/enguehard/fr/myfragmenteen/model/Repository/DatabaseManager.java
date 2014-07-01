package app.myapplicationeen.enguehard.fr.myfragmenteen.model.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.myapplicationeen.enguehard.fr.myfragmenteen.Music;

/**
 * Created by Fitec on 01/07/2014.
 */

// depuis une application mobile il est extrêmement déconseillé de permettre
// un accès à une source de données quelconque pour des raisons évidentes de sécurité.
// création des 3 add/remove/IsFavorite public void par Alt+Entrée sur FavoriteRepository
public class DatabaseManager extends SQLiteOpenHelper implements FavoriteRepository
{
    private static final String DATABASE_name ="mydb.sqlite";
    private static final int CURRENT_DB_VERSION = 1;
    //    Context = activity
    public DatabaseManager(Context context, SQLiteDatabase.CursorFactory factory, int version) {
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

//
//    Cursor c=db.rawQuery("SELECT x from t WHERE type='table' AND x='request'");

    @Override
    public void add(Music music) {

//        contentvalues values = new ContentValues();
//        getWritableDatabase().insert("Favorite", )

//        = "clé" valeur  = "nom de colonne" / valeur
    }


    @Override
    public void remove(Music music) {
    // mise à jour de la base de données par db.update("table", rep, "id="+id,null)
    }

    @Override
    public boolean isFavorite(Music music) {
//        Cursor c = db.rawQuery("SELECT x from t WHERE type='table' AND x='request'");

//        if(music.getIdJsonMusic())
        return false;
    }
}

package app.myapplicationeen.enguehard.fr.myfragmenteen.model.fetcher;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;

/**
 * Created by Fitec on 27/06/2014.
 */
public class VolleyConnectionManager implements ConnectionManager
{
    private RequestQueue requestQueue;
        // MENU  : Code : Generate : requestQueue
    public VolleyConnectionManager(Context context)
    {
        this.requestQueue = Volley.newRequestQueue(context);
        this.requestQueue.start();
    }

    // methode implémenté par Alt+Entrée sur ConnectionManager pour choisir : performURLRequest
    @Override
    public void performURLRequest(String url, final OnConnectionResultListener listener)
    {
        JsonObjectRequest request = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                listener.OnConnectionResult(jsonObject, null);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.OnConnectionResult(null, volleyError);
            }
        }  // ,new Response.ErrorListener() = 4éme parameter de la methode : JsonObjectRequest
        );

        this.requestQueue.add(request);
    }

    public void  loadImageFromURL(String url, final OnImageReceiveListener listener)
    {
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                listener.onImageReceive(bitmap, null);
            }
        }, 60, 60, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onImageReceive(null, volleyError);
            }
        })    ;
        // on ajoute tout le bordel du dessus à ma request queue
        this.requestQueue.add(request);

    }
}

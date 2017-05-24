package com.example.computador.pruebabdonline.Controlador;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public final class VolleySingleton {
    //Clase que adapta un Singleton para la petición de Volley y que la llamada asincrona no se bloquee

    // Atributos
    private static VolleySingleton singleton;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context context) {
        VolleySingleton.context = context;
        requestQueue = getRequestQueue();
    }

    //Se cerciora que solo haya una instancia del VolleySingleton
    public static synchronized VolleySingleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new VolleySingleton(context);
        }
        return singleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    //Añade la petición al servidor a la cola
    public  void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }
}

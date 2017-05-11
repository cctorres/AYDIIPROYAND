package com.example.computador.pruebabdonline.Controlador;


import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ObtenerServiciosWebSingleton extends AsyncTask<String,Void,String>{

    public AsyncResponse delegate = null;

    public ObtenerServiciosWebSingleton(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {
        BDController BD = new BDController();
        String devuelve ="";
        if(params[0].equalsIgnoreCase("obtenerMesas")){    // Consulta de todos las mesas
            devuelve = BD.obtenerMesas();
            return devuelve;
        }
        else if(params[0].equalsIgnoreCase("insertarMesa")){    // insert
                devuelve = BD.insertarMesa(params[1],params[2]);
            return(devuelve);
        }
        else if(params[0].equalsIgnoreCase("obtenerMesaByID")){
            devuelve = BD.obtenerMesaByID(params[1]);
            return(devuelve);
        }
        else if(params[0].equalsIgnoreCase("actualizarMesa")){    // update
                devuelve = BD.actualizarMesa(params[1],params[2],params[3]);
                return devuelve;

        }
        else if(params[0].equalsIgnoreCase("borrarMesa")) {    // delete
            devuelve = BD.borrarMesa(params[1]);
            return devuelve;
        }
        else if(params[0].equalsIgnoreCase("obtenerRestaurante")){
            devuelve = BD.obtenerRestaurantes();
            return devuelve;
        }
        else if(params[0].equalsIgnoreCase("actualizarRestaurante")){
            devuelve = BD.actualizarRestaurante(params[1],params[2],params[3],params[4],params[5]);
            return devuelve;
        }
        return "no entró";
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            delegate.processFinish(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }




}




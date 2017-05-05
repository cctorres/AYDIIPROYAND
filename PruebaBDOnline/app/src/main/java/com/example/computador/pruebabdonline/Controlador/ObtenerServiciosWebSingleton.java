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
        String cadena = params[0]; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        if(params[1].equalsIgnoreCase("obtenerMesas")){    // Consulta de todos las mesas
            devuelve = this.obtenerMesas(params[0]);
            return devuelve;
        }
        else if(params[1].equalsIgnoreCase("insertarMesa")){    // insert
                devuelve = BD.insertarMesas(params[0],params[2],params[3]);
            return(devuelve);
        }
        else if(params[1].equalsIgnoreCase("4")){    // update



        }
        else if(params[1].equalsIgnoreCase("5")) {    // delete

            try {
                HttpURLConnection urlConn;

                DataOutputStream printout;
                DataInputStream input;
                url = new URL(cadena);
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setDoInput(true);
                urlConn.setDoOutput(true);
                urlConn.setUseCaches(false);
                urlConn.setRequestProperty("Content-Type", "application/json");
                urlConn.setRequestProperty("Accept", "application/json");
                urlConn.connect();
                //Creo el Objeto JSON
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("id_mesa", params[2]);
                // Envio los parámetros post.
                OutputStream os = urlConn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonParam.toString());
                writer.flush();
                writer.close();

                int respuesta = urlConn.getResponseCode();


                StringBuilder result = new StringBuilder();

                if (respuesta == HttpURLConnection.HTTP_OK) {

                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        result.append(line);
                        //response+=line;
                    }

                    //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                    JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                    //Accedemos al vector de resultados

                    String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                    if (resultJSON.equalsIgnoreCase("1")) {      // hay un alumno que mostrar
                        devuelve = "Alumno borrado correctamente";

                    } else if (resultJSON.equalsIgnoreCase("2")) {
                        devuelve = "No hay alumnos";
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return devuelve;

        }
        return null;
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(s);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public String obtenerMesas(String php){
        String cadena = php;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            //Conexión
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a través de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay datos
                    JSONArray alumnosJSON = respuestaJSON.getJSONArray("mesas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<alumnosJSON.length();i++){
                        devuelve = devuelve + "("+alumnosJSON.getJSONObject(i).getString("id_mesa") + " * " +
                                alumnosJSON.getJSONObject(i).getString("estado_mesa") + " * " +
                                alumnosJSON.getJSONObject(i).getString("id_pedido_mesa") + ")";
                    }
                }
                else if (resultJSON.equalsIgnoreCase("2")){
                    devuelve = "No hay datos insertados";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }


    public String actulizarMesa(String php, String idMesa, String estadoMesa, String idPedidoMesa){
        String cadena = php; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_mesa", idMesa);
            jsonParam.put("estado_mesa", estadoMesa);
            jsonParam.put("id_pedido_mesa", idPedidoMesa);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "Alumno actualizado correctamente";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "El alumno no pudo actualizarse";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }
}




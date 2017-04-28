package com.example.computador.pruebabdonline;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

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

public class PruebaWeb extends AppCompatActivity implements View.OnClickListener {

    Button agregar;
    Button borrar;
    Button mostrar;
    TextView nombre;
    TextView apellido;
    TextView id;
    TextView codigo;
    TextView resultado;

    ObtenerWebService hiloconexion;

    //URL del servidor
    String URLD = "http://ameboid-grasses.000webhostapp.com";
    //rutas de los Web Services
    String obtenerEmpleados = URLD + "/obtenerEmpleados.php";
    String obtenerEmpleadoID = URLD + "/obtenerEmpleadoByID.php";
    String insertarEmpleado = URLD + "/insertarEmpleado.php";
    String borrarEmpleado = URLD + "/borrarMesa.php";
    String actualizarEmpleado = URLD + "actualizarEmpleado.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //enlaces de componentes visuales con el xml

        agregar = (Button) findViewById(R.id.agregar);
        mostrar = (Button) findViewById(R.id.mostrar);
        nombre = (TextView) findViewById(R.id.nombre);
        apellido = (TextView) findViewById(R.id.identificacion);
        id = (TextView) findViewById(R.id.nombre);
        codigo = (TextView) findViewById(R.id.codigo);
        resultado = (TextView) findViewById(R.id.resultado);

        // listener
        mostrar.setOnClickListener(this);
        agregar.setOnClickListener(this);
        borrar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prueba_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.agregar:
                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(insertarEmpleado,"1");//insertar empleados
                break;
            case R.id.borrar:
                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(borrarEmpleado,"2",id.getText().toString());//borrar empleados
                break;
            case R.id.mostrar:
                //hiloconexion = new ObtenerWebService();
                //hiloconexion.execute(obtenerEmpleados,"3");//Obtener empleados
                break;
            default:

                break;
        }
    }

    public class ObtenerWebService extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            resultado.setText(s);
            //super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(String... params) {
            String cadena = params[0];
            String devuelve = "";
            URL url = null;

            if(params[1].equalsIgnoreCase("1")){//agregar



            }
            else if (params[1]=="2"){    // delete

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

                        if (resultJSON == "1") {      // hay un alumno que mostrar
                            devuelve = "Alumno borrado correctamente";

                        } else if (resultJSON == "2") {
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
            else if (params[1].equalsIgnoreCase("3")){//mostrar
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON



                        if (resultJSON.equalsIgnoreCase("1")){      // hay empleados a mostrar
                            JSONArray empleadosJSON = respuestaJSON.getJSONArray("empleados");   // estado es el nombre del campo en el JSON
                            for(int i=0;i<empleadosJSON.length();i++){
                                devuelve = devuelve + empleadosJSON.getJSONObject(i).getString("codEmpleado") + " " +
                                        empleadosJSON.getJSONObject(i).getString("idEmpleado") + " " +
                                        empleadosJSON.getJSONObject(i).getString("nomEmpleado") + " " +
                                        empleadosJSON.getJSONObject(i).getString("telEmpleado") + "\n";
                            }
                        }
                        else{
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
    }

   
}

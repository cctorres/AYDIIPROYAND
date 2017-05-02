package com.example.computador.pruebabdonline.Vista;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computador.pruebabdonline.Controlador.AsyncResponse;
import com.example.computador.pruebabdonline.Controlador.ObtenerServiciosWeb;
import com.example.computador.pruebabdonline.R;

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

public class PruebaMesa extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    Button agregar;
    Button borrar;
    Button mostrar;
    EditText identificador;
    EditText estado;
    EditText pedido;
    TextView resultado;
    Button IDe;
    Button actualizar;

    ObtenerServiciosWeb hiloconexion;

    //URL del servidor
    String URLD = "http://ameboid-grasses.000webhostapp.com";
    //rutas de los Web Services
    String GET = URLD + "/obtenerMesas.php";
    String GETID = URLD + "/obtenerMesaByID.php";
    String INSERT = URLD + "/insertarMesa.php";
    String ERASE = URLD + "/borrarMesa.php";
    String UPDATE = URLD + "/actualizarMesa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_mesa);
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
        borrar = (Button) findViewById(R.id.borrar);
        IDe = (Button) findViewById(R.id.ide);
        actualizar = (Button) findViewById(R.id.actualizar);

        identificador = (EditText) findViewById(R.id.identificador);
        estado = (EditText) findViewById(R.id.estado);
        pedido = (EditText) findViewById(R.id.pedido);

        resultado = (TextView) findViewById(R.id.resultado);

        // listener
        mostrar.setOnClickListener(this);
        agregar.setOnClickListener(this);
        borrar.setOnClickListener(this);
        IDe.setOnClickListener(this);
        actualizar.setOnClickListener(this);
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
            case R.id.mostrar:

                hiloconexion = new ObtenerServiciosWeb(this);
                hiloconexion.execute(GET,"1");   // Parámetros que recibe doInBackground

                break;
            case R.id.ide:

                hiloconexion = new ObtenerServiciosWeb(this);
                String cadenallamada = GETID + "?id_mesa=" + identificador.getText().toString();
                hiloconexion.execute(cadenallamada,"2");   // Parámetros que recibe doInBackground


                break;
            case R.id.agregar:

                hiloconexion = new ObtenerServiciosWeb(this);
                hiloconexion.execute(INSERT,"3",estado.getText().toString(),pedido.getText().toString());   // Parámetros que recibe doInBackground


                break;
            case R.id.actualizar:

                hiloconexion = new ObtenerServiciosWeb(this);
                hiloconexion.execute(UPDATE,"4",identificador.getText().toString(),estado.getText().toString(),pedido.getText().toString());   // Parámetros que recibe doInBackground


                break;
            case R.id.borrar:

                hiloconexion = new ObtenerServiciosWeb(this);
                hiloconexion.execute(ERASE,"5",identificador.getText().toString());   // Parámetros que recibe doInBackground


                break;
            default:

                break;
        }
    }


    @Override
    public void processFinish(String output) {
        Toast.makeText(this, output+"*"+identificador.getText()+estado.getText()+pedido.getText(), Toast.LENGTH_SHORT).show();
        resultado.setText(output);
    }
}

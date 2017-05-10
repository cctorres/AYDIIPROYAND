package com.example.computador.pruebabdonline.Vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computador.pruebabdonline.Controlador.AsyncResponse;
import com.example.computador.pruebabdonline.Controlador.JSonAdapter;
import com.example.computador.pruebabdonline.Controlador.ObtenerServiciosWebSingleton;
import com.example.computador.pruebabdonline.Modelo.Restaurante;
import com.example.computador.pruebabdonline.R;

import org.json.JSONException;

public class VistaRestaurante extends AppCompatActivity implements View.OnClickListener, AsyncResponse {

    TextView tvNombre, tvDireccion, tvNumero, tvTelefono;
    EditText etNombre, etDireccion, etNumero, etTelefono;
    Button actualizar;
    ObtenerServiciosWebSingleton hiloconexion;
    String JsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_restautante);

        //Enlazar componentes visuales
        tvNombre = (TextView) findViewById(R.id.tv_nombre_vr);
        tvDireccion = (TextView) findViewById(R.id.tv_direccion_vr);
        tvNumero = (TextView) findViewById(R.id.tv_numero_vr);
        tvTelefono = (TextView) findViewById(R.id.tv_telefono_vr);

        etNombre = (EditText) findViewById(R.id.et_nombrer_vr);
        etDireccion = (EditText) findViewById(R.id.et_direccion_vr);
        etNumero = (EditText) findViewById(R.id.et_numero_vr);
        etTelefono = (EditText) findViewById(R.id.et_telefono_vr);

        actualizar = (Button) findViewById(R.id.bt_actualizar_vr);
        actualizar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.actualizar:



               break;
            default:
                break;
        }
    }

    public void llenarDatosVR(TextView nom, TextView dir, TextView num, TextView tel) throws JSONException {
        String respuesta;
        hiloconexion = new ObtenerServiciosWebSingleton(this);
        hiloconexion.execute("obtenerRestaurante");  // Parámetros que recibe doInBackground
        JSonAdapter restauranteJSon = new JSonAdapter();
        Restaurante rest = restauranteJSon.restauranteAdapter(this.JsonString);
        nom.setText(rest.getNombre());
        dir.setText(rest.getDireccion());
        num.setText(Integer.toString(rest.getNumeroMesas()));
        tel.setText(rest.getTelefono());
    }

    @Override
    public void processFinish(String output) {
        JsonString = output;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            llenarDatosVR(tvNombre, tvDireccion, tvNumero, tvTelefono);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

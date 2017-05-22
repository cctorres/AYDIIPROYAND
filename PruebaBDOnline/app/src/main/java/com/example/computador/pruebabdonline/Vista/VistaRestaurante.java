package com.example.computador.pruebabdonline.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computador.pruebabdonline.Controlador.DBController;
import com.example.computador.pruebabdonline.Controlador.JSonAdapter;
import com.example.computador.pruebabdonline.Controlador.PHPGetter;
import com.example.computador.pruebabdonline.Controlador.VolleySingleton;
import com.example.computador.pruebabdonline.Modelo.Restaurante;
import com.example.computador.pruebabdonline.R;

import org.json.JSONException;

public class VistaRestaurante extends AppCompatActivity implements View.OnClickListener {

    private TextView tvNombre, tvDireccion, tvNumero, tvTelefono;
    private EditText etNombre, etDireccion, etNumero, etTelefono;
    private Button actualizar;
    private PHPGetter php = new PHPGetter();
    private DBController db = new DBController();

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

        //Listener de los botones
        actualizar.setOnClickListener(this);
    }

    /**
     * Métodos de los botones
     * @param v Vista
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_actualizar_vr:
                String nombre = etNombre.getText().toString();
                String direccion = etDireccion.getText().toString();
                String numero = etNumero.getText().toString();
                String telefono = etTelefono.getText().toString();
                //Valida que los datos se hallan ingresado
                if(nombre.equals("") | direccion.equals("") | numero.equals("") | telefono.equals("")){
                    Toast.makeText(this, "Faltan datos por llenar", Toast.LENGTH_LONG).show();
                    return;
                }
                //Se hace una petición de actualizar el restaurante
                db.actualizarRestaurante(nombre,direccion,numero,telefono,this);
                this.refrescarActiviry();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String url = php.getObtenerRestaurantes();//Pide el url del PHP necesario
        //Hace una petición volley para obtener los datos del restaurante
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        JSonAdapter json = new JSonAdapter();
                        Restaurante restaurante = null;
                        try {
                            restaurante = json.restauranteAdapter(respuesta);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Setea los datos del restaurante en los TextViews correspondientes
                        tvNombre.setText("Nombre: "+restaurante.getNombre());
                        tvDireccion.setText("Dirección: "+restaurante.getDireccion());
                        tvNumero.setText("Mesas: "+Integer.toString(restaurante.getNumeroMesas()));
                        tvTelefono.setText("Telefono: "+restaurante.getTelefono());
                        etNombre.setText(restaurante.getNombre());
                        etDireccion.setText(restaurante.getDireccion());
                        etNumero.setText(Integer.toString(restaurante.getNumeroMesas()));
                        etTelefono.setText(restaurante.getTelefono());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        VolleySingleton.getInstance(this).addToRequestQueue(peticion);

    }

    /**
     * Cierra y reabre la activity
     */
    public void refrescarActiviry(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}

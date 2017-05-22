package com.example.computador.pruebabdonline.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computador.pruebabdonline.Controlador.JSonAdapter;
import com.example.computador.pruebabdonline.Controlador.PHPGetter;
import com.example.computador.pruebabdonline.Controlador.VolleySingleton;
import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.Modelo.Restaurante;
import com.example.computador.pruebabdonline.R;

import org.json.JSONException;

public class VistaAdmin extends AppCompatActivity implements View.OnClickListener{

    //Declaración de variables
    private Button pedidos, carta, empleados, inventario, restaurante;
    private Intent intent;
    private Empleado usuario;
    private TextView restauranteNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_admin);

        //Enlazar componentes visuales

        carta = (Button) findViewById(R.id.bt_carta_va);
        empleados = (Button) findViewById(R.id.bt_empleados_va);
        restaurante = (Button) findViewById(R.id.bt_restaurante_va);
        restauranteNombre = (TextView) findViewById(R.id.tv_restaurante_va);

        usuario = (Empleado) getIntent().getSerializableExtra("usuario");

        //Click listener
        carta.setOnClickListener(this);
        empleados.setOnClickListener(this);
        restaurante.setOnClickListener(this);
    }

    /**
     * Métodos de los botones
     * @param v Vista
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_restaurante_va:
                intent = new Intent(VistaAdmin.this, VistaRestaurante.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                break;
            case R.id.bt_empleados_va:
                intent = new Intent(VistaAdmin.this, EmpleadosVista.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                break;
            case R.id.bt_carta_va:
                intent = new Intent(VistaAdmin.this, MenuVista.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            default:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PHPGetter php = new PHPGetter();
        String url = php.getObtenerRestaurantes();

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
                        restauranteNombre.setText(restaurante.getNombre());
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
}

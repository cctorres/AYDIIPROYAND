package com.example.computador.pruebabdonline.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.computador.pruebabdonline.Controlador.DBController;
import com.example.computador.pruebabdonline.Controlador.JSonAdapter;
import com.example.computador.pruebabdonline.Controlador.PHPGetter;
import com.example.computador.pruebabdonline.Controlador.VolleySingleton;
import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.Modelo.EmpleadoAdapter;
import com.example.computador.pruebabdonline.Modelo.Restaurante;
import com.example.computador.pruebabdonline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class EmpleadosVista extends AppCompatActivity implements View.OnClickListener{

    private DBController db = new DBController();
    private PHPGetter php = new PHPGetter();
    private EmpleadoAdapter adapter;
    private EditText identificacion,nombre,telefono,contraseña, verificarContraseña;
    private Spinner cargo;
    private Button agregar;
    private ListView lista;
    private String[] cargos = new String[] {"Administrador", "Cocinero", "Mesero", "Repartidor", "Supervisor"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados_vista);

        //enlazar componentes visuales
        identificacion = (EditText) findViewById(R.id.et_identificacion_emv);
        nombre = (EditText) findViewById(R.id.et_nombre_emv);
        telefono = (EditText) findViewById(R.id.et_telefono_emv);
        contraseña = (EditText) findViewById(R.id.et_contraseña_emv);
        verificarContraseña = (EditText) findViewById(R.id.et_verificarcontraseña_emv);

        agregar = (Button) findViewById(R.id.bt_agregar_emv);
        agregar.setOnClickListener(this);
        cargo = (Spinner) findViewById(R.id.sp_cargo_emv);

        //Llenar Spiner con el vector de cargos
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cargos);
        cargo.setAdapter(adapter);
    }

    /**
     * Métodos de los botones
     * @param v Vista
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_agregar_emv:
                String id = identificacion.getText().toString();
                String nom = nombre.getText().toString();
                String tel = telefono.getText().toString();
                String pass = contraseña.getText().toString();
                String vpass = verificarContraseña.getText().toString();
                String carg = cargo.getSelectedItem().toString();
                //Valida que se hallan ingresado los datos
                if(id.equals("") | nom.equals("") | tel.equals("") | pass.equals("") | vpass.equals("")){
                    Toast.makeText(this,"Falta un campo por llenar",Toast.LENGTH_LONG).show();
                    return;
                }
                //Se valida que los campos de contraseñas sean iguales
                if(!pass.equals(vpass)){
                    Toast.makeText(this,"Los campos de contraseña son distintos",Toast.LENGTH_LONG).show();
                    return;
                }
                //Se hace una petición para agregar un empleado
                db.insertarEmpleado(id,nom,carg,tel,pass,this);
                refrescarActiviry();
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Empleado> arrayEmp = new ArrayList<>();
        String url = php.getObtenerEmpleados(); //Obtener el link del php
        //Generar la petición Volley al servidor
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        JSonAdapter json = new JSonAdapter();
                        final ArrayList<Empleado> empleadosLista;
                        try {
                            empleadosLista = json.empleadoAdapter(respuesta); //generar el ArrayList de empelados del JSON del servidor
                            adapter = new EmpleadoAdapter(EmpleadosVista.this, empleadosLista);
                            //Cargar el adapter del ListView de empleados con el Array de empleados
                            lista = (ListView) findViewById(R.id.lv_empleados_emv);
                            lista.setAdapter(adapter);
                            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //Al presionar clic, enviar a la activity del detalle del empleado seleccionado
                                    Intent intent = new Intent(EmpleadosVista.this, EmpleadoDetalle.class);
                                    intent.putExtra("empleadoObject",empleadosLista.get(position));
                                    startActivity(intent);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
     * Reinicia la activity
     */
    public void refrescarActiviry(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

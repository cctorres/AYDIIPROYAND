package com.example.computador.pruebabdonline.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.ArrayList;

public class inicioSesion extends AppCompatActivity implements View.OnClickListener{

    private Empleado usuario;

    //Datos para entrar como superusuario si todavia no hay un administrador en la BD remota
    String idAdmin = "12345";
    String contraseñaAdmin = "admin";
    ArrayList<Empleado> empleados;

    //Declaración de variables
    Button iniciarSesion;
    EditText identificacion;
    EditText contraseña;
    Intent intent;
    private PHPGetter php = new PHPGetter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Enlazar componentes visuales
        iniciarSesion = (Button) findViewById(R.id.bt_iniciarsesion_is);
        identificacion = (EditText) findViewById(R.id.et_identificacion_is);
        contraseña = (EditText) findViewById(R.id.et_contraseña_is);

        //Click listener
        iniciarSesion.setOnClickListener(this);

        setearListaEmpleados();
    }

    public void iniciarSesion(){
        String id = this.identificacion.getText().toString();
        String pass = this.contraseña.getText().toString();
        if(id.equalsIgnoreCase(idAdmin) & pass.equalsIgnoreCase(contraseñaAdmin)){
            usuario = new Empleado();
            usuario.setCargoEmpleado("administrador");
            usuario.setIdEmpleado(0);
            intent = new Intent(inicioSesion.this, VistaAdmin.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }
        if(this.verificarSesion(id,pass,empleados)){
            if(usuario.getCargoEmpleado().equalsIgnoreCase("administrador")){
                intent = new Intent(inicioSesion.this, VistaAdmin.class);
                intent.putExtra("usuario", usuario);
            }
            else{
                intent = new Intent(inicioSesion.this, MenuVista.class);
                intent.putExtra("usuario", usuario);
            }
            startActivity(intent);
        }
        else{
            Toast mensaje =
                    Toast.makeText(getApplicationContext(),
                            "usuario o contraseña incorrectos", Toast.LENGTH_SHORT);

            mensaje.show();

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_iniciarsesion_is:
                    setearListaEmpleados();
                    iniciarSesion();
               break;
            default:

                break;
        }
    }

    public void setearListaEmpleados(){
        String url = php.getObtenerEmpleados();
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        JSonAdapter json = new JSonAdapter();
                        try {
                            empleados = json.empleadoAdapter(respuesta);
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

    public boolean verificarSesion(String id, String pass, ArrayList<Empleado> listaEmpleados){
        listaEmpleados = this.empleados;
        for(int i =0;i<listaEmpleados.size();i++){
            String ident = Integer.toString(listaEmpleados.get(i).getIdEmpleado());
            String passw = listaEmpleados.get(i).getContraseñaEmpleado();
            if(id.equalsIgnoreCase(ident) & passw.equalsIgnoreCase(pass)){
                usuario = empleados.get(i);
                return true;
            }
        }
        return false;
    }
}

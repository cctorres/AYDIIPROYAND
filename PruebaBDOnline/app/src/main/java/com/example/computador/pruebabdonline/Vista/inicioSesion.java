package com.example.computador.pruebabdonline.Vista;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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



    //Declaración de variables
    private ArrayList<Empleado> empleados;
    private Button iniciarSesion;
    private EditText identificacion;
    private EditText contraseña;
    private Intent intent;
    private PHPGetter php = new PHPGetter();
    private int MY_PERMISSIONS_REQUEST_INTERNET;

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

        //Solicitar permiso a internet si no ha sido concedido
        solicitarPermisos();
        setearListaEmpleados();


    }

    /**
     * Ejecuta el método iniciar sesión
     */
    public void iniciarSesion(){
        //Tomar los datos digitados por el usuario
        String id = this.identificacion.getText().toString();
        String pass = this.contraseña.getText().toString();

        //Verificar si entró con los datos de "superusuario"
        if(id.equalsIgnoreCase(idAdmin) & pass.equalsIgnoreCase(contraseñaAdmin)){
            usuario = new Empleado();
            usuario.setCargoEmpleado("administrador");
            usuario.setIdEmpleado(0);
            intent = new Intent(inicioSesion.this, VistaAdmin.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }
        //Valida que los datos ingresados estén en la base de datos
        if(this.verificarSesion(id,pass,empleados)){
            //Si los datos son válidos, carga la vista de acuerdo al cargo que posea
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
            //Sí los datos son incorrectos, envia un mensaje de verificación
            Toast mensaje =
                    Toast.makeText(getApplicationContext(),
                            "usuario o contraseña incorrectos", Toast.LENGTH_SHORT);

            mensaje.show();

        }

    }

    /**
     * Métodos de los botones
     * @param v Vista
     */
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

    /**
     * Hace una llamada al servidor para buscar los empleados en la BD
     */
    public void setearListaEmpleados(){
        //busca el url del servidor
        String url = php.getObtenerEmpleados();
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        JSonAdapter json = new JSonAdapter();
                        try {
                            //Setea el array de empleados con el JSON recibido desde el servidor
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
        //Genera la petición
        VolleySingleton.getInstance(this).addToRequestQueue(peticion);
    }

    /**
     * Verifica que los datos ingresados sean congruentes con algún empleado del Arrat
     * @param id String con el ID del ususario
     * @param pass String con la contraseña del usuario
     * @param listaEmpleados Array con los empleados de la BD
     * @return Valor booleando dependiendo si el usuario es válido
     */
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

    /**
     * Solicita el permiso de internet si no ha sido otorgado
     */
    private void solicitarPermisos() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_INTERNET);
            }
        }
    }
}

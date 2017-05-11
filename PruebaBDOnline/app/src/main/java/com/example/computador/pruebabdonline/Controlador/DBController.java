package com.example.computador.pruebabdonline.Controlador;


import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.computador.pruebabdonline.Modelo.Restaurante;
import com.example.computador.pruebabdonline.Vista.VistaRestaurante;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DBController {

    private PHPGetter php = new PHPGetter();

    public DBController() {
    }

    public void actualizarRestaurante(String nombre, String direccion, String numero, String telefono, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getActualizarRestaurante();
        parametros.put("id_restaurante", "1");
        parametros.put("nombre_restaurante", nombre);
        parametros.put("direccion_restaurante", direccion);
        parametros.put("num_mesas_restaurante", numero);
        parametros.put("telefono_restaurante", telefono);

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(parametros),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores

                    }
                });
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }

    public void insertarEmpleado(String id, String nombre, String cargo, String telefono, String contraseña, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getInsertarEmpleado();
        parametros.put("id_empleado", id);
        parametros.put("nombre_empleado", nombre);
        parametros.put("cargo_empleado", cargo);
        parametros.put("telefono_empleado", telefono);
        parametros.put("contraseña_empleado", contraseña);

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(parametros),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejo de errores

                    }
                });
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }
}

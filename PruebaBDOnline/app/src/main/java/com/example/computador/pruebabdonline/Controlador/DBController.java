package com.example.computador.pruebabdonline.Controlador;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.Time;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.Modelo.Restaurante;
import com.example.computador.pruebabdonline.R;
import com.example.computador.pruebabdonline.Vista.ComidaDetalle;
import com.example.computador.pruebabdonline.Vista.VistaRestaurante;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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

    public void eliminarEmpleado(int cod, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getBorrarEmpleado();
        parametros.put("cod_empleado", Integer.toString((cod)));
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

    public void actualizarEmpleado(String codigo, String ident, String nombre, String cargo, String telefono, String contraseña, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getActualizarEmpleado();
        parametros.put("cod_empleado", codigo);
        parametros.put("id_empleado", ident);
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

    public void eliminarIngrediente(String id, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getBorrarIngrediente();
        parametros.put("id_ingrediente", id);
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

    public void agregarComida(String nombre, String precio, String categoria, String restriccion, String descripcion,
                                 String ingredientes, String codigoImagen, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getInsertarComida();

        parametros.put("nombre_comida", nombre);
        parametros.put("precio_comida", precio);
        parametros.put("categoria_comida", categoria);
        parametros.put("restriccion_comida", restriccion);
        parametros.put("descripcion_comida", descripcion);
        parametros.put("ingredientes_comida", ingredientes);
        parametros.put("foto_comida", codigoImagen);
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

    public void actualizarComida(String id,String nombre, String precio, String categoria, String restriccion, String descripcion,
                              String ingredientes, String codigoImagen, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getActualizarComida();

        parametros.put("id_comida", id);
        parametros.put("nombre_comida", nombre);
        parametros.put("precio_comida", precio);
        parametros.put("categoria_comida", categoria);
        parametros.put("restriccion_comida", restriccion);
        parametros.put("descripcion_comida", descripcion);
        parametros.put("ingredientes_comida", ingredientes);
        parametros.put("foto_comida", codigoImagen);
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

    public void agregarIngrediente(String nombre, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getInsertarIngrediente();
        parametros.put("nombre_ingrediente", nombre);
        parametros.put("cantidad_ingrediente", "0");
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

    public void eliminarComida(String id, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getBorrarComida();
        parametros.put("id_comida", id);
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

    public void agregarPedido(String precioPedido, String estadoPedido, String codComidaPedido,
                              String codEmpleadoPedido, String idMesaPedido, Context ctx){

        Time dia = new Time (Time.getCurrentTimezone());
        dia.setToNow();
        String diaString = Integer.toString(dia.monthDay);
        String mesString = Integer.toString(dia.month);
        String añoString = Integer.toString(dia.year);
        String fechaPedido = diaString+"/"+mesString+"/"+añoString;

        HashMap<String, String> parametros = new HashMap();
        String url = php.getInsertarPedido();
        parametros.put("fecha_pedido", fechaPedido);
        parametros.put("precio_pedido", precioPedido);
        parametros.put("estado_pedido", estadoPedido);
        parametros.put("codigos_comida_pedido", codComidaPedido);
        parametros.put("cod_empleado_pedido", codEmpleadoPedido);
        parametros.put("id_mesa_pedido", idMesaPedido);
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

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

    //Clase encarga de hacer las peticiones Volley al servidor

    private PHPGetter php = new PHPGetter();

    /**
     * Constructor vacio
     */
    public DBController() {
    }

    /**
     * Genera una petición Volley de actualizar el Restaurante
     * @param nombre Nuevo nombre del restaurante
     * @param direccion Nueva dirección del restaurante
     * @param numero nuevo número del restaurante
     * @param telefono Nuevo telefono del restaurante
     * @param ctx Contexto que invoca el método
     */
    public void actualizarRestaurante(String nombre, String direccion, String numero, String telefono, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getActualizarRestaurante(); //Obtiene la URL de la petición
        //Setea los parametros envíados
        parametros.put("id_restaurante", "1");
        parametros.put("nombre_restaurante", nombre);
        parametros.put("direccion_restaurante", direccion);
        parametros.put("num_mesas_restaurante", numero);
        parametros.put("telefono_restaurante", telefono);
        //Genera una petición JSON
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
        //Envía la petición con le JSON generado
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Genera una petición para la inserción de un empleado a la BD
     * @param id ID del empleado
     * @param nombre nombre del empleado
     * @param cargo cargo del empleado
     * @param telefono telefono del empleado
     * @param contraseña contraseña del empleado
     * @param ctx contexto de la activity
     */
    public void insertarEmpleado(String id, String nombre, String cargo, String telefono, String contraseña, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getInsertarEmpleado(); //Obtiene la URL del php de la petición
        //Setea los datos enviados por el parametro
        parametros.put("id_empleado", id);
        parametros.put("nombre_empleado", nombre);
        parametros.put("cargo_empleado", cargo);
        parametros.put("telefono_empleado", telefono);
        parametros.put("contraseña_empleado", contraseña);
        //Genera una petición JSON
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
        //Envía la petición con le JSON generado
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Genera una petición que elimina un empleado
     * @param cod codigo del empleado que se eliminará
     * @param ctx contexto de la activity
     */
    public void eliminarEmpleado(int cod, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getBorrarEmpleado(); //Obtiene la URL del PHP necesario
        //Setea los datos enviados por el parametro
        parametros.put("cod_empleado", Integer.toString((cod)));
        //Genera una petición JSON
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
        //Envía la petición con le JSON generado
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Genera una petición que actualiza un empleado
     * @param codigo Codigo del empleado que se actualizará
     * @param ident ID del empleado
     * @param nombre nombre del empleado
     * @param cargo cargo del empleado
     * @param telefono telefono del empleado
     * @param contraseña contraseña del empleado
     * @param ctx Contexto de la activity
     */
    public void actualizarEmpleado(String codigo, String ident, String nombre, String cargo, String telefono, String contraseña, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getActualizarEmpleado();//Obtiene la URL del PHP necesario
        //Setea los datos enviados por el parametro
        parametros.put("cod_empleado", codigo);
        parametros.put("id_empleado", ident);
        parametros.put("nombre_empleado", nombre);
        parametros.put("cargo_empleado", cargo);
        parametros.put("telefono_empleado", telefono);
        parametros.put("contraseña_empleado", contraseña);
        //Genera una petición JSON
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
        //Envía la petición con le JSON generado
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Genera una petición que agrega una comida a la BD
     * @param nombre Nombre de la comida
     * @param precio precio de la comida
     * @param categoria categoria de la comida
     * @param restriccion restricción de la comida
     * @param descripcion descripción de la comida
     * @param ingredientes ingredientes de la comida
     * @param codigoImagen codigo de la imagen
     * @param ctx contexto de la aplicación
     */
    public void agregarComida(String nombre, String precio, String categoria, String restriccion, String descripcion,
                                 String ingredientes, String codigoImagen, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getInsertarComida();//Obtiene la URL del PHP necesario
        //Setea los datos enviados por el parametro
        parametros.put("nombre_comida", nombre);
        parametros.put("precio_comida", precio);
        parametros.put("categoria_comida", categoria);
        parametros.put("restriccion_comida", restriccion);
        parametros.put("descripcion_comida", descripcion);
        parametros.put("ingredientes_comida", ingredientes);
        parametros.put("foto_comida", codigoImagen);
        //Genera una petición JSON
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
        //Envía la petición con le JSON generado
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

    /**
     * Genera una petición que eliminará una comida de la BD
     * @param id ID de la comida que se eliminará
     * @param ctx Contexto de la activity
     */
    public void eliminarComida(String id, Context ctx){
        HashMap<String, String> parametros = new HashMap();
        String url = php.getBorrarComida(); //Obtiene la URL del PHP necesario
        //Setea los datos enviados por el parametro
        parametros.put("id_comida", id);
        //Genera una petición JSON
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
        //Envía la petición con le JSON generado
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Genera una petición que agrega un pedido a la BD
     * @param precioPedido Precio del pedido
     * @param estadoPedido estado del pedido
     * @param codComidaPedido codigo de la comida del pedido
     * @param codEmpleadoPedido codigo del empleado del pedido
     * @param idMesaPedido id de la mesa del pedido
     * @param ctx Contexto de la activity
     */
    public void agregarPedido(String precioPedido, String estadoPedido, String codComidaPedido,
                              String codEmpleadoPedido, String idMesaPedido, Context ctx){

        Time dia = new Time (Time.getCurrentTimezone());
        dia.setToNow();//Se pregunta por la fecha
        String diaString = Integer.toString(dia.monthDay);
        String mesString = Integer.toString(dia.month+1);
        String añoString = Integer.toString(dia.year);
        String fechaPedido = diaString+"/"+mesString+"/"+añoString;//Se le da a la fecha (dd/mm/yyyy)

        HashMap<String, String> parametros = new HashMap();
        String url = php.getInsertarPedido();//Obtiene la URL del PHP necesario
        //Setea los datos enviados por el parametro
        parametros.put("fecha_pedido", fechaPedido);
        parametros.put("precio_pedido", precioPedido);
        parametros.put("estado_pedido", estadoPedido);
        parametros.put("codigos_comida_pedido", codComidaPedido);
        parametros.put("cod_empleado_pedido", codEmpleadoPedido);
        parametros.put("id_mesa_pedido", idMesaPedido);
        //Genera una petición JSON
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
        //Envía la petición con le JSON generado
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsArrayRequest);
    }
}

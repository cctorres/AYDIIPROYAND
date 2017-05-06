package com.example.computador.pruebabdonline.Controlador;


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

public class BDController {

    //URL del servidor
    String URLD = "http://ameboid-grasses.000webhostapp.com";
    //rutas de los Web Services de la Tabla Mesa
    String obtenerMesas = URLD + "/obtenerMesas.php";
    String obtenerMesaByID = URLD + "/obtenerMesaByID.php";
    String insertarMesa = URLD + "/insertarMesa.php";
    String borrarMesa = URLD + "/borrarMesa.php";
    String actualizarMesa = URLD + "/actualizarMesa.php";
    //rutas de los Web Services de la Tabla Comida
    String obtenerComidas = URLD + "/obtenerComidas.php";
    String obtenerComidaByID = URLD + "/obtenerComidaByID.php";
    String insertarComida = URLD + "/insertarComida.php";
    String borrarComida = URLD + "/borrarComida.php";
    String actualizarComida = URLD + "/actualizarComida.php";
    //rutas de los Web Services de la Tabla Empleado
    String obtenerEmpleados = URLD + "/obtenerEmpleados.php";
    String obtenerEmpleadoByID = URLD + "/obtenerEmpleadoByID.php";
    String insertarEmpleado = URLD + "/insertarEmpleado.php";
    String borrarEmpleado = URLD + "/borrarEmpleado.php";
    String actualizarEmpleado = URLD + "/actualizarEmpleado.php";
    //rutas de los Web Services de la Tabla Pedido
    String obtenerPedidos = URLD + "/obtenerPedidos.php";
    String obtenerPedidoByID = URLD + "/obtenerPedidoByID.php";
    String insertarPedido = URLD + "/insertarPedido.php";
    String borrarPedido = URLD + "/borrarPedido.php";
    String actualizarPedido = URLD + "/actualizarPedido.php";
    //rutas de los Web Services de la Tabla Restaurante
    String obtenerRestaurantes = URLD + "/obtenerRestaurantes.php";
    String insertarRestaurante = URLD + "/insertarRestaurante.php";
    String borrarRestaurante = URLD + "/borrarRestaurante.php";
    String actualizarRestaurante = URLD + "/actualizarPedido.php";





    public String insertarMesa(String estadoMesa, String idPedidoMesa){
        String cadena = insertarMesa;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("estado_mesa", estadoMesa);
            jsonParam.put("id_pedido_mesa", idPedidoMesa);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerMesas(){
        String cadena = obtenerMesas;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            //Conexión
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a través de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay datos
                    JSONArray alumnosJSON = respuestaJSON.getJSONArray("mesas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<alumnosJSON.length();i++){
                        devuelve = devuelve + "("+alumnosJSON.getJSONObject(i).getString("id_mesa") + " * " +
                                alumnosJSON.getJSONObject(i).getString("estado_mesa") + " * " +
                                alumnosJSON.getJSONObject(i).getString("id_pedido_mesa") + ")";
                    }
                }
                else if (resultJSON.equalsIgnoreCase("2")){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String borrarMesa(String idMesa){
        String cadena = borrarMesa;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_mesa", idMesa);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {      // hay un alumno que mostrar
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String actualizarMesa(String idMesa, String estadoMesa, String idPedidoMesa){
        String cadena = actualizarMesa; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_mesa", idMesa);
            jsonParam.put("estado_mesa", estadoMesa);
            jsonParam.put("id_pedido_mesa", idPedidoMesa);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerMesaByID(String idMesa){
        String cadena = obtenerMesaByID+"?id_mesa="+idMesa; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay un alumno que mostrar
                    devuelve = devuelve + "("+respuestaJSON.getJSONObject("mesas").getString("id_mesa") + " * " +
                            respuestaJSON.getJSONObject("mesas").getString("estado_mesa") + " * " +
                            respuestaJSON.getJSONObject("mesas").getString("id_pedido_mesa")+")";

                }
                else if (resultJSON=="2"){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerComidas(){
        String cadena = obtenerComidas;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            //Conexión
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a través de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay datos
                    JSONArray alumnosJSON = respuestaJSON.getJSONArray("comidas");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<alumnosJSON.length();i++){
                        devuelve = devuelve + "("+alumnosJSON.getJSONObject(i).getString("id_comida") + " * " +
                                alumnosJSON.getJSONObject(i).getString("precio_comida") + " * " +
                                alumnosJSON.getJSONObject(i).getString("categoria_comida") + " * " +
                                alumnosJSON.getJSONObject(i).getString("restriccion_comida") + " * " +
                                alumnosJSON.getJSONObject(i).getString("descripcion_comida") + " * " +
                                alumnosJSON.getJSONObject(i).getString("ingredientes_comida") + " * " +
                                alumnosJSON.getJSONObject(i).getString("foto_comida") + ")";
                    }
                }
                else if (resultJSON.equalsIgnoreCase("2")){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerComidaByID(String idComida){
        String cadena = obtenerComidaByID+"?id_comida="+idComida; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay un alumno que mostrar
                    devuelve = devuelve + "("+respuestaJSON.getJSONObject("comidas").getString("id_comida") + " * " +
                            respuestaJSON.getJSONObject("comidas").getString("precio_comida") + " * " +
                            respuestaJSON.getJSONObject("comidas").getString("categoria_comida") + " * " +
                            respuestaJSON.getJSONObject("comidas").getString("restriccion_comida") + " * " +
                            respuestaJSON.getJSONObject("comidas").getString("descripcion_comida") + " * " +
                            respuestaJSON.getJSONObject("comidas").getString("ingredientes_comida") + " * " +
                            respuestaJSON.getJSONObject("comidas").getString("foto_comida")+")";

                }
                else if (resultJSON=="2"){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String insertarComida(String precioComida, String categoriaComida, String restriccionComida,
                                 String descripcionComida, String ingredientesComida, String fotoComida){
        String cadena = insertarComida;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("precio_comida", precioComida);
            jsonParam.put("categoria_comida", categoriaComida);
            jsonParam.put("restriccion_comida", restriccionComida);
            jsonParam.put("descripcion_comida", descripcionComida);
            jsonParam.put("ingredientes_comida", ingredientesComida);
            jsonParam.put("foto_comida", fotoComida);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String borrarComida(String idComida){
        String cadena = borrarComida;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_comida", idComida);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String actualizarMesa(String idComida, String precioComida, String categoriaComida, String restriccionComida,
                                 String descripcionComida, String ingredientesComida, String fotoComida){
        String cadena = actualizarComida; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_comida", idComida);
            jsonParam.put("precio_comida", precioComida);
            jsonParam.put("categoria_comida", categoriaComida);
            jsonParam.put("restriccion_comida", restriccionComida);
            jsonParam.put("descripcion_comida", descripcionComida);
            jsonParam.put("ingredientes_comida", ingredientesComida);
            jsonParam.put("foto_comida", fotoComida);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerEmpleados(){
        String cadena = obtenerEmpleados;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            //Conexión
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a través de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay datos
                    JSONArray alumnosJSON = respuestaJSON.getJSONArray("empleados");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<alumnosJSON.length();i++){
                        devuelve = devuelve + "("+alumnosJSON.getJSONObject(i).getString("cod_empleado") + " * " +
                                alumnosJSON.getJSONObject(i).getString("id_empleado") + " * " +
                                alumnosJSON.getJSONObject(i).getString("nombre_empleado") + " * " +
                                alumnosJSON.getJSONObject(i).getString("cargo_empleado") + " * " +
                                alumnosJSON.getJSONObject(i).getString("telefono_empleado") + " * " +
                                alumnosJSON.getJSONObject(i).getString("contraseña_empleado") + ")";
                    }
                }
                else if (resultJSON.equalsIgnoreCase("2")){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerEmpleadoByID(String codEmpleado){
        String cadena = obtenerEmpleadoByID+"?cod_empleado="+codEmpleado; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay un alumno que mostrar
                    devuelve = devuelve + "("+respuestaJSON.getJSONObject("empleados").getString("cod_empleado") + " * " +
                            respuestaJSON.getJSONObject("empleados").getString("id_empleado") + " * " +
                            respuestaJSON.getJSONObject("empleados").getString("nombre_empleado") + " * " +
                            respuestaJSON.getJSONObject("empleados").getString("cargo_empleado") + " * " +
                            respuestaJSON.getJSONObject("empleados").getString("telefono_empleado") + " * " +
                            respuestaJSON.getJSONObject("empleados").getString("contraseña_empleado")+")";

                }
                else if (resultJSON=="2"){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String insertarEmpleado(String idEmpleado, String nombreEmpleado, String cargoEmpleado,
                                   String telefonoEmpleado, String contraseñaEmpleado){
        String cadena = insertarEmpleado;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_empleado", idEmpleado);
            jsonParam.put("nombre_empleado", nombreEmpleado);
            jsonParam.put("cargo_empleado", cargoEmpleado);
            jsonParam.put("telefono_empleado", telefonoEmpleado);
            jsonParam.put("contraseña_empleado", contraseñaEmpleado);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String borrarEmpleado(String codEmpleado){
        String cadena = borrarEmpleado;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("cod_empleado", codEmpleado);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String actualizarEmpleado(String codEmpleado, String idEmpleado, String nombreEmpleado, String cargoEmpleado,
                                 String telefonoEmpleado, String contraseñaEmpleado){
        String cadena = actualizarEmpleado; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("cod_empleado", codEmpleado);
            jsonParam.put("id_empleado", idEmpleado);
            jsonParam.put("nombre_empleado", nombreEmpleado);
            jsonParam.put("cargo_empleado", cargoEmpleado);
            jsonParam.put("telefono_empleado", telefonoEmpleado);
            jsonParam.put("contraseña_empleado", contraseñaEmpleado);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerPedidos(){
        String cadena = obtenerPedidos;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            //Conexión
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a través de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay datos
                    JSONArray alumnosJSON = respuestaJSON.getJSONArray("pedidos");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<alumnosJSON.length();i++){
                        devuelve = devuelve + "("+alumnosJSON.getJSONObject(i).getString("id_pedido") + " * " +
                                alumnosJSON.getJSONObject(i).getString("fecha_pedido") + " * " +
                                alumnosJSON.getJSONObject(i).getString("precio_pedido") + " * " +
                                alumnosJSON.getJSONObject(i).getString("estado_pedido") + " * " +
                                alumnosJSON.getJSONObject(i).getString("codigos_comida_pedido") + " * " +
                                alumnosJSON.getJSONObject(i).getString("cod_empleado_pedido") + " * " +
                                alumnosJSON.getJSONObject(i).getString("id_mesa_pedido") + ")";
                    }
                }
                else if (resultJSON.equalsIgnoreCase("2")){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerPedidoByID(String idPedido){
        String cadena = obtenerPedidoByID+"?id_pedido="+idPedido; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay un alumno que mostrar
                    devuelve = devuelve + "("+respuestaJSON.getJSONObject("pedidos").getString("id_pedido") + " * " +
                            respuestaJSON.getJSONObject("pedidos").getString("fecha_pedido") + " * " +
                            respuestaJSON.getJSONObject("pedidos").getString("precio_pedido") + " * " +
                            respuestaJSON.getJSONObject("pedidos").getString("estado_pedido") + " * " +
                            respuestaJSON.getJSONObject("pedidos").getString("codigos_comida_pedido") + " * " +
                            respuestaJSON.getJSONObject("pedidos").getString("cod_empleado_pedido") + " * " +
                            respuestaJSON.getJSONObject("pedidos").getString("id_mesa_pedido")+")";

                }
                else if (resultJSON=="2"){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String insertarPedido(String fechaPedido, String precioPedido, String estadoPedido, String codigosComidaPedido,
                                   String codEmpleadoPedido, String idMesaPedido){
        String cadena = insertarPedido;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("fecha_pedido", fechaPedido);
            jsonParam.put("precio_pedido", precioPedido);
            jsonParam.put("estado_pedido", estadoPedido);
            jsonParam.put("codigos_comida_pedido", codigosComidaPedido);
            jsonParam.put("cod_empleado_pedido", codEmpleadoPedido);
            jsonParam.put("id_mesa_pedido", idMesaPedido);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String borrarPedido(String idPedido){
        String cadena = borrarPedido;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_pedido", idPedido);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String actualizarPedido(String idPedido, String fechaPedido, String precioPedido, String estadoPedido,
                                   String codigosComidaPedido, String codEmpleadoPedido, String idMesaPedido){
        String cadena = actualizarPedido; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_pedido", idPedido);
            jsonParam.put("fecha_pedido", fechaPedido);
            jsonParam.put("precio_pedido", precioPedido);
            jsonParam.put("estado_pedido", estadoPedido);
            jsonParam.put("codigos_comida_pedido", codigosComidaPedido);
            jsonParam.put("cod_empleado_pedido", codEmpleadoPedido);
            jsonParam.put("id_mesa_pedido", idMesaPedido);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String obtenerRestaurantes(){
        String cadena = obtenerRestaurantes;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            //Conexión
            url = new URL(cadena);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
            //connection.setHeader("content-type", "application/json");
            int respuesta = connection.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader
                // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                // que tranformar el BufferedReader a String. Esto lo hago a través de un
                // StringBuilder.
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")){      // hay datos
                    JSONArray alumnosJSON = respuestaJSON.getJSONArray("restaurantes");   // estado es el nombre del campo en el JSON
                    for(int i=0;i<alumnosJSON.length();i++){
                        devuelve = devuelve + "("+alumnosJSON.getJSONObject(i).getString("id_restaurante") + " * " +
                                alumnosJSON.getJSONObject(i).getString("nombre_restaurante") + " * " +
                                alumnosJSON.getJSONObject(i).getString("direccion_restaurante") + " * " +
                                alumnosJSON.getJSONObject(i).getString("num_mesas_restaurante") + " * " +
                                alumnosJSON.getJSONObject(i).getString("telefono_restaurante") + ")";
                    }
                }
                else if (resultJSON.equalsIgnoreCase("2")){
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

   public String insertarRestaurante(String nombreRestaurante, String direccionRestaurante,
                                     String numMesasRestaurante, String telefonoRestaurante){
        String cadena = insertarRestaurante;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            DataOutputStream printout;
            DataInputStream input;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("nombre_restaurante", nombreRestaurante);
            jsonParam.put("direccion_restaurante", direccionRestaurante);
            jsonParam.put("num_mesas_restaurante", numMesasRestaurante);
            jsonParam.put("telefono_restaurante", telefonoRestaurante);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String borrarRestaurante(String idRestaurante){
        String cadena = borrarRestaurante;
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_restaurante", idRestaurante);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result.append(line);
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }

    public String actualizarRestaurante(String idRestaurante, String nombreRestaurante, String direccionRestaurante,
                                        String numMesasRestaurante, String telefonoRestaurante){
        String cadena = actualizarRestaurante; //String con la dirección del PHP
        URL url = null; // Url de donde queremos obtener información
        String devuelve ="";
        try {
            HttpURLConnection urlConn;
            url = new URL(cadena);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setDoInput(true);
            urlConn.setDoOutput(true);
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            urlConn.connect();
            //Creo el Objeto JSON
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id_restaurante", idRestaurante);
            jsonParam.put("nombre_restaurante", nombreRestaurante);
            jsonParam.put("direccion_restaurante", direccionRestaurante);
            jsonParam.put("num_mesas_restaurante", numMesasRestaurante);
            jsonParam.put("telefono_restaurante", telefonoRestaurante);
            // Envio los parámetros post.
            OutputStream os = urlConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonParam.toString());
            writer.flush();
            writer.close();
            int respuesta = urlConn.getResponseCode();
            StringBuilder result = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    result.append(line);        // Paso toda la entrada al StringBuilder
                }
                //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                //Accedemos al vector de resultados
                String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                if (resultJSON.equalsIgnoreCase("1")) {
                    devuelve = "correcto";
                } else if (resultJSON.equalsIgnoreCase("2")) {
                    devuelve = "incorrecto";
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return devuelve;
    }
}

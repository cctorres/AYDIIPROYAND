package com.example.computador.pruebabdonline.Controlador;


import com.example.computador.pruebabdonline.Modelo.Comida;
import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.Modelo.Pedido;
import com.example.computador.pruebabdonline.Modelo.Restaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSonAdapter {

    //Clase encargada de adaptar el formato JSON a los objetos

    public JSonAdapter() {
    }

    /**
     * Genera un objeto de la clase Restaurante que esté en JSON
     * @param json String con formato JSON
     * @return Retorna un Objeto de tipo Restaurante
     * @throws JSONException
     */
    public Restaurante restauranteAdapter(String json) throws JSONException {
        String nombre, direccion, telefono;
        int id, numero;
        nombre = direccion = telefono = "";
        id = numero = 0;
        //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
        JSONObject respuestaJSON = new JSONObject(json);   //Creo un JSONObject a partir del StringBuilder pasado a cadena
        //Accedemos al vector de resultados
        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
        if (resultJSON.equalsIgnoreCase("1")){      // hay datos
            JSONArray objetoJSON = respuestaJSON.getJSONArray("Restaurante");   // estado es el nombre del campo en el JSON
            for(int i=0;i<objetoJSON.length();i++){
                        id = Integer.parseInt(objetoJSON.getJSONObject(i).getString("id_restaurante"));
                        nombre = objetoJSON.getJSONObject(i).getString("nombre_restaurante");
                        direccion = objetoJSON.getJSONObject(i).getString("direccion_restaurante");
                        numero = Integer.parseInt(objetoJSON.getJSONObject(i).getString("num_mesas_restaurante"));
                        telefono = objetoJSON.getJSONObject(i).getString("telefono_restaurante");
            }
            Restaurante objeto = new Restaurante(id, nombre, direccion, numero, telefono);
            return(objeto);
        }
        return null;
    }

    /**
     * Genera un array de objetos de la clase empleado que esté en JSON
     * @param json String con formato JSON
     * @return Retorna un array de objetos de la clase empleado
     * @throws JSONException
     */
    public ArrayList<Empleado> empleadoAdapter(String json) throws JSONException {
        ArrayList<Empleado> array = new ArrayList<Empleado>();
        int codEmpleado, idEmpleado,telefonoEmpleado;
        String nombreEmpleado, cargoEmpleado, contraseñaEmpleado;
        nombreEmpleado = cargoEmpleado = contraseñaEmpleado = "";
        codEmpleado = idEmpleado = telefonoEmpleado = 0;
        //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
        JSONObject respuestaJSON = new JSONObject(json);   //Creo un JSONObject a partir del StringBuilder pasado a cadena
        //Accedemos al vector de resultados
        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
        if (resultJSON.equalsIgnoreCase("1")){      // hay datos
            JSONArray objetoJSON = respuestaJSON.getJSONArray("empleados");   // estado es el nombre del campo en el JSON
            for(int i=0;i<objetoJSON.length();i++){
                codEmpleado = Integer.parseInt(objetoJSON.getJSONObject(i).getString("cod_empleado"));
                idEmpleado = Integer.parseInt(objetoJSON.getJSONObject(i).getString("id_empleado"));
                nombreEmpleado = objetoJSON.getJSONObject(i).getString("nombre_empleado");
                cargoEmpleado = objetoJSON.getJSONObject(i).getString("cargo_empleado");
                telefonoEmpleado = Integer.parseInt(objetoJSON.getJSONObject(i).getString("telefono_empleado"));
                contraseñaEmpleado = objetoJSON.getJSONObject(i).getString("contraseña_empleado");
                Empleado objeto = new Empleado(codEmpleado, idEmpleado, telefonoEmpleado, nombreEmpleado,cargoEmpleado, contraseñaEmpleado);
                array.add(objeto);
            }

        }
        return array;
    }

    /**
     * Genera un array de objetos de la clase pedido que esté en JSON
     * @param json String con formato JSON
     * @return Retorna un array de objetos de la clase pedido
     * @throws JSONException
     */
    public ArrayList<Pedido> pedidoAdapter(String json) throws JSONException {
        ArrayList<Pedido> array = new ArrayList<>();
        int idPedido, precioPedido,codEmpleadoPedido, idMesaPedido;
        String estadoPedido, codigosComidaPedido, fechaPed;

        estadoPedido = codigosComidaPedido = fechaPed = "";
        idPedido = precioPedido = codEmpleadoPedido = idMesaPedido = 0;
        //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
        JSONObject respuestaJSON = new JSONObject(json);   //Creo un JSONObject a partir del StringBuilder pasado a cadena
        //Accedemos al vector de resultados
        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
        if (resultJSON.equalsIgnoreCase("1")){      // hay datos
            JSONArray objetoJSON = respuestaJSON.getJSONArray("pedidos");   // estado es el nombre del campo en el JSON
            for(int i=0;i<objetoJSON.length();i++){
                idPedido = Integer.parseInt(objetoJSON.getJSONObject(i).getString("id_pedido"));
                fechaPed = objetoJSON.getJSONObject(i).getString("fecha_pedido");
                precioPedido = Integer.parseInt(objetoJSON.getJSONObject(i).getString("precio_pedido"));
                estadoPedido = objetoJSON.getJSONObject(i).getString("estado_pedido");
                codEmpleadoPedido = Integer.parseInt(objetoJSON.getJSONObject(i).getString("cod_empleado_pedido"));
                idMesaPedido = Integer.parseInt(objetoJSON.getJSONObject(i).getString("id_mesa_pedido"));
                Pedido objeto = new Pedido(idPedido, precioPedido, codEmpleadoPedido, idMesaPedido, estadoPedido, codigosComidaPedido, fechaPed);
                array.add(objeto);
            }

        }
        return array;
    }


    /**
     * Genera un array de objetos de la clase comida que esté en JSON
     * @param json String con formato JSON
     * @return Retorna un array de objetos de la clase comida
     * @throws JSONException
     */
    public ArrayList<Comida> comidaAdapter(String json) throws JSONException {
        ArrayList<Comida> array = new ArrayList<>();
        int idComida, precioComida;
        String nombreComida, categoriaComida, restriccionComida, descripcionComida, ingredientesComida, fotoComida;

        nombreComida = categoriaComida = restriccionComida = descripcionComida = ingredientesComida = fotoComida = "";
        idComida = precioComida = 0;
        //Se crea un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
        JSONObject respuestaJSON = new JSONObject(json);   //Creo un JSONObject a partir del StringBuilder pasado a cadena
        //Accedemos al vector de resultados
        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
        if (resultJSON.equalsIgnoreCase("1")){      // hay datos
            JSONArray objetoJSON = respuestaJSON.getJSONArray("comidas");   // estado es el nombre del campo en el JSON
            for(int i=0;i<objetoJSON.length();i++){
                idComida = Integer.parseInt(objetoJSON.getJSONObject(i).getString("id_comida"));
                nombreComida = objetoJSON.getJSONObject(i).getString("nombre_comida");
                precioComida = Integer.parseInt(objetoJSON.getJSONObject(i).getString("precio_comida"));
                categoriaComida = objetoJSON.getJSONObject(i).getString("categoria_comida");
                restriccionComida = objetoJSON.getJSONObject(i).getString("restriccion_comida");
                descripcionComida = objetoJSON.getJSONObject(i).getString("descripcion_comida");
                ingredientesComida = objetoJSON.getJSONObject(i).getString("ingredientes_comida");
                fotoComida = objetoJSON.getJSONObject(i).getString("foto_comida");
                Comida objeto = new Comida(idComida, precioComida, nombreComida, categoriaComida, restriccionComida, descripcionComida, ingredientesComida, fotoComida);
                array.add(objeto);
            }

        }
        return array;
    }


}

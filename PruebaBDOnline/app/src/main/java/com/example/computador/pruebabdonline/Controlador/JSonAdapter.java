package com.example.computador.pruebabdonline.Controlador;


import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.Modelo.Restaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSonAdapter {

    public JSonAdapter() {
    }

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
}

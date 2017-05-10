package com.example.computador.pruebabdonline.Controlador;


import com.example.computador.pruebabdonline.Modelo.Restaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            JSONArray objetoJSON = respuestaJSON.getJSONArray("restaurantes");   // estado es el nombre del campo en el JSON
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
}

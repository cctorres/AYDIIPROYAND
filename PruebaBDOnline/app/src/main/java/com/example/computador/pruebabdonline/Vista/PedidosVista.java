package com.example.computador.pruebabdonline.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computador.pruebabdonline.Controlador.JSonAdapter;
import com.example.computador.pruebabdonline.Controlador.PHPGetter;
import com.example.computador.pruebabdonline.Controlador.VolleySingleton;
import com.example.computador.pruebabdonline.Modelo.Ingrediente;
import com.example.computador.pruebabdonline.Modelo.Mesa;
import com.example.computador.pruebabdonline.Modelo.MesaAdapter;
import com.example.computador.pruebabdonline.R;

import org.json.JSONException;

import java.util.ArrayList;

public class PedidosVista extends AppCompatActivity {

    private ListView listaMesas;
    private ArrayList<Mesa> todasMesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_vista);

        listaMesas = (ListView) findViewById(R.id.lv_mesas_pv);


    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarListViewPedidos();


    }

    public void llenarListViewPedidos(){
        PHPGetter php = new PHPGetter();
        String url = php.getObtenerMesas();
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        JSonAdapter json = new JSonAdapter();
                        final ArrayList<Mesa> mesasLista;
                        try {
                            todasMesas = json.mesaAdapter(respuesta);
                            MesaAdapter adapter = new MesaAdapter(PedidosVista.this, todasMesas);
                            listaMesas.setAdapter(adapter);
                            listaMesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
}

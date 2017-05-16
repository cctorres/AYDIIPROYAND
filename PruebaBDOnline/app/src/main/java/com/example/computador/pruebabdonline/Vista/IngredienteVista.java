package com.example.computador.pruebabdonline.Vista;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computador.pruebabdonline.Controlador.DBController;
import com.example.computador.pruebabdonline.Controlador.JSonAdapter;
import com.example.computador.pruebabdonline.Controlador.PHPGetter;
import com.example.computador.pruebabdonline.Controlador.VolleySingleton;
import com.example.computador.pruebabdonline.Modelo.Ingrediente;
import com.example.computador.pruebabdonline.Modelo.IngredienteAdapter;
import com.example.computador.pruebabdonline.Modelo.Restaurante;
import com.example.computador.pruebabdonline.R;

import org.json.JSONException;

import java.util.ArrayList;

public class IngredienteVista extends AppCompatActivity implements View.OnClickListener{

    private ListView lvIngredientes;
    private Button agregarIng, eliminarIng;
    private PHPGetter php = new PHPGetter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingrediente_vista);

        //Enlazar componentes visuales

        lvIngredientes = (ListView) findViewById(R.id.lv_ingredientes_iv);
        agregarIng = (Button) findViewById(R.id.bt_agregar_iv);
        eliminarIng = (Button) findViewById(R.id.bt_eliminar_iv);

        //Click listener
        agregarIng.setOnClickListener(this);
        eliminarIng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_agregar_iv:
                agregarIngrediente();
                break;
            case R.id.bt_eliminar_iv:
                eliminarIngrediente();
                break;
            default:

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarListViewIngredientes();


    }

    public void eliminarIngrediente() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_eliminar_ingrediente, null);
        builder.setView(v);
        Button cancelar = (Button) v.findViewById(R.id.bt_cancelar_dialog_ei);
        Button confirmar = (Button) v.findViewById(R.id.bt_confirmar_dialog_ei);
        final EditText idIngrediente = (EditText) v.findViewById(R.id.et_identificacion_dialog_ei);
        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        IngredienteVista.this.startActivity(IngredienteVista.this.getIntent());
                    }
                }
        );

        confirmar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = idIngrediente.getText().toString();
                        DBController db = new DBController();
                        db.eliminarIngrediente(id, IngredienteVista.this);
                        finish();
                        IngredienteVista.this.startActivity(IngredienteVista.this.getIntent());
                    }
                }
        );
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    public void agregarIngrediente() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_agregar_ingrediente, null);
        builder.setView(v);
        Button cancelar = (Button) v.findViewById(R.id.bt_cancelar_dialog_ai);
        Button confirmar = (Button) v.findViewById(R.id.bt_confirmar_dialog_ai);
        final EditText nombreIngrediente = (EditText) v.findViewById(R.id.et_nombre_dialog_ai);
        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        IngredienteVista.this.startActivity(IngredienteVista.this.getIntent());
                    }
                }
        );

        confirmar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombre = nombreIngrediente.getText().toString();
                        DBController db = new DBController();
                        db.agregarIngrediente(nombre, IngredienteVista.this);
                        finish();
                        IngredienteVista.this.startActivity(IngredienteVista.this.getIntent());
                    }
                }
        );
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    public void llenarListViewIngredientes(){
        String url = php.getObtenerIngredientes();
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        JSonAdapter json = new JSonAdapter();
                        final ArrayList<Ingrediente> ingredientesLista;
                        try {
                            ingredientesLista = json.ingredienteAdapter(respuesta);
                            IngredienteAdapter adapter = new IngredienteAdapter(IngredienteVista.this, ingredientesLista);
                            lvIngredientes.setAdapter(adapter);
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

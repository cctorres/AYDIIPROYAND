package com.example.computador.pruebabdonline.Vista;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computador.pruebabdonline.Controlador.DBController;
import com.example.computador.pruebabdonline.Controlador.JSonAdapter;
import com.example.computador.pruebabdonline.Controlador.PHPGetter;
import com.example.computador.pruebabdonline.Controlador.VolleySingleton;
import com.example.computador.pruebabdonline.Modelo.Comida;
import com.example.computador.pruebabdonline.Modelo.ComidaAdapter;
import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.Modelo.EmpleadoAdapter;
import com.example.computador.pruebabdonline.R;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MenuVista extends AppCompatActivity implements View.OnClickListener{

    private String[] categorias = new String[] {"Comida tipica", "Bebida", "Comida vegetariana", "Asado", "Comida de mar", "Otros", " "};
    private String[] restricciones = new String[] {"Alto en lactosa", "Alto en calorias", "Alto en azúcar", "Ninguna"};
    private ListView lvListaComidas;
    private Button agregar, filtrar;
    private CheckBox cbAzucar,cbCalorias,cbLactosa, cbEditar;
    private boolean bAzucar, bCalorias, bLactosa;
    private Spinner spCategorias;
    private final int SELECT_PICTURE = 300;
    private String codigoImagen = "";
    private Empleado usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vista);

        lvListaComidas = (ListView) findViewById(R.id.lv_comidas_mv);
        agregar = (Button) findViewById(R.id.bt_agregar_mv);
        filtrar = (Button) findViewById(R.id.bt_filtrar_mv);
        cbAzucar = (CheckBox) findViewById(R.id.cb_azucar_mv);
        cbCalorias = (CheckBox) findViewById(R.id.cb_calorias_mv);
        cbLactosa = (CheckBox) findViewById(R.id.cb_lactosa_mv);
        cbEditar = (CheckBox) findViewById(R.id.cb_editar);
        spCategorias = (Spinner) findViewById(R.id.sp_categoria_mv);


        agregar.setOnClickListener(this);
        filtrar.setOnClickListener(this);

        llenarSpinnerCategoria(categorias,spCategorias);

        usuario = (Empleado) getIntent().getSerializableExtra("usuario");
        if(getIntent().hasExtra("valorCaloria")){
            bCalorias = getIntent().getExtras().getBoolean("valorCaloria");
        }
        if(getIntent().hasExtra("valorAzucar")){
            bAzucar = getIntent().getExtras().getBoolean("valorAzucar");
        }
        if(getIntent().hasExtra("valorLactosa")){
            bLactosa = getIntent().getExtras().getBoolean("valorLactosa");
        }


        if(bCalorias==true){
            cbCalorias.setChecked(true);
        }
        if(bLactosa==true){
            cbLactosa.setChecked(true);
        }
        if(bAzucar==true){
            cbAzucar.setChecked(true);
        }

        onResume();


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_agregar_mv:
                insertarComidaDialog();

                break;
            case R.id.bt_filtrar_mv:
                int i = lvListaComidas.getCount();
                i = i+1;
                filtrarCheckbox(lvListaComidas);
                /*Intent intent = new Intent(MenuVista.this, MenuVista.class);
                if(cbAzucar.isChecked()){
                    bAzucar = true;
                }
                if(cbLactosa.isChecked()){
                    bLactosa = true;
                }
                if(cbCalorias.isChecked()){
                    bCalorias = true;
                }
                intent.putExtra("valorLactosa", bLactosa);
                intent.putExtra("valorCaloria", bCalorias);
                intent.putExtra("valorAzucar", bAzucar);
                startActivity(intent);*/

                break;
            default:

                break;
        }
    }

    public void insertarComidaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_agregar_comida, null);
        builder.setView(v);
        Button cancelar = (Button) v.findViewById(R.id.bt_cancelar_dialog_ac);
        Button confirmar = (Button) v.findViewById(R.id.bt_confirmar_dialog_ac);
        Button imagen = (Button) v.findViewById(R.id.bt_imagen_dialog_ac);
        imagen.setOnClickListener(this);
        final EditText nombreDialog = (EditText) v.findViewById(R.id.et_nombre_dialog_ac);
        final EditText precioDialog = (EditText) v.findViewById(R.id.et_precio_dialog_ac);
        final EditText descripcionDialog = (EditText) v.findViewById(R.id.et_descrpcion_dialog_ac);
        final EditText ingredientesDialog = (EditText) v.findViewById(R.id.et_ingredientes_dialog_ac);
        final Spinner categoriaDialog = (Spinner) v.findViewById(R.id.sp_categoria_ac);
        final Spinner restriccionDialog = (Spinner) v.findViewById(R.id.sp_restriccion_ac);
        llenarSpinners(categorias,restricciones, categoriaDialog, restriccionDialog);

        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        MenuVista.this.startActivity(MenuVista.this.getIntent());
                    }
                }
        );

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
            }
        });

        confirmar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombre = nombreDialog.getText().toString();
                        String precio = precioDialog.getText().toString();
                        String descripcion = descripcionDialog.getText().toString();
                        String ingredientes = ingredientesDialog.getText().toString();
                        String categoria = categoriaDialog.getSelectedItem().toString();
                        String restriccion = restriccionDialog.getSelectedItem().toString();
                        DBController db = new DBController();
                        db.agregarComida(nombre, precio, categoria, restriccion, descripcion, ingredientes, codigoImagen, MenuVista.this);
                        finish();
                        MenuVista.this.startActivity(MenuVista.this.getIntent());
                    }
                }
        );
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    public void llenarSpinners(String[] categorias, String[] restricciones, Spinner categoriasp, Spinner restriccionessp){
        ArrayAdapter<String> adapterCat = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categorias);
        categoriasp.setAdapter(adapterCat);
        ArrayAdapter<String> adapterRest = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, restricciones);
        restriccionessp.setAdapter(adapterRest);
    }

    public void llenarSpinnerCategoria(String[] categorias, Spinner categoriasp){
        ArrayAdapter<String> adapterCat = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categorias);
        categoriasp.setAdapter(adapterCat);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        byte[] byteArray = stream.toByteArray();
                        codigoImagen = byteArray.toString();
                        codigoImagen = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_LONG);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarSpinnerCategoria(categorias,spCategorias);
        PHPGetter php = new PHPGetter();
        String url = php.getObtenerComidas();
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        JSonAdapter json = new JSonAdapter();
                        final ArrayList<Comida> comidaLista;
                        try {
                            comidaLista = json.comidaAdapter(respuesta);
                            ComidaAdapter adapter = new ComidaAdapter(MenuVista.this, comidaLista);
                            lvListaComidas.setAdapter(adapter);
                            lvListaComidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent;
                                    if(cbEditar.isChecked()){
                                        intent = new Intent(MenuVista.this, ComidaDetalle.class);
                                        intent.putExtra("comidaObject",comidaLista.get(position));
                                        startActivity(intent);
                                    }
                                    else{
                                        Comida comidaSel = comidaLista.get(position);
                                        DBController db = new DBController();
                                        db.agregarPedido(Integer.toString(comidaSel.getPrecioComida()),
                                                "Proceso",
                                                Integer.toString(comidaSel.getIdComida()),
                                                Integer.toString(usuario.getCodEmpleado()),
                                                "1",
                                                MenuVista.this);
                                    }

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

    public void filtrarCheckbox(ListView listaComidas){
        final ArrayList<Comida> comidasFiltradas = new ArrayList<>();
        Comida comida = new Comida();
        for(int i = 0; i<listaComidas.getCount();i++){
            comida = (Comida) listaComidas.getItemAtPosition(i);
            if(!(cbAzucar.isChecked() & cbLactosa.isChecked() & cbCalorias.isChecked())){
                comidasFiltradas.add(comida);
                continue;
            }
            if(cbLactosa.isChecked()){
                if(!comida.getRestriccionComida().equalsIgnoreCase("Alto en lactosa")){
                    comidasFiltradas.add(comida);
                    continue;
                }
            }
            if(cbAzucar.isChecked()){
                if(!comida.getRestriccionComida().equalsIgnoreCase("Alto en azúcar")){
                    comidasFiltradas.add(comida);
                    continue;
                }
            }
            if(cbCalorias.isChecked()){
                if(!comida.getRestriccionComida().equalsIgnoreCase("Alto en calorias")){
                    comidasFiltradas.add(comida);
                    continue;
                }
            }
        }
        for(int i = 0;i<comidasFiltradas.size();i++){
            comida = comidasFiltradas.get(i);
            String filtro = spCategorias.getSelectedItem().toString();
            if((!filtro.equalsIgnoreCase(comida.getCategoriaComida()))
                    & !spCategorias.getSelectedItem().toString().equalsIgnoreCase(" ")){
                comidasFiltradas.remove(i);
            }
        }
        ComidaAdapter adapter = new ComidaAdapter(MenuVista.this, comidasFiltradas);
        listaComidas.setAdapter(adapter);
        listaComidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if(cbEditar.isChecked()){
                    intent = new Intent(MenuVista.this, ComidaDetalle.class);
                    intent.putExtra("comidaObject",comidasFiltradas.get(position));
                    startActivity(intent);
                }
                else{
                    Comida comidaSel = comidasFiltradas.get(position);
                    DBController db = new DBController();
                    db.agregarPedido(Integer.toString(comidaSel.getPrecioComida()),
                                     "Proceso",
                                     Integer.toString(comidaSel.getIdComida()),
                                     Integer.toString(usuario.getCodEmpleado()),
                                     "1",
                                     MenuVista.this);
                }

            }
        });



    }


}

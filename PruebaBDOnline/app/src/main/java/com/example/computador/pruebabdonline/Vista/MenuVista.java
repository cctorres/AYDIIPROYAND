package com.example.computador.pruebabdonline.Vista;

import android.content.ClipData;
import android.content.DialogInterface;
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

    private String[] categorias = new String[] {" ","Comida tipica", "Bebida", "Comida vegetariana", "Asado", "Comida de mar", "Otros"};
    private String[] restricciones = new String[] {"Alto en lactosa", "Alto en calorias", "Alto en azúcar", "Ninguna"};
    private ListView lvListaComidas;
    private Button agregar, filtrar;
    private CheckBox cbAzucar,cbCalorias,cbLactosa, cbEditar;
    private boolean bAzucar, bCalorias, bLactosa;
    private Spinner spCategorias;
    private final int SELECT_PICTURE = 300;
    private String codigoImagen = "";
    private Empleado usuario;
    private String nroMesaSel;
    private Comida comidaSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vista);

        //Conexión de los componentes visuales
        lvListaComidas = (ListView) findViewById(R.id.lv_comidas_mv);
        agregar = (Button) findViewById(R.id.bt_agregar_mv);
        filtrar = (Button) findViewById(R.id.bt_filtrar_mv);
        cbAzucar = (CheckBox) findViewById(R.id.cb_azucar_mv);
        cbCalorias = (CheckBox) findViewById(R.id.cb_calorias_mv);
        cbLactosa = (CheckBox) findViewById(R.id.cb_lactosa_mv);
        cbEditar = (CheckBox) findViewById(R.id.cb_editar);
        spCategorias = (Spinner) findViewById(R.id.sp_categoria_mv);

        //Listener de los botones
        agregar.setOnClickListener(this);
        filtrar.setOnClickListener(this);

        llenarSpinnerCategoria(categorias,spCategorias);

        //Obtiene el usuario de la activity anterior
        usuario = (Empleado) getIntent().getSerializableExtra("usuario");

        //Obtiene los valores de los Checkbox de la activity anterior
        if(getIntent().hasExtra("valorCaloria")){
            bCalorias = getIntent().getExtras().getBoolean("valorCaloria");
        }
        if(getIntent().hasExtra("valorAzucar")){
            bAzucar = getIntent().getExtras().getBoolean("valorAzucar");
        }
        if(getIntent().hasExtra("valorLactosa")){
            bLactosa = getIntent().getExtras().getBoolean("valorLactosa");
        }

        //Valida el cargo del empleado para poder usar el botón de agregar
        if(!usuario.getCargoEmpleado().equalsIgnoreCase("administrador")){
            agregar.setEnabled(false);
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


    /**
     * Métodos de los botones
     * @param v Vista
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_agregar_mv:
                //genera el Dialog de la comida
                insertarComidaDialog();
                break;
            case R.id.bt_filtrar_mv:
                filtrarCheckbox(lvListaComidas);
                break;
            default:

                break;
        }
    }

    //Genera el Dialog de insertar comida
    public void insertarComidaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_agregar_comida, null);
        builder.setView(v);
        //Enlace a los botones
        Button cancelar = (Button) v.findViewById(R.id.bt_cancelar_dialog_ac);
        Button confirmar = (Button) v.findViewById(R.id.bt_confirmar_dialog_ac);
        Button imagen = (Button) v.findViewById(R.id.bt_imagen_dialog_ac);
        imagen.setOnClickListener(this);
        //Enlace de comoponentes visuales
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
        //Genera el dialogo para seleccionar la imagen
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
            }
        });
        //Confirma la acción
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
                        //Validar que hayan datos
                        if(nombre.equals("") | precio.equals("") | descripcion.equals("") | ingredientes.equals("") | categoria.equals("") | restriccion.equals("")){
                            Toast.makeText(MenuVista.this,"Faltan datos por llenar",Toast.LENGTH_LONG).show();
                            return;
                        }
                        DBController db = new DBController();
                        //Genera la petición de agregar comida
                        db.agregarComida(nombre, precio, categoria, restriccion, descripcion, ingredientes, codigoImagen, MenuVista.this);
                        finish();
                        MenuVista.this.startActivity(MenuVista.this.getIntent());
                    }
                }
        );
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    //Llena los Spinner
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


    //Método que se ejecuta después de seleccionar la imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path); //Se obtiene la imagen seleccionada
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream); //Se comprime la imagen
                        byte[] byteArray = stream.toByteArray(); //Se pasa la imagen a codigo de bits
                        codigoImagen = byteArray.toString(); //Se setea un string con los bits generados
                        codigoImagen = Base64.encodeToString(byteArray, Base64.DEFAULT); //Se codifica la imagen en BASE64
                        Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_LONG).show();

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
                            //Se hace un Arraylist del Json obtenido de la petición
                            comidaLista = json.comidaAdapter(respuesta);
                            ComidaAdapter adapter = new ComidaAdapter(MenuVista.this, comidaLista);
                            //Se llena el listview con las comidas
                            lvListaComidas.setAdapter(adapter);
                            lvListaComidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent;
                                    //Si la opciónn de editar está marcada se envia a la activity de detalle de la comida
                                    if(cbEditar.isChecked()){
                                        intent = new Intent(MenuVista.this, ComidaDetalle.class);
                                        intent.putExtra("comidaObject",comidaLista.get(position));
                                        intent.putExtra("usuario", usuario);
                                        startActivity(intent);
                                    }
                                    else{
                                        //Si no está marcado se selecciona la comida para generar la petición con el dialog
                                        AlertDialog dialogo = createSingleListDialog();
                                        dialogo.show();
                                        Toast.makeText(MenuVista.this, "Pedido de comida realizado", Toast.LENGTH_LONG).show();
                                        comidaSel = comidaLista.get(position);
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

    //Filtra las comidas dependiendo de las opciones marcada
    public void filtrarCheckbox(ListView listaComidas){
        final ArrayList<Comida> comidasFiltradascb = new ArrayList<>();
        final ArrayList<Comida> comidasFiltradassp = new ArrayList<>();
        Comida comida = new Comida();
        for(int i = 0; i<listaComidas.getCount();i++){
            comida = (Comida) listaComidas.getItemAtPosition(i);
            //Si ningún checkbox está seleccionado no se filtra nada
            if((cbAzucar.isChecked()==false) && (cbLactosa.isChecked()==false) && (cbCalorias.isChecked()==false)){
                comidasFiltradascb.add(comida);
                continue;
            }
            //Si el filtro de lactosa está seleccionado se verifica la restricción
            if(cbLactosa.isChecked()){
                if(!comida.getRestriccionComida().equalsIgnoreCase("Alto en lactosa")){
                    comidasFiltradascb.add(comida);
                    continue;
                }
            }
            //Si el filtro de azucar está seleccionado se verifica la restricción
            if(cbAzucar.isChecked()){
                if(!comida.getRestriccionComida().equalsIgnoreCase("Alto en azúcar")){
                    comidasFiltradascb.add(comida);
                    continue;
                }
            }
            //Si el filtro de calorias está seleccionado se verifica la restricción
            if(cbCalorias.isChecked()){
                if(!comida.getRestriccionComida().equalsIgnoreCase("Alto en calorias")){
                    comidasFiltradascb.add(comida);
                    continue;
                }
            }
        }
        //Filtra las comidas según la categoría que tenga, si el spinner está vacia no filtra
        if(spCategorias.getSelectedItem().toString().equalsIgnoreCase(" ")){
            for(int i = 0; i<comidasFiltradascb.size();i++){
                comida = comidasFiltradascb.get(i);
                comidasFiltradassp.add(comida);
            }
        }
        else{ //De lo contrario, verifica la categoria con la seleccionada en el spinner
            for(int i = 0;i<comidasFiltradascb.size();i++){
                comida = comidasFiltradascb.get(i);
                String filtro = spCategorias.getSelectedItem().toString();
                if(filtro.equalsIgnoreCase(comida.getCategoriaComida())){
                    comidasFiltradassp.add(comida);
                }

            }
        }
        ComidaAdapter adapter = new ComidaAdapter(MenuVista.this, comidasFiltradassp);
        listaComidas.setAdapter(adapter);
        listaComidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if(cbEditar.isChecked()){
                    intent = new Intent(MenuVista.this, ComidaDetalle.class);
                    intent.putExtra("comidaObject",comidasFiltradassp.get(position));
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                }
                else{
                    //Si no está marcado se selecciona la comida para generar la petición con el dialog
                    AlertDialog dialogo = createSingleListDialog();
                    dialogo.show();
                    Toast.makeText(MenuVista.this, "Pedido de comida realizado", Toast.LENGTH_LONG).show();
                    comidaSel = comidasFiltradassp.get(position);
                }

            }
        });



    }

    /**
     * Crea una ventana tipo Dialog para hacer el pedido de la comida
     * @return Una ventana tipo Dialog
     */
    public AlertDialog createSingleListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuVista.this);

        final CharSequence[] items = new CharSequence[10];

        items[0] = "Mesas";
        items[1] = "1";
        items[2] = "2";
        items[3] = "3";
        items[4] = "4";
        items[5] = "5";
        items[6] = "6";
        items[7] = "7";
        items[8] = "8";
        items[9] = "9";

        builder.setTitle("Seleccionar mesa")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(
                                MenuVista.this,
                                "Seleccionaste la mesa:" + items[which],
                                Toast.LENGTH_SHORT)
                                .show();
                                nroMesaSel = items[which].toString();
                                DBController db = new DBController();
                                //Envía la petición al servidor de agrergar un pedido
                                db.agregarPedido(Integer.toString(comidaSel.getPrecioComida()),
                                "Proceso",
                                Integer.toString(comidaSel.getIdComida()),
                                Integer.toString(usuario.getCodEmpleado()),
                                nroMesaSel,
                                MenuVista.this);

                    }
                });

        return builder.create();
    }


}

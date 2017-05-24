package com.example.computador.pruebabdonline.Vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computador.pruebabdonline.Controlador.DBController;
import com.example.computador.pruebabdonline.Controlador.PHPGetter;
import com.example.computador.pruebabdonline.Modelo.Comida;
import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ComidaDetalle extends AppCompatActivity implements View.OnClickListener{

    private String[] categorias = new String[] {"Comida tipica", "Bebida", "Comida vegetariana", "Asado", "Comida de mar", "Otros"};
    private String[] restricciones = new String[] {"Alto en lactosa", "Alto en calorias", "Alto en azúcar", "Ninguna"};
    private final int SELECT_PICTURE = 300;
    private String codigoImagen = "";
    private Comida com;
    private TextView nomComida, precioComida, catComida, resComida, descComida, ingreComida;
    private ImageView fotoComida;
    private Button eliminar, modificar;
    private Empleado usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_detalle);

        //tomar la comida de la otra activity
        com = (Comida) getIntent().getSerializableExtra("comidaObject");
        //Tomar el usuario de la otra activity
        usuario = (Empleado) getIntent().getSerializableExtra("usuario");

        //Enlazar componentes visuales
        fotoComida = (ImageView) findViewById(R.id.iv_imagen_cd);
        nomComida = (TextView) findViewById(R.id.tv_nombre_cd);
        precioComida = (TextView) findViewById(R.id.tv_precio_cd);
        catComida = (TextView) findViewById(R.id.tv_categoria_cd);
        resComida = (TextView) findViewById(R.id.tv_restriccion_cd);
        descComida = (TextView) findViewById(R.id.tv_descripcion_cd);
        ingreComida = (TextView) findViewById(R.id.tv_ingredientes_cd);
        eliminar = (Button) findViewById(R.id.bt_eliminar_cd);
        modificar = (Button) findViewById(R.id.bt_actualizar_cd);

        //Proporcionarle los listener a los botones
        eliminar.setOnClickListener(this);
        modificar.setOnClickListener(this);

        //Si el rol del usuario no es de administrador, deshabilita las opciones de eliminar o modificar las comidas
        if(!usuario.getCargoEmpleado().equalsIgnoreCase("administrador")){
            eliminar.setEnabled(false);
            modificar.setEnabled(false);
        }
    }

    /**
     * Métodos de los botones
     * @param v Vista
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_eliminar_cd:
                //Genera el dialog de eliminación de comida
                    eliminarComidaDialog();
                break;
            case R.id.bt_actualizar_cd:
                //genera el dialog para la actualización de datos de la comida
                actualizarComidaDialog();
                break;
            default:
                break;
         }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Setea los datos de la comida a los componentes visuales
        nomComida.setText("Nombre: "+com.getNombreComida());
        precioComida.setText("Precio: $"+Integer.toString(com.getPrecioComida()));
        catComida.setText("Categoria: "+com.getCategoriaComida());
        resComida.setText("Restricción: "+com.getRestriccionComida());
        descComida.setText("Descripción: "+com.getDescripcionComida());
        ingreComida.setText("Ingredientes: "+com.getIngredientesComida());
        final String encodedString = com.getFotoComida();
        //Decodifica el codigo de BIT64 para generar un BitMap que se seteará en el Imageview
        final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        fotoComida.setImageBitmap(decodedBitmap);
    }

    //Dialog de confirmación de eliminación
    public void eliminarComidaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmar acción")
                .setMessage("¿Está seguro de eliminar el plato?")
                .setPositiveButton("Sí",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBController db = new DBController();
                                //Envía la petición al servidor para eliminar la comida
                                db.eliminarComida(Integer.toString(com.getIdComida()), ComidaDetalle.this);
                                Intent intent = new Intent(ComidaDetalle.this, MenuVista.class);
                                intent.putExtra("usuaruio",usuario);
                                ComidaDetalle.this.startActivity(intent);
                                ComidaDetalle.this.finish();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    //Dialog de actualizar la comida
    public void actualizarComidaDialog() {
        //Genera el Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_agregar_comida, null);
        builder.setView(v);
        Button cancelar = (Button) v.findViewById(R.id.bt_cancelar_dialog_ac);
        Button confirmar = (Button) v.findViewById(R.id.bt_confirmar_dialog_ac);
        Button imagen = (Button) v.findViewById(R.id.bt_imagen_dialog_ac);
        imagen.setOnClickListener(this);
        //Setea los datos del Dialog con los de la comida
        final EditText nombreDialog = (EditText) v.findViewById(R.id.et_nombre_dialog_ac);
        final EditText precioDialog = (EditText) v.findViewById(R.id.et_precio_dialog_ac);
        final EditText descripcionDialog = (EditText) v.findViewById(R.id.et_descrpcion_dialog_ac);
        final EditText ingredientesDialog = (EditText) v.findViewById(R.id.et_ingredientes_dialog_ac);
        final Spinner categoriaDialog = (Spinner) v.findViewById(R.id.sp_categoria_ac);
        final Spinner restriccionDialog = (Spinner) v.findViewById(R.id.sp_restriccion_ac);

        nombreDialog.setText(com.getNombreComida());
        precioDialog.setText(Integer.toString(com.getPrecioComida()));
        descripcionDialog.setText(com.getDescripcionComida());
        ingredientesDialog.setText(com.getIngredientesComida());
        llenarSpinners(categorias,restricciones, categoriaDialog, restriccionDialog);
        //Acción al cancelar la petición de actualización
        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        ComidaDetalle.this.startActivity(ComidaDetalle.this.getIntent());
                    }
                }
        );
        //Acción que abre el seleccionador de imágenes
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
            }
        });
        //Acción de confirmación de la actualización
        confirmar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toma los datos digitados por el usuario
                        String nombre = nombreDialog.getText().toString();
                        String precio = precioDialog.getText().toString();
                        String descripcion = descripcionDialog.getText().toString();
                        String ingredientes = ingredientesDialog.getText().toString();
                        String categoria = categoriaDialog.getSelectedItem().toString();
                        String restriccion = restriccionDialog.getSelectedItem().toString();
                        String id = Integer.toString(com.getIdComida());
                        if(codigoImagen.equalsIgnoreCase("")){
                            codigoImagen = com.getFotoComida();
                        }
                        DBController db = new DBController();
                        //Hace la petición al servidor para actualizar los datos
                        db.actualizarComida(id,nombre,precio,categoria,restriccion,descripcion,ingredientes,codigoImagen);
                        finish();
                        Intent intent = new Intent(ComidaDetalle.this, MenuVista.class);
                        intent.putExtra("usuaruio",usuario);
                        ComidaDetalle.this.startActivity(intent);
                        ComidaDetalle.this.finish();
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

    //Método que se ejecuta después de seleccionar la imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case SELECT_PICTURE:
                    Uri path = data.getData();//Toma la imagen seleccionada
                    try {
                        //Codifica la imagen en BITg4
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        //Comprime la imagen para que pese menos
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        byte[] byteArray = stream.toByteArray();
                        codigoImagen = byteArray.toString();
                        codigoImagen = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}

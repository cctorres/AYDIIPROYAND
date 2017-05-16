package com.example.computador.pruebabdonline.Vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.computador.pruebabdonline.Controlador.DBController;
import com.example.computador.pruebabdonline.Modelo.Comida;
import com.example.computador.pruebabdonline.R;

public class ComidaDetalle extends AppCompatActivity implements View.OnClickListener{

    private String[] categorias = new String[] {"Comida tipica", "Bebida", "Comida vegetariana", "Asado", "Comida de mar, Otros"};
    private String[] restricciones = new String[] {"Alto en lactosa", "Alto en calorias", "Alto en azúcar", "Ninguna"};
    private final int SELECT_PICTURE = 300;
    private String codigoImagen = "";
    private Comida com;
    private TextView nomComida, precioComida, catComida, resComida, descComida, ingreComida;
    private ImageView fotoComida;
    private Button eliminar, modificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida_detalle);

        //tomar la comida de la otra activity
        com = (Comida) getIntent().getSerializableExtra("comidaObject");

        fotoComida = (ImageView) findViewById(R.id.iv_imagen_cd);
        nomComida = (TextView) findViewById(R.id.tv_nombre_cd);
        precioComida = (TextView) findViewById(R.id.tv_precio_cd);
        catComida = (TextView) findViewById(R.id.tv_categoria_cd);
        resComida = (TextView) findViewById(R.id.tv_restriccion_cd);
        descComida = (TextView) findViewById(R.id.tv_descripcion_cd);
        ingreComida = (TextView) findViewById(R.id.tv_ingredientes_cd);
        eliminar = (Button) findViewById(R.id.bt_eliminar_cd);
        modificar = (Button) findViewById(R.id.bt_actualizar_cd);

        eliminar.setOnClickListener(this);
        modificar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_eliminar_cd:
                    eliminarComidaDialog();

                break;
            case R.id.bt_actualizar_cd:
                actualizarComidaDialog();
                break;
            default:
                break;
         }
    }

    @Override
    protected void onResume() {
        super.onResume();
        nomComida.setText("Nombre: "+com.getNombreComida());
        precioComida.setText("Precio: $"+Integer.toString(com.getPrecioComida()));
        catComida.setText("Categoria: "+com.getCategoriaComida());
        resComida.setText("Restricción: "+com.getRestriccionComida());
        descComida.setText("Descripción: "+com.getDescripcionComida());
        ingreComida.setText("Ingredientes: "+com.getIngredientesComida());
        final String encodedString = com.getFotoComida();
        final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        fotoComida.setImageBitmap(decodedBitmap);
    }

    public void eliminarComidaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmar acción")
                .setMessage("¿Está seguro de eliminar al empleado?")
                .setPositiveButton("Sí",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBController db = new DBController();
                                Intent intent = new Intent(ComidaDetalle.this, MenuVista.class);
                                db.eliminarComida(Integer.toString(com.getIdComida()), ComidaDetalle.this);
                                ComidaDetalle.this.finish();
                                startActivity(intent);
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

    public void actualizarComidaDialog() {
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

        nombreDialog.setText(com.getNombreComida());
        precioDialog.setText(Integer.toString(com.getPrecioComida()));
        descripcionDialog.setText(com.getDescripcionComida());
        ingredientesDialog.setText(com.getIngredientesComida());
        llenarSpinners(categorias,restricciones, categoriaDialog, restriccionDialog);

        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        ComidaDetalle.this.startActivity(ComidaDetalle.this.getIntent());
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
                        String id = Integer.toString(com.getIdComida());
                        if(codigoImagen.equalsIgnoreCase("")){
                            codigoImagen = com.getFotoComida();
                        }
                        DBController db = new DBController();
                        db.actualizarComida(id,nombre,precio,categoria,restriccion,descripcion,ingredientes,codigoImagen,ComidaDetalle.this);
                        finish();
                        ComidaDetalle.this.finish();
                        ComidaDetalle.this.startActivity(ComidaDetalle.this.getIntent());
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

}

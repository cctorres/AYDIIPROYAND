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

    private String[] categorias = new String[] {"Comida tipica", "Bebida", "Comida vegetariana", "Asado", "Comida de mar, Otros"};
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
        usuario = (Empleado) getIntent().getSerializableExtra("usuario");

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

        if(!usuario.getCargoEmpleado().equalsIgnoreCase("administrador")){
            eliminar.setEnabled(false);
            modificar.setEnabled(false);
        }
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
                        PHPGetter php = new PHPGetter();
                        actualizarComida act = new actualizarComida();
                        act.execute(php.getActualizarComida(),"4",id,nombre,precio,categoria,restriccion,descripcion,ingredientes,codigoImagen);
                        /*
                        DBController db = new DBController();
                        db.actualizarComida(id,nombre,precio,categoria,restriccion,descripcion,ingredientes,codigoImagen,ComidaDetalle.this);
                        */finish();
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

    public class actualizarComida extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";

            if(params[1].equalsIgnoreCase("4")){    // update

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
                    HashMap<String, String> jsonParam = new HashMap();

                    jsonParam.put("id_comida",params[2]);
                    jsonParam.put("nombre_comida", params[3]);
                    jsonParam.put("precio_comida", params[4]);
                    jsonParam.put("categoria_comida", params[5]);
                    jsonParam.put("restriccion_comida", params[6]);
                    jsonParam.put("descripcion_comida", params[7]);
                    jsonParam.put("ingredientes_comida", params[8]);
                    jsonParam.put("foto_comida", params[9]);
                    JSONObject json = new JSONObject(jsonParam);
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(json.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();


                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line=br.readLine()) != null) {
                            result.append(line);
                            //response+=line;
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON.equalsIgnoreCase("1")) {      // hay un alumno que mostrar
                            devuelve = "Alumno actualizado correctamente";

                        } else if (resultJSON.equalsIgnoreCase("2")) {
                            devuelve = "El alumno no pudo actualizarse";
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
            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
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
                        Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
        }

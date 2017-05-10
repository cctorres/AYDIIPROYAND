package com.example.computador.pruebabdonline.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.computador.pruebabdonline.R;

public class inicioSesion extends AppCompatActivity implements View.OnClickListener{

    //Datos para entrar como superusuario si todavia no hay un administrador en la BD remota
    String idAdmin = "123456789";
    String contraseñaAdmin = "123456789";

    //Declaración de variables
    Button iniciarSesion;
    EditText identificacion;
    EditText contraseña;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        //Enlazar componentes visuales
        iniciarSesion = (Button) findViewById(R.id.bt_iniciarsesion_is);
        identificacion = (EditText) findViewById(R.id.et_identificacion_is);
        contraseña = (EditText) findViewById(R.id.et_contraseña_is);

        //Click listener
        iniciarSesion.setOnClickListener(this);
    }

    public void iniciarSesion(){
        String id = this.identificacion.getText().toString();
        String pass = this.contraseña.getText().toString();
        if(id.equalsIgnoreCase(idAdmin) & pass.equalsIgnoreCase(contraseñaAdmin)){
            intent = new Intent(inicioSesion.this, VistaAdmin.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_iniciarsesion_is:
                    iniciarSesion();
               break;
            default:

                break;
        }
    }
}

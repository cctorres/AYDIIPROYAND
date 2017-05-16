package com.example.computador.pruebabdonline.Vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.R;

public class VistaAdmin extends AppCompatActivity implements View.OnClickListener{

    //Declaración de variables
    private Button pedidos, carta, empleados, inventario, restaurante;
    private Intent intent;
    private Empleado usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_admin);

        //Enlazar componentes visuales

        pedidos = (Button) findViewById(R.id.bt_pedidos_va);
        carta = (Button) findViewById(R.id.bt_carta_va);
        empleados = (Button) findViewById(R.id.bt_empleados_va);
        inventario = (Button) findViewById(R.id.bt_inventario_va);
        restaurante = (Button) findViewById(R.id.bt_restaurante_va);

        usuario = (Empleado) getIntent().getSerializableExtra("usuario");

        //Click listener
        pedidos.setOnClickListener(this);
        carta.setOnClickListener(this);
        empleados.setOnClickListener(this);
        inventario.setOnClickListener(this);
        restaurante.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_restaurante_va:
                intent = new Intent(VistaAdmin.this, VistaRestaurante.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                break;
            case R.id.bt_pedidos_va:
                intent = new Intent(VistaAdmin.this, PedidosVista.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                break;
            case R.id.bt_empleados_va:
                intent = new Intent(VistaAdmin.this, EmpleadosVista.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                break;
            case R.id.bt_inventario_va:
                intent = new Intent(VistaAdmin.this, IngredienteVista.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                break;
            case R.id.bt_carta_va:
                intent = new Intent(VistaAdmin.this, MenuVista.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            default:

                break;
        }
    }
}

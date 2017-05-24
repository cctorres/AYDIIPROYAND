package com.example.computador.pruebabdonline.Vista;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.computador.pruebabdonline.Controlador.DBController;
import com.example.computador.pruebabdonline.Controlador.JSonAdapter;
import com.example.computador.pruebabdonline.Controlador.PHPGetter;
import com.example.computador.pruebabdonline.Controlador.VolleySingleton;
import com.example.computador.pruebabdonline.Modelo.Empleado;
import com.example.computador.pruebabdonline.Modelo.EmpleadoAdapter;
import com.example.computador.pruebabdonline.Modelo.Pedido;
import com.example.computador.pruebabdonline.Modelo.PedidoAdapter;
import com.example.computador.pruebabdonline.R;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class EmpleadoDetalle extends AppCompatActivity implements View.OnClickListener{

    private Empleado emp;
    private PedidoAdapter adapter;
    private ArrayList<Pedido> listaPedidos;
    private TextView codEmp,idEmp,nomEmp,cargoEmp,telEmp, pedidoTotal, pedidoDiario;
    private ListView lvPedidos;
    private Button eliminar, modificar;
    private Empleado usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado_detalle);
        //tomar el empleado de la otra activity
        emp = (Empleado) getIntent().getSerializableExtra("empleadoObject");
        usuario = (Empleado) getIntent().getSerializableExtra("usuario");

        //Ligar componentes visuales
        codEmp = (TextView) findViewById(R.id.tv_codigo_empdv);
        idEmp = (TextView) findViewById(R.id.tv_identificacion_empdv);
        nomEmp = (TextView) findViewById(R.id.tv_nombre_empdv);
        cargoEmp = (TextView) findViewById(R.id.tv_cargo_empdv);
        telEmp = (TextView) findViewById(R.id.tv_telefono_empdv);
        pedidoTotal = (TextView) findViewById(R.id.tv_canttotal_empdv);
        pedidoDiario = (TextView) findViewById(R.id.tv_cantdiaria_empdv);
        lvPedidos = (ListView) findViewById(R.id.lv_pedidos_empdv);
        eliminar = (Button) findViewById(R.id.bt_eliminar_empdv);
        modificar = (Button) findViewById(R.id.bt_modificar_empdv);

        //Listener botones
        eliminar.setOnClickListener(this);
        modificar.setOnClickListener(this);

        //Llenar los Textview de los datos del empleado.
        codEmp.setText("Nro Id: "+Integer.toString(emp.getCodEmpleado()));
        idEmp.setText("Codigo: "+Integer.toString(emp.getIdEmpleado()));
        nomEmp.setText(emp.getNombreEmpleado());
        cargoEmp.setText("Cargo: "+emp.getCargoEmpleado());
        telEmp.setText("Telefono: "+Integer.toString(emp.getTelefonoEmpleado()));
    }

    /**
     * Métodos de los botones
     * @param v Vista
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_eliminar_empdv:
                //Ejecuta el Dialog para la comfirmación de la eliminación
                confirmaEliminacionEmpleado();
                break;
            case R.id.bt_modificar_empdv:
                //Ejecuta el Dialog para la actualización de los datos del empleado
                actualizarEmpleadoDialog();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        llenarListViewPedidos();
    }

    /**
     * Genera un Dialog para confirma que se desea eliminar al empleado de la BD
     */
    public void confirmaEliminacionEmpleado() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirmar acción")
                .setMessage("¿Está seguro de eliminar al empleado?")
                .setPositiveButton("Sí",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBController db = new DBController();
                                Intent intent = new Intent(EmpleadoDetalle.this, EmpleadosVista.class);
                                intent.putExtra("usuario",usuario);
                                EmpleadoDetalle.this.finish();
                                //Envía la petición al servidor de elimnar al empleado de la BD
                                db.eliminarEmpleado(emp.getCodEmpleado(), EmpleadoDetalle.this);
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

    /**
     * Genera el Dialog para actualizar los datos del empleado
     */
    public void actualizarEmpleadoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_actualizar_empleado, null);
        builder.setView(v);
        Button cancelar = (Button) v.findViewById(R.id.bt_cancelar_dialog_ae);
        Button confirmar = (Button) v.findViewById(R.id.bt_confirmar_dialog_ae);
        final EditText nombreDialog = (EditText) v.findViewById(R.id.et_nombre_dialog_ae);
        final EditText idDialog = (EditText) v.findViewById(R.id.et_identificacion_dialog_ae);
        final EditText cargoDialog = (EditText) v.findViewById(R.id.et_cargo_dialog_ae);
        final EditText telDialog = (EditText) v.findViewById(R.id.et_telefono_dialog_ae);
        nombreDialog.setText(emp.getNombreEmpleado());
        idDialog.setText(Integer.toString(emp.getIdEmpleado()));
        cargoDialog.setText(emp.getCargoEmpleado());
        telDialog.setText(Integer.toString(emp.getTelefonoEmpleado()));
        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        confirmar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nombre = nombreDialog.getText().toString();
                        String id = idDialog.getText().toString();
                        String cargo = cargoDialog.getText().toString();
                        String tel = telDialog.getText().toString();
                        DBController db = new DBController();
                        db.actualizarEmpleado(Integer.toString(emp.getCodEmpleado()), id, nombre, cargo, tel, emp.getContraseñaEmpleado(), EmpleadoDetalle.this);
                        finish();
                    }
                }
        );
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }

    /**
     * Llena el Listvire de la vista con los pedidos que haya hecho el empleado
     */
    public void llenarListViewPedidos(){ArrayList<Empleado> arrayEmp = new ArrayList<>();
        PHPGetter php = new PHPGetter();
        String url = php.getObtenerPedidos();
        //Genera la petición al servidor sobre los pedidos
        StringRequest peticion = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respuesta) {
                        JSonAdapter json = new JSonAdapter();
                        final ArrayList<Pedido> pedidosLista;
                        try {
                            pedidosLista = json.pedidoAdapter(respuesta); //Del Json envíado por el servidor hace un Array con los pedidos
                            Pedido p = new Pedido();
                            //Filta los pedidos por el Cod del empleado
                            ArrayList<Pedido> aux = p.filtrarPedidoEmpleado(pedidosLista, emp.getCodEmpleado());
                            adapter = new PedidoAdapter(EmpleadoDetalle.this, aux, emp.getCodEmpleado());
                            lvPedidos = (ListView) findViewById(R.id.lv_pedidos_empdv);
                            //Genera un adapter para llenar el ListView de los pedidos
                            lvPedidos.setAdapter(adapter);
                            pedidoTotal.setText("Total de pedidos: "+ Integer.toString(aux.size()));
                            //Filtra los pedidos por el empleado y el día actual
                            aux = p.filtrarPedidoEmpleadoYDia(pedidosLista, emp.getCodEmpleado());
                            pedidoDiario.setText("Total de pedidos hoy: "+aux.size());
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
        //Envía la petición a la Cola del Volley
        VolleySingleton.getInstance(this).addToRequestQueue(peticion);
    }
}

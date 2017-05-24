package com.example.computador.pruebabdonline.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.computador.pruebabdonline.R;

import java.util.ArrayList;

// Clase para adaptar el Objeto Empleado a un Listview

public class EmpleadoAdapter extends BaseAdapter{


    private ArrayList<Empleado> empleados;
    private Context context;

    /**
     * El constructor
     * @param empleados
     */
    public EmpleadoAdapter(Context context, ArrayList<Empleado> empleados) {
        this.empleados = empleados;
        this.context = context;

    }

    /**
     * Este método devuelve el número de
     * elementos de nuestro ListView.
     */
    public int getCount() {
        return empleados.size();
    }

    /**
     * Este método  devuele el elemento de una posición determinada.
     */
    public Object getItem(int position) {
        return empleados.get(position);
    }

    /**
     * Aquí tenemos que devolver el ID del elemento. Del ELEMENTO, no del View.
     * Por lo general esto no se usa, así que return 0 y listo.
     */
    public long getItemId(int position) {
        return position;
    }

    /**
     * Setea los datos dle objeto del listvire en el Adapter
     * @param position Posición del Item en el Listview
     * @param convertView Vista
     * @param parent Array con el que se llenará el ListView
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null){
            //NO existe, creamos uno
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_empleado,null);
        }
        TextView cod = (TextView) convertView.findViewById(R.id.tv_codigo_iem);
        TextView id = (TextView) convertView.findViewById(R.id.tv_identificador_iem);
        TextView nombre = (TextView) convertView.findViewById(R.id.tv_nombre_iem);
        TextView cargo = (TextView) convertView.findViewById(R.id.tv_cargo_iem);
        TextView telefono = (TextView) convertView.findViewById(R.id.tv_telefono_iem);

        cod.setText(Integer.toString(empleados.get(position).getCodEmpleado()));
        id.setText(Integer.toString(empleados.get(position).getIdEmpleado()));
        nombre.setText(empleados.get(position).getNombreEmpleado());
        cargo.setText(empleados.get(position).getCargoEmpleado());
        telefono.setText(Integer.toString(empleados.get(position).getTelefonoEmpleado()));

        return convertView;
    }
}


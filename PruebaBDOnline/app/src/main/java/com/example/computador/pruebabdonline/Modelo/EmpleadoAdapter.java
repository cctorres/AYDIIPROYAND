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

/**
 * Created by pc on 11/05/2017.
 */

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
     * Este método simplemente nos devuelve el número de
     * elementos de nuestro ListView. Evidentemente es el tamaño del arraylist
     */
    public int getCount() {
        return empleados.size();
    }

    /**
     * Este método nos devuele el elemento de una posición determinada.
     * El elemento es el Rectángulo, así que...
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
     * El método más complicado. Aquí tenemos que devolver el View a representar.
     * En este método nos pasan 3 valores. El primero es la posición del elemento,
     * el segundo es el View a utilizar que será uno que ya no esté visible y que
     * lo reutilizaremos, y el último es el ViewGroup, es en nuestro caso, el ListView.
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


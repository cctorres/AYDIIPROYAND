package com.example.computador.pruebabdonline.Modelo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.computador.pruebabdonline.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class IngredienteAdapter extends BaseAdapter {

    private ArrayList<Ingrediente> ingredientes;
    private Context context;


    public IngredienteAdapter(Context context, ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
        this.context = context;

    }

    /**
     * Este método devuelve el número de
     * elementos de nuestro ListView. Evidentemente es el tamaño del arraylist
     */
    public int getCount() {
        return ingredientes.size();
    }

    /**
     * Este método  devuele el elemento de una posición determinada.
     * El elemento es el Rectángulo, así que...
     */
    public Object getItem(int position) {
        return ingredientes.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_ingrediente,null);
        }

        TextView idIngrediente = (TextView) convertView.findViewById(R.id.tv_identificacion_ii);
        TextView nomIngrediente = (TextView) convertView.findViewById(R.id.tv_nombre_ii);
        TextView cantIngrediente = (TextView) convertView.findViewById(R.id.tv_cantidad_ii);

        idIngrediente.setText(Integer.toString((ingredientes.get(position).getIdIngrediente())));
        nomIngrediente.setText(ingredientes.get(position).getNombreIngrediente());
        cantIngrediente.setText(Integer.toString((ingredientes.get(position).getCantIngrediente())));

        return convertView;
    }
}
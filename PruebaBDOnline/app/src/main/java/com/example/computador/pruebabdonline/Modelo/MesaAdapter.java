package com.example.computador.pruebabdonline.Modelo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.computador.pruebabdonline.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MesaAdapter extends BaseAdapter {

    private ArrayList<Mesa> mesas;
    private Context context;


    public MesaAdapter(Context context, ArrayList<Mesa> mesas) {
        this.mesas = mesas;
        this.context = context;

    }

    /**
     * Este método devuelve el número de
     * elementos de nuestro ListView.
     */
    public int getCount() {
        return mesas.size();
    }

    /**
     * Este método  devuele el elemento de una posición determinada.
     */
    public Object getItem(int position) {
        return mesas.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_mesa,null);
        }

        TextView estadoMesa = (TextView) convertView.findViewById(R.id.tv_estado_im);
        TextView idMesa = (TextView) convertView.findViewById(R.id.tv_id_mesa_im);



        idMesa.setText(Integer.toString(mesas.get(position).getIdMesa()));
        estadoMesa.setText(mesas.get(position).getEstadoMesa());



        return convertView;
    }
}

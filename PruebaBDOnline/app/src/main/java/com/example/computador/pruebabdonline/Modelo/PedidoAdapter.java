package com.example.computador.pruebabdonline.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.computador.pruebabdonline.R;

import java.util.ArrayList;

/**
 * Created by Computador on 13/05/2017.
 */

public class PedidoAdapter extends BaseAdapter {


    private ArrayList<Pedido> pedidos;
    private Context context;
    private int codEmp;

    /**
     * El constructor
     * @param pedidos
     */
    public PedidoAdapter(Context context, ArrayList<Pedido> pedidos, int codEmp) {
        this.pedidos = pedidos;
        this.context = context;
        this.codEmp = codEmp;

    }

    /**
     * Este método devuelve el número de
     * elementos de nuestro ListView.
     */
    public int getCount() {
        return pedidos.size();
    }

    /**
     * Este método  devuele el elemento de una posición determinada.
     */
    public Object getItem(int position) {
        return pedidos.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_pedido,null);
        }
        if(pedidos.get(position).getCodEmpleadoPedido()== codEmp){
            TextView id = (TextView) convertView.findViewById(R.id.tv_idpedido_ip);
            TextView mesa = (TextView) convertView.findViewById(R.id.tv_mesapedido_ip);
            TextView fecha = (TextView) convertView.findViewById(R.id.tv_fechapedido_ip);
            TextView precio = (TextView) convertView.findViewById(R.id.tv_preciopedido_ip);


            id.setText("ID: "+Integer.toString(pedidos.get(position).getIdPedido()));
            mesa.setText("Mesa: "+Integer.toString(pedidos.get(position).getIdMesaPedido()));
            fecha.setText("Fecha: "+pedidos.get(position).getFechaPedido());
            precio.setText("Precio: "+Integer.toString(pedidos.get(position).getPrecioPedido()));
        }
        return convertView;
    }
}
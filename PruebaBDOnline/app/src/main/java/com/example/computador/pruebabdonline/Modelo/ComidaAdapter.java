package com.example.computador.pruebabdonline.Modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.computador.pruebabdonline.R;

import java.util.ArrayList;


public class ComidaAdapter extends BaseAdapter{


    private ArrayList<Comida> comidas;
    private Context context;

    /**
     * El constructor
     * @param comidas
     */
    public ComidaAdapter(Context context, ArrayList<Comida> comidas) {
        this.comidas = comidas;
        this.context = context;

    }

    /**
     * Este método devuelve el número de
     * elementos de nuestro ListView. Evidentemente es el tamaño del arraylist
     */
    public int getCount() {
        return comidas.size();
    }

    /**
     * Este método  devuele el elemento de una posición determinada.
     * El elemento es el Rectángulo, así que...
     */
    public Object getItem(int position) {
        return comidas.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_comida,null);
        }

        ImageView imagen = (ImageView) convertView.findViewById(R.id.iv_imagen_icom);
        TextView nombre = (TextView) convertView.findViewById(R.id.tv_nombre_comida_icom);
        TextView precio = (TextView) convertView.findViewById(R.id.tv_precio_icom);

        final String encodedString = comidas.get(position).getFotoComida();
        final String pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);
        final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);


        imagen.setImageBitmap(decodedBitmap);
        nombre.setText(comidas.get(position).getNombreComida());
        precio.setText("$"+Integer.toString(comidas.get(position).getPrecioComida()));

        return convertView;
    }
}

package com.example.front_android.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.front_android.Modelos.Camara;
import com.example.front_android.R;

import java.util.List;

public class AdaptadorListaCamaras extends ArrayAdapter<Camara> {

    public interface OnCamaraClickListener {
        void onCamaraClick(Camara camara);
        void onFavoritoClick(Camara camara);
    }


    private List<Camara> miListaCamaras;
    //Definimos el contexto y recurso a utilizar
    private Context miContexto;
    private int recursosLayout;
    private AdaptadorListaCamaras.OnCamaraClickListener listener;

    public AdaptadorListaCamaras(@NonNull Context context, int resource, List<Camara> objects) {
        super(context, resource, objects);
        this.miListaCamaras = objects;
        this.miContexto = context;
        this.recursosLayout = resource;
    }


    public void setOnCamaraClickListener(AdaptadorListaCamaras.OnCamaraClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View miVista = convertView;

        if(miVista == null){
            miVista = LayoutInflater.from(miContexto).inflate(R.layout.fila_lista_camaras,null);

        }

        // Definimos como se va a construir el objeto en este adaptador
        Camara camara = miListaCamaras.get(position);



        TextView nombreCam = miVista.findViewById(R.id.texto_nombreCam);
        nombreCam.setText(camara.getNombre() != null ?  camara.getNombre() : "No disponible");


        TextView region = miVista.findViewById(R.id.texto_region);
        region.setText(camara.getRegion() != null ? camara.getRegion().getNombreEs() : "No disponible");



        ImageButton imagen = miVista.findViewById(R.id.img);
        imagen.setImageResource(camara.getImagen());

        imagen.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFavoritoClick(camara);
            }
        });

        // Manejar el clic en el elemento completo
        miVista.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCamaraClick(camara);
            }
        });

        return miVista;
    }


}

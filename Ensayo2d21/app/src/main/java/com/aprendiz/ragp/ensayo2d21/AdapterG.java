package com.aprendiz.ragp.ensayo2d21;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdapterG extends RecyclerView.Adapter<AdapterG.Holder> {
    List<Datos> datosList = new ArrayList<>();
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void itemClick(int position);
    }

    public AdapterG(List<Datos> datosList) {
        this.datosList = datosList;
    }

    public void setMlistener(OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        Holder myholder = new Holder(view,mlistener);
        return myholder;

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.connetcViews(datosList.get(position));
    }

    @Override
    public int getItemCount() {
        return datosList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView txtTitle = itemView.findViewById(R.id.txtTitle);
        ImageView imageback = itemView.findViewById(R.id.imagenItem);
        public Holder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!= null){
                        int position= getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listener.itemClick(position);
                        }
                    }
                }
            });
        }



        public void connetcViews(Datos datos){
            txtTitle.setText(datos.getTitle());
            Bitmap foto = BitmapFactory.decodeFile(datos.getImage());
            Bitmap foto1 = Bitmap.createScaledBitmap(foto,200,200,true);
            imageback.setImageBitmap(foto1);
            Log.i("Holaaaaaaaa",datos.getImage());

        }
    }
}

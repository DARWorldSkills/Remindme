package com.aprendiz.ragp.ensayo2d21;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class Detalle_Recuerdo extends AppCompatActivity{
    ImageView imgDetalle;
    TextView txtdetalle;
    Button btnstart;
    Button btnstop;
    MediaPlayer reproductor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle__recuerdo);
        inizialite();
        inputdata();



    }

    private void inputdata() {
        Datos tmpDatos = new Datos();
        reproductor = new MediaPlayer();
        if (Listar_Recuerdos.pdatos!=null){
            tmpDatos= Listar_Recuerdos.pdatos;
            imgDetalle.setImageURI(Uri.fromFile(new File(tmpDatos.getImage())));
            txtdetalle.setText(tmpDatos.getTitle());

            try {
                reproductor.setDataSource(tmpDatos.getAudio());
                reproductor.prepare();
                reproductor.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Listar_Recuerdos.pdatos=null;
        }

        if (MainActivity.pdatos!=null){

            tmpDatos= MainActivity.pdatos;
            imgDetalle.setImageURI(Uri.fromFile(new File(tmpDatos.getImage())));
            txtdetalle.setText(tmpDatos.getTitle());
            try {
                reproductor.setDataSource(tmpDatos.getAudio());
                reproductor.prepare();
                reproductor.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            MainActivity.pdatos=null;
        }

        final Datos tmpDatos1 = tmpDatos;
        btnstart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    reproductor = new MediaPlayer();

                    reproductor.setDataSource(tmpDatos1.getAudio());
                    reproductor.prepare();
                    reproductor.start();
                    btnstop.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reproductor.stop();
                reproductor.release();
                btnstop.setEnabled(false);
            }
        });



    }

    private void inizialite() {
        imgDetalle = findViewById(R.id.imageViewDR);
        txtdetalle = findViewById(R.id.txtTitleDR);
        btnstart = findViewById(R.id.btnstartDR);
        btnstop = findViewById(R.id.btnstopDR);

    }

}

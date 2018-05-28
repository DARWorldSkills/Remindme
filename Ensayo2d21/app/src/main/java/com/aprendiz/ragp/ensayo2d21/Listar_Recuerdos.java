package com.aprendiz.ragp.ensayo2d21;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class Listar_Recuerdos extends AppCompatActivity {

    public static Datos pdatos= null;
    Button btncrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar__recuerdos);

        final GestorDB gestorDB = new GestorDB(this);
        final List<Datos> listdatos = gestorDB.listaRecuerdos();
        AdapterG adapter = new AdapterG(listdatos);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setMlistener(new AdapterG.OnItemClickListener() {
            @Override
            public void itemClick(int position) {

                Intent intent = new Intent(Listar_Recuerdos.this, Detalle_Recuerdo.class);
                pdatos=listdatos.get(position);
                startActivity(intent);

            }
        });

        btncrear= findViewById(R.id.btnCrear);

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Listar_Recuerdos.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

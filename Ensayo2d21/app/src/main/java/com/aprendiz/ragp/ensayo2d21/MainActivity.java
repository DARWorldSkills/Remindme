package com.aprendiz.ragp.ensayo2d21;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    Button btnrecord, btnsaveA, btnstart, btnstop, btncamera, btnsaveAll;
    String pathSave="";
    String pathImage="";
    ImageView layoutimagen;
    EditText txtTitle;
    public static Datos pdatos = null;

    private static final int REQUEST_PERMISION_CODE=15;
    private static final int REQUEST_CAMERA=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inizialite();
        if (Build.VERSION.SDK_INT>=24){
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        btnsaveA.setEnabled(false);
        btnstart.setEnabled(false);
        btnstop.setEnabled(false);
        mediaRecorder = new MediaRecorder();

        btncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });


        btnrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                permissionRecord();
                startRecording();

                btnrecord.setEnabled(false);
                btnsaveA.setEnabled(true);
                btnstart.setEnabled(false);
                btnstop.setEnabled(false);

                Toast.makeText(MainActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
            }
        });

        btnsaveA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnrecord.setEnabled(true);
                btnsaveA.setEnabled(false);
                btnstart.setEnabled(true);
                btnstop.setEnabled(false);
                stopRecording();
            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnrecord.setEnabled(false);
                btnsaveA.setEnabled(true);
                btnstart.setEnabled(false);
                btnstop.setEnabled(true);
                mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(pathSave);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Playing", Toast.LENGTH_SHORT).show();

            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnrecord.setEnabled(true);
                btnsaveA.setEnabled(false);
                btnstart.setEnabled(true);
                btnstop.setEnabled(false);
                if (mediaPlayer!= null){
                    mediaPlayer.stop();}
                    mediaPlayer.release();

                }

        });

        btnsaveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputRecuerdo();
            }
        });




    }

    private void inputRecuerdo() {
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        Datos datos = new Datos();
        datos.setTitle(txtTitle.getText().toString());
        datos.setImage(pathImage);
        datos.setAudio(pathSave);

        try{


            values.put("TITLE",datos.getTitle());
            values.put("IMAGE",datos.getImage());
            values.put("AUDIO",datos.getAudio());

            db.insert("DATOS",null,values);

            Intent intent = new Intent(MainActivity.this,Listar_Recuerdos.class);
            pdatos=datos;
            startActivity(intent);
            finish();

        }catch (Exception e){
            Toast.makeText(this, "Faltan campos por completar", Toast.LENGTH_SHORT).show();
        }


    }

    private void inizialite() {
        btnrecord = findViewById(R.id.btnrecord);
        btnsaveA = findViewById(R.id.btnsave);
        btnstop = findViewById(R.id.btnStop);
        btnstart = findViewById(R.id.btnStart);
        btncamera = findViewById(R.id.btncamera);
        btnsaveAll = findViewById(R.id.btnsaveAll);
        txtTitle = findViewById(R.id.txtTitleA);
        layoutimagen = findViewById(R.id.imageLayout);
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISION_CODE);
            }
        }

        callCamera();
    }

    private void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(this.getPackageManager())!=null){
            File file = null;
            try {
                file = createFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            if (file!= null){
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, REQUEST_CAMERA);
            }

        }


    }

    private File createFile() throws IOException {
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="JPEG_"+timeStamp+"_";
        File f= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image= File.createTempFile(imageFileName, ".jpg",f);
        pathImage= image.getAbsolutePath();

        return image;

    }

    protected void startRecording() {
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // TODO Auto-generated method stub
        pathSave = Environment.getExternalStorageDirectory().getAbsolutePath();
        pathSave += "/audiorecordtest"+timeStamp+".3gp";
        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(pathSave);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            Toast.makeText(getApplicationContext(), "IllegalStateException called", Toast.LENGTH_LONG).show();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            Toast.makeText(getApplicationContext(), "prepare() failed", Toast.LENGTH_LONG).show();

        }

        mediaRecorder.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }



    public void permissionRecord(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISION_CODE);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISION_CODE:{

                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permiso consedido", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        layoutimagen.setImageURI(Uri.fromFile(new File(pathImage)));
    }
}

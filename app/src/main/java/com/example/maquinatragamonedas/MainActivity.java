package com.example.maquinatragamonedas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    int dificultad = 100;
    int columna;
    boolean[] continuar = {false, false, false};
    TextView tv, txtDificultad1;
    int[] fotoId = {
            R.drawable.uno,
            R.drawable.dos,
            R.drawable.tres,
            R.drawable.cuatro,
            R.drawable.cinco,
            R.drawable.seis,
            R.drawable.siete,
            R.drawable.ocho,
            R.drawable.nueve,
    };
    int[][] secuencia = {
            {0, 1, 2, 3, 4, 5, 6, 7, 8},
            {8, 7, 6, 5, 4, 3, 2, 1, 0},
            {4, 5, 3, 2, 6, 7, 1, 0, 8}};
    ImageView[][] imagev = new ImageView[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin);

        tv = findViewById(R.id.texto);
        txtDificultad1 = findViewById(R.id.difi);
        imagev[0][0] = findViewById(R.id.imageView1);
        imagev[1][0] = findViewById(R.id.imageView2);
        imagev[2][0] = findViewById(R.id.imageView3);
        imagev[0][1] = findViewById(R.id.imageView4);
        imagev[1][1] = findViewById(R.id.imageView5);
        imagev[2][1] = findViewById(R.id.imageView6);
        imagev[0][2] = findViewById(R.id.imageView7);
        imagev[1][2] = findViewById(R.id.imageView8);
        imagev[2][2] = findViewById(R.id.imageView9);
        View boton1 = findViewById(R.id.button);
        boton1.setOnClickListener(this);
        View boton2 = findViewById(R.id.button2);
        boton2.setOnClickListener(this);
        View boton3 = findViewById(R.id.button3);
        boton3.setOnClickListener(this);
        View boton4 = findViewById(R.id.button4);
        boton4.setOnClickListener(this);
        View boton5 = findViewById(R.id.button5);
        boton5.setOnClickListener(this);
        View boton6 = findViewById(R.id.button6);
        boton6.setOnClickListener(this);
    }

    class MiAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... parameter) {
            int columna = parameter[0];
            while (continuar[columna]) {
                int elemento1 = secuencia[columna][0];
                for (int i = 0; i < 8; i++) {
                    secuencia[columna][i] = secuencia[columna][i + 1];
                }
                secuencia[columna][8] = elemento1;
                try {
                    Thread.sleep(Math.abs(dificultad));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(columna);
            }
            return "Stop columna " + (columna + 1);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            int columna = progress[0];
            for (int i = 0; i < 3; i++) {
                imagev[i][columna].setImageResource(
                        fotoId[secuencia[columna][i]]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (continuar[0] == false & continuar[1] == false & continuar[2] == false) {
                if (secuencia[0][1] == secuencia[1][1]
                        & secuencia[0][1] == secuencia[2][1]) {
                    tv.setText("Premio");
                } else {
                    tv.setText("Suerte la proxima vez");
                    tv.setText("Pruebe suerte");
                }
            } else {
                tv.setText("" + result);
            }
        }

    }
    // end AsyncTask
    @Override
    public void onClick(View boton){
        if (boton.getId()==R.id.button2|
            boton.getId()==R.id.button4|
            boton.getId()==R.id.button6){
                if(boton.getId()==R.id.button2)
                    dificultad=dificultad+10;
            if(boton.getId()==R.id.button4)
                dificultad=200;
            if(boton.getId()==R.id.button6)
                dificultad=dificultad-10;
            txtDificultad1.setText("Dificultad; "+dificultad);
        }
        else {
            if(boton.getId()==R.id.button)columna=0;
            if(boton.getId()==R.id.button3)columna=1;
            if(boton.getId()==R.id.button5)columna=2;

            continuar[columna]=!continuar[columna];
            if (continuar[columna]){
                new MiAsyncTask().execute(columna);
                ((TextView)boton).setText("Parar");
            }
            else{
                ((TextView)boton).setText("Continuar");
            }

        }
    }//end onClick
}

package com.ufc.quixada.elderlycare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    Button buttonIdoso, buttonCuidador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSharedPreferences();
        buttonIdoso = findViewById(R.id.btnIdoso);
        buttonCuidador = findViewById(R.id.btnCuidador);
        buttonIdoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IdosoActivity.class);
                startActivity(intent);
            }
        });

        buttonCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CuidadorActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void getSharedPreferences() {
        Preferencias preferencias = new Preferencias(MainActivity.this);
        Integer codigoIdoso = preferencias.getCodigoIdoso();
        String tipoUsuario = preferencias.getTipoUsuario();
        Log.d("codigoIdoso", "algo" + codigoIdoso);
        Log.d("tipoUsuario", "algo" + tipoUsuario);
        if(codigoIdoso != 0) {
            if(tipoUsuario.equals("idoso")) {
                Intent intent = new Intent(MainActivity.this, IdosoNotificarActivity.class);
                startActivity(intent);
            } else if(tipoUsuario.equals("cuidador")) {
                Intent intent = new Intent(MainActivity.this, CuidadorAguardarActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}

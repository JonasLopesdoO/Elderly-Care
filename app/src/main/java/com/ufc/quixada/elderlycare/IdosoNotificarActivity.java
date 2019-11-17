package com.ufc.quixada.elderlycare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class IdosoNotificarActivity extends AppCompatActivity {

    TextView txtCodigoIdoso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idoso_notificar);

        txtCodigoIdoso = findViewById(R.id.txtCodigoIdoso);
        getSharedPreferences();
    }

    protected void getSharedPreferences() {
        Preferencias preferencias = new Preferencias(IdosoNotificarActivity.this);
        Integer codigoIdoso = preferencias.getCodigoIdoso();
        txtCodigoIdoso.setText(String.valueOf(codigoIdoso));
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}

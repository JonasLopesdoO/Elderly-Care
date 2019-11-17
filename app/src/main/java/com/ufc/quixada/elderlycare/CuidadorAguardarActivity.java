package com.ufc.quixada.elderlycare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CuidadorAguardarActivity extends AppCompatActivity {

    TextView txtCodigoIdoso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_aguardar);

        txtCodigoIdoso = findViewById(R.id.txtCodigoIdoso);
        getSharedPreferences();
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    protected void getSharedPreferences() {
        Preferencias preferencias = new Preferencias(CuidadorAguardarActivity.this);
        Integer codigoIdoso = preferencias.getCodigoIdoso();
        txtCodigoIdoso.setText(String.valueOf(codigoIdoso));
    }
}

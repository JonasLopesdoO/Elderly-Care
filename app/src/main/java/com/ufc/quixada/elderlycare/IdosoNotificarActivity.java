package com.ufc.quixada.elderlycare;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

public class IdosoNotificarActivity extends AppCompatActivity {

    LinearLayout layoutIdosoMain;
    TextView txtCodigoIdoso;
    Integer codigoIdoso;
    DatabaseReference idosoCuidadorRef = ConfiguracaoFirebase.getFirebase().child("idoso-cuidador");

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idoso_notificar);

        txtCodigoIdoso = findViewById(R.id.txtCodigoIdoso);
        getSharedPreferences();
        layoutIdosoMain = findViewById(R.id.layoutIdosoMain);

        layoutIdosoMain.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {
                idosoCuidadorRef.child(String.valueOf(codigoIdoso)).setValue(1);
            }

            @Override
            public void onDoubleClick(View view) {
                idosoCuidadorRef.child(String.valueOf(codigoIdoso)).setValue(2);
            }
        }, 500));
        layoutIdosoMain.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                idosoCuidadorRef.child(String.valueOf(codigoIdoso)).setValue(3);
                return true;
            }
        });
    }

    protected void getSharedPreferences() {
        Preferencias preferencias = new Preferencias(IdosoNotificarActivity.this);
        codigoIdoso = preferencias.getCodigoIdoso();
        txtCodigoIdoso.setText(String.valueOf(codigoIdoso));
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}

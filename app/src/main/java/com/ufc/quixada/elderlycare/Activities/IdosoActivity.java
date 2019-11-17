package com.ufc.quixada.elderlycare.Activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.quixada.elderlycare.Configuracao.ConfiguracaoFirebase;
import com.ufc.quixada.elderlycare.Configuracao.Preferencias;
import com.ufc.quixada.elderlycare.R;

public class IdosoActivity extends AppCompatActivity {
    Integer codigo;
    TextView codigoTextView;
    DatabaseReference codigoRef = ConfiguracaoFirebase.getFirebase().child("codigo");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idoso);
        codigoTextView = findViewById(R.id.textViewCodigo);
    }

    @Override
    protected void onStart() {
        super.onStart();
        codigoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                codigo = dataSnapshot.getValue(Integer.class);
                codigoTextView.setText(String.valueOf(codigo));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        codigoRef.setValue(codigo+1);
        salvarPreferencias();
    }

    private void salvarPreferencias() {
        Preferencias preferencias = new Preferencias(IdosoActivity.this);
        preferencias.salvarUsuarioPreferencias("idoso", codigo);
    }
}




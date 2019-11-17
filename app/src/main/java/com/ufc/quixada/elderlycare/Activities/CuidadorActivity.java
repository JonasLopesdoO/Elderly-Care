package com.ufc.quixada.elderlycare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.ufc.quixada.elderlycare.Configuracao.ConfiguracaoFirebase;
import com.ufc.quixada.elderlycare.Configuracao.Preferencias;
import com.ufc.quixada.elderlycare.R;

import java.util.HashMap;
import java.util.Map;

public class CuidadorActivity extends AppCompatActivity {

    Button btnSalvarIdoso;
    EditText edtCodigoIdoso;
    DatabaseReference idosoCuidadorRef = ConfiguracaoFirebase.getFirebase().child("idoso-cuidador");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador);

        btnSalvarIdoso = findViewById(R.id.btnCadastrarIdoso);
        edtCodigoIdoso = findViewById(R.id.edtCodigoIdoso);

        btnSalvarIdoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<Integer, Integer> cuidadorIdosoRelacao = new HashMap<>();
                cuidadorIdosoRelacao.put(Integer.valueOf(edtCodigoIdoso.getText().toString()), 0);
                idosoCuidadorRef.child(edtCodigoIdoso.getText().toString()).setValue(0);
                salvarPreferencias();
                alert("Relacao entre cuidador e idoso cadastrada com sucesso!");
                startActivity(new Intent(CuidadorActivity.this, CuidadorAguardarActivity.class));
            }
        });
    }

    private void salvarPreferencias() {
        Preferencias preferencias = new Preferencias(CuidadorActivity.this);
        preferencias.salvarUsuarioPreferencias("cuidador", Integer.valueOf(edtCodigoIdoso.getText().toString()));
    }

    public void alert(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}

package com.ufc.quixada.elderlycare.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufc.quixada.elderlycare.Configuracao.ConfiguracaoFirebase;
import com.ufc.quixada.elderlycare.Configuracao.Preferencias;
import com.ufc.quixada.elderlycare.R;

import static com.ufc.quixada.elderlycare.Activities.NotificationApp.CHANNEL_1_ID;
import static com.ufc.quixada.elderlycare.Activities.NotificationApp.CHANNEL_2_ID;

public class CuidadorAguardarActivity extends AppCompatActivity {
    NotificationManagerCompat notificationManager;
    TextView txtCodigoIdoso;
    DatabaseReference idosoCuidadorRef;
    Integer codigoIdoso;
    Integer codigoFirebaseChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_aguardar);
        txtCodigoIdoso = findViewById(R.id.txtCodigoIdoso);
        notificationManager = NotificationManagerCompat.from(this);
        getSharedPreferences();
    }

    protected void getSharedPreferences() {
        Preferencias preferencias = new Preferencias(CuidadorAguardarActivity.this);
        codigoIdoso = preferencias.getCodigoIdoso();
        txtCodigoIdoso.setText(String.valueOf(codigoIdoso));
        idosoCuidadorRef = ConfiguracaoFirebase.getFirebase().child("idoso-cuidador").child(String.valueOf(codigoIdoso));
    }

    @Override
    protected void onResume() {
        super.onResume();
        idosoCuidadorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                codigoFirebaseChild = dataSnapshot.getValue(Integer.class);
                if(codigoFirebaseChild == 3) {
                    sendOnChannel2();
                } else if(codigoFirebaseChild == 1 || codigoFirebaseChild == 2) {
                    sendOnChannel1();
                }
                //pushNotificacao();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendOnChannel1() {
        Intent intent = new Intent(CuidadorAguardarActivity.this, CuidadorAguardarActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(CuidadorAguardarActivity.this, 0, intent, 0);
        Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_alert)
                .setContentTitle("IDOSO PRECISA DE CUIDADOS!")
                .setContentText(setarMensagemCorretaNaNotificacao())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setSound(som)
                .build();

        setarNotificacaoParaZero();
        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2() {
        Intent intent = new Intent(CuidadorAguardarActivity.this, CuidadorAguardarActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(CuidadorAguardarActivity.this, 0, intent, 0);
        Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_alert)
                .setContentTitle("IDOSO PRECISA DE CUIDADOS!")
                .setContentText(setarMensagemCorretaNaNotificacao())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(pendingIntent)
                .setSound(som)
                .build();

        setarNotificacaoParaZero();
        notificationManager.notify(2, notification);
    }

    private String setarMensagemCorretaNaNotificacao() {
        switch (codigoFirebaseChild) {
            case 1:
                return "O seu idoso precisa beber água";
            case 2:
                return "O seu idoso precisa ir ao banheiro.";
            case 3:
                return "IDOSO NÃO ESTÁ PASSANDO BEM!.";
        }
        return null;
    }

    private void setarNotificacaoParaZero() {
        idosoCuidadorRef.setValue(0);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }
}

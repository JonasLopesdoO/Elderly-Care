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

public class CuidadorAguardarActivity extends AppCompatActivity {

    TextView txtCodigoIdoso;
    DatabaseReference idosoCuidadorRef;
    Integer codigoIdoso;
    Integer codigoFirebaseChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_aguardar);
        txtCodigoIdoso = findViewById(R.id.txtCodigoIdoso);
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
                pushNotificacao();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pushNotificacao() {
        if(codigoFirebaseChild != 0) {
            Intent intent = new Intent(CuidadorAguardarActivity.this, CuidadorAguardarActivity.class);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(CuidadorAguardarActivity.this, 0, intent, 0);

            NotificationCompat.Builder builderNotification = new NotificationCompat.Builder(CuidadorAguardarActivity.this);
            builderNotification.setTicker("Elderly Care");
            builderNotification.setContentTitle("IDOSO PRECISA DE CUIDADOS!");
            builderNotification.setSmallIcon(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_light_focused);
            builderNotification.setLargeIcon(BitmapFactory.decodeResource(getResources(), com.google.firebase.database.R.drawable.common_google_signin_btn_icon_light_normal_background));
            builderNotification.setContentText(setarMensagemCorretaNaNotificacao());
            builderNotification.setContentIntent(pendingIntent);

            setarNotificacaoParaZero();

            Notification notification = builderNotification.build();
            notification.vibrate = new long[]{150, 300, 150, 600};
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_light_normal_background, notification);

            try {
                Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone toque = RingtoneManager.getRingtone(CuidadorAguardarActivity.this, som);
                toque.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

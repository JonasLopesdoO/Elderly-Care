package com.ufc.quixada.elderlycare.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.database.*;
import com.ufc.quixada.elderlycare.Activities.CuidadorAguardarActivity;
import com.ufc.quixada.elderlycare.Configuracao.ConfiguracaoFirebase;
import com.ufc.quixada.elderlycare.Configuracao.Preferencias;

import java.util.ArrayList;

public class ServiceNotificar extends Service {

    DatabaseReference codigoRef;
    private Context context;
    Integer codigoIdoso;

    public ArrayList<Downloader> threads = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getSharedPreferences();
        Log.d("Script", "OnCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Script", "OnStarCommand");
        context = getApplicationContext();

        Downloader worker = new Downloader(startId);
        worker.start();
        threads.add(worker);
        return super.onStartCommand(intent, flags, startId);
    }

    public class Downloader extends Thread {

        int startId;
        boolean ativo = true;

        public Downloader(int startId) {
            this.startId = startId;
        }

        public void run() {
            do {
                ChildEventListener childListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Intent intent = new Intent(context, CuidadorAguardarActivity.class);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                        NotificationCompat.Builder builderNotification = new NotificationCompat.Builder(context);
                        builderNotification.setTicker("Elderly Care");
                        builderNotification.setContentTitle("IDOSO PRECISA DE CUIDADOS!");
                        builderNotification.setContentText("O seu idoso precisa beber água.");
                        builderNotification.setSmallIcon(R.drawable.common_google_signin_btn_icon_light_focused);
                        builderNotification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.common_google_signin_btn_icon_light_normal_background));
                        builderNotification.setContentIntent(pendingIntent);

                        Notification notification = builderNotification.build();
                        notification.vibrate = new long[]{150, 300, 150, 600};
                        notification.flags = Notification.FLAG_AUTO_CANCEL;
                        notificationManager.notify(R.drawable.common_google_signin_btn_icon_light_normal_background, notification);

                        try {
                            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone toque = RingtoneManager.getRingtone(context, som);
                            toque.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Intent intent = new Intent(context, CuidadorAguardarActivity.class);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                        NotificationCompat.Builder builderNotification = new NotificationCompat.Builder(context);
                        builderNotification.setTicker("Elderly Care");
                        builderNotification.setContentTitle("IDOSO PRECISA DE CUIDADOS!");
                        builderNotification.setContentText("O seu idoso precisa beber água.");
                        builderNotification.setSmallIcon(R.drawable.common_google_signin_btn_icon_light_focused);
                        builderNotification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.common_google_signin_btn_icon_light_normal_background));
                        builderNotification.setContentIntent(pendingIntent);

                        Notification notification = builderNotification.build();
                        notification.vibrate = new long[]{150, 300, 150, 600};
                        notification.flags = Notification.FLAG_AUTO_CANCEL;
                        notificationManager.notify(R.drawable.common_google_signin_btn_icon_light_normal_background, notification);

                        try {
                            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone toque = RingtoneManager.getRingtone(context, som);
                            toque.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                codigoRef.addChildEventListener(childListener);

                stopSelf(startId);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (ativo);
        }
    }

    protected void getSharedPreferences() {
        Preferencias preferencias = new Preferencias(ServiceNotificar.this);
        codigoIdoso = preferencias.getCodigoIdoso();
        codigoRef = ConfiguracaoFirebase.getFirebase().child("idoso-cuidador").child(String.valueOf(codigoIdoso));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0, tam = threads.size(); i < tam; i++) {
            threads.get(i).ativo = false;
        }
    }
}

package com.ufc.quixada.elderlycare;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView mConditionTextView;
    Button mButtonSunny;
    Button mButtonFoggy;

    DatabaseReference mRootRef = ConfiguracaoFirebase.getFirebase();
    DatabaseReference mIdosoRef = mRootRef.child("idoso");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConditionTextView = findViewById(R.id.textViewCondition);
        mButtonSunny = findViewById(R.id.btnSunny);
        mButtonFoggy = findViewById(R.id.btnFoggy);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mIdosoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mConditionTextView.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mButtonSunny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIdosoRef.setValue("Sunny");
            }
        });

        mButtonFoggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIdosoRef.setValue("Foggy");
            }
        });
    }
}

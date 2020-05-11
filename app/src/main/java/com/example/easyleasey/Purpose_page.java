package com.example.easyleasey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Purpose_page extends AppCompatActivity{

    private Button addnew,rentcar;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purposepage);

        addnew = findViewById(R.id.add_new);
        rentcar = findViewById(R.id.rentcar);

        mAuth = FirebaseAuth.getInstance();
        mCurrentuser = mAuth.getCurrentUser();

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Purpose_page.this,Addcar_page.class));
            }
        });

        rentcar.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Purpose_page.this,Filter_page.class));
            }
        }));
    }



}

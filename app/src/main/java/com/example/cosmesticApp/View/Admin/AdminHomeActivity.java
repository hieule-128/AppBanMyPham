package com.example.cosmesticApp.View.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cosmesticApp.R;
import com.example.cosmesticApp.View.SignInActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private CardView cHoaDon, cAddProduct, cSignOut, cAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Init();
        Event();
    }

    private void Event() {
        cAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminUsersActivity.class);
                startActivity(intent);
            }
        });

        cHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminBillMainActivity.class);
                startActivity(intent);
            }
        });

        cAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminProductActivity.class);
                startActivity(intent);
            }
        });

        cSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Init() {
        cAddUser = findViewById(R.id.cAddUser);
        cHoaDon = findViewById(R.id.cHoaDon);
        cAddProduct = findViewById(R.id.cAddProduct);
        cSignOut = findViewById(R.id.cSignOut);
    }
}
package com.example.cosmesticApp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cosmesticApp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText edtEmailForgot/*, edtPassForgot*/;
    private Button btnForgot;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // Check Internet
    private BroadcastReceiver MyReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        InitWidget();
        Event();
    }

    private void Event() {
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stremail = edtEmailForgot.getText().toString().trim();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(stremail)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            Toast.makeText(ForgotPasswordActivity.this, "Đã gửi Email", Toast.LENGTH_SHORT).show();
                            finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        //String errorMessage = e.getMessage();
                        Toast.makeText(ForgotPasswordActivity.this, "Lỗi: không tìm thấy email được đăng ký!!!" /*+ errorMessage*/, Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    private void InitWidget() {
        edtEmailForgot = findViewById(R.id.edt_email_forgot);
        btnForgot = findViewById(R.id.btn_forgot);
    }

}
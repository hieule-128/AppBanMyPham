package com.example.doan_tmdt.View.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_tmdt.MainActivity;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Models.User;
import com.example.doan_tmdt.Presenter.UserPresenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.View.SignInActivity;
import com.example.doan_tmdt.View.SignUpActivity;
import com.example.doan_tmdt.my_interface.IClickCTHD;
import com.example.doan_tmdt.my_interface.UserView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminUsersActivity extends AppCompatActivity implements UserView {

    private ImageView imgBackUsers;
    private RecyclerView rcvUsers;
    private AppCompatImageView imageAddUsers;
    private AdminUsersAdapter adapter;
    private ArrayList<User> mlistUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private UserPresenter userPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        InitWidget();
        Init();
        Event();
    }

    private void Event() {
        imgBackUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imageAddUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AdminUsersActivity.this);
                dialog.setContentView(R.layout.dialog_add_users);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                EditText edtAddEmailUsers = dialog.findViewById(R.id.edt_add_email_users);
                EditText edtAddPassUsers = dialog.findViewById(R.id.edt_add_pass_users);
                EditText edtAddConfirmUsers = dialog.findViewById(R.id.edt_add_confirm_users);
                Button btnAddUsers = dialog.findViewById(R.id.btn_add_users);
                Button btnHuyUsers = dialog.findViewById(R.id.btn_huy_users);
                dialog.setCanceledOnTouchOutside(true);

                btnAddUsers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String strEmail = edtAddEmailUsers.getText().toString().trim();
                        String strPass = edtAddPassUsers.getText().toString().trim();
                        String strConfirm = edtAddConfirmUsers.getText().toString().trim();
                        if (strEmail.length() > 0){
                            if (strPass.length() > 0) {
                                if (strPass.equals(strConfirm)){
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    auth.createUserWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                HashMap<String,String> hashMap =  new HashMap<>();
                                                hashMap.put("iduser",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                hashMap.put("email", strEmail);
                                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                db.collection("IDUser").add(hashMap);
                                                Map<String, Object> chinh = new HashMap<>();
                                                chinh.put("iduser",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                chinh.put("hoten", "");
                                                chinh.put("diachi", "");
                                                chinh.put("sdt", "");
                                                chinh.put("ngaysinh", "");
                                                chinh.put("gioitinh", "");
                                                chinh.put("avatar", "");
                                                db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Profile").add(chinh);
                                                Toast.makeText(AdminUsersActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();

                                            } else if (!isEmailValid(strEmail)){
                                                Toast.makeText(AdminUsersActivity.this, "Email định dạng không đúng", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(AdminUsersActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(AdminUsersActivity.this, "Mật khẩu xác nhận không khớp.\nVui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(AdminUsersActivity.this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                            }

                        } else{
                            Toast.makeText(AdminUsersActivity.this, "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                btnHuyUsers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    private void Init() {
        mlistUser = new ArrayList<>();
    }

    private void InitWidget() {
        imgBackUsers = findViewById(R.id.img_back_users);
        rcvUsers = findViewById(R.id.rcv_users);
        imageAddUsers = findViewById(R.id.image_add_users);

        userPresenter = new UserPresenter(AdminUsersActivity.this);
        db.collection("IDUser").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               for (QueryDocumentSnapshot q : queryDocumentSnapshots){
                   userPresenter.HandleGetUsers(q.getString("iduser"), q.getString("email"));
               }
            }
        });

    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataUser(String id, String email, String name, String address, String phone, String date, String gender, String avatar) {

        mlistUser.add(new User(id, email, name, address, phone, date, gender, avatar));
        adapter = new AdminUsersAdapter(AdminUsersActivity.this, mlistUser, new IClickCTHD() {
            @Override
            public void onClickCTHD(int pos) {
                User user = mlistUser.get(pos);

                Dialog dialog = new Dialog(AdminUsersActivity.this);
                dialog.setContentView(R.layout.dialog_info_users);
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                EditText edtIdusers = dialog.findViewById(R.id.edt_id_users);
                EditText edtEmailUsers = dialog.findViewById(R.id.edt_email_users);
                EditText edtNameUsers = dialog.findViewById(R.id.edt_name_users);
                EditText edtAddressUsers = dialog.findViewById(R.id.edt_address_users);
                EditText edtSDTUsers = dialog.findViewById(R.id.edt_sdt_users);
                EditText edtDateUsers = dialog.findViewById(R.id.edt_date_users);
                EditText edtGenderUsers = dialog.findViewById(R.id.edt_gender_users);
                ImageView btnCancelUsers = dialog.findViewById(R.id.btn_cancel_users);
                dialog.setCanceledOnTouchOutside(true);

                edtIdusers.setText(user.getIduser());
                edtEmailUsers.setText(user.getEmail());
                edtNameUsers.setText(user.getName());
                edtAddressUsers.setText(user.getAddress());
                edtSDTUsers.setText(user.getPhone());
                edtDateUsers.setText(user.getDate());
                edtGenderUsers.setText(user.getSex());

                btnCancelUsers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
        rcvUsers.setLayoutManager(new GridLayoutManager(AdminUsersActivity.this, 2));
        rcvUsers.setAdapter(adapter);
    }

}
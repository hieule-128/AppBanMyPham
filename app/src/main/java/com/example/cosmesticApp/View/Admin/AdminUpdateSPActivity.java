package com.example.cosmesticApp.View.Admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.example.cosmesticApp.Models.Product;
import com.example.cosmesticApp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminUpdateSPActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private CircleImageView imgAddCategory;
    private ImageView btnAddBack, btnEdit, btnDelete;
    private EditText updateTenSP, updateGiatienSP, updateHansudungSP, updateTrongluongSP, updateSoluongSP, updateTypeSP, updateMotaSP;
    private ImageView imgEdit;
    private Button btnDanhmuc;
    private Spinner spinnerDanhMuc;
    private FirebaseFirestore db;
    private List<String> list;
    private Product product;
    private String image = "";
    private static final int LIBRARY_PICKER = 12312;
    private ProgressDialog dialog;
    private String loaisp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_spactivity);
        InitWidget();
        Init();
        Event();
    }

    private void Event() {
        btnAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminUpdateSPActivity.this, AdminAddLoaiSPActivity.class);
                intent.putExtra("loaisp", loaisp);
                startActivity(intent);
            }
        });

        btnDanhmuc.setOnClickListener(view -> spinnerDanhMuc.performClick());
        spinnerDanhMuc.setOnItemSelectedListener(this);
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        // Nếu xóa bất kỳ 1 sản phẩm nào đó thì những hóa đơn có chứa sản phẩm đó cũng phải bị xóa hoặc dùng nhiều cách khác.
        // Ở đây lựa chọn xóa luôn hóa đơn chứa sản phẩm bị xóa.
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("SanPham").document(product.getId()).delete().addOnSuccessListener(unused -> {
                    db.collection("IDUser").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot q: queryDocumentSnapshots){
                                Log.d("checkiduser", q.getString("iduser"));

                                // Từ iduser mà ta có, lấy ra tất cả id_hoadon có id_product
                                db.collection("ChitietHoaDon").document(q.getString("iduser")).
                                        collection("ALL").whereEqualTo("id_product", product.getId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                        for (QueryDocumentSnapshot d: queryDocumentSnapshots){
                                            Log.d("checkidhoadon", d.getString("id_hoadon"));

                                            // Từ id_hoadon mà ta có, thực hiện xóa id hóa đơn của bảng HoaDon
                                            db.collection("HoaDon").document(d.getString("id_hoadon")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(AdminUpdateSPActivity.this, "Xoá sản phẩm thành công!!!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });
                            }

                        }
                    });

                    setResult(RESULT_OK);
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(AdminUpdateSPActivity.this, "Xoá sản phẩm thất bại!!!", Toast.LENGTH_SHORT).show();
                });

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    return;
                }
                try {
                    Product sp = new Product();
                    sp.setGiatien(Long.parseLong(updateGiatienSP.getText().toString()));
                    sp.setMota(updateMotaSP.getText().toString());
                    sp.setHansudung(updateHansudungSP.getText().toString());
                    sp.setType(Long.parseLong(updateTypeSP.getText().toString()));
                    sp.setTensp(updateTenSP.getText().toString());
                    sp.setSoluong(Long.parseLong(updateSoluongSP.getText().toString()));
                    sp.setTrongluong(updateTrongluongSP.getText().toString());
                    sp.setLoaisp(spinnerDanhMuc.getSelectedItem().toString());
                    sp.setHinhanh(image);
                    db.collection("SanPham").document(product.getId()).set(sp)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(AdminUpdateSPActivity.this, "Cập nhật sản phẩm thành công!!!", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminUpdateSPActivity.this, "Cập nhật sản phẩm thất bại!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void Init() {
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        list.add("Chọn Danh mục");

        // Nhận data từ AdminProductActivity
        if (getIntent() != null && getIntent().hasExtra("SP")) {
            product = (Product) getIntent().getSerializableExtra("SP");
        }

        db.collection("LoaiProduct").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                    list.add(q.getString("tenloai"));
                    Log.d("TAG", "onSuccess: " + q.getString("tenloai"));
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(AdminUpdateSPActivity.this, android.R.layout.simple_list_item_1, list);
                spinnerDanhMuc.setAdapter(arrayAdapter);
                if (list.size() > 0) {
                    spinnerDanhMuc.setSelection(1);
                    if (product != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).equals(product.getLoaisp())) {
                                spinnerDanhMuc.setSelection(i);
                                break;
                            }
                        }
                    }
                }

            }
        });
        updateTenSP.setText(product.getTensp());
        updateGiatienSP.setText(NumberFormat.getInstance().format(product.getGiatien()));
        updateSoluongSP.setText(NumberFormat.getInstance().format(product.getSoluong()));
        updateHansudungSP.setText(product.getHansudung());
        updateTrongluongSP.setText(product.getTrongluong());
        updateTypeSP.setText(product.getLoaisp());
        updateMotaSP.setText(product.getMota());
        Picasso.get().load(product.getHinhanh()).into(imgAddCategory);
    }

    private boolean validate() {
        if (TextUtils.isEmpty(image)) {
            Toast.makeText(this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(updateGiatienSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập giá tiền", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(updateTenSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(updateHansudungSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập hạn sử dụng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(updateTrongluongSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập Trọng lượng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(updateSoluongSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(updateTypeSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(updateMotaSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập mô tả", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void InitWidget() {
        imgAddCategory = findViewById(R.id.img_add_category);
        btnAddBack = findViewById(R.id.btn_add_back);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);
        updateTenSP = findViewById(R.id.product_update);
        updateGiatienSP = findViewById(R.id.product_price);
        updateHansudungSP = findViewById(R.id.date_product);
        updateTrongluongSP = findViewById(R.id.quality_product);
        updateSoluongSP = findViewById(R.id.qty_product);
        updateTypeSP = findViewById(R.id.type_product);
        updateMotaSP = findViewById(R.id.des_product);
        imgEdit = findViewById(R.id.image_edit);
        btnDanhmuc = findViewById(R.id.btn_category);
        spinnerDanhMuc = findViewById(R.id.spinner_danhmuc);

        // Dialog
        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Upload image");
        dialog.setMessage("Uploading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    private void pickImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    LIBRARY_PICKER);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), LIBRARY_PICKER);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // pick image after request permission success
            pickImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LIBRARY_PICKER && resultCode == RESULT_OK && null != data) {
            try {

                dialog.show();
                Uri uri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();
                String filename = System.currentTimeMillis() + "";
                StorageReference storageReference;
                storageReference = FirebaseStorage.getInstance("gs://cosmesticstoreapp.appspot.com/").getReference();
                storageReference.child("Profile").child(filename + ".jpg").putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getTask().isSuccessful()) {
                            storageReference.child("Profile").child(filename + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(@NonNull Uri uri) {
                                    imgEdit.setImageBitmap(bitmap);
                                    image = uri.toString();
                                }
                            });
                            Toast.makeText(AdminUpdateSPActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            } catch (FileNotFoundException e) {
                Log.d("CHECKED", e.getMessage());
                dialog.dismiss();
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position > 0) {
            btnDanhmuc.setText(spinnerDanhMuc.getSelectedItem().toString());
            String s = list.get(position);
            loaisp = s;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
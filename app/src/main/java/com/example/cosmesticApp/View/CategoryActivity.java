package com.example.cosmesticApp.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cosmesticApp.Adapter.CategoryAdapter;
import com.example.cosmesticApp.Models.Product;
import com.example.cosmesticApp.R;
import com.example.cosmesticApp.my_interface.IClickOpenBottomSheet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvCategory;
    private EditText edtSearch;
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Product product;
    private ArrayList<Product> arr_makeup, arr_perfume, arr_hair, arr_skincare, arr_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        InitWidget();
        Init();
        Event();
    }

    private void Init() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("loaiproduct", 1);
        Log.d("zxc", position + "");
        switch (position){
            case 0:
                tvCategory.setText("Trang Điểm");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Trang Điểm").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_makeup.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_makeup, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {

                                            product = arr_makeup.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }

                            }
                        });
                break;
            case 1:
                tvCategory.setText("Nước Hoa");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Nước Hoa").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_perfume.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_perfume, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {
//                                    setBottomSheetDialog();
                                            product = arr_perfume.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }

                            }
                        });
                break;
            case 2:
                tvCategory.setText("Chăm Sóc Tóc");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Chăm Sóc Tóc").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_hair.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_hair, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {
                                            product = arr_hair.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }

                            }
                        });
                break;
            case 3:
                tvCategory.setText("Chăm Sóc Da");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Chăm Sóc Da").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_skincare.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_skincare, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {
                                            product = arr_skincare.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }

                            }
                        });
                break;
            case 4:
                tvCategory.setText("Chăm Sóc Cơ Thể");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Chăm Sóc Cơ Thể").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_body.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_body, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {
                                            product = arr_body.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }

                            }
                        });
                break;
        }
    }

    private void SendData(){
        Intent intent = new Intent(CategoryActivity.this, DetailSPActivity.class);
        intent.putExtra("search", product);
        startActivity(intent);
    }

    private void Event() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void InitWidget() {
        imgBack = findViewById(R.id.img_back);
        tvCategory = findViewById(R.id.tv_category);
        edtSearch = findViewById(R.id.edt_search);
        rcvCategory = findViewById(R.id.rcv_category);

        arr_makeup = new ArrayList<>();
        arr_perfume = new ArrayList<>();
        arr_hair = new ArrayList<>();
        arr_skincare = new ArrayList<>();
        arr_body = new ArrayList<>();

    }

}

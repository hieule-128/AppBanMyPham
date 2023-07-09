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
    private ArrayList<Product> arr_MakeUp, arr_PerFume, arr_Hair, arr_Skincare, arr_Body;

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
                                arr_khac.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_MakeUp, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {

                                    product = arr_MakeUp.get(position);
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
                                arr_micay.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_PerFume, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
//                                    setBottomSheetDialog();
                                    product = arr_PerFume.get(position);
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
                                arr_chaosup.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_Hair, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_Hair.get(position);
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
                                arr_pizza.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_Skincare, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_Skincare.get(position);
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
                                arr_sandwich.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_Body, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_Body.get(position);
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
//            case 5:
//                tvCategory.setText("Đồ uống");
//                firestore.collection("SanPham").
//                        whereEqualTo("loaisp","Đồ uống").
//                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
//                        if(queryDocumentSnapshots.size()>0){
//                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
//                                // lấy id trên firebase
//                                arr_douong.add(new Product(d.getId(),d.getString("tensp"),
//                                        d.getLong("giatien"),d.getString("hinhanh"),
//                                        d.getString("loaisp"),d.getString("mota"),
//                                        d.getLong("soluong"),d.getString("hansudung"),
//                                        d.getLong("type"),d.getString("trongluong")));
//                            }
//                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_douong, new IClickOpenBottomSheet() {
//                                @Override
//                                public void onClickOpenBottomSheet(int position) {
//                                    product = arr_douong.get(position);
//                                    SendData();
//                                }
//                            });
//                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
//                            // Thêm đường phân cách giữa các dòng
//                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
//                            rcvCategory.addItemDecoration(itemDecoration);
//                            rcvCategory.setAdapter(categoryAdapter);
//                        }
//
//                    }
//                });
//                break;
//            case 6:
//                tvCategory.setText("Rau");
//                firestore.collection("SanPham").
//                        whereEqualTo("loaisp","Rau").
//                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
//                        if(queryDocumentSnapshots.size()>0){
//                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
//                                // lấy id trên firebase
//                                arr_lau.add(new Product(d.getId(),d.getString("tensp"),
//                                        d.getLong("giatien"),d.getString("hinhanh"),
//                                        d.getString("loaisp"),d.getString("mota"),
//                                        d.getLong("soluong"),d.getString("hansudung"),
//                                        d.getLong("type"),d.getString("trongluong")));
//                            }
//                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_lau, new IClickOpenBottomSheet() {
//                                @Override
//                                public void onClickOpenBottomSheet(int position) {
//                                    product = arr_lau.get(position);
//                                    SendData();
//                                }
//                            });
//                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
//                            // Thêm đường phân cách giữa các dòng
//                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
//                            rcvCategory.addItemDecoration(itemDecoration);
//                            rcvCategory.setAdapter(categoryAdapter);
//                        }
//
//                    }
//                });
//                break;
//            case 7:
//                tvCategory.setText("Trái cây");
////                findViewById(R.id.tv_message_chat).setBackgroundResource(R.drawable.facebook_drawable_chat);
//                firestore.collection("SanPham").
//                        whereEqualTo("loaisp","Trái cây").
//                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
//                        if(queryDocumentSnapshots.size()>0){
//                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
//                                // lấy id trên firebase
//                                arr_doannhanh.add(new Product(d.getId(),d.getString("tensp"),
//                                        d.getLong("giatien"),d.getString("hinhanh"),
//                                        d.getString("loaisp"),d.getString("mota"),
//                                        d.getLong("soluong"),d.getString("hansudung"),
//                                        d.getLong("type"),d.getString("trongluong")));
//                            }
//                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_doannhanh, new IClickOpenBottomSheet() {
//                                @Override
//                                public void onClickOpenBottomSheet(int position) {
//                                    product = arr_doannhanh.get(position);
//                                    SendData();
//                                }
//                            });
//                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
//                            // Thêm đường phân cách giữa các dòng
//                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
//                            rcvCategory.addItemDecoration(itemDecoration);
//                            rcvCategory.setAdapter(categoryAdapter);
//                        }
//
//                    }
//                });
//                break;
//        }
//    }

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

        arr_MakeUp = new ArrayList<>();
        arr_PerFume = new ArrayList<>();
        arr_Hair = new ArrayList<>();
        arr_Skincare = new ArrayList<>();
        arr_Body = new ArrayList<>();
//        arr_douong = new ArrayList<>();
//        arr_lau = new ArrayList<>();
//        arr_doannhanh = new ArrayList<>();

    }

}
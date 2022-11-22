package com.example.doan_tmdt.Presenter;

import com.example.doan_tmdt.Models.Giohang;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.my_interface.GioHangView;
import com.example.doan_tmdt.my_interface.IGioHang;

import java.util.ArrayList;

public class GioHangPresenter implements IGioHang {
    private Giohang giohang;
    private GioHangView callback;

    public GioHangPresenter(GioHangView callback) {
        this.callback = callback;
        giohang = new Giohang(this);
    }

    public  void AddCart(String idsp, Long soluong){
        giohang.AddCart(idsp, soluong);
    }

    public  void  HandlegetDataGioHang(){
        giohang.HandlegetDataGioHang();
    }
    public  void  HandleDeleteDataGioHang(String id){
        giohang.HandleDeleteDataGioHang(id);
    }

    @Override
    public void OnSucess() {
        callback.OnSucess();

    }

    @Override
    public void OnFail() {
        callback.OnFail();
    }

    @Override
    public void getDataSanPham(String id, String idsp,String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        callback.getDataSanPham(id,idsp,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong);
    }

    public void HandlegetDataCTHD(String id) {
        giohang.HandleGetDataCTHD(id);

    }
    public void HandlegetDataCTHD(String id,String uid) {
        giohang.HandleGetDataCTHD(id,uid);

    }
}

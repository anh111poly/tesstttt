/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.edu.model;

import java.util.Date;
import poly.edu.untinity.XDate;

/**
 *
 * @author Admin
 */
public class NguoiHoc {

    private String maNH;
    private String hoTen;
    private boolean gioiTinh;
    private Date ngaySinh;
    private String soDienThoai;
    private String email;
    private String ghiChu;
    private NhanVien maNhanVien;
    private Date ngayDK;

    public NguoiHoc() {
    }

    public NguoiHoc(String maNH, String hoTen, boolean gioiTinh, Date ngaySinh, String soDienThoai, String email, String ghiChu, NhanVien maNhanVien, Date ngayDK) {
        this.maNH = maNH;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.ghiChu = ghiChu;
        this.maNhanVien = maNhanVien;
        this.ngayDK = ngayDK;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public NhanVien getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(NhanVien maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Date getNgayDK() {
        ngayDK = XDate.now();
        return ngayDK;
    }

    public void setNgayDK(Date ngayDK) {
        this.ngayDK = ngayDK;
    }

}

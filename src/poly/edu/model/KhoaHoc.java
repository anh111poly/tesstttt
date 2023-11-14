package poly.edu.model;

import java.util.Date;
import poly.edu.untinity.XDate;

public class KhoaHoc {
    
    private int maKH;
    private ChuyenDe chuyenDe;
    private double hocPhi;
    private int thoiLuong;
    private Date ngayKG;
    private String ghiChu;
    private NhanVien nhanVien;
    private Date ngayTao;
    
    public KhoaHoc() {
    }
    
    public KhoaHoc(ChuyenDe maCD, double hocPhi, int thoiLuong, Date ngayKG, String ghiChu, NhanVien maNV, Date ngayTao) {
        this.chuyenDe = maCD;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.ngayKG = ngayKG;
        this.ghiChu = ghiChu;
        this.nhanVien = maNV;
        this.ngayTao = ngayTao;
    }
    
    public KhoaHoc(int maKH, ChuyenDe maCD, double hocPhi, int thoiLuong, Date ngayKG, String ghiChu, NhanVien maNV, Date ngayTao) {
        this.maKH = maKH;
        this.chuyenDe = maCD;
        this.hocPhi = hocPhi;
        this.thoiLuong = thoiLuong;
        this.ngayKG = ngayKG;
        this.ghiChu = ghiChu;
        this.nhanVien = maNV;
        this.ngayTao = ngayTao;
    }
    
    public int getMaKH() {
        return maKH;
    }
    
    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }
    
    public ChuyenDe getMaCD() {
        return chuyenDe;
    }
    
    public void setMaCD(ChuyenDe chuyenDe) {
        this.chuyenDe = chuyenDe;
    }
    
    public double getHocPhi() {
        return hocPhi;
    }
    
    public void setHocPhi(double hocPhi) {
        this.hocPhi = hocPhi;
    }
    
    public int getThoiLuong() {
        return thoiLuong;
    }
    
    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }
    
    public Date getNgayKG() {
        return ngayKG;
    }
    
    public void setNgayKG(Date ngayKG) {
        this.ngayKG = ngayKG;
    }
    
    public String getGhiChu() {
        return ghiChu;
    }
    
    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public NhanVien getMaNV() {
        return nhanVien;
    }
    
    public void setMaNV(NhanVien maNV) {
        this.nhanVien = maNV;
    }
    
    public Date getNgayTao() {
        ngayTao = XDate.now();
        return ngayTao;
    }
    
    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
    
    @Override
    public String toString() {
        return String.valueOf(maKH);
    }
    
}

package poly.edu.service;

import java.util.ArrayList;
import poly.edu.model.NhanVien;
import java.sql.*;
import poly.edu.untinity.DBContext;

public class NhanVienService {

    public ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> listNhanVien = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        String sql = "Select * from NhanVien";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getString(3));
                nv.setVaiTro(rs.getBoolean(4));
                listNhanVien.add(nv);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return listNhanVien;
    }

    public NhanVien getNhanVienByID(String ma) {

        Connection cn = DBContext.getConnection();
        String sql = "Select * from NhanVien where MaNV=?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, ma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setMatKhau(rs.getString(2));
                nv.setHoTen(rs.getString(3));
                nv.setVaiTro(rs.getBoolean(4));

                return nv;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public Integer updateNhanVien(NhanVien nv) {
        Integer r = null;
        String sql = "UPDATE NhanVien SET MatKhau =? WHERE  MaNV=?";
        Connection cnn = DBContext.getConnection();
        try {
            PreparedStatement ps = cnn.prepareStatement(sql);
            ps.setObject(1, nv.getMatKhau());
            ps.setObject(2, nv.getMaNV());

            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return r;

    }

    public Integer addNhanVien(NhanVien nv) {
        Integer r = null;
        String sql = "INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro)VALUES (?,?,?,?)";
        Connection cnn = DBContext.getConnection();
        try {
            PreparedStatement ps = cnn.prepareStatement(sql);
            ps.setObject(1, nv.getMaNV());
            ps.setObject(2, nv.getMatKhau());
            ps.setObject(3, nv.getHoTen());
            ps.setObject(4, nv.isVaiTro());

            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return r;

    }
    
    public Integer deleteNhanVien(String ma) {
        Integer r = null;
        String sql = "DELETE NhanVien WHERE MaNV=? ";
        Connection cnn = DBContext.getConnection();
        try {
            PreparedStatement ps = cnn.prepareStatement(sql);
           ps.setString(1, ma);

            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return r;

    }

    public boolean checkLogin(String ma, String pass) {
        NhanVien nv = getNhanVienByID(ma);
        if (nv != null) {
            if (nv.getMatKhau().equals(pass)) {
                return true;
            }
        }

        return false;
    }
}

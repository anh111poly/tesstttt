package poly.edu.service;

import java.util.ArrayList;
import poly.edu.model.HocVien;
import java.sql.*;
import poly.edu.model.KhoaHoc;
import poly.edu.model.NguoiHoc;
import poly.edu.untinity.DBContext;

public class HocVienService {

    public ArrayList<HocVien> getAllHocVien() {
        ArrayList<HocVien> listHocVien = new ArrayList<>();
        String sql = "SELECT * FROM HocVien";
        Connection cn = DBContext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HocVien hv = new HocVien();

                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getInt(2));

                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(3));

                hv.setMaHV(rs.getInt(1));
                hv.setKhoaHoc(kh);
                hv.setNguoiHoc(nh);
                hv.setDiem(rs.getFloat(4));

                listHocVien.add(hv);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listHocVien;
    }

    public ArrayList<HocVien> getHocVienByKhoaHoc(int maKH) {
        ArrayList<HocVien> listHocVien = new ArrayList<>();
        String sql = "SELECT * FROM HocVien WHERE MaKH=?";
        Connection cn = DBContext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, maKH);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HocVien hv = new HocVien();

                // Lấy dữ liệu từ ResultSet cho các trường HocVien
                hv.setMaHV(rs.getInt("MaHV"));
                hv.setDiem(rs.getFloat("Diem"));

                // Lấy dữ liệu từ ResultSet cho KhoaHoc
                KhoaHoc kh = new KhoaHoc();
                kh.setMaKH(rs.getInt("MaKH"));

                // Lấy dữ liệu từ ResultSet cho NguoiHoc
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString("MaNH"));

                // Thiết lập KhoaHoc và NguoiHoc cho HocVien
                hv.setKhoaHoc(kh);
                hv.setNguoiHoc(nh);

                listHocVien.add(hv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listHocVien;
    }

    public Integer addHocVien(HocVien hv) {
        Integer r = null;
        Connection cn = DBContext.getConnection();
        String sql = "INSERT INTO HocVien (MaKH, MaNH, Diem) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);

            ps.setObject(1, hv.getKhoaHoc().getMaKH());
            ps.setObject(2, hv.getNguoiHoc().getMaNH());
            ps.setObject(3, hv.getDiem());

            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    public Integer deleteHocVien(int maHV) {
        Integer r = null;
        Connection cn = DBContext.getConnection();
        String sql = "delete from HocVien where MaHV =?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, maHV);

            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public Integer updateHocVien(HocVien hv) {
        Integer r = null;
        Connection cn = DBContext.getConnection();
        String sql = "UPDATE HocVien SET Diem = ? WHERE MaHV = ?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            
            ps.setObject(1, hv.getDiem());
            ps.setObject(2, hv.getMaHV());

            r = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

}

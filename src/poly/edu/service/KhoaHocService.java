package poly.edu.service;

import java.nio.file.Files;
import java.util.ArrayList;
import poly.edu.model.KhoaHoc;
import java.sql.*;
import poly.edu.model.ChuyenDe;
import poly.edu.model.NhanVien;
import poly.edu.untinity.DBContext;

/**
 *
 * @author Admin
 */
public class KhoaHocService {

    public ArrayList<KhoaHoc> getAllKhoaHoc() {
        ArrayList<KhoaHoc> listKH = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        try {
            String sql = "SELECT * FROM KhoaHoc";
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                KhoaHoc kh = new KhoaHoc();
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString(2));
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(7));

                kh.setMaKH(rs.getInt(1));
                kh.setMaCD(cd);
                kh.setHocPhi(rs.getDouble(3));
                kh.setThoiLuong(rs.getInt(4));
                kh.setNgayKG(rs.getDate(5));
                kh.setGhiChu(rs.getString(6));
                kh.setMaNV(nv);
                kh.setNgayTao(rs.getDate(8));

                listKH.add(kh);

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return listKH;
    }

    public KhoaHoc getKhoaHocByID(int maKH) {

        Connection cn = DBContext.getConnection();
        try {
            String sql = "SELECT * FROM KhoaHoc WHERE MaKH=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, maKH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                KhoaHoc kh = new KhoaHoc();
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString(2));
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(7));

                kh.setMaKH(rs.getInt(1));
                kh.setMaCD(cd);
                kh.setHocPhi(rs.getDouble(3));
                kh.setThoiLuong(rs.getInt(4));
                kh.setNgayKG(rs.getDate(5));
                kh.setGhiChu(rs.getString(6));
                kh.setMaNV(nv);
                kh.setNgayTao(rs.getDate(8));

                return kh;

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public ArrayList<KhoaHoc> getKhoaHocByMaCD(String maCD) {
        ArrayList<KhoaHoc> listKH = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        try {
            String sql = "SELECT * FROM KhoaHoc where MaCD = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, maCD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                KhoaHoc kh = new KhoaHoc();
                ChuyenDe cd = new ChuyenDe();
                cd.setMaCD(rs.getString(2));
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(7));

                kh.setMaKH(rs.getInt(1));
                kh.setMaCD(cd);
                kh.setHocPhi(rs.getDouble(3));
                kh.setThoiLuong(rs.getInt(4));
                kh.setNgayKG(rs.getDate(5));
                kh.setGhiChu(rs.getString(6));
                kh.setMaNV(nv);
                kh.setNgayTao(rs.getDate(8));

                listKH.add(kh);

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return listKH;
    }

    public Integer addKhoaHoc(KhoaHoc kh) {
        Connection cn = DBContext.getConnection();
        Integer r = null;
        try {
            String sql = "INSERT INTO KhoaHoc ( MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV, NgayTao) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, kh.getMaCD().getMaCD());
            ps.setObject(2, kh.getHocPhi());
            ps.setObject(3, kh.getThoiLuong());
            ps.setObject(4, kh.getNgayKG());
            ps.setObject(5, kh.getGhiChu());
            ps.setObject(6, kh.getMaNV().getMaNV());
            ps.setObject(7, kh.getNgayTao());

            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return r;
    }

    public Integer updateKhoaHoc(KhoaHoc kh) {
        Connection cn = DBContext.getConnection();
        Integer r = null;
        try {
            String sql = "UPDATE KhoaHoc SET NgayKG =?,MaNV =?, GhiChu=? where MaKH=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, kh.getNgayKG());
            ps.setObject(2, kh.getMaNV().getMaNV());
            ps.setObject(3, kh.getGhiChu());
            ps.setObject(4, kh.getMaKH());

            r = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public Integer deleteKhoaHoc(String maKH) {
        Integer r = null;
        try {
            Connection cn = DBContext.getConnection();
            String sql = "DELETE FROM KhoaHoc WHERE MaKH =?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, maKH);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return r;
    }
}

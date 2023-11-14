package poly.edu.service;

import java.util.ArrayList;
import poly.edu.model.NguoiHoc;
import java.sql.*;
import poly.edu.model.NhanVien;
import poly.edu.untinity.DBContext;

public class NguoiHocService {

    public ArrayList<NguoiHoc> getAllNguoiHoc() {
        ArrayList<NguoiHoc> listNH = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        String sql = "SELECT * FROM NguoiHoc";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(8));

                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                nh.setHoTen(rs.getString(2));
                nh.setGioiTinh(rs.getBoolean(3));
                nh.setNgaySinh(rs.getDate(4));
                nh.setSoDienThoai(rs.getString(5));
                nh.setEmail(rs.getString(6));
                nh.setGhiChu(rs.getString(7));
                nh.setMaNhanVien(nv);
                nh.setNgayDK(rs.getDate(9));
                listNH.add(nh);

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return listNH;
    }

    public ArrayList<NguoiHoc> getNguoiHocHocVien() {
        ArrayList<NguoiHoc> listNH = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        String sql = "SELECT MaNH, HoTen, NgaySinh , GioiTinh , DienThoai, Email FROM NguoiHoc";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                nh.setHoTen(rs.getString(2));
                nh.setNgaySinh(rs.getDate(3));
                nh.setGioiTinh(rs.getBoolean(4));
                nh.setSoDienThoai(rs.getString(5));
                nh.setEmail(rs.getString(6));

                listNH.add(nh);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNH;
    }

    public ArrayList<NguoiHoc> selectNotInCourse(int maKH, String keyword) {
        ArrayList<NguoiHoc> listNH = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        String sql = "SELECT MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email "
                + "FROM NguoiHoc "
                + "WHERE HoTen LIKE ? AND MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH = ?)";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%"); // Set the keyword first
            ps.setInt(2, maKH); // Set maKH second
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString("MaNH"));
                nh.setHoTen(rs.getString("HoTen"));
                nh.setNgaySinh(rs.getDate("NgaySinh"));
                nh.setGioiTinh(rs.getBoolean("GioiTinh"));
                nh.setSoDienThoai(rs.getString("DienThoai"));
                nh.setEmail(rs.getString("Email"));
                listNH.add(nh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close database resources (Connection, PreparedStatement, ResultSet) here in a real application
        }
        return listNH;
    }

    public ArrayList<NguoiHoc> getAllNguoiHocByKeyword(String keyWord) {
        ArrayList<NguoiHoc> listNH = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        String sql = "SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, "%" + keyWord + "%"); // Add wildcards to the keyword here
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(8));

                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                nh.setHoTen(rs.getString(2));
                nh.setGioiTinh(rs.getBoolean(3));
                nh.setNgaySinh(rs.getDate(4));
                nh.setSoDienThoai(rs.getString(5));
                nh.setEmail(rs.getString(6));
                nh.setGhiChu(rs.getString(7));
                nh.setMaNhanVien(nv);
                nh.setNgayDK(rs.getDate(9));
                listNH.add(nh);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return listNH;
    }

    public NguoiHoc getAllNguoiHocByID(String maNH) {
        Connection cn = DBContext.getConnection();
        String sql = "SELECT * FROM NguoiHoc WHERE MaNH=?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, maNH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(8));

                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                nh.setHoTen(rs.getString(2));
                nh.setGioiTinh(rs.getBoolean(3));
                nh.setNgaySinh(rs.getDate(4));
                nh.setSoDienThoai(rs.getString(5));
                nh.setEmail(rs.getString(6));
                nh.setGhiChu(rs.getString(7));
                nh.setMaNhanVien(nv);
                nh.setNgayDK(rs.getDate(9));

                return nh;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public NguoiHoc getNguoiHocByTen(String HoTen) {
        Connection cn = DBContext.getConnection();
        String sql = "SELECT * FROM NguoiHoc WHERE HoTen=?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, HoTen);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(8));

                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                nh.setHoTen(rs.getString(2));
                nh.setGioiTinh(rs.getBoolean(3));
                nh.setNgaySinh(rs.getDate(4));
                nh.setSoDienThoai(rs.getString(5));
                nh.setEmail(rs.getString(6));
                nh.setGhiChu(rs.getString(7));
                nh.setMaNhanVien(nv);
                nh.setNgayDK(rs.getDate(9));

                return nh;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public NguoiHoc getTenNguoiHocByMa(String maNH) {
        Connection cn = DBContext.getConnection();
        String sql = "SELECT HoTen FROM NguoiHoc WHERE MaNH=?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, maNH);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NguoiHoc nh = new NguoiHoc();

                nh.setHoTen(rs.getString(1));
                return nh;
            }

        } catch (Exception e) {
        }

        return null;

    }

    public Integer addNguoiHoc(NguoiHoc nh) {
        Integer r = null;
        try {
            Connection cn = DBContext.getConnection();
            String sql = "INSERT INTO NguoiHoc(MaNH, HoTen, GioiTinh, NgaySinh, DienThoai, Email, GhiChu, MaNV, NgayDK) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, nh.getMaNH());
            ps.setObject(2, nh.getHoTen());
            ps.setObject(3, nh.isGioiTinh());
            ps.setObject(4, nh.getNgaySinh());
            ps.setObject(5, nh.getSoDienThoai());
            ps.setObject(6, nh.getEmail());
            ps.setObject(7, nh.getGhiChu());
            ps.setObject(8, nh.getMaNhanVien().getMaNV());
            ps.setObject(9, nh.getNgayDK());

            r = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return r;
    }

    public Integer updateNguoiHoc(NguoiHoc nh) {
        Integer r = null;
        try {
            Connection cn = DBContext.getConnection();
            String sql = "UPDATE NguoiHoc SET HoTen =?, GioiTinh =?, NgaySinh =?, DienThoai =?, Email =?, GhiChu =?, MaNV =?, NgayDK =? WHERE MaNH=?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(9, nh.getMaNH());
            ps.setObject(1, nh.getHoTen());
            ps.setObject(2, nh.isGioiTinh());
            ps.setObject(3, nh.getNgaySinh());
            ps.setObject(4, nh.getSoDienThoai());
            ps.setObject(5, nh.getEmail());
            ps.setObject(6, nh.getGhiChu());
            ps.setObject(7, nh.getMaNhanVien().getMaNV());
            ps.setObject(8, nh.getNgayDK());

            r = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return r;
    }

    public Integer deleteNguoiHoc(String maNH) {
        Integer r = null;
        try {
            Connection cn = DBContext.getConnection();
            String sql = "DELETE FROM NguoiHoc WHERE maNH = ?;";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, maNH);
            r = ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return r;
    }

}

package poly.edu.service;

import java.util.ArrayList;
import poly.edu.model.ChuyenDe;
import java.sql.*;
import poly.edu.untinity.DBContext;

public class ChuyenDeService {

    public ArrayList<ChuyenDe> getAllChuyenDe() {
        ArrayList<ChuyenDe> listCD = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        String sql = "SELECT * FROM ChuyenDe";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChuyenDe cd = new ChuyenDe();

                cd.setMaCD(rs.getString(1));
                cd.setTenCD(rs.getString(2));
                cd.setHocPhi(rs.getFloat(3));
                cd.setThoiLuong(rs.getInt(4));
                cd.setHinh(rs.getString(5));
                cd.setMoTa(rs.getString(6));

                listCD.add(cd);

            }

        } catch (Exception e) {
        }
        return listCD;

    }
    public ChuyenDe getChuyenDeByID(String maCD) {
        Connection cn = DBContext.getConnection();
        String sql = "SELECT * FROM ChuyenDe WHERE MaCD = ?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, maCD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChuyenDe cd = new ChuyenDe();

                cd.setMaCD(rs.getString(1));
                cd.setTenCD(rs.getString(2));
                cd.setHocPhi(rs.getFloat(3));
                cd.setThoiLuong(rs.getInt(4));
                cd.setHinh(rs.getString(5));
                cd.setMoTa(rs.getString(6));

                return cd;

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }
    public ChuyenDe getChuyenDeByTen(String tenCD) {
        Connection cn = DBContext.getConnection();
        String sql = "SELECT * FROM ChuyenDe WHERE tenCD = ?";
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, tenCD);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChuyenDe cd = new ChuyenDe();

                cd.setMaCD(rs.getString(1));
                cd.setTenCD(rs.getString(2));
                cd.setHocPhi(rs.getFloat(3));
                cd.setThoiLuong(rs.getInt(4));
                cd.setHinh(rs.getString(5));
                cd.setMoTa(rs.getString(6));

                return cd;

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public Integer addChuyenDe(ChuyenDe cd) {
        Integer r = null;
        try {
            String sql = "INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?,?,?,?,?,?)";
            Connection cn = DBContext.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(1, cd.getMaCD());
            ps.setObject(2, cd.getTenCD());
            ps.setObject(3, cd.getHocPhi());
            ps.setObject(4, cd.getThoiLuong());
            ps.setObject(5, cd.getHinh());
            ps.setObject(6, cd.getMoTa());
            r = ps.executeUpdate();
        } catch (Exception e) {
      
        }

        return r;
    }

    public Integer updateChuyenDe(ChuyenDe cd) {
        Integer r = null;
        try {
            String sql = "UPDATE ChuyenDe SET TenCD =?, HocPhi =?, ThoiLuong =?, Hinh =?, MoTa =? WHERE MaCD =?";
            Connection cn = DBContext.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setObject(6, cd.getMaCD());
            ps.setObject(1, cd.getTenCD());
            ps.setObject(2, cd.getHocPhi());
            ps.setObject(3, cd.getThoiLuong());
            ps.setObject(4, cd.getHinh());
            ps.setObject(5, cd.getMoTa());
            r = ps.executeUpdate();
        } catch (Exception e) {
          
        }

        return r;
    }

    public Integer deleteChuyenDe(String maCD) {
        Integer r = null;
        try {
            String sql = "DELETE FROM ChuyenDe WHERE MaCD =?";
            Connection cn = DBContext.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setString(1, maCD);
            r = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return r;
    }

}

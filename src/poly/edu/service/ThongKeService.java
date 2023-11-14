
package poly.edu.service;

import java.util.ArrayList;
import java.sql.*;
import poly.edu.model.HocVien;
import poly.edu.model.NguoiHoc;
import poly.edu.model.TKDiemCD;
import poly.edu.model.TKDoanhThu;
import poly.edu.model.TKNguoiHoc;
import poly.edu.untinity.DBContext;

/**
 *
 * @author Admin
 */
public class ThongKeService {

    public ArrayList<HocVien> getThongKeDiem(int maKh) {
        ArrayList<HocVien> listHV = new ArrayList<>();
        String sql = "exec sp_BangDiem @MaKH = ?";
        Connection cn = DBContext.getConnection();
        try {
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, maKh);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NguoiHoc nh = new NguoiHoc();
                nh.setMaNH(rs.getString(1));
                HocVien hv = new HocVien();
                hv.setNguoiHoc(nh);
                hv.setDiem(rs.getFloat(3));

                listHV.add(hv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHV;
    }

    public ArrayList<TKNguoiHoc> getThongKeNH() {
        ArrayList<TKNguoiHoc> listTKNH = new ArrayList<>();
        String sql = "exec sp_ThongKeNguoiHoc";
        Connection cn = DBContext.getConnection();
        try {
            PreparedStatement ps = cn.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TKNguoiHoc tk = new TKNguoiHoc();
                tk.setNam(rs.getInt(1));
                tk.setSoLuongNH(rs.getInt(2));
                tk.setDauTien(rs.getDate(3));
                tk.setCuoiCung(rs.getDate(4));

                listTKNH.add(tk);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTKNH;
    }

    public ArrayList<TKDiemCD> getThongKeDiemCD() {
        ArrayList<TKDiemCD> listDiemCD = new ArrayList<>();
        String sql = "exec sp_ThongKeDiem";
        Connection cn = DBContext.getConnection();
        try {
            PreparedStatement ps = cn.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TKDiemCD diemCD = new TKDiemCD();
                diemCD.setTenCD(rs.getString(1));
                diemCD.setSoLuong(rs.getInt(2));
                diemCD.setDiemTN(rs.getFloat(3));
                diemCD.setDiemCN(rs.getFloat(4));
                diemCD.setDiemTB(rs.getFloat(5));
                listDiemCD.add(diemCD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDiemCD;
    }

    public ArrayList<TKDoanhThu> getDoanhThuByYear(int years) {
        ArrayList<TKDoanhThu> listDT = new ArrayList<>();
        String sql = "exec sp_ThongKeDoanhThu @Year =?";
        Connection cn = DBContext.getConnection();
        try {
            PreparedStatement ps = cn.prepareCall(sql);
            ps.setObject(1, years);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TKDoanhThu dt = new TKDoanhThu();
                dt.setTenCD(rs.getString(1));
                dt.setSoKH(rs.getInt(2));
                dt.setSoHV(rs.getInt(3));
                dt.setDoanhThu(rs.getFloat(4));
                dt.setThapNhat(rs.getFloat(5));
                dt.setCaoNhat(rs.getFloat(6));
                dt.setTrungBinh(rs.getFloat(7));

                listDT.add(dt);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDT;

    }

    public ArrayList<Integer> selectYears() {
        String sql = "SELECT DISTINCT year(NgayKG) Year FROM KhoaHoc ORDER BY Year DESC";
        ArrayList<Integer> listYear = new ArrayList<>();
        Connection cn = DBContext.getConnection();
        try {
            PreparedStatement ps = cn.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listYear.add(rs.getInt(1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listYear;
    }

}

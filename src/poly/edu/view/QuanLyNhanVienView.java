/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package poly.edu.view;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.edu.model.NhanVien;
import poly.edu.service.NhanVienService;
import poly.edu.untinity.Auth;
import poly.edu.untinity.XImage;

/**
 *
 * @author Admin
 */
public class QuanLyNhanVienView extends javax.swing.JDialog {

    DefaultTableModel model = new DefaultTableModel();
    ArrayList<NhanVien> listNhanVien = new ArrayList<>();
    NhanVienService serviceNV = new NhanVienService();
    int row = 0;

    public QuanLyNhanVienView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        model = (DefaultTableModel) tblNhanVien.getModel();
        listNhanVien = serviceNV.getAllNhanVien();
        loadToTable();
        loadLocatinFirst();

    }

    void loadLocatinFirst() {
        if (model.getRowCount() > 0) {
            int index = tblNhanVien.getRowCount() - 1;
            tblNhanVien.setRowSelectionInterval(index, index    );
            showDetail();

        }
    }

    void init() {
        setIconImage(XImage.getIcon());
        setLocationRelativeTo(null);
        setTitle("EduSys - Quản lý nhân viên");
        updateStatus();
    }

    void loadToTable() {
        listNhanVien = serviceNV.getAllNhanVien();
        model.setRowCount(0);
        for (NhanVien nv : listNhanVien) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getMatKhau(),
                nv.getHoTen(),
                nv.isVaiTro() ? "Trưởng phòng" : "Nhân viên"
            });

        }
    }

    void showDetail() {
        int index = tblNhanVien.getSelectedRow();
        txtMaNhanVien.setText(tblNhanVien.getValueAt(index, 0).toString());
        txtMatKhau.setText(tblNhanVien.getValueAt(index, 1).toString());
        txtXacNhanMatKhau.setText(tblNhanVien.getValueAt(index, 1).toString());
        txtHoTen.setText(tblNhanVien.getValueAt(index, 2).toString());
        boolean chucVu = tblNhanVien.getValueAt(3, index).toString().equals("Trưởng phòng");
        rdoTruongPhong.setSelected(chucVu);
        rdoNhanVien.setSelected(!chucVu);
    }

    NhanVien getFrom() {
        String maNV = txtMaNhanVien.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        String xacNhanMK = new String(txtXacNhanMatKhau.getPassword());
        String hoTen = txtHoTen.getText().trim();
        if (maNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống !");
            txtMaNhanVien.requestFocus();
            return null;
        }

        if (maNV.length() > 20) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được lớn hơn 20 kí tự !");
            txtMaNhanVien.requestFocus();
            return null;
        }
        if (matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống !");
            txtMatKhau.requestFocus();
            return null;

        }

        if (matKhau.length() < 3) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải lớn hơn 2 kí tự !");
            txtMatKhau.requestFocus();
            return null;
        }
        if (matKhau.length() > 50) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được lớn hơn 50 kí tự");
        }
        if (xacNhanMK.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu không được để trống !");
            txtMatKhau.requestFocus();
            return null;
        }
        if (xacNhanMK.length() < 2) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu phải lớn hơn 2 kí tự !");
            txtMatKhau.requestFocus();
            return null;
        }

        if (xacNhanMK.length() > 50) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu không được lớn hơn 50 kí tự !");
            txtMatKhau.requestFocus();
            return null;
        }
        if (!matKhau.equals(xacNhanMK)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu và xác nhận mật khẩu phải giống nhau !");
            txtMatKhau.requestFocus();
            return null;
        }
        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống !");
            txtHoTen.requestFocus();
            return null;
        }
        if (hoTen.length() < 3) {
            JOptionPane.showMessageDialog(this, "Họ tên phải lớn 3 kí tự");
            txtHoTen.requestFocus();
            return null;
        }
        if (hoTen.length() > 50) {
            JOptionPane.showMessageDialog(this, "Họ tên không được quá 50 kí tự");
            txtHoTen.requestFocus();
            return null;
        }
        boolean chucVu;
        if (rdoTruongPhong.isSelected()) {
            chucVu = true;
        } else {
            chucVu = false;
        }

        NhanVien nv = new NhanVien(maNV, hoTen, matKhau, chucVu);
        listNhanVien.add(nv);
        return nv;

    }

    void edit() {
        int row = tblNhanVien.getSelectedRow();
        try {
            String maNV = (String) tblNhanVien.getValueAt(this.row, 0);
            NhanVien model = serviceNV.getNhanVienByID(maNV);
            if (model != null) {
                setFrom(model);
                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không thể truy vấn dữ liệu");
        }
    }

    void setFrom(NhanVien nv) {
        txtMaNhanVien.setText(nv.getMaNV());
        txtMatKhau.setText(nv.getMatKhau());
        txtXacNhanMatKhau.setText(nv.getMatKhau());
        txtHoTen.setText(nv.getHoTen());
        rdoTruongPhong.setSelected(nv.isVaiTro());
        rdoNhanVien.setSelected(!nv.isVaiTro());
    }

    void updateStatus() {

        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblNhanVien.getRowCount() - 1;
        txtMaNhanVien.setEditable(!edit);

        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void clearFrom() {
        this.setFrom(new NhanVien());
        this.updateStatus();
        row = -1;
        updateStatus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        txtMatKhau = new javax.swing.JPasswordField();
        txtXacNhanMatKhau = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        rdoTruongPhong = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnNhapMoi = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel4.setText("Xác nhận mật khẩu");

        jLabel5.setText("Họ và tên");

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });
        jPanel5.add(btnFirst);

        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        jPanel5.add(btnPrev);

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        jPanel5.add(btnNext);

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        jPanel5.add(btnLast);

        jLabel6.setText("Họ và tên");

        buttonGroup1.add(rdoTruongPhong);
        rdoTruongPhong.setText("Trưởng phòng");

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setSelected(true);
        rdoNhanVien.setText("Nhân viên ");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel4.add(btnThem);

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel4.add(btnSua);

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoa);

        btnNhapMoi.setText("Mới");
        btnNhapMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapMoiActionPerformed(evt);
            }
        });
        jPanel4.add(btnNhapMoi);

        jLabel2.setText("Mã nhân viên");

        jLabel3.setText("Mật khẩu");

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MÃ NV", "MẬT KHẨU", "HỌ TÊN ", "VAI TRÒ"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoTruongPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(352, 352, 352))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtXacNhanMatKhau, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaNhanVien)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(12, 12, 12)
                .addComponent(txtXacNhanMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoTruongPhong)
                            .addComponent(rdoNhanVien))
                        .addGap(25, 25, 25)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstNhanVien();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prevNhanVien();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextNhanVien();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastNhanVien();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm nhân viên hay không ?", "add", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        try {
            String maNV = txtMaNhanVien.getText().trim();
            boolean maNhanVienTT = false;
            for (NhanVien nv : listNhanVien) {
                if (nv.getMaNV().equals(maNV)) {
                    maNhanVienTT = true;
                }
            }

            if (maNhanVienTT) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại không thể thêm");
            } else {
                NhanVien nv = getFrom();
                if (serviceNV.addNhanVien(nv) != null) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    loadToTable();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa nhân viên hay không ?", "update", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }

        try {
            int i = tblNhanVien.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(this, "Chưa chọn dòng để thực hiện");
                return;
            }
            NhanVien nv = getFrom();
            if (serviceNV.updateNhanVien(nv) != null) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                loadToTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa nhân viên hay không ?", "delete", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa nhân viên");
        } else {
            try {
                int i = tblNhanVien.getSelectedRow();
                if (i < 0) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn dòng để thực hiện");
                    return;
                }
                if (serviceNV.deleteNhanVien(txtMaNhanVien.getText().trim()) != null) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    clearFrom();
                    loadToTable();
                    loadLocatinFirst();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnNhapMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapMoiActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Clear From hay không ?", "clear", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        clearFrom();
    }//GEN-LAST:event_btnNhapMoiActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        try {
            showDetail();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    void firstNhanVien() {

        row = 0;
        edit();
        tblNhanVien.setRowSelectionInterval(row, row);
    }

    void prevNhanVien() {
        if (row > 0) {
            row--;
            edit();
            tblNhanVien.setRowSelectionInterval(row, row);
        }
    }

    void nextNhanVien() {
        if (row < tblNhanVien.getRowCount() - 1) {
            row++;
            edit();
            tblNhanVien.setRowSelectionInterval(row, row);
        }
    }

    void lastNhanVien() {
        row = tblNhanVien.getRowCount() - 1;
        edit();
        tblNhanVien.setRowSelectionInterval(row, row);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNhanVienView dialog = new QuanLyNhanVienView(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNhapMoi;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtXacNhanMatKhau;
    // End of variables declaration//GEN-END:variables
}

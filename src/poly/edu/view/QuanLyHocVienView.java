/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package poly.edu.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.edu.model.ChuyenDe;
import poly.edu.model.HocVien;
import poly.edu.model.KhoaHoc;
import poly.edu.model.NguoiHoc;
import poly.edu.service.ChuyenDeService;
import poly.edu.service.HocVienService;
import poly.edu.service.KhoaHocService;
import poly.edu.service.NguoiHocService;
import poly.edu.untinity.XImage;

public class QuanLyHocVienView extends javax.swing.JDialog {

    ArrayList<KhoaHoc> listKhoaHoc = new ArrayList<>();
    ArrayList<ChuyenDe> listChuyenDe = new ArrayList<>();
    ArrayList<NguoiHoc> listNguoiHoc = new ArrayList<>();
    ArrayList<HocVien> listHocVien = new ArrayList<>();
    ChuyenDeService serviceCD = new ChuyenDeService();
    KhoaHocService serviceKH = new KhoaHocService();
    NguoiHocService serviceNH = new NguoiHocService();
    HocVienService serviceHV = new HocVienService();
    DefaultComboBoxModel comboModelCD = new DefaultComboBoxModel();
    DefaultComboBoxModel comboModelKh = new DefaultComboBoxModel();
    DefaultTableModel modelNH = new DefaultTableModel();
    DefaultTableModel modelHV = new DefaultTableModel();

    public QuanLyHocVienView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        modelNH = (DefaultTableModel) tblNguoiHoc.getModel();
        modelHV = (DefaultTableModel) tblHocVien.getModel();
        listChuyenDe = serviceCD.getAllChuyenDe();
        listNguoiHoc = serviceNH.getNguoiHocHocVien();
        fillComboboxChuyenDe();
        loadToTableHocVien();
        eventLoaHocVien();
        eventTimKiem();
    }

    void init() {
        setIconImage(XImage.getIcon());
        setLocationRelativeTo(null);
        setTitle("EduSys - Quản lý học viên");

    }

    void fillComboboxChuyenDe() {
        Set<String> setCD = new HashSet<>();
        for (ChuyenDe cd : listChuyenDe) {
            setCD.add(cd.toString());
        }
        comboModelCD = (DefaultComboBoxModel) new DefaultComboBoxModel<>(setCD.toArray());
        cbocChuyenDe.setModel(comboModelCD);
        fillComboboxKhoaHoc();
        loadToTableNguoiHocByKeyWord();
    }

    void fillComboboxKhoaHoc() {
        comboModelKh = (DefaultComboBoxModel) cboKhoaHoc.getModel();
        comboModelKh.removeAllElements();
        ChuyenDe chuyenDe = null;
        int selectedIndex = cbocChuyenDe.getSelectedIndex();
        if (selectedIndex != -1) {
            chuyenDe = listChuyenDe.get(selectedIndex);
        }
        if (chuyenDe != null) {
            try {
                listKhoaHoc = serviceKH.getKhoaHocByMaCD(chuyenDe.getMaCD());
                for (KhoaHoc kh : listKhoaHoc) {
                    comboModelKh.addElement(kh);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void loadToTableNguoiHoc() {
        listNguoiHoc = serviceNH.getNguoiHocHocVien();
        modelNH.setRowCount(0);
        for (NguoiHoc nh : listNguoiHoc) {
            modelNH.addRow(new Object[]{
                nh.getMaNH(),
                nh.getHoTen(),
                nh.isGioiTinh() ? "Nam" : "Nữ",
                nh.getNgaySinh(),
                nh.getSoDienThoai(),
                nh.getEmail()
            });

        }

    }

    void loadToTableNguoiHocByKeyWord() {
        listNguoiHoc = serviceNH.getNguoiHocHocVien();
        modelNH.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if (kh != null) {
                System.out.println(kh.getMaKH());
                String keyWord = txtTimkiem.getText().trim();
                listNguoiHoc = serviceNH.selectNotInCourse(kh.getMaKH(), keyWord);
                for (NguoiHoc nh : listNguoiHoc) {
                    modelNH.addRow(new Object[]{
                        nh.getMaNH(),
                        nh.getHoTen(),
                        nh.isGioiTinh() ? "Nam" : "Nữ",
                        nh.getNgaySinh(),
                        nh.getSoDienThoai(),
                        nh.getEmail()
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void loadToTableHocVien() {
        modelHV.setRowCount(0);

        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();

            if (kh != null) {
                listHocVien = serviceHV.getHocVienByKhoaHoc(kh.getMaKH());

                for (int i = 0; i < listHocVien.size(); i++) {
                    HocVien hv = listHocVien.get(i);
                    NguoiHoc nguoiHoc = hv.getNguoiHoc();

                    if (nguoiHoc != null) {
                        nguoiHoc = serviceNH.getTenNguoiHocByMa(hv.getNguoiHoc().getMaNH());
                        Object[] row = {
                            i + 1,
                            hv.getMaHV(),
                            hv.getNguoiHoc().getMaNH(),
                            nguoiHoc.getHoTen(),
                            hv.getDiem()
                        };

                        modelHV.addRow(row);
                    }
                }
            }
            loadToTableNguoiHoc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    HocVien getFrom() {
        int row = tblHocVien.getSelectedRow();
        float diemHV;
        String diem = tblHocVien.getValueAt(row, 4).toString();

        String maHV = tblHocVien.getValueAt(row, 1).toString();
        if (diem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Điểm học viên không được để trống");
            return null;
        }

        try {
            diemHV = Float.parseFloat(diem);
            if (diemHV < 0) {
                JOptionPane.showMessageDialog(this, "Điểm học viên phải lớn hơn 0");
                return null;
            }
            if (diemHV > 10) {
                JOptionPane.showMessageDialog(this, "Điểm học viên phải nhỏ hơn 11");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Điểm học viên phải là số");
            return null;
        }

        HocVien hv = new HocVien(Integer.parseInt(maHV), diemHV);
        return hv;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cbocChuyenDe = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        btnXoaKhoiKhoaHoc = new javax.swing.JButton();
        btnCapNhatDiem = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        txtTimkiem = new javax.swing.JTextField();
        btnThemVaoKhoaHoc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CHUYÊN ĐỀ "));

        cbocChuyenDe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbocChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbocChuyenDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocChuyenDe, 0, 231, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbocChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("KHÓA HỌC"));

        cboKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cboKhoaHoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "MÃ HV", "MÃ NH", "HỌ TÊN", "ĐIỂM"
            }
        ));
        jScrollPane1.setViewportView(tblHocVien);
        if (tblHocVien.getColumnModel().getColumnCount() > 0) {
            tblHocVien.getColumnModel().getColumn(0).setResizable(false);
            tblHocVien.getColumnModel().getColumn(0).setPreferredWidth(15);
            tblHocVien.getColumnModel().getColumn(1).setPreferredWidth(15);
            tblHocVien.getColumnModel().getColumn(2).setPreferredWidth(15);
            tblHocVien.getColumnModel().getColumn(3).setPreferredWidth(200);
            tblHocVien.getColumnModel().getColumn(4).setPreferredWidth(15);
        }

        btnXoaKhoiKhoaHoc.setText("Xóa khỏi khóa học");
        btnXoaKhoiKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhoiKhoaHocActionPerformed(evt);
            }
        });

        btnCapNhatDiem.setText("Cập nhật điểm");
        btnCapNhatDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatDiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXoaKhoiKhoaHoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapNhatDiem)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCapNhatDiem, btnXoaKhoiKhoaHoc});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCapNhatDiem)
                    .addComponent(btnXoaKhoiKhoaHoc)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCapNhatDiem, btnXoaKhoiKhoaHoc});

        jTabbedPane1.addTab("HỌC VIÊN", jPanel3);

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NH", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "ĐIỆN THOẠI", "EMAIL"
            }
        ));
        jScrollPane2.setViewportView(tblNguoiHoc);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(txtTimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        btnThemVaoKhoaHoc.setText("Thêm vào khoá học");
        btnThemVaoKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVaoKhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemVaoKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThemVaoKhoaHoc)
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("NGƯỜI HỌC", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbocChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbocChuyenDeActionPerformed
        fillComboboxKhoaHoc();
    }//GEN-LAST:event_cbocChuyenDeActionPerformed

    private void btnThemVaoKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVaoKhoaHocActionPerformed

        KhoaHoc khoaHoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
        List<Integer> rowsToDelete = new ArrayList<>();

        for (int row : tblNguoiHoc.getSelectedRows()) {
            NguoiHoc nh = new NguoiHoc();
            nh.setMaNH((String) tblNguoiHoc.getValueAt(row, 0));
            HocVien hv = new HocVien();
            hv.setKhoaHoc(khoaHoc);

            if (khoaHoc != null) {
                hv.setDiem(-1);
                hv.setNguoiHoc(nh);
                if (serviceHV.addHocVien(hv) != null) {
                    rowsToDelete.add(row);
                    loadToTableHocVien();

                }

            } else {
                JOptionPane.showMessageDialog(this, "Khóa học không hợp lệ!");
                return;
            }
        }

        for (int i = rowsToDelete.size() - 1; i >= 0; i--) {
            modelNH.removeRow(rowsToDelete.get(i));
        }
        modelNH = (DefaultTableModel) tblNguoiHoc.getModel();
        modelNH.fireTableDataChanged();

        JOptionPane.showMessageDialog(this, "Thêm người học vào khóa học thành công!");


    }//GEN-LAST:event_btnThemVaoKhoaHocActionPerformed

    private void btnXoaKhoiKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhoiKhoaHocActionPerformed
        int index = tblHocVien.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng để thực hiện");
            return;
        }
        int maHV = (int) tblHocVien.getValueAt(index, 1);
        try {
            if (serviceHV.deleteHocVien(maHV) != null) {
                loadToTableHocVien();
                JOptionPane.showMessageDialog(this, "Xoá thành công học viên");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXoaKhoiKhoaHocActionPerformed

    private void btnCapNhatDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatDiemActionPerformed
        int i = tblHocVien.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng để thực hiện");
            return;
        }

        try {
            int row = tblHocVien.getSelectedRow();
            int maHv = (int) tblHocVien.getValueAt(row, 1);
            HocVien hv = getFrom();
            hv.setMaHV(maHv);
            if (serviceHV.updateHocVien(hv) != null) {
                JOptionPane.showMessageDialog(this, "Sửa thành công học viên");
                loadToTableHocVien();

            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnCapNhatDiemActionPerformed

    void eventTimKiem() {
        txtTimkiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadToTableNguoiHocByKeyWord();
            }
        });

    }

    void eventLoaHocVien() {
        cboKhoaHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadToTableHocVien();
            }
        });

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
            java.util.logging.Logger.getLogger(QuanLyHocVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyHocVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyHocVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyHocVienView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyHocVienView dialog = new QuanLyHocVienView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCapNhatDiem;
    private javax.swing.JButton btnThemVaoKhoaHoc;
    private javax.swing.JButton btnXoaKhoiKhoaHoc;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JComboBox<String> cbocChuyenDe;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtTimkiem;
    // End of variables declaration//GEN-END:variables
}

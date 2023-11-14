/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package poly.edu.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.edu.model.ChuyenDe;
import poly.edu.model.KhoaHoc;
import poly.edu.model.NhanVien;
import poly.edu.service.ChuyenDeService;
import poly.edu.service.KhoaHocService;
import poly.edu.service.NhanVienService;
import poly.edu.untinity.XImage;

/**
 *
 * @author Admin
 */
public class QuanLyKhoaHocView extends javax.swing.JDialog {

    ArrayList<NhanVien> listNhanVien = new ArrayList<>();
    ArrayList<KhoaHoc> listKhoaHoc = new ArrayList<>();
    ArrayList<ChuyenDe> listChuyenDe = new ArrayList<>();
    KhoaHocService khoaHocSer = new KhoaHocService();
    ChuyenDeService chuyenDeSer = new ChuyenDeService();
    NhanVienService nhanVienSer = new NhanVienService();
    DefaultTableModel model = new DefaultTableModel();

    DefaultComboBoxModel<KhoaHoc> combobox = new DefaultComboBoxModel<>();
    int row = 0;

    public QuanLyKhoaHocView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        listKhoaHoc = khoaHocSer.getAllKhoaHoc();
        listChuyenDe = chuyenDeSer.getAllChuyenDe();
        listNhanVien = nhanVienSer.getAllNhanVien();
        model = (DefaultTableModel) tblKhoaHoc.getModel();
        txtGhiChu.setEditable(false);
        Set<String> setCD = new HashSet<>();
        for (ChuyenDe cd : listChuyenDe) {
            setCD.add(cd.toString());
        }   
        combobox = (DefaultComboBoxModel) new DefaultComboBoxModel<>(setCD.toArray());
        cboChuyenDe.setModel((DefaultComboBoxModel) combobox);
        eventLayTenCombobox();
        eventlayHocPhiComBobox();
        eventLayThoiLuongToComBoBox();
        EventChuyenDe();
        loadToTable();
        updateStatus();
        txtHocPhi.setEditable(false);
        txtThoiLuong.setEditable(false);
        txtNgayTao.setEditable(false);
    }

    void init() {
        setIconImage(XImage.getIcon());
        setLocationRelativeTo(null);
        setTitle("EduSys - Quản lý khóa học");
    }

    void loadToTable() {
        listKhoaHoc = khoaHocSer.getAllKhoaHoc();
        model.setRowCount(0);
        for (KhoaHoc kh : listKhoaHoc) {
            model.addRow(new Object[]{
                kh.getMaKH(),
                kh.getHocPhi(),
                kh.getThoiLuong(),
                kh.getNgayKG(),
                kh.getMaNV(),
                kh.getNgayTao(),
                kh.getMaCD().getMaCD(),
                kh.getGhiChu()
            });
        }
    }

    void loadToTableTheoKH() {
        String selectedChuyenDe = cboChuyenDe.getSelectedItem().toString();
        listKhoaHoc = khoaHocSer.getAllKhoaHoc();
        model.setRowCount(0);

        for (KhoaHoc kh : listKhoaHoc) {
            String maCD = kh.getMaCD().getMaCD();
            String tenChuyenDe = findTenChuyenDe(maCD);

            if (tenChuyenDe != null && tenChuyenDe.trim().equalsIgnoreCase(selectedChuyenDe.trim())) {
                model.addRow(new Object[]{
                    kh.getMaKH(),
                    kh.getHocPhi(),
                    kh.getThoiLuong(),
                    kh.getNgayKG(),
                    kh.getMaNV(),
                    kh.getNgayTao(),
                    kh.getMaCD().getMaCD(),
                    kh.getGhiChu()
                }
                );
            }
        }

    }

    void showDetail() {
        int index = tblKhoaHoc.getSelectedRow();
        String selectedChuyenDe = cboChuyenDe.getSelectedItem().toString();
        txtChuyenDe.setText(selectedChuyenDe);
        txtHocPhi.setText(tblKhoaHoc.getValueAt(index, 1).toString());
        txtThoiLuong.setText(tblKhoaHoc.getValueAt(index, 2).toString());
        txtKhaiGiang.setText(tblKhoaHoc.getValueAt(index, 3).toString());
        txtNguoiTao.setText(tblKhoaHoc.getValueAt(index, 4).toString());
        txtNgayTao.setText(tblKhoaHoc.getValueAt(index, 5).toString());
        txtGhiChu.setText(tblKhoaHoc.getValueAt(index, 6).toString());

    }

    void setFrom(KhoaHoc kh) {
        txtChuyenDe.setText(kh.getMaCD() + "");
        txtGhiChu.setText(kh.getGhiChu());
        txtHocPhi.setText(kh.getHocPhi() + "");
        txtKhaiGiang.setText(kh.getNgayKG() + "");
        txtNgayTao.setText(kh.getNgayTao() + "");
        txtThoiLuong.setText(kh.getThoiLuong() + "");
        txtGhiChu.setText(kh.getGhiChu());
    }

        void updateStatus() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblKhoaHoc.getRowCount() - 1;
        txtChuyenDe.setEditable(!edit);

        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void edit() {

        try {
            int maKH = (int) tblKhoaHoc.getValueAt(this.row, 0);
            KhoaHoc kh = khoaHocSer.getKhoaHocByID(maKH);
            if (kh != null) {
                setFrom(kh);
                updateStatus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không thể truy vấn dữ liệu");
        }
    }

    void clearFrom() {
        this.setFrom(new KhoaHoc());
        this.updateStatus();
        row = -1;
        updateStatus();
    }

    KhoaHoc getFrom() {

        String tenCD = cboChuyenDe.getSelectedItem().toString();
        float hocPhi = Float.parseFloat(txtHocPhi.getText().trim());
        int thoiLuong = Integer.parseInt(txtThoiLuong.getText().trim());
        String khaiGiang = txtKhaiGiang.getText().trim();
        String nguoiTao = txtNguoiTao.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();

        String maCD = chuyenDeSer.getChuyenDeByTen(tenCD).getMaCD();
        ChuyenDe cd = new ChuyenDe();
        cd.setMaCD(maCD);

        ChuyenDe cdHocPhi = new ChuyenDe();
        cdHocPhi.setHocPhi(hocPhi);

        ChuyenDe cdThoiLuong = new ChuyenDe();
        cdThoiLuong.setThoiLuong(thoiLuong);

        NhanVien nv = new NhanVien();
        nv.setMaNV(nguoiTao);

        SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd", new Locale("vi", "VN"));
        Date dateKhaiGiang = null;
        try {
            dateKhaiGiang = formart.parse(khaiGiang);
            Date currentDate = new Date();
            if (dateKhaiGiang.before(currentDate)) {
                JOptionPane.showMessageDialog(this, "Ngày khai giảng không thể nhỏ hơn ngày hiện tại!");
                txtKhaiGiang.requestFocus();
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày khai giảng không đúng định dạng yyyy-MM-dd!");
            txtKhaiGiang.requestFocus();
            return null;
        }

        try {
            formart.setLenient(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi trong việc đặt định dạng ngày!");
            txtKhaiGiang.requestFocus();
            return null;
        }

        KhoaHoc kh = new KhoaHoc(cd, hocPhi, thoiLuong, dateKhaiGiang, ghiChu, nv, dateKhaiGiang);
        return kh;

    }

    void WhiteForm() {
        txtChuyenDe.setText("");
        txtKhaiGiang.setText("");
        txtHocPhi.setText("");
        txtGhiChu.setText("");
        txtNgayTao.setText("");
        txtNguoiTao.setText("");
        txtThoiLuong.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cboChuyenDe = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        txtChuyenDe = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnNhapMoi = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtKhaiGiang = new javax.swing.JTextField();
        txtThoiLuong = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhoaHoc = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setText("QUẢN LÝ KHÓA HỌC");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cboChuyenDe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jLabel1.setText("Chuyên đề");

        jLabel7.setText("Khai giảng");

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

        txtChuyenDe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtChuyenDe.setForeground(new java.awt.Color(204, 0, 0));
        txtChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtChuyenDeActionPerformed(evt);
            }
        });

        jLabel8.setText("Ghi chú");

        txtHocPhi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHocPhiActionPerformed(evt);
            }
        });

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        jLabel3.setText("Học phí");

        txtNguoiTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNguoiTaoActionPerformed(evt);
            }
        });

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

        jLabel4.setText("Người tạo");

        txtKhaiGiang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKhaiGiangActionPerformed(evt);
            }
        });

        txtThoiLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThoiLuongActionPerformed(evt);
            }
        });

        jLabel5.setText("Thời lượng (giờ)");

        txtNgayTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayTaoActionPerformed(evt);
            }
        });

        jLabel6.setText("Ngày tạo");

        tblKhoaHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ KH", "HỌC PHÍ", "THỜI LƯỢNG", "KHAI GIẢNG", "TẠO BỞI", "NGÀY TẠO", "GHI CHÚ"
            }
        ));
        tblKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhoaHocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhoaHoc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtChuyenDe)
                                    .addComponent(txtHocPhi)
                                    .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtKhaiGiang)
                                    .addComponent(txtThoiLuong)
                                    .addComponent(txtNgayTao))))
                        .addGap(15, 15, 15))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(12, 12, 12)
                        .addComponent(txtChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNguoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(12, 12, 12)
                        .addComponent(txtKhaiGiang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstKhoaHoc();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prevKhoaHoc();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextKhoaHoc();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastKhoaHoc();
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtChuyenDeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtChuyenDeActionPerformed

    private void txtHocPhiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHocPhiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHocPhiActionPerformed

    private void txtNguoiTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNguoiTaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNguoiTaoActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm khóa học này không ?", "add", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        boolean maNhanVienTonTai = true;
        String maNhanVien = txtNguoiTao.getText().trim();
        for (NhanVien nv : listNhanVien) {
            if (nv.getMaNV().equals(maNhanVien)) {
                maNhanVienTonTai = false;
                break;

            }
        }

        if (maNhanVienTonTai) {
            JOptionPane.showMessageDialog(this, "Người tạo không tồn tại, hãy kiểm tra lại !");

        } else {
            KhoaHoc kh = getFrom();
            try {
                if (khoaHocSer.addKhoaHoc(kh) != null) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công");
                    loadToTableTheoKH();
                }
            } catch (Exception e) {
              
            }

        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa khóa học này hay không ?", "update", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }

        int row = tblKhoaHoc.getSelectedRow();
        String maKh = tblKhoaHoc.getValueAt(row, 0).toString();
        KhoaHoc kh = getFrom();
        kh.setMaKH(Integer.parseInt(maKh));
        try {
            if (khoaHocSer.updateKhoaHoc(kh) != null) {
                JOptionPane.showMessageDialog(this, "Sửa khóa học thành công ");
                loadToTableTheoKH();
                loadToTable();
            }

        } catch (Exception e) {
          
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa khóa học này không ?", "delete", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {

            return;
        }
        int index = tblKhoaHoc.getSelectedRow();
        String maNV = tblKhoaHoc.getValueAt(index, 0).toString();
        try {
            if (khoaHocSer.deleteKhoaHoc(maNV) != null) {
                JOptionPane.showMessageDialog(this, "Xóa thành công khóa học !");

                WhiteForm();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnNhapMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapMoiActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn clear from hay không ?", "Clear", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        clearFrom();
    }//GEN-LAST:event_btnNhapMoiActionPerformed

    private void txtKhaiGiangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKhaiGiangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKhaiGiangActionPerformed

    private void txtThoiLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThoiLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThoiLuongActionPerformed

    private void txtNgayTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayTaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayTaoActionPerformed

    private void tblKhoaHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoaHocMouseClicked
        showDetail();
    }//GEN-LAST:event_tblKhoaHocMouseClicked

    void EventChuyenDe() {
        cboChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadToTableTheoKH();
            }
        });
    }

    private String findTenChuyenDe(String maCD) {
        for (ChuyenDe cd : listChuyenDe) {
            if (cd.getMaCD().equals(maCD)) {
                return cd.getTenCD();

            }
        }
        return "";
    }

    private String findTenChuyenDe2(String tenCD) {
        for (ChuyenDe cd : listChuyenDe) {
            if (cd.getTenCD().equals(tenCD)) {
                return cd.getTenCD();
            }
        }
        return "";
    }

    void eventLayTenCombobox() {
        cboChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTenCD = cboChuyenDe.getSelectedItem().toString();
                String tenCD = findTenChuyenDe2(selectedTenCD);
                txtChuyenDe.setText(tenCD);
            }
        });
    }

    private float findHocPhi(String tenCD) {
        for (ChuyenDe cd : listChuyenDe) {
            if (cd.getTenCD().equals(tenCD)) {
                return cd.getHocPhi();
            }
        }
        return -1;
    }

    void eventlayHocPhiComBobox() {
        cboChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTenCD = cboChuyenDe.getSelectedItem().toString();
                float hocPhi = findHocPhi(selectedTenCD);

                if (hocPhi != -1) {
                    txtHocPhi.setText(String.valueOf(hocPhi));
                } else {
                    txtHocPhi.setText("Course not found");

                }
            }
        });
    }

    private int findThoiLuong(String tenCD) {
        for (ChuyenDe cd : listChuyenDe) {
            if (cd.getTenCD().equals(tenCD)) {
                return cd.getThoiLuong();

            }
        }
        return -1;
    }

    void eventLayThoiLuongToComBoBox() {
        cboChuyenDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTenCD = cboChuyenDe.getSelectedItem().toString();
                int thoiLuong = findThoiLuong(selectedTenCD);
                if (thoiLuong != 1) {
                    txtThoiLuong.setText(String.valueOf(thoiLuong));
                } else {
                    txtThoiLuong.setText("Not found");
                }

            }
        });
    }

    void firstKhoaHoc() {
        row = 0;
        edit();
        tblKhoaHoc.setRowSelectionInterval(row, row);
    }

    void prevKhoaHoc() {
        if (row > 0) {
            row--;
            edit();
            tblKhoaHoc.setRowSelectionInterval(row, row);
        }
    }

    void nextKhoaHoc() {
        if (row < tblKhoaHoc.getRowCount() - 1) {
            row++;
            edit();
            tblKhoaHoc.setRowSelectionInterval(row, row);
        }
    }

    void lastKhoaHoc() {
        row = tblKhoaHoc.getRowCount() - 1;
        edit();
        tblKhoaHoc.setRowSelectionInterval(row, row);
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
            java.util.logging.Logger.getLogger(QuanLyKhoaHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhoaHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhoaHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyKhoaHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyKhoaHocView dialog = new QuanLyKhoaHocView(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cboChuyenDe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblKhoaHoc;
    private javax.swing.JTextField txtChuyenDe;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtKhaiGiang;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtThoiLuong;
    // End of variables declaration//GEN-END:variables
}

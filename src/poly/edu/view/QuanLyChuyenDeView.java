package poly.edu.view;

import java.io.File;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.edu.model.ChuyenDe;
import poly.edu.service.ChuyenDeService;
import poly.edu.untinity.XImage;

/**
 *
 * @author Admin
 */
public class QuanLyChuyenDeView extends javax.swing.JDialog {

    ArrayList<ChuyenDe> listCD = new ArrayList<>();
    ChuyenDeService serviceCD = new ChuyenDeService();
    DefaultTableModel model = new DefaultTableModel();
    JFileChooser fileChooser = new JFileChooser();
    int row = 0;

    public QuanLyChuyenDeView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        model = (DefaultTableModel) tblChuyenDe.getModel();
        listCD = serviceCD.getAllChuyenDe();
        loadToTable();
    }

    void init() {
        setIconImage(XImage.getIcon());
        setLocationRelativeTo(null);
        setTitle("EduSys - Quản lý chuyên đề");
        updateStatus();
    }

    void loadToTable() {
        listCD = serviceCD.getAllChuyenDe();
        model.setRowCount(0);
        for (ChuyenDe cd : listCD) {
            model.addRow(new Object[]{
                cd.getMaCD(),
                cd.getTenCD(),
                cd.getHocPhi(),
                cd.getThoiLuong(),
                cd.getHinh(),
                cd.getMoTa()
            });

        }

    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.saveIMG(file);
            ImageIcon icon = XImage.read(file.getName());
            lblHinhAnh.setIcon(icon);
            lblHinhAnh.setToolTipText(file.getName());
        }
    }

    void showDetail(ChuyenDe cd) {
        txtMaChuyenDe.setText(cd.getMaCD());
        txtTenChuyenDe.setText(cd.getTenCD());
        txtThoiLuong.setText(cd.getThoiLuong() + "");
        txtHocPhi.setText(cd.getHocPhi() + "");
        txtMoTa.setText(cd.getMoTa());
        String hinh = cd.getHinh();
        if (hinh != null && !hinh.equals("")) {
            lblHinhAnh.setIcon(XImage.read(hinh));
            lblHinhAnh.setToolTipText(hinh);
        }

    }

    ChuyenDe getFrom() {
        String maCD = txtMaChuyenDe.getText();
        String tenCD = txtTenChuyenDe.getText();
        String thoiLuong = txtThoiLuong.getText();
        String hocPhi = txtHocPhi.getText();
        String moTa = txtMoTa.getText();
        int thoiLuongINT;
        float hocPhiFL;

        if (maCD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã chuyên đề không được để trống !");
            txtMaChuyenDe.requestFocus();
            return null;
        }
        if (maCD.length() != 5) {
            JOptionPane.showMessageDialog(this, "Mã chuyên đề phải 5 kí tự !");
            txtMaChuyenDe.requestFocus();
            return null;
        }

        if (tenCD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên chuyên đề không được để trống !");
            txtTenChuyenDe.requestFocus();
            return null;
        }
        if (tenCD.length() > 50) {
            JOptionPane.showMessageDialog(this, "Tên chuyên đề không được quá 50 kí tự !");
            txtTenChuyenDe.requestFocus();
            return null;
        }
        if (thoiLuong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thời lượng không được để trống !");
            txtThoiLuong.requestFocus();
            return null;
        }

        try {
            thoiLuongINT = Integer.parseInt(thoiLuong);
            if (thoiLuongINT <= 0) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải lớn hơn 0 !");
                txtThoiLuong.requestFocus();
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thời lượng phải là số nguyên !");
            txtThoiLuong.requestFocus();
            return null;
        }

        if (hocPhi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Học phí không được để trống !");
            txtHocPhi.requestFocus();
            return null;
        }

        try {
            hocPhiFL = Float.parseFloat(hocPhi);
            if (hocPhiFL <= 0) {
                JOptionPane.showMessageDialog(this, "Học phí phải lớn hơn 0 !");
                txtHocPhi.requestFocus();
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Học phí không được để trống !");
            txtHocPhi.requestFocus();
            return null;
        }
        if (moTa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mô tả không được để trống !");
            txtMoTa.requestFocus();
            return null;
        }
        if (moTa.length() > 255) {
            JOptionPane.showMessageDialog(this, "Mô tả không được quá 255 kí tự");
        }

        String hinhAnh = lblHinhAnh.getToolTipText();
        Icon IconAnh = lblHinhAnh.getIcon();
        if (IconAnh == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn hình ảnh chuyên đề");
            lblHinhAnh.requestFocus();
            return null;
        }

        ChuyenDe cd = new ChuyenDe(maCD, tenCD, hocPhiFL, thoiLuongINT, hinhAnh, moTa);
        return cd;
    }

    void edit() {
        try {
            String maCD = (String) tblChuyenDe.getValueAt(this.row, 0);
            ChuyenDe cd = serviceCD.getChuyenDeByID(maCD);
            if (cd != null) {
                showDetail(cd);
                updateStatus();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void updateStatus() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblChuyenDe.getRowCount() - 1;

        txtMaChuyenDe.setEditable(!edit);

        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void clearFrom() {
        this.showDetail(new ChuyenDe());
        this.updateStatus();
        row = -1;
        lblHinhAnh.setIcon(null);

        updateStatus();

        // Kiểm tra nếu giá trị trả về từ getHinh() không phải là null trước khi thực hiện bất kỳ thao tác nào
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblHinhAnh = new javax.swing.JLabel();
        txtTenChuyenDe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtThoiLuong = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtHocPhi = new javax.swing.JTextField();
        txtMaChuyenDe = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnNhapMoi = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiemMa = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblChuyenDe = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("QUẢN LÝ CHUYÊN ĐỀ");

        jLabel7.setText("Mô tả chuyên đề");

        jLabel4.setText("Học phí");

        jLabel5.setText("Hình logo");

        lblHinhAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinhAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHinhAnhMousePressed(evt);
            }
        });

        jLabel6.setText("Thời lượng (giờ)");

        jLabel2.setText("Mã chuyên đề");

        jLabel3.setText("Tên chuyên đề");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

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

        btnNhapMoi.setText("Nhập mới");
        btnNhapMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapMoiActionPerformed(evt);
            }
        });
        jPanel4.add(btnNhapMoi);

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

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel3.setLayout(new java.awt.GridLayout(1, 0));
        jPanel3.add(txtTimKiemMa);

        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        tblChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "MÃ CD", "TÊN CD", "HỌC PHÍ", "THỜI LƯỢNG", "HÌNH ẢNH", "MÔ TẢ"
            }
        ));
        tblChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChuyenDeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblChuyenDe);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaChuyenDe)
                            .addComponent(txtTenChuyenDe)
                            .addComponent(txtThoiLuong)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtHocPhi, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMaChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtThoiLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(1, 1, 1)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHinhAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblHinhAnhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhAnhMousePressed
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblHinhAnhMousePressed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm chuyên đề hay không ?", "add", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        String maCD = txtMaChuyenDe.getText().trim();
        boolean maCDTT = false;
        for (ChuyenDe cd : listCD) {
            if (cd.getMaCD().equals(maCD)) {
                maCDTT = true;
                break;
            }

        }

        if (maCDTT) {
            JOptionPane.showMessageDialog(this, "Mã chuyên đề đã tồn tại, không thể thêm !");
        } else {
            try {
                ChuyenDe cd = getFrom();
                if (serviceCD.addChuyenDe(cd) != null) {
                    JOptionPane.showMessageDialog(this, "Thêm chuyên đề thành công !");
                    loadToTable();
                    clearFrom();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa chuyên đề hay không ?", "delete", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        try {

            int i = tblChuyenDe.getSelectedRow();
            if (i < 0) {
                JOptionPane.showMessageDialog(this, "Chưa chọn dòng để thực hiện !");
                return;
            }

            String maCD = txtMaChuyenDe.getText().trim();

            if (serviceCD.deleteChuyenDe(maCD) != null) {
                JOptionPane.showMessageDialog(this, "Xóa chuyên đề thành công");
                clearFrom();
                loadToTable();

            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnNhapMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapMoiActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn Clear From hay không ?", "clear", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        clearFrom();
    }//GEN-LAST:event_btnNhapMoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstChuyenDe();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prevChuyenDe();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextChuyenDe();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastChuyenDe();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn tìm kiếm chuyên đề hay không ?", "find", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        String maCD = txtTimKiemMa.getText().trim();
        boolean maCDTT = false;
        for (ChuyenDe cd : listCD) {
            if (cd.getMaCD().equals(maCD)) {
                maCDTT = true;
                break;
            }
        }
        if (maCD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã để tìm kiếm !");
            txtTimKiemMa.requestFocus();
        } else if (!maCDTT) {
            JOptionPane.showMessageDialog(this, "Mã chuyên đề không tồn tại !");
            txtTimKiemMa.requestFocus();
        } else {
            ChuyenDe cd = serviceCD.getChuyenDeByID(maCD);
            if (serviceCD.getChuyenDeByID(maCD) != null) {
                JOptionPane.showMessageDialog(this, "Tìm kiếm chuyên đề thành công");
                showDetail(cd);

            }
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuyenDeMouseClicked
        try {
            int index = tblChuyenDe.getSelectedRow();
            ChuyenDe cd = listCD.get(index);
            showDetail(cd);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_tblChuyenDeMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm chuyên đề hay không ?", "add", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        int i = tblChuyenDe.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn dòng để thực hiện !");
            return;
        }
        try {
            ChuyenDe cd = getFrom();
            if (serviceCD.updateChuyenDe(cd) != null) {
                JOptionPane.showMessageDialog(this, "Sửa chuyên đề thành công !");
                loadToTable();
                clearFrom();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnSuaActionPerformed
    void firstChuyenDe() {

        row = 0;
        edit();
        tblChuyenDe.setRowSelectionInterval(row, row);
    }

    void prevChuyenDe() {
        if (row > 0) {
            row--;
            edit();
            tblChuyenDe.setRowSelectionInterval(row, row);
        }
    }

    void nextChuyenDe() {
        if (row < tblChuyenDe.getRowCount() - 1) {
            row++;
            edit();
            tblChuyenDe.setRowSelectionInterval(row, row);
        }
    }

    void lastChuyenDe() {
        row = tblChuyenDe.getRowCount() - 1;
        edit();
        tblChuyenDe.setRowSelectionInterval(row, row);
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
            java.util.logging.Logger.getLogger(QuanLyChuyenDeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyChuyenDeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyChuyenDeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyChuyenDeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyChuyenDeView dialog = new QuanLyChuyenDeView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHinhAnh;
    private javax.swing.JTable tblChuyenDe;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaChuyenDe;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenChuyenDe;
    private javax.swing.JTextField txtThoiLuong;
    private javax.swing.JTextField txtTimKiemMa;
    // End of variables declaration//GEN-END:variables
}

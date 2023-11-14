package poly.edu.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.edu.model.NguoiHoc;
import poly.edu.model.NhanVien;
import poly.edu.service.NguoiHocService;
import poly.edu.untinity.Auth;
import poly.edu.untinity.XImage;

public class QuanLyNguoiHocView extends javax.swing.JDialog {

    DefaultTableModel model = new DefaultTableModel();
    ArrayList<NguoiHoc> listNH = new ArrayList<>();
    NguoiHocService serviceNH = new NguoiHocService();
    private static final String pEmail = "^(.+)@([\\w-]+\\.)+([a-zA-Z]{2,4})$";

    int row = 0;

    public QuanLyNguoiHocView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        model = (DefaultTableModel) tblNguoiHoc.getModel();
        listNH = serviceNH.getAllNguoiHoc();
        loadToTable();
        loadFirstNguoiHoc();

    }

    void init() {
        setIconImage(XImage.getIcon());
        setLocationRelativeTo(null);
        setTitle("EduSys - Quản lý người học");
        updateStatus();

    }

    void loadToTable() {
        listNH = serviceNH.getAllNguoiHoc();
        model.setRowCount(0);
        for (NguoiHoc nh : listNH) {
            model.addRow(new Object[]{
                nh.getMaNH(),
                nh.getHoTen(),
                nh.isGioiTinh() ? "Nam" : "Nữ",
                nh.getNgaySinh(),
                nh.getSoDienThoai(),
                nh.getEmail(),
                nh.getMaNhanVien(),
                nh.getNgayDK()
            });

        }

    }

    void loadToTableByKeyWord() {
        String keyWord = txtTimKiem.getText().trim();
        listNH = serviceNH.getAllNguoiHocByKeyword(keyWord);
        model.setRowCount(0);
        for (NguoiHoc nh : listNH) {
            model.addRow(new Object[]{
                nh.getMaNH(),
                nh.getHoTen(),
                nh.isGioiTinh() ? "Nam" : "Nữ",
                nh.getNgaySinh(),
                nh.getSoDienThoai(),
                nh.getEmail(),
                nh.getMaNhanVien(),
                nh.getNgayDK()
            });

        }

    }

    void loadFirstNguoiHoc() {
        if (model.getRowCount() > 0) {
            tblNguoiHoc.setRowSelectionInterval(0, 0);
            showDetail();
        }
    }

    void checkMa() {
        String maNv = Auth.user.getMaNV();
        if (maNv != null) {
            JOptionPane.showMessageDialog(this, "Dung");
        } else {
            JOptionPane.showMessageDialog(this, "sai");
        }
    }

    void showDetail() {

        int index = tblNguoiHoc.getSelectedRow();
        txtMaNguoiHoc.setText(tblNguoiHoc.getValueAt(index, 0).toString());
        txtHoTen.setText(tblNguoiHoc.getValueAt(index, 1).toString());
        boolean gt = tblNguoiHoc.getValueAt(index, 2).toString().equals("Nam");
        rdoMale.setSelected(gt);
        rdoFemale.setSelected(!gt);
        txtNgaySinh.setText(tblNguoiHoc.getValueAt(index, 3).toString());
        txtSoDienThoai.setText(tblNguoiHoc.getValueAt(index, 4).toString());
        txtEmail.setText(tblNguoiHoc.getValueAt(index, 5).toString());
        txtMaNhanVien.setText(tblNguoiHoc.getValueAt(index, 6).toString());
        txtGhiChu.setText(tblNguoiHoc.getValueAt(index, 7).toString());

    }

    void setFrom(NguoiHoc nh) {
        txtMaNguoiHoc.setText(nh.getMaNH());
        txtHoTen.setText(nh.getHoTen());
        if (nh.isGioiTinh() == true) {
            rdoMale.isSelected();
        } else {
            rdoFemale.isSelected();
        }
        txtNgaySinh.setText(nh.getNgaySinh() + "");
        txtSoDienThoai.setText(nh.getSoDienThoai());
        txtEmail.setText(nh.getEmail());
        txtGhiChu.setText(nh.getGhiChu());
    }

    void edit() {
        try {
            String maNV = (String) tblNguoiHoc.getValueAt(this.row, 0);
            NguoiHoc nh = serviceNH.getAllNguoiHocByID(maNV);
            if (nh != null) {
                setFrom(nh);
                updateStatus();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không thể truy vấn dữ liệu");
        }
    }

    void updateStatus() {
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblNguoiHoc.getRowCount() - 1;
        txtMaNguoiHoc.setEditable(!edit);

        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);

        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);

    }

    void clearFrom() {
        this.setFrom(new NguoiHoc());
        this.updateStatus();
        row = -1;
        updateStatus();
    }

    NguoiHoc getFrom() {
        String maNH = txtMaNguoiHoc.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String ngaySinh = txtNgaySinh.getText().trim();
        String SDT = txtSoDienThoai.getText().trim();
        String email = txtEmail.getText().trim();
        String ghiChu = txtGhiChu.getText().trim();
        int soDienThoaiINT;
        boolean gt = false;
        if (rdoMale.isSelected()) {
            gt = rdoMale.isSelected();
        } else {
            rdoFemale.isSelected();
        }
        if (maNH.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã người học không được để trống !");
            txtMaNguoiHoc.requestFocus();
            return null;
        }
        if (maNH.length() != 7) {
            JOptionPane.showMessageDialog(this, "Mã người học phải 7 kí tự !");
            txtMaNguoiHoc.requestFocus();
            return null;
        }
        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên người học không được để trống !");
            txtHoTen.requestFocus();
            return null;
        }
        if (hoTen.length() <= 5) {
            JOptionPane.showMessageDialog(this, "Họ tên phải nhiều hơn 5 kí tự !");
            txtHoTen.requestFocus();
            return null;
        }
        if (hoTen.length() > 50) {
            JOptionPane.showMessageDialog(this, "Họ tên không được quá 50 kí tự");
            txtHoTen.requestFocus();
            return null;
        }

        if (ngaySinh.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ngày sinh người học không được để trống !");
            txtNgaySinh.requestFocus();
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date ngaySinhDate = null;
        try {
            ngaySinhDate = format.parse(ngaySinh);

            Date currentDate = new Date();
            if (ngaySinhDate.after(currentDate)) {
                JOptionPane.showMessageDialog(this, "Ngày sinh không thể lớn hơn ngày hiện tại!");
                txtNgaySinh.requestFocus();
                return null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng yyyy-MM-dd !");
            txtNgaySinh.requestFocus();
            return null;
        }
        try {
            format.setLenient(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi trong việc đặt định dạng ngày!");
            txtNgaySinh.requestFocus();
            return null;
        }

        if (SDT.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại người học không được để trống !");
            txtSoDienThoai.requestFocus();
            return null;
        }
        try {
            soDienThoaiINT = Integer.parseInt(SDT);
            if (soDienThoaiINT <= 0) {
                JOptionPane.showMessageDialog(this, "Số điện thoại phải là số nguyên dương");
                return null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số điện thoại người học phải là số nguyên !");
            return null;
        }

        if (SDT.length() <= 5) {
            JOptionPane.showMessageDialog(this, "Số điện thoại người học phải nhiều hơn 5 số !");
            txtSoDienThoai.requestFocus();
            return null;
        }
        if (SDT.length() > 24) {
            JOptionPane.showMessageDialog(this, "Số điện thoại người học không được quá 24 số !");
            txtSoDienThoai.requestFocus();
            return null;
        }

        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email người học không được để trống !");
            txtEmail.requestFocus();
            return null;
        }
        Matcher mcher = Pattern.compile(pEmail).matcher(txtEmail.getText().trim());
        if (!mcher.matches()) {
            JOptionPane.showMessageDialog(this, "Email sai định dạng");
            txtEmail.requestFocus();
            return null;
        }
        if (email.length() <= 10) {
            JOptionPane.showMessageDialog(this, "Email phải nhiều hơn 10 kí tự !");
            txtEmail.requestFocus();
            return null;
        }
        if (email.length() > 50) {
            JOptionPane.showMessageDialog(this, "Email không được quá 50 kí tự");
            txtEmail.requestFocus();
            return null;
        }

        if (ghiChu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ghi chú không được để trống !");
            txtGhiChu.requestFocus();
            return null;
        }
        NhanVien nv = new NhanVien();
        String maNV = txtMaNhanVien.getText().trim();
        nv.setMaNV(maNV);

        NguoiHoc nh = new NguoiHoc();
        Date dateDK = nh.getNgayDK();

        NguoiHoc nguoiHoc = new NguoiHoc(maNH, hoTen, gt, ngaySinhDate, SDT, email, ghiChu, nv, dateDK);

        return nguoiHoc;
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
        txtNgaySinh = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSoDienThoai = new javax.swing.JTextField();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaNguoiHoc = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        txtHoTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        rdoMale = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnNhapMoi = new javax.swing.JButton();
        rdoFemale = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel9.setText("Mã nhân viên");

        jLabel6.setText("Điện thoại");

        jLabel2.setText("Mã người học");

        jLabel7.setText("Địa chỉ email");

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

        jLabel3.setText("Họ và tên");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        jLabel4.setText("Giới tính");

        jLabel8.setText("Ghi chú");

        buttonGroup1.add(rdoMale);
        rdoMale.setSelected(true);
        rdoMale.setText("Nam");

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

        buttonGroup1.add(rdoFemale);
        rdoFemale.setText("Nữ");

        jLabel5.setText("Ngày sinh");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("QUẢN LÝ NGƯỜI HỌC");

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NH", "HỌ TÊN", "GIỚI TÍNH", "NGÀY SINH", "SỐ ĐIỆN THOẠI", "EMAIL", "MÃ NV", "NGÀY DK"
            }
        ));
        tblNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiHocMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNguoiHoc);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));

        btnTimKiem.setText("Tìm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtTimKiem)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addComponent(txtMaNhanVien)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoMale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(156, 156, 156)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNgaySinh)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(191, 191, 191)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoMale)
                    .addComponent(rdoFemale)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSoDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        firstNguoiHoc();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm người học này hay không ?", "add", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        String maNH = txtMaNguoiHoc.getText().trim();
        boolean maNHTT = false;
        for (NguoiHoc nguoiHoc : listNH) {
            if (nguoiHoc.getMaNH().equals(maNH)) {
                maNHTT = true;
                break;
            }
        }
        if (maNHTT) {
            JOptionPane.showMessageDialog(this, "Mã người học tồn tại không thể thêm !");
            txtMaNguoiHoc.requestFocus();
        } else {
            NguoiHoc nh = getFrom();
            try {
                if (serviceNH.addNguoiHoc(nh) != null) {
                    JOptionPane.showMessageDialog(this, "Thêm người học thành công");
                    loadToTable();
                    clearFrom();
                }
            } catch (Exception e) {

            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa người học này hay không ?", "update", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        int i = tblNguoiHoc.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng để thực hiện câu lệnh !");
            return;
        }
        NguoiHoc nh = getFrom();
        try {
            if (serviceNH.updateNguoiHoc(nh) != null) {
                JOptionPane.showMessageDialog(this, "Sửa người học thành công !");
                loadToTable();
            }
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa người học này hay không ?", "clear", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        int i = tblNguoiHoc.getSelectedRow();
        if (i < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng để thực hiện câu lệnh !");
            return;
        }
        String maNH = txtMaNguoiHoc.getText().trim();
        try {
            if (serviceNH.deleteNguoiHoc(maNH) != null) {
                JOptionPane.showMessageDialog(this, "Xóa thành công người học");
                clearFrom();
                loadToTable();
                loadFirstNguoiHoc();
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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn tìm người học này hay không ?", "update", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        }
        String keyWord = txtTimKiem.getText().trim();
        if (keyWord.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keyword không được để trống, hãy nhập keyword để tìm kiếm!");
            txtTimKiem.requestFocus();
            return;
        }
        try {

            if (serviceNH.getAllNguoiHocByKeyword(keyWord) != null) {
                JOptionPane.showMessageDialog(this, "Tìm người học thành công");
                loadToTableByKeyWord();

            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiHocMouseClicked
        showDetail();
    }//GEN-LAST:event_tblNguoiHocMouseClicked

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prevNguoiHoc();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextNguoiHoc();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastNguoiHoc();
    }//GEN-LAST:event_btnLastActionPerformed

    void firstNguoiHoc() {
        row = 0;
        edit();
        tblNguoiHoc.setRowSelectionInterval(row, row);
    }

    void prevNguoiHoc() {
        if (row > 0) {
            row--;
            edit();
            tblNguoiHoc.setRowSelectionInterval(row, row);
        }
    }

    void nextNguoiHoc() {
        if (row < tblNguoiHoc.getRowCount() - 1) {
            row++;
            edit();
            tblNguoiHoc.setRowSelectionInterval(row, row);
        }
    }

    void lastNguoiHoc() {
        row = tblNguoiHoc.getRowCount() - 1;
        edit();
        tblNguoiHoc.setRowSelectionInterval(row, row);
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
            java.util.logging.Logger.getLogger(QuanLyNguoiHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNguoiHocView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyNguoiHocView dialog = new QuanLyNguoiHocView(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JTable tblNguoiHoc;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNguoiHoc;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSoDienThoai;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}

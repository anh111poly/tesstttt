package poly.edu.view;

import javax.swing.JOptionPane;
import poly.edu.model.NhanVien;
import poly.edu.service.NhanVienService;
import poly.edu.untinity.Auth;
import poly.edu.untinity.XImage;

public class DoiMatKhauView extends javax.swing.JDialog {

    NhanVienService serviceNV = new NhanVienService();

    /**
     * Creates new form DoiMatKhauDialog
     */
    public DoiMatKhauView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init() {
        setIconImage(XImage.getIcon());
        setLocationRelativeTo(null);
        setTitle("EduSys - Đổi mật khẩu");
    }

    void huyBo() {
        int check = JOptionPane.showConfirmDialog(this, "Bạn có muốn hủy bỏ hay không ?", "huy bo", JOptionPane.YES_NO_OPTION);
        if (check != JOptionPane.YES_NO_OPTION) {
            return;
        } else {
            this.dispose();
        }

    }

    NhanVien getFrom() {
        String maNV = txtTenDangNhap.getText().trim();
        String matKhau1 = new String(txtMatKhau1.getPassword());
        String matKhau2 = new String(txtMatKhau2.getPassword());
        String matKhau3 = new String(txtMatKhau3.getPassword());
        if (maNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhâp không được để trống");
            txtTenDangNhap.requestFocus();
            return null;
        }
        if (matKhau1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống");
            txtMatKhau1.requestFocus();
            return null;
        }
        if (matKhau2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không được để trống");
            txtMatKhau2.requestFocus();
            return null;
        }
        if (matKhau3.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu mới không được để trống");
            txtMatKhau3.requestFocus();
            return null;
        }
        if (maNV.length() > 20) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập không được lớn hơn 20 kí tự");
            txtTenDangNhap.requestFocus();
            return null;
        }
        if (matKhau1.length() < 3) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải lớn  hơn 2 kí tự");
            txtMatKhau1.requestFocus();
            return null;
        }
        if (matKhau1.length() > 50) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được lớn hơn 50 kí tự");
            txtMatKhau1.requestFocus();
            return null;
        }

        if (matKhau2.length() < 3) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới phải lớn  hơn 2 kí tự");
            txtMatKhau2.requestFocus();
            return null;
        }
        if (matKhau2.length() > 50) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới không được lớn hơn 50 kí tự");
            txtMatKhau2.requestFocus();
            return null;
        }

        if (matKhau3.length() < 3) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu mới phải lớn  hơn 2 kí tự");
            txtMatKhau3.requestFocus();
            return null;
        }
        if (matKhau3.length() > 50) {
            JOptionPane.showMessageDialog(this, "Xác nhận mậkt khẩu mới không được lớn hơn 50 kí tự");
            txtMatKhau3.requestFocus();
            return null;
        }

        NhanVien nv = new NhanVien(maNV, matKhau3);

        return nv;

    }

    void doiMatKhau() {
        String maNV = txtTenDangNhap.getText().trim();
        String matKhau = new String(txtMatKhau1.getPassword());
        String matKhauMoi1 = new String(txtMatKhau2.getPassword());
        String matKhauMoi2 = new String(txtMatKhau3.getPassword());

        NhanVien nv = serviceNV.getNhanVienByID(maNV);

        if (!maNV.equals(nv.getMaNV())) {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập");
            txtTenDangNhap.requestFocus();
            return;
        } else if (!matKhau.equals(nv.getMatKhau())) {
            JOptionPane.showMessageDialog(this, "Sai mật khẩu");
            txtMatKhau1.requestFocus();
            return;
        } else if (!matKhauMoi1.equals(matKhauMoi2)) {
            JOptionPane.showMessageDialog(this, "Xác nhận mật khẩu mới không chính xác");
            txtTenDangNhap.requestFocus();
            return;
        }

        // Update the user's password using the new NhanVien object (nv1)
        NhanVien nv1 = getFrom();

        if (nv1 == null) {
            JOptionPane.showMessageDialog(this, "NhanVien is null. Please check your code.");
            return;
        }

        // Set the updated password in the user object
        nv.setMatKhau(nv1.getMatKhau());

        // You should also update any other user information if needed
        // For example, nv.setTenDangNhap(nv1.getTenDangNhap());
        // Save the updated user information
        serviceNV.updateNhanVien(nv);

        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
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
        lblLogo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMatKhau1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtMatKhau2 = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtMatKhau3 = new javax.swing.JPasswordField();
        btnDongY = new javax.swing.JButton();
        btnHuyBo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("ĐỔI MẬT KHẨU");

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/edu/images/security.png"))); // NOI18N

        jLabel2.setText("Tên đăng nhập");

        jLabel3.setText("Mật khẩu hiện tại ");

        jLabel4.setText("Mật khẩu mới");

        jLabel5.setText("Xác nhận mật khẩu mới");

        btnDongY.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/edu/images/Accept.png"))); // NOI18N
        btnDongY.setText("Đồng ý");
        btnDongY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYActionPerformed(evt);
            }
        });

        btnHuyBo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/edu/images/No.png"))); // NOI18N
        btnHuyBo.setText("Hủy bỏ");
        btnHuyBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyBoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogo))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMatKhau2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDongY)
                                .addGap(31, 31, 31)
                                .addComponent(btnHuyBo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatKhau3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatKhau1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatKhau1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMatKhau2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatKhau3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDongY, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHuyBo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyBoActionPerformed
        huyBo();
    }//GEN-LAST:event_btnHuyBoActionPerformed

    private void btnDongYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYActionPerformed
        doiMatKhau();
    }//GEN-LAST:event_btnDongYActionPerformed

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
            java.util.logging.Logger.getLogger(DoiMatKhauView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DoiMatKhauView dialog = new DoiMatKhauView(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDongY;
    private javax.swing.JButton btnHuyBo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JPasswordField txtMatKhau1;
    private javax.swing.JPasswordField txtMatKhau2;
    private javax.swing.JPasswordField txtMatKhau3;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}

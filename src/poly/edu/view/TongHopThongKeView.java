package poly.edu.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import poly.edu.model.HocVien;
import poly.edu.model.KhoaHoc;
import poly.edu.model.NguoiHoc;
import poly.edu.model.TKDiemCD;
import poly.edu.model.TKDoanhThu;
import poly.edu.model.TKNguoiHoc;
import poly.edu.service.KhoaHocService;
import poly.edu.service.NguoiHocService;
import poly.edu.service.ThongKeService;
import poly.edu.untinity.XImage;

public class TongHopThongKeView extends javax.swing.JDialog {

    KhoaHocService serviceKH = new KhoaHocService();
    ThongKeService serviceTK = new ThongKeService();
    NguoiHocService serviceNH = new NguoiHocService();

    ArrayList<KhoaHoc> listKH = new ArrayList<>();
    ArrayList<HocVien> listHV = new ArrayList<>();
    ArrayList<TKNguoiHoc> listTKNH = new ArrayList<>();
    ArrayList<TKDiemCD> listDiemCD = new ArrayList<>();
    ArrayList<Integer> listYear = new ArrayList<>();
    ArrayList<TKDoanhThu> listDT = new ArrayList<>();

    DefaultTableModel modelDiem = new DefaultTableModel();
    DefaultTableModel modelNH = new DefaultTableModel();
    DefaultTableModel modelDiemCD = new DefaultTableModel();
    DefaultTableModel modelDoanhThu = new DefaultTableModel();

    DefaultComboBoxModel<KhoaHoc> comBoKH = new DefaultComboBoxModel<>();
    DefaultComboBoxModel comBoNam = new DefaultComboBoxModel<>();

    public TongHopThongKeView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();

    }

    void init() {
        setIconImage(XImage.getIcon());
        setLocationRelativeTo(null);
        setTitle("EduSys - Thống kê");
        listKH = serviceKH.getAllKhoaHoc();
        loadComBoBoxKhoaHoc();
        String selectedKH = cboKhoaHoc.getSelectedItem().toString();
        listHV = serviceTK.getThongKeDiem(Integer.parseInt(selectedKH));
        modelDiem = (DefaultTableModel) tblBangDiem.getModel();
        EventLoadDiem();
        listTKNH = serviceTK.getThongKeNH();
        modelNH = (DefaultTableModel) tblNguoiHoc.getModel();
        loadToTableNguoiHoc();
        listDiemCD = serviceTK.getThongKeDiemCD();
        modelDiemCD = (DefaultTableModel) tblDiemChuyenDe.getModel();
        loadToTableDiemCD();
        loadComBoBoxYear();
        modelDoanhThu = (DefaultTableModel) tblDoanhThu.getModel();
        String selectedYear = cboKhoaHoc.getSelectedItem().toString();
        listDT = serviceTK.getDoanhThuByYear(Integer.parseInt(selectedYear));
        eventDoanhThu();

    }

    void loadComBoBoxKhoaHoc() {
        Set<String> setKH = new HashSet<>();
        for (KhoaHoc kh : listKH) {
            setKH.add(kh.toString());
        }
        comBoKH = (DefaultComboBoxModel) new DefaultComboBoxModel<>(setKH.toArray());
        cboKhoaHoc.setModel((DefaultComboBoxModel) comBoKH);

    }

    void loadTableDiem() {
        String selectedKH = cboKhoaHoc.getSelectedItem().toString();
        listHV = serviceTK.getThongKeDiem(Integer.parseInt(selectedKH));

        modelDiem.setRowCount(0);
        for (HocVien hocVien : listHV) {
            modelDiem.addRow(new Object[]{
                hocVien.getNguoiHoc().getMaNH(),
                giveNameByMaNH(hocVien.getNguoiHoc().getMaNH()),
                hocVien.getDiem(),
                hocVien.xepLoai()
            });
        }

    }

    void loadToTableNguoiHoc() {
        listTKNH = serviceTK.getThongKeNH();
        modelNH.setRowCount(0);
        for (TKNguoiHoc tknh : listTKNH) {
            modelNH.addRow(new Object[]{
                tknh.getNam(),
                tknh.getSoLuongNH(),
                tknh.getDauTien(),
                tknh.getCuoiCung()
            });
        }
    }

    void loadToTableDiemCD() {
        listDiemCD = serviceTK.getThongKeDiemCD();
        modelDiemCD.setRowCount(0);
        for (TKDiemCD dcd : listDiemCD) {
            modelDiemCD.addRow(new Object[]{
                dcd.getTenCD(),
                dcd.getSoLuong(),
                dcd.getDiemTN(),
                dcd.getDiemCN(),
                dcd.getDiemTB()
            });
        }
    }

    void loadComBoBoxYear() {
        comBoNam = (DefaultComboBoxModel) cboNam.getModel();
        comBoNam.removeAllElements();
        listYear = serviceTK.selectYears();
        for (Integer year : listYear) {
            comBoNam.addElement(year);

        }

    }

    void loadToTalbeDoanhThu() {
        String selectedYear = cboNam.getSelectedItem().toString();
        listDT = serviceTK.getDoanhThuByYear(Integer.parseInt(selectedYear));
        modelDoanhThu.setRowCount(0);
        for (TKDoanhThu dt : listDT) {
            modelDoanhThu.addRow(new Object[]{
                dt.getTenCD(),
                dt.getSoKH(),
                dt.getSoHV(),
                dt.getDoanhThu(),
                dt.getThapNhat(),
                dt.getCaoNhat(),
                dt.getTrungBinh()
            });

        }
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBangDiem = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDiemChuyenDe = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDoanhThu = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("TỔNG HỢP THỐNG KÊ");

        jLabel2.setText("KHÓA HỌC: ");

        cboKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        tblBangDiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "MÃ NH", "HỌ TÊN", "ĐIỂM", "XẾP LOẠI"
            }
        ));
        jScrollPane1.setViewportView(tblBangDiem);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboKhoaHoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("BẢNG ĐIỂM", jPanel1);

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "NĂM", "SỐ NH", "ĐK SỚM NHẤT", "ĐK MUỘN NHẤT"
            }
        ));
        jScrollPane2.setViewportView(tblNguoiHoc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("NGƯỜI HỌC", jPanel2);

        tblDiemChuyenDe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CHUYÊN ĐỀ", "SLHV", "ĐIỂM TN", "ĐIỂM CN", "ĐIỂM TB"
            }
        ));
        jScrollPane3.setViewportView(tblDiemChuyenDe);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ĐIỂM CHUYÊN ĐỀ", jPanel3);

        tblDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "CHUYÊN ĐỀ", "SỐ KH", "SỐ HV", "DOANH THU", "THẤP NHẤT", "CAO NHẤT", "TRUNG BÌNH"
            }
        ));
        jScrollPane4.setViewportView(tblDoanhThu);

        jLabel3.setText("NĂM: ");

        cboNam.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboNam, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 9, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("DOANH THU", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    void EventLoadDiem() {
        cboKhoaHoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTableDiem();
            }
        });
    }

    void eventDoanhThu() {
        cboNam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadToTalbeDoanhThu();
            }
        });
    }

    String giveNameByMaNH(String maNH) {
        ArrayList<NguoiHoc> listNH = serviceNH.getAllNguoiHoc();
        for (NguoiHoc nh : listNH) {
            if (nh.getMaNH().equals(maNH)) {
                return nh.getHoTen();
            }

        }

        return "";
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
            java.util.logging.Logger.getLogger(TongHopThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TongHopThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TongHopThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TongHopThongKeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TongHopThongKeView dialog = new TongHopThongKeView(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblBangDiem;
    private javax.swing.JTable tblDiemChuyenDe;
    private javax.swing.JTable tblDoanhThu;
    private javax.swing.JTable tblNguoiHoc;
    // End of variables declaration//GEN-END:variables
}

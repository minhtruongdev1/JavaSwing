/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import DungChung.ExportExcel;
import edu.QLCH.Dao.MaLoaiSPDao;
import edu.QLCH.Entity.LoaiSP;
import edu.QLCH.Helper.AuthHelper;
import edu.QLCH.Helper.MsgBoxHelper;
import edu.QLCH.Helper.UtilityHelper;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class QuanLyLoaiSPJDialog extends javax.swing.JDialog {

    int row = 0; // Hàng được chọn hiện tại trên bảng
    MaLoaiSPDao lspdao = new MaLoaiSPDao();
    /**
     * Creates new form QuanLiSanPham
     */
    public QuanLyLoaiSPJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init(){
//            setIconImage(ShareHelper.getAppIcon());
            setLocationRelativeTo(null);
            setTitle("HỆ THỐNG QUẢN LÝ LOẠI SẢN PHẨM");
            this.fillTable();
            row = -1;
            this.updateStatus();
    }
    
    void fillTable(){
        DefaultTableModel model = (DefaultTableModel) tblLoaiSP.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng trên JTable
        try {
            List<LoaiSP> list = lspdao.selectAll();
            for (LoaiSP lsp : list){
                Object[] row ={
                    lsp.getMaLoaiSP(),
                    lsp.getTenLoaiSP(),
                    lsp.getSoLuong(),
                    lsp.getGiaCa(),
                    lsp.getMoTa()
                };
                model.addRow(row);// Thêm một hàng vào JTable
            }
        } catch (Exception e) {
        MsgBoxHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    void setForm(LoaiSP model){
       txtMaLSP.setText(model.getMaLoaiSP());
       txtTenLSP.setText(model.getTenLoaiSP());
       txtSoLuong.setText(String.valueOf(model.getSoLuong()));
       txtGiaCa.setText(String.valueOf(model.getGiaCa()));
       txtMoTa.setText(model.getMoTa());
    }
    
    LoaiSP getForm(){
        LoaiSP model = new LoaiSP();
        model.setMaLoaiSP(txtMaLSP.getText());
        model.setTenLoaiSP(txtTenLSP.getText());
        model.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        model.setGiaCa(Float.valueOf(txtGiaCa.getText()));
        model.setMoTa(txtMoTa.getText());
        return model;
    }
    void clearForm(){
        this.setForm(new LoaiSP());
        row = -1;
        updateStatus();
    }
    
    void edit(){
       try{
            String macd = (String) tblLoaiSP.getValueAt(this.row, 0);
            LoaiSP model = lspdao.selectById(macd);
            if (model != null){
                setForm(model);
                updateStatus();
            }
        }catch (Exception e){
            MsgBoxHelper.alert(this, "Lỗi try vấn dữ liệu");
        }
    }
    
    void insert(){
        LoaiSP model = getForm();
        try {
            lspdao.insert(model);
            fillTable();
            clearForm();
            MsgBoxHelper.alert(this, "Thêm mới thành công");
        } catch (Exception e) {
            MsgBoxHelper.alert(this, "Thêm mới thất bại");
        }
    }
    
    void update(){
        LoaiSP model = getForm();
        try {
            lspdao.update(model);
            System.out.println(model.getMoTa());
            fillTable();
            MsgBoxHelper.alert(this, "Cập nhật thành công!");
            
        } catch (Exception e) {
            e.printStackTrace();
            MsgBoxHelper.alert(this, "Cập nhật thất bại!");
        }
    }
    
    void delete(){
        if(!AuthHelper.isManager()){
            MsgBoxHelper.alert(this, "Bạn không có quyền xóa chuyên đề!");
        }else{
            if(MsgBoxHelper.confirm(this, "Bạn có muốn xóa hay không")){
                String macd = txtMaLSP.getText();
                try {
                    lspdao.delete(macd);
                    fillTable();
                    clearForm();
                    MsgBoxHelper.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBoxHelper.alert(this, "Xóa thất bại");
                }
            }
        }
    }
    
    void first(){
        row = 0;
        edit();
    }    
    void prev(){
        if(row > 0){
            row--;
            edit();
        }
    }
    void next(){
        if(row < tblLoaiSP.getRowCount()-1){
            row ++;
            edit();
        }
    }
    void last(){
        row = tblLoaiSP.getRowCount()-1;
        edit();
    }
       private void exportExcel1() {
        new ExportExcel().exportExcel("DANH SÁCH BẢNG LOẠI SẢN PHẨM", "LOẠI SẢN PHẨM", tblLoaiSP, new int[]{2000, 5000, 5000, 6000, 5000});
        JOptionPane.showMessageDialog(this, "Xuất file excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    void updateStatus(){
        boolean edit = this.row >= 0;
        boolean first = this.row == 0;
        boolean last = this.row == tblLoaiSP.getRowCount()-1;
        // Trạng thái form
        txtMaLSP.setEditable(!edit);
        btnInsert.setEnabled(!edit);
        btnUpdate.setEnabled(edit);
        btnDelete.setEnabled(edit);
        //trạng thái điều hướng
        btnFirst.setEnabled(edit && !first);//enable 4 nút này khi ở editable
        btnPrev.setEnabled(edit && !first); //disable khi First, Prev khi ở bản ghi đầu (index = 0)
        btnNext.setEnabled(edit && !last);  //disable khi Next, Last khi ở bản ghi cuối
        btnLast.setEnabled(edit && !last);  //index = tblGridView.getRowCount() - 1
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtMaLSP = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtGiaCa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoaiSP = new javax.swing.JTable();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtTenLSP = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Quản Lí Loại Sản Phẩm");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 11, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Tên loại sản phẩm");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, -1, -1));

        txtMaLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaLSPActionPerformed(evt);
            }
        });
        jPanel1.add(txtMaLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 299, -1));

        jLabel4.setForeground(new java.awt.Color(255, 51, 51));
        jLabel4.setText("Mã Sản Phẩm Không Chính Xác");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Giá cả");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        txtGiaCa.setText("0");
        jPanel1.add(txtGiaCa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 299, -1));

        jLabel8.setForeground(new java.awt.Color(255, 51, 51));
        jLabel8.setText("Giá Không Chính Xác");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Triệu");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, -1, -1));

        tblLoaiSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Loại SP", "Tên Loại SP", "Số Lượng", "Giá Nhập(Triệu)", "Mô Tả"
            }
        ));
        tblLoaiSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblLoaiSPMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoaiSP);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 925, 150));

        btnInsert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonThem.png"))); // NOI18N
        btnInsert.setBorder(null);
        btnInsert.setBorderPainted(false);
        btnInsert.setContentAreaFilled(false);
        btnInsert.setDefaultCapable(false);
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        jPanel1.add(btnInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 130, -1));

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonCapNhat.png"))); // NOI18N
        btnUpdate.setBorder(null);
        btnUpdate.setBorderPainted(false);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setDefaultCapable(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel1.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, 130, -1));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonMoi.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setDefaultCapable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 320, 120, -1));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonXoa.png"))); // NOI18N
        btnDelete.setBorder(null);
        btnDelete.setBorderPainted(false);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setDefaultCapable(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, -1, -1));
        jPanel1.add(txtTenLSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 299, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Mã loại sản phẩm");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Mô tả");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, -1, -1));
        jPanel1.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 299, -1));

        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgDau.png"))); // NOI18N
        btnFirst.setBorder(null);
        btnFirst.setBorderPainted(false);
        btnFirst.setContentAreaFilled(false);
        btnFirst.setDefaultCapable(false);
        jPanel1.add(btnFirst, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 600, 73, -1));

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgPre.png"))); // NOI18N
        btnPrev.setBorder(null);
        btnPrev.setBorderPainted(false);
        btnPrev.setContentAreaFilled(false);
        btnPrev.setDefaultCapable(false);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 600, 73, -1));

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgNext.png"))); // NOI18N
        btnNext.setBorder(null);
        btnNext.setBorderPainted(false);
        btnNext.setContentAreaFilled(false);
        btnNext.setDefaultCapable(false);
        jPanel1.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 600, 73, -1));

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgCuoi.png"))); // NOI18N
        btnLast.setBorder(null);
        btnLast.setBorderPainted(false);
        btnLast.setContentAreaFilled(false);
        btnLast.setDefaultCapable(false);
        jPanel1.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 600, 73, -1));

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 298, 71));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Số lượng");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonExcel.png"))); // NOI18N
        jButton10.setBorder(null);
        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.setDefaultCapable(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 580, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1051, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrevActionPerformed

    public boolean checkTrungMa(JTextField txt) {
        txt.setBackground(white);
        if (lspdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBoxHelper.alert(this, txt.getName() + " đã bị tồn tại.");
            return false;
        }
    }
    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
       if (UtilityHelper.checkNullText(txtMaLSP)
                && UtilityHelper.checkNullText(txtTenLSP)
                && UtilityHelper.checkNullText(txtSoLuong)
                && UtilityHelper.checkNullText(txtGiaCa)
                && UtilityHelper.checkNullText(txtMoTa)) {
             {
                if (checkTrungMa(txtMaLSP)) {
                    insert();
                }
            }
        }
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
       update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
      delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblLoaiSPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiSPMousePressed
       if (evt.getClickCount() == 2) {
            this.row = tblLoaiSP.rowAtPoint(evt.getPoint());//lấy vị trí dòng được chọn
            edit();
            fillTable();
        }     
    }//GEN-LAST:event_tblLoaiSPMousePressed

    private void txtMaLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaLSPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaLSPActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        exportExcel1();
    }//GEN-LAST:event_jButton10ActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyLoaiSPJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyLoaiSPJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyLoaiSPJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyLoaiSPJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyLoaiSPJDialog dialog = new QuanLyLoaiSPJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblLoaiSP;
    private javax.swing.JTextField txtGiaCa;
    private javax.swing.JTextField txtMaLSP;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenLSP;
    // End of variables declaration//GEN-END:variables
}

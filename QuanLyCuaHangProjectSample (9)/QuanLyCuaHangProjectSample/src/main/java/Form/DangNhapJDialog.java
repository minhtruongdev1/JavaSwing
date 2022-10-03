/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

//import edu.QLCH.Dao.NhanVienDao;

import edu.QLCH.Dao.NhanVienDao;
import edu.QLCH.Entity.NhanVien;
import edu.QLCH.Helper.AuthHelper;
import edu.QLCH.Helper.MsgBoxHelper;
import java.awt.Color;

//import edu.QLCH.Entity.NhanVien;
//import edu.QLCH.Helper.AuthHelper;
//import edu.QLCH.Helper.MsgBoxHelper;
//import java.awt.Color;

/**
 *
 * @author ASUS
 */
public class DangNhapJDialog extends javax.swing.JDialog {
    public static String tenDN = "";
    public static String vt = "";
    /**
     * Creates new form DangNhapJDialog
     */
    public DangNhapJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    void init(){
//     setIconImage(ShareHelper.getAppIcon());
            setLocationRelativeTo(null);
            setTitle("HỆ THỐNG ĐĂNG NHẬP");
    }
    NhanVienDao dao = new NhanVienDao() ;
 
    
    void dangNhap(){
       String manv = txtUsername.getText();
       String matKhau = new String(txtPassword.getPassword());
       NhanVien nhanVien = dao.selectById(manv);
        if(nhanVien == null){
           MsgBoxHelper.alert(this, "Sai tên Đăng Nhập hoặc Mật khẩu");
        }
        else{
            AuthHelper.user = nhanVien;
            MsgBoxHelper.alert(this, "Đăng nhập thành công");
            this.dispose();
        }
    }
    void ketThuc(){
        if(MsgBoxHelper.confirm(this, "Bạn muốn kết thúc ứng dụng")){
            System.exit(0); 
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnexit = new javax.swing.JButton();
        hinhform = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tên đăng nhập");
        jLabel2.setToolTipText("");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 130, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mật khẩu");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 100, -1));

        txtUsername.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtUsername.setText("PD01");
        txtUsername.setToolTipText("");
        txtUsername.setMinimumSize(new java.awt.Dimension(1, 17));
        txtUsername.setName(""); // NOI18N
        txtUsername.setPreferredSize(new java.awt.Dimension(1, 17));
        getContentPane().add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 330, 30));

        txtPassword.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPassword.setText("123");
        txtPassword.setMinimumSize(new java.awt.Dimension(1, 17));
        getContentPane().add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 330, 30));

        btnLogin.setBackground(new java.awt.Color(255, 255, 255));
        btnLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Đăng nhập");
        btnLogin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnLogin.setContentAreaFilled(false);
        btnLogin.setDefaultCapable(false);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, 130, 40));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/people.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 400, -1));

        btnexit.setBackground(new java.awt.Color(255, 255, 255));
        btnexit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnexit.setForeground(new java.awt.Color(255, 255, 255));
        btnexit.setText("Thoát");
        btnexit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnexit.setContentAreaFilled(false);
        btnexit.setDefaultCapable(false);
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });
        getContentPane().add(btnexit, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, 130, 40));

        hinhform.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BGLogin.png"))); // NOI18N
        getContentPane().add(hinhform, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 4, 410, 730));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        txtUsername.setBackground(Color.white);
        txtPassword.setBackground(Color.white);
        if(txtUsername.getText().trim().length()>0){
            if(txtPassword.getPassword().length>0){
                dangNhap();
            }else{
                txtPassword.setBackground(Color.red);
                MsgBoxHelper.alert(this, "Không được để trống tên mật khẩu");
            }
        }else{
            txtUsername.setBackground(Color.red);
            MsgBoxHelper.alert(this, "Không được để trống tài khoản");
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        ketThuc();
    }//GEN-LAST:event_btnexitActionPerformed

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
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DangNhapJDialog dialog = new DangNhapJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnexit;
    private javax.swing.JLabel hinhform;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
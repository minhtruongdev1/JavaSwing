/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import DungChung.ExportExcel;
import edu.QLCH.Dao.KhachHangDao;
import edu.QLCH.Dao.MaLoaiSPDao;
import edu.QLCH.Dao.SanPhamDao;
import edu.QLCH.Entity.KhachHang;
import edu.QLCH.Entity.LoaiSP;
import edu.QLCH.Entity.SanPham;
import edu.QLCH.Helper.AuthHelper;
import edu.QLCH.Helper.DateHelper;
import static edu.QLCH.Helper.JdbcHelper.getStmt;
import edu.QLCH.Helper.MsgBoxHelper;
import edu.QLCH.Helper.ShareHelper;
import edu.QLCH.Helper.UtilityHelper;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.awt.Image;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FPT
 */
public class QuanLySanPhamJDialog extends javax.swing.JDialog {

    
    /**
     * Creates new form QuanLySanPham1JDialog
     */
    public QuanLySanPhamJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        init();
    }
    int row = 0; // Hàng được chọn hiện tại trên bảng
    MaLoaiSPDao lspdao = new MaLoaiSPDao();
    SanPhamDao dao = new SanPhamDao();

    void init(){
            setLocationRelativeTo(null);
            setTitle("HỆ THỐNG QUẢN LÝ SẢN PHẨM");
            this.fillComBoxMaLoaiSP();
            this.fillTable();
            
            row = -1;
    }
    
    void fillTable(){
        DefaultTableModel model = (DefaultTableModel) tblSanpham.getModel();
        model.setRowCount(0);
        try {
            List<SanPham> list = dao.selectAll();
            for (SanPham sp : list) {
                Object[] row = {
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getMaLoaiSP(),
                    sp.getSoLuong(),
                    sp.getGiaCa(),
                    sp.getHinhSP(),
                    sp.getMoTa(),
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBoxHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    void fillComBoxMaLoaiSP(){
        try {
            List<LoaiSP> list = lspdao.selectAll();
            for (LoaiSP lsp : list) {
                Object[] row = {
                    lsp.getMaLoaiSP(),
                    lsp.getTenLoaiSP(),
                    lsp.getSoLuong(),
                    lsp.getGiaCa(),
                    lsp.getMoTa()
                };
                cboMaLoaiSP.addItem(lsp.getMaLoaiSP());
            }
        } catch (Exception e) {
            MsgBoxHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    
    void setForm(SanPham sp){
        txtMasp.setText(sp.getMaSP());
        cboMaLoaiSP.setSelectedItem(sp.getMaLoaiSP());
        txttensp.setText(sp.getTenSP());
        txtsoluong.setText(String.valueOf(sp.getSoLuong()));
        txtgiaca.setText(String.valueOf(sp.getGiaCa()));
        txtmota.setText(sp.getMoTa());
    }
    
    SanPham getForm(){
        SanPham sp = new SanPham();
        sp.setMaSP(txtMasp.getText());
        sp.setTenSP(txttensp.getText());
        sp.setGiaCa(Double.valueOf(txtgiaca.getText()));
        sp.setSoLuong(Integer.valueOf(txtsoluong.getText()));
        sp.setMoTa(txtmota.getText());
        sp.setMaLoaiSP((String) cboMaLoaiSP.getSelectedItem());      
        return sp;
     }
    
    void clearForm(){
        this.setForm(new SanPham());
        row = -1;
//        updateStatus();
    }
    void edit(){
        String masp = (String) tblSanpham.getValueAt(this.row, 0);
        System.out.println(this.row);
        SanPham sp = dao.selectById(masp);
        this.setForm(sp);
//        updateStatus();
//        tabs.setSelectedIndex(0);
//        lblTenCD.setText(chuyenDe.getTenCD());
    }
    
    void insert(){
        SanPham model = getForm();
        try {
            dao.insert(model);
            fillTable();
            clearForm();
            MsgBoxHelper.alert(this, "Thêm mới thành công");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBoxHelper.alert(this, "Thêm mới thất bại");
        }
    }
    
    void update(){
         SanPham model = getForm();
        try {
            dao.update(model);
            fillTable();
            MsgBoxHelper.alert(this, "cập nhật thành công");
        } catch (Exception e) {
            MsgBoxHelper.alert(this, "Cập nhật thất bại");
        }
    }
    
    void delete(){
         if(!AuthHelper.isManager()){
            MsgBoxHelper.alert(this, "Bạn không có quyền xóa !");
        }else{
            if(MsgBoxHelper.confirm(this, "Bạn có muốn xóa hay không")){
                String masp = txtMasp.getText();
                try {
                    dao.delete(masp);
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
        if(row < tblSanpham.getRowCount()-1){
            row ++;
            edit();
        }
    }
    void last(){
        row = tblSanpham.getRowCount()-1;
        edit();
    }
    JFileChooser fileChooser = new JFileChooser();
    void selectImage() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (ShareHelper.save(file)) {
                // Hiển thị hình lên form
                lblHinh.setIcon(ShareHelper.read(file.getName()));
                lblHinh.setToolTipText(file.getName());
            }
        }
    }
//    void updateStatus(){
//        boolean edit = this.row >= 0;
//        boolean first = this.row == 0;
//        boolean last = this.row == tblKhoaHoc.getRowCount()-1;
//        // Trạng thái form
//        txtMaNV.setEnabled(!edit);
//        btnInsert.setEnabled(!edit);
//        btnUpdate.setEnabled(edit);
//        btnDelete.setEnabled(edit);
//        //trạng thái điều hướng
//        btnFirst.setEnabled(edit && !first);//enable 4 nút này khi ở editable
//        btnPrev.setEnabled(edit && !first); //disable khi First, Prev khi ở bản ghi đầu (index = 0)
//        btnNext.setEnabled(edit && !last);  //disable khi Next, Last khi ở bản ghi cuối
//        btnLast.setEnabled(edit && !last);  //index = tblGridView.getRowCount() - 1
//    }
    private void timKiem() {
        fillTable();
        clearForm();
        row = -1;
//        updateStatus();
    }
     private void exportExcel() {
        new ExportExcel().exportExcel("DANH SÁCH BẢNG SẢN PHẨM", "SẢN PHẨM", tblSanpham, new int[]{2000, 5000, 5000, 6000, 5000,5000,5000});
        JOptionPane.showMessageDialog(this, "Xuất file excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
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
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMasp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtgiaca = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanpham = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txttensp = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtsoluong = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtmota = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        lblHinh = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cboMaLoaiSP = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Quản Lí Sản Phẩm");

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Tên sản phẩm");

        txtMasp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtMasp.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtMasp.setDoubleBuffered(true);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Mã Loại Sản Phẩm");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Giá cả");

        txtgiaca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtgiaca.setText("0");
        txtgiaca.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Triệu");

        tblSanpham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Mã Loại Sản Phẩm", "Số Lượng", "Gía Cả", "Hình Sản Phẩm", "Mô Tả"
            }
        ));
        tblSanpham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSanphamMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblSanphamMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanpham);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonThem.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setDefaultCapable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonCapNhat.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setDefaultCapable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonXoa.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setDefaultCapable(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txttensp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txttensp.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Mã sản phẩm");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Hình sản phẩm");

        txtsoluong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtsoluong.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtmota.setColumns(20);
        txtmota.setRows(5);
        jScrollPane2.setViewportView(txtmota);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgDau.png"))); // NOI18N
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setDefaultCapable(false);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgPre.png"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setDefaultCapable(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgNext.png"))); // NOI18N
        jButton7.setBorder(null);
        jButton7.setBorderPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton7.setDefaultCapable(false);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgCuoi.png"))); // NOI18N
        jButton8.setBorder(null);
        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.setDefaultCapable(false);

        lblHinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblHinh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblHinhMouseEntered(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Số lượng");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Mô tả");

        cboMaLoaiSP.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cboMaLoaiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaLoaiSPActionPerformed(evt);
            }
        });

        jButton9.setText("Tìm kiếm");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

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

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonFPT.png"))); // NOI18N
        jButton11.setBorder(null);
        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.setDefaultCapable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1)
                        .addGap(423, 423, 423)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jButton9))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel10))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(txtMasp, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(491, 491, 491)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtgiaca, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jLabel9))
                            .addComponent(jLabel5)
                            .addComponent(cboMaLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(29, 29, 29)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txttensp, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))))
                        .addGap(91, 91, 91)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 925, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(394, 394, 394)
                        .addComponent(jButton10)
                        .addGap(18, 18, 18)
                        .addComponent(jButton11)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel1))
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel3))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMasp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txttensp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboMaLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtgiaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jButton2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel13)
                        .addGap(13, 13, 13)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton3)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6)
                    .addComponent(jButton5)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
    selectImage();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void lblHinhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseEntered

    }//GEN-LAST:event_lblHinhMouseEntered

    private void cboMaLoaiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaLoaiSPActionPerformed
//       chonMa();
        
    }//GEN-LAST:event_cboMaLoaiSPActionPerformed

     public boolean checkTrungMa(JTextField txt) {
        txt.setBackground(white);
        if (dao.selectById(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBoxHelper.alert(this, txt.getName() + " đã bị tồn tại.");
            return false;
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      if (UtilityHelper.checkNullText(txtMasp)
                && UtilityHelper.checkNullText(txttensp)
                && UtilityHelper.checkNullText(txtsoluong)
                && UtilityHelper.checkNullText(txtgiaca)
                && UtilityHelper.checkNullText(txtmota)) {
                       
                if (checkTrungMa(txtMasp)) {
                    insert();
                }
            
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       update();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        delete();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        clearForm();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblSanphamMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanphamMouseReleased
     
    }//GEN-LAST:event_tblSanphamMouseReleased

    private void tblSanphamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanphamMousePressed
         if (evt.getClickCount() == 2) {
            this.row = tblSanpham.rowAtPoint(evt.getPoint());//lấy vị trí dòng được chọn
            edit();
            fillTable();
        }     
    }//GEN-LAST:event_tblSanphamMousePressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
       timKiem();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
      exportExcel();
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
            java.util.logging.Logger.getLogger(QuanLySanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLySanPhamJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLySanPhamJDialog dialog = new QuanLySanPhamJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> cboMaLoaiSP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JTable tblSanpham;
    private javax.swing.JTextField txtMasp;
    private javax.swing.JTextField txtgiaca;
    private javax.swing.JTextArea txtmota;
    private javax.swing.JTextField txtsoluong;
    private javax.swing.JTextField txttensp;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import DungChung.ExportExcel;
import edu.QLCH.Dao.HDCTDAO;
import edu.QLCH.Dao.HoaDonDao;
import edu.QLCH.Dao.KhachHangDao;
import edu.QLCH.Dao.MaLoaiSPDao;
import edu.QLCH.Dao.SanPhamDao;
import edu.QLCH.Entity.HDCT;
import edu.QLCH.Entity.HoaDon;
import edu.QLCH.Entity.KhachHang;
import edu.QLCH.Entity.LoaiSP;
import edu.QLCH.Entity.SanPham;
import edu.QLCH.Helper.AuthHelper;
import edu.QLCH.Helper.DateHelper;
import edu.QLCH.Helper.DoanhThuTheoNamDAO;
import edu.QLCH.Helper.MsgBoxHelper;
import edu.QLCH.Helper.UtilityHelper;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FPT
 */
public class QuanLyHD extends javax.swing.JDialog {

    /**
     * Creates new form QuanLyHD
     */
    public QuanLyHD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
      int row = 0; // Hàng được chọn hiện tại trên bảng
    MaLoaiSPDao lspdao = new MaLoaiSPDao();
    HoaDonDao hdao = new HoaDonDao();
    KhachHangDao khdao = new KhachHangDao();
    HDCTDAO hdctdao = new HDCTDAO();
    SanPhamDao spdao = new SanPhamDao();
    
    
    /**
     * Creates new form QuanLiSanPham
     */
    void init(){
//            setIconImage(ShareHelper.getAppIcon());
            setLocationRelativeTo(null);
            setTitle("HỆ THỐNG QUẢN LÝ HÓA ĐƠN");
            this.fillTable();
            this.fillTableHDCT();
            fillComBoxMaKhachHang();
            fillComBoxMaSP();
            fillComBoxMaHD();
            row = -1;
//            this.updateStatus();
    }
    
    void fillTable(){
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng trên JTable
        try {
            String keyword = txtTimHD.getText();
            List<HoaDon> list = hdao.selectByKeyword(keyword);
            for (HoaDon hd : list){
                Object[] row ={
                    hd.getMaHD(),
                    DateHelper.toString(hd.getNgayTao(), "dd/MM/yyyy"),
                    hd.getMaNV(),
                    hd.getMaKH(),
                };
                model.addRow(row);// Thêm một hàng vào JTable
            }
        } catch (Exception e) {
            e.printStackTrace();
        MsgBoxHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
     void fillTableHDCT(){
        DefaultTableModel model = (DefaultTableModel) tblHDCT.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng trên JTable
        try {
            String keyword = txtTimKiemHDCT.getText();
            
            List<HDCT> list = hdctdao.selectByKeyword(keyword);
            for (HDCT hdct : list){
                Object[] row ={
                    hdct.getMaHDCT(),
                    hdct.getMaSP(),
                    hdct.getMaHD(),
                    hdct.getSoluong(),
                    hdct.getGiaBan(),
                    hdct.getThanhtien(),
                    
                };
                model.addRow(row);// Thêm một hàng vào JTable
            }
        } catch (Exception e) {
            e.printStackTrace();
        MsgBoxHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
     
     void fillComBoxMaKhachHang(){
        try {
            List<KhachHang> list = khdao.selectAll();
            for (KhachHang mkh : list) {
                Object[] row = {
                    mkh.getMaKH(),
                    mkh.getTenKH(),
                    mkh.getDiaChi(),
                    mkh.getNgaySinh(),
                    mkh.getDienThoai(),
                    mkh.getEmail(),
                    mkh.getGhiChu(),
                        
                };
                cboMaKhachHang.addItem(mkh.getMaKH());
            }
        } catch (Exception e) {
            MsgBoxHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
     
     void fillComBoxMaSP(){
        try {
            List<SanPham> list = spdao.selectAll();
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
                cboMaSP.addItem(sp.getMaSP());
            }
        } catch (Exception e) {
            MsgBoxHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
     
     void fillComBoxMaHD(){
        try {
            List<HoaDon> list = hdao.selectAll();
            for (HoaDon hd : list) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaNV(),
                    hd.getMaKH(),
                    hd.getNgayTao(),
                    
                };
//                cboMaSP.addItem(sp.getMaSP());
                  cboMaHD.addItem(hd.getMaHD());
            }
        } catch (Exception e) {
            MsgBoxHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
     
     void setFormHDCT(HDCT model){
       DoanhThuTheoNamDAO dtdao = new DoanhThuTheoNamDAO();
       txthdct.setText(String.valueOf(model.getMaHDCT()));
       cboMaHD.setSelectedItem(model.getMaHD());
//       txtmasp.setText(String.valueOf(model.getMaSP()));
       cboMaSP.setToolTipText(String.valueOf(model.getMaSP()));
//       cboMaSP.setSelectedItem(model.getMaSP());
       txtsoluongct.setText(String.valueOf(model.getSoluong()));
       txtgiaban.setText(String.valueOf(model.getGiaBan()));

    }
    
    HDCT getFormHDCT(){
        HDCT hdctmodel = new HDCT();
        hdctmodel.setMaHDCT(txthdct.getText());
        hdctmodel.setMaHD((String) cboMaHD.getSelectedItem());     
        hdctmodel.setMaSP((String) cboMaSP.getSelectedItem());  
        hdctmodel.setGiaBan(Double.valueOf(txtgiaban.getText()));
        hdctmodel.setSoluong(Integer.valueOf(txtsoluongct.getText()));
        hdctmodel.setThanhtien(Double.valueOf(txtthanhtien.getText()));
        
        
        return hdctmodel;
    }
    void clearFormCT(){
         HDCT hdctmodel = new  HDCT();
       hdctmodel.setMaHDCT(hdctmodel.getMaHDCT());
        hdctmodel.setMaHD(hdctmodel.getMaHD());
        hdctmodel.setMaSP(hdctmodel.getMaSP());
        hdctmodel.setGiaBan(hdctmodel.getGiaBan());
        hdctmodel.setThanhtien(hdctmodel.getThanhtien());
        setFormHDCT(hdctmodel);
    }
    
    void editHDCT(){
   
             String mahdct =  (String) tblHDCT.getValueAt(this.row, 0);
            
            HDCT hd = hdctdao.selectById(mahdct);
            this.setFormHDCT(hd);
  
    }
     void insertHDCT(){
        HDCT model = getFormHDCT();
        try {
            
            hdctdao.insert(model);
            fillTable();
            clearForm();
            MsgBoxHelper.alert(this, "Thêm mới thành công");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBoxHelper.alert(this, "Thêm mới thất bại");
        }
    }
    
    void updateHDCT(){
        HDCT model = getFormHDCT();
        try {
           hdctdao.update(model);
       
            fillTable();
            MsgBoxHelper.alert(this, "Cập nhật thành công!");
            
        } catch (Exception e) {
            e.printStackTrace();
            MsgBoxHelper.alert(this, "Cập nhật thất bại!");
        }
    }
    
    void deleteHDCT(){
        if(!AuthHelper.isManager()){
            MsgBoxHelper.alert(this, "Bạn không có quyền xóa!");
        }else{
            if(MsgBoxHelper.confirm(this, "Bạn có muốn xóa hay không")){
                String mahdct = txthdct.getText();
                try {
                    hdctdao.delete(mahdct);
                    fillTable();
                    clearForm();
                    MsgBoxHelper.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBoxHelper.alert(this, "Xóa thất bại");
                }
            }
        }
    }
    void setForm(HoaDon model){
       txtMaHD.setText(String.valueOf(model.getMaHD()));
       cboMaKhachHang.setSelectedItem(model.getMaKH());
       txtMaNV.setText(String.valueOf(model.getMaNV()));
       txtNgayLap.setText(DateHelper.toString(model.getNgayTao(), "dd/MM/yyyy"));
    }
    
    HoaDon getForm(){
        HoaDon model = new HoaDon();
        model.setMaHD(txtMaHD.getText());
        model.setMaKH((String) cboMaKhachHang.getSelectedItem());
        model.setMaNV(AuthHelper.user.getMaNV());
        model.setNgayTao(DateHelper.toDate(txtNgayLap.getText(), "dd/MM/yyyy"));
        return model;
    }
    void clearForm(){
        HoaDon model = new HoaDon();
        model.setMaHD(model.getMaHD());
        model.setMaNV(AuthHelper.user.getMaNV());
        model.setNgayTao(DateHelper.addDays(new Date(), 30));
        model.setNgayTao(new Date());
        
        setForm(model);
    }
    
    void edit(){
   
        String mahd = (String) tblHoaDon.getValueAt(this.row, 0);
        System.out.println(this.row);
        HoaDon hd = hdao.selectById(mahd);
        this.setForm(hd);
  
    }
    
    void insert(){
        HoaDon model = getForm();
        try {
            hdao.insert(model);
            fillTable();
            clearForm();
            
            MsgBoxHelper.alert(this, "Thêm mới thành công");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBoxHelper.alert(this, "Thêm mới thất bại");
        }
    }
    
    void update(){
        HoaDon model = getForm();
        try {
            hdao.update(model);
       
            fillTable();
            MsgBoxHelper.alert(this, "Cập nhật thành công!");
            
        } catch (Exception e) {
            e.printStackTrace();
            MsgBoxHelper.alert(this, "Cập nhật thất bại!");
        }
    }
    
    void delete(){
        if(!AuthHelper.isManager()){
            MsgBoxHelper.alert(this, "Bạn không có quyền xóa!");
        }else{
            if(MsgBoxHelper.confirm(this, "Bạn có muốn xóa hay không")){
                String mahd = txtMaHD.getText();
                try {
                    hdao.delete(mahd);
                    fillTable();
                    clearForm();
                    MsgBoxHelper.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    e.printStackTrace();
                    MsgBoxHelper.alert(this, "Xóa thất bại");
                }
            }
        }
    }
    void thanhtien(){
         int sl = Integer.parseInt(txtsoluongct.getText());
         double gb = Double.parseDouble(txtgiaban.getText());
         txtthanhtien.setText(String.valueOf(sl*gb));
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
        if(row < tblHoaDon.getRowCount()-1){
            row ++;
            edit();
        }
    }
    void last(){
        row = tblHoaDon.getRowCount()-1;
        edit();
    }
    
    private void timKiemHDCT(){
        fillTableHDCT();
        clearForm();
        row = -1;
//        updateStatus();
    }
    private void timKiemHD(){
        fillTable();
        clearForm();
        row = -1;
//        updateStatus();
    }
    public boolean checkTrungMaHDCT(JTextField txt) {
        txt.setBackground(white);
        if (hdctdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBoxHelper.alert(this,  "Mã hóa đơn chi tiết đã tồn tại.");
            return false;
        }
    }
          private void exportExcel1() {
        new ExportExcel().exportExcel("DANH SÁCH BẢNG SẢN PHẨM", "SẢN PHẨM", tblHDCT, new int[]{2000, 5000, 5000, 6000, 5000,5000});
        JOptionPane.showMessageDialog(this, "Xuất file excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
             private void exportExcelHoaDon() {
        new ExportExcel().exportExcel("DANH SÁCH BẢNG SẢN PHẨM", "SẢN PHẨM", tblHoaDon, new int[]{2000, 5000, 5000, 6000});
        JOptionPane.showMessageDialog(this, "Xuất file excel thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
//
//    void updateStatus(){
//        boolean edit = this.row >= 0;
//        boolean first = this.row == 0;
//        boolean last = this.row == tblLoaiSP.getRowCount()-1;
//        // Trạng thái form
//        txtMaLSP.setEditable(!edit);
//        btnInsert.setEnabled(!edit);
//        btnUpdate.setEnabled(edit);
//        btnDelete.setEnabled(edit);
//        //trạng thái điều hướng
//        btnFirst.setEnabled(edit && !first);//enable 4 nút này khi ở editable
//        btnPrev.setEnabled(edit && !first); //disable khi First, Prev khi ở bản ghi đầu (index = 0)
//        btnNext.setEnabled(edit && !last);  //disable khi Next, Last khi ở bản ghi cuối
//        btnLast.setEnabled(edit && !last);  //index = tblGridView.getRowCount() - 1
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTimHD = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        lblLoiMaHD = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblLoiNhayLap = new javax.swing.JLabel();
        txtNgayLap = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        splTable = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        cboMaKhachHang = new javax.swing.JComboBox<>();
        btnTimHD = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txthdct = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtgiaban = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtthanhtien = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHDCT = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        txtHDCTADD = new javax.swing.JButton();
        txtHDCTUpdate = new javax.swing.JButton();
        txtHDCTDelete = new javax.swing.JButton();
        txtHDCTnew = new javax.swing.JButton();
        txtTimKiemHDCT = new javax.swing.JTextField();
        txtsoluongct = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cboMaSP = new javax.swing.JComboBox<>();
        btnTimHDCT = new javax.swing.JButton();
        cboMaHD = new javax.swing.JComboBox<>();
        jButton13 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý hóa đơn");
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tìm Kiếm");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Mã Hóa Đơn");

        lblLoiMaHD.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblLoiMaHD.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiMaHD.setText("Mã hóa đơn không chính xác");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(55, 38, 91));
        jLabel18.setText("Ngày lập");

        lblLoiNhayLap.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblLoiNhayLap.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiNhayLap.setText("Chưa chọn ngày lập");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(55, 38, 91));
        jLabel20.setText("Mã nhân viên");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(55, 38, 91));
        jLabel19.setText("Mã Khách Hàng");

        tblHoaDon.setAutoCreateRowSorter(true);
        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn", "Ngày lập", "Mã nhân viên", "Mã khách hàng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblHoaDon.setAutoscrolls(false);
        tblHoaDon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblHoaDon.setFocusable(false);
        tblHoaDon.setGridColor(new java.awt.Color(255, 255, 255));
        tblHoaDon.setOpaque(false);
        tblHoaDon.setRequestFocusEnabled(false);
        tblHoaDon.setRowHeight(30);
        tblHoaDon.setRowMargin(0);
        tblHoaDon.setSelectionBackground(new java.awt.Color(55, 38, 91));
        tblHoaDon.setShowHorizontalLines(false);
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHoaDonMousePressed(evt);
            }
        });
        splTable.setViewportView(tblHoaDon);

        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonThem.png"))); // NOI18N
        btnThem.setBorder(null);
        btnThem.setBorderPainted(false);
        btnThem.setContentAreaFilled(false);
        btnThem.setDefaultCapable(false);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonCapNhat.png"))); // NOI18N
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setContentAreaFilled(false);
        jButton6.setDefaultCapable(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonXoa.png"))); // NOI18N
        jButton7.setBorder(null);
        jButton7.setBorderPainted(false);
        jButton7.setContentAreaFilled(false);
        jButton7.setDefaultCapable(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonMoi.png"))); // NOI18N
        jButton8.setBorder(null);
        jButton8.setBorderPainted(false);
        jButton8.setContentAreaFilled(false);
        jButton8.setDefaultCapable(false);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgCuoi.png"))); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgDau.png"))); // NOI18N

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgPre.png"))); // NOI18N

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgNext.png"))); // NOI18N

        cboMaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaKhachHangActionPerformed(evt);
            }
        });

        btnTimHD.setText("Tìm");
        btnTimHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimHDActionPerformed(evt);
            }
        });

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonExcel.png"))); // NOI18N
        jButton14.setBorder(null);
        jButton14.setBorderPainted(false);
        jButton14.setContentAreaFilled(false);
        jButton14.setDefaultCapable(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButton8)
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(lblLoiMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(cboMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(128, 128, 128)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(140, 140, 140)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTimHD, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLoiNhayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(78, 78, 78)
                                        .addComponent(btnTimHD, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel20)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 32, Short.MAX_VALUE)))))
                .addGap(74, 74, 74))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(splTable, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton14)
                .addGap(86, 86, 86))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel18)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtTimHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLoiMaHD)
                    .addComponent(lblLoiNhayLap))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboMaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(splTable, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton14)
                    .addComponent(jButton1)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addGap(95, 95, 95))
        );

        jTabbedPane1.addTab("Hóa Đơn", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(55, 38, 91));
        jLabel10.setText("Tìm kiếm");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(55, 38, 91));
        jLabel11.setText("Mã hóa đơn chi tiết");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(55, 38, 91));
        jLabel12.setText("Số Lượng");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(55, 38, 91));
        jLabel16.setText("Giá Bán");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(55, 38, 91));
        jLabel17.setText("Mã Sản phẩm");

        txtthanhtien.setEditable(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(55, 38, 91));
        jLabel15.setText("Thành tiền");

        tblHDCT.setAutoCreateRowSorter(true);
        tblHDCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HDCT", "Mã Sản Phẩm", "Mã Hóa Đơn", "Số Lượng", "Thành Tiền", "Giá Bán"
            }
        ));
        tblHDCT.setGridColor(new java.awt.Color(255, 255, 255));
        tblHDCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblHDCTMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblHDCT);

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgDau.png"))); // NOI18N

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgPre.png"))); // NOI18N

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgNext.png"))); // NOI18N

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgCuoi.png"))); // NOI18N

        txtHDCTADD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonThem.png"))); // NOI18N
        txtHDCTADD.setBorder(null);
        txtHDCTADD.setBorderPainted(false);
        txtHDCTADD.setContentAreaFilled(false);
        txtHDCTADD.setDefaultCapable(false);
        txtHDCTADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHDCTADDActionPerformed(evt);
            }
        });

        txtHDCTUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonCapNhat.png"))); // NOI18N
        txtHDCTUpdate.setBorder(null);
        txtHDCTUpdate.setBorderPainted(false);
        txtHDCTUpdate.setContentAreaFilled(false);
        txtHDCTUpdate.setDefaultCapable(false);
        txtHDCTUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHDCTUpdateActionPerformed(evt);
            }
        });

        txtHDCTDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonXoa.png"))); // NOI18N
        txtHDCTDelete.setBorder(null);
        txtHDCTDelete.setBorderPainted(false);
        txtHDCTDelete.setContentAreaFilled(false);
        txtHDCTDelete.setDefaultCapable(false);
        txtHDCTDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHDCTDeleteActionPerformed(evt);
            }
        });

        txtHDCTnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonMoi.png"))); // NOI18N
        txtHDCTnew.setBorder(null);
        txtHDCTnew.setBorderPainted(false);
        txtHDCTnew.setContentAreaFilled(false);
        txtHDCTnew.setDefaultCapable(false);
        txtHDCTnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHDCTnewActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(55, 38, 91));
        jLabel13.setText("Mã hóa đơn ");

        btnTimHDCT.setText("Tìm");
        btnTimHDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimHDCTActionPerformed(evt);
            }
        });

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bgButtonExcel.png"))); // NOI18N
        jButton13.setBorder(null);
        jButton13.setBorderPainted(false);
        jButton13.setContentAreaFilled(false);
        jButton13.setDefaultCapable(false);
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 92, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton13)
                                .addGap(83, 83, 83))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(170, 170, 170)
                                .addComponent(txtHDCTADD)
                                .addGap(18, 18, 18)
                                .addComponent(txtHDCTUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(txtHDCTDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(txtHDCTnew))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtthanhtien, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel15)
                                    .addComponent(txthdct, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                                    .addComponent(jLabel11)
                                    .addComponent(cboMaSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel16)
                                                .addGap(305, 305, 305))
                                            .addComponent(txtgiaban, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel12)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(65, 65, 65)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtTimKiemHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(btnTimHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cboMaHD, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtsoluongct, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(33, 33, 33))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel10)
                        .addComponent(txtTimKiemHDCT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTimHDCT))
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txthdct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtsoluongct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtthanhtien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtgiaban, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHDCTADD)
                            .addComponent(txtHDCTDelete)
                            .addComponent(txtHDCTnew)
                            .addComponent(txtHDCTUpdate))
                        .addGap(43, 43, 43)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton9)
                            .addComponent(jButton10)
                            .addComponent(jButton11)
                            .addComponent(jButton12)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton13)))
                .addGap(56, 56, 56))
        );

        jTabbedPane1.addTab("Hóa đơn chi tiết", jPanel2);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 69, 1030, 680));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("Quản Lí Hóa Đơn");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked

    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void tblHoaDonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMousePressed
        if (evt.getClickCount() == 2) {
            this.row = tblHoaDon.rowAtPoint(evt.getPoint());//lấy vị trí dòng được chọn
            edit();
            fillTable();
           System.out.println(this.row);
        }     
    }//GEN-LAST:event_tblHoaDonMousePressed

    public boolean checkTrungMa(JTextField txt) {
        txt.setBackground(white);
        if (hdao.selectById(txt.getText()) == null) {
            return true;
        } else {
            txt.setBackground(pink);
            MsgBoxHelper.alert(this,  "Mã hóa đơn đã bị tồn tại.");
            return false;
        }
    }
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
      if (UtilityHelper.checkNullText(txtMaHD)
                && UtilityHelper.checkNullText(txtNgayLap)
                && UtilityHelper.checkNullText(txtMaNV)) {
            if (UtilityHelper.checkMaNH(txtMaHD)
                    
                    && UtilityHelper.checkDate(txtNgayLap)
                    ) {
                if (checkTrungMa(txtMaHD)) {
                    insert();
                }
            }
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
     update();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       delete();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
       clearForm();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtHDCTADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHDCTADDActionPerformed
       if (UtilityHelper.checkNullText(txthdct)
                && UtilityHelper.checkNullText(txtsoluongct)
                
                && UtilityHelper.checkNullText(txtgiaban)) {
             {
                if (checkTrungMaHDCT(txthdct)) {
                    thanhtien();
                    insertHDCT();
                }
            }
        }
        
    }//GEN-LAST:event_txtHDCTADDActionPerformed

    private void txtHDCTUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHDCTUpdateActionPerformed
      updateHDCT();
    }//GEN-LAST:event_txtHDCTUpdateActionPerformed

    private void txtHDCTDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHDCTDeleteActionPerformed
     deleteHDCT();
    }//GEN-LAST:event_txtHDCTDeleteActionPerformed

    private void txtHDCTnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHDCTnewActionPerformed
      clearFormCT();
    }//GEN-LAST:event_txtHDCTnewActionPerformed

    private void cboMaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMaKhachHangActionPerformed

    private void btnTimHDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimHDCTActionPerformed
        // TODO add your handling code here:
        timKiemHDCT();
    }//GEN-LAST:event_btnTimHDCTActionPerformed

    private void btnTimHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimHDActionPerformed
        // TODO add your handling code here:
        timKiemHD();
    }//GEN-LAST:event_btnTimHDActionPerformed

    private void tblHDCTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDCTMousePressed
        if (evt.getClickCount() == 2) {
            this.row = tblHDCT.rowAtPoint(evt.getPoint());//lấy vị trí dòng được chọn
            editHDCT();
            fillTableHDCT();
          System.out.println(this.row);
        }     
    }//GEN-LAST:event_tblHDCTMousePressed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        exportExcel1();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
       exportExcelHoaDon();
    }//GEN-LAST:event_jButton14ActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyHD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyHD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyHD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyHD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyHD dialog = new QuanLyHD(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimHD;
    private javax.swing.JButton btnTimHDCT;
    private javax.swing.JComboBox<String> cboMaHD;
    private javax.swing.JComboBox<String> cboMaKhachHang;
    private javax.swing.JComboBox<String> cboMaSP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblLoiMaHD;
    private javax.swing.JLabel lblLoiNhayLap;
    private javax.swing.JScrollPane splTable;
    private javax.swing.JTable tblHDCT;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JButton txtHDCTADD;
    private javax.swing.JButton txtHDCTDelete;
    private javax.swing.JButton txtHDCTUpdate;
    private javax.swing.JButton txtHDCTnew;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayLap;
    private javax.swing.JTextField txtTimHD;
    private javax.swing.JTextField txtTimKiemHDCT;
    private javax.swing.JTextField txtgiaban;
    private javax.swing.JTextField txthdct;
    private javax.swing.JTextField txtsoluongct;
    private javax.swing.JTextField txtthanhtien;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Dao;
import edu.QLCH.Entity.KhachHang;

import edu.QLCH.Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ASUS
 */
public class KhachHangDao extends EduSysDao<KhachHang, String>{
    public void insert(KhachHang model){
        String sql="INSERT INTO KhachHang(MaKH, TenKH, DiaChi, NgaySinh, Dienthoai, Email) VALUES(?, ?, ?, ?, ?, ?)";
        JdbcHelper.update(sql, 
                model.getMaKH(),
                model.getTenKH(),
                model.getDiaChi(),
                model.getNgaySinh(),
                model.getDienThoai(),
                model.getEmail()); 
    }
    public void update(KhachHang model) {
        String sql="UPDATE KhachHang SET TenKH=?, DiaChi=?, NgaySinh=?, DienThoai=?, Email=? WHERE MaKH=?";
        JdbcHelper.update(sql,
                model.getTenKH(),
                model.getDiaChi(),
                model.getNgaySinh(),
                model.getDienThoai(),
                model.getEmail(),
                model.getMaKH());
    }
    public void delete(String MaKH){
     String sql="DELETE FROM KhachHang WHERE MaKH=?";
     JdbcHelper.update(sql, MaKH);
     }
    
    public List<KhachHang> selectAll(){
        String sql = "SELECT * FROM KhachHang";
        return selectBySql(sql);
    }
    
    public KhachHang selectById(String makh){
        String sql="SELECT * FROM KhachHang WHERE MaKH=?";
        List<KhachHang> list = selectBySql(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    protected List<KhachHang> selectBySql(String sql, Object...args){
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try{
                rs = JdbcHelper.query(sql, args);
                while (rs.next()) {
                    KhachHang entity = new KhachHang();
                    entity.setMaKH(rs.getString("MaKH"));
                    entity.setTenKH(rs.getString("TenKH"));
                    entity.setDiaChi(rs.getString("DiaChi"));
                    entity.setNgaySinh(rs.getDate("NgaySinh"));
                    entity.setDienThoai(rs.getString("DienThoai"));
                    entity.setEmail(rs.getString("Email"));

                    list.add(entity);
                }
            }finally{
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    public List<KhachHang> selectByKhachHang(String MaKH){
        String sql = "SELECT * FROM KhachHang WHERE MaKH=?";
        return this.selectBySql(sql, MaKH);
    }
        public List<KhachHang> selectByKeyword(String keyword){
        String sql="SELECT * FROM KhachHang WHERE MaKH LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
   
}

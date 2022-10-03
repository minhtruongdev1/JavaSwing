/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Dao;


import edu.QLCH.Entity.SanPham;
import edu.QLCH.Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class SanPhamDao {
     public void insert(SanPham model){
        String sql="INSERT INTO SanPham (MaSP, TenSP, MaLoaiSP, Soluong, GiaCa, HinhSP, MoTa) VALUES (?, ?, ?, ?, ?, ?, ? )";
         JdbcHelper.update(sql,
             model.getMaSP(),
             model.getTenSP(),
             model.getMaLoaiSP(),
             model.getSoLuong(),
             model.getGiaCa(),
             model.getHinhSP(),
             model.getMoTa());
             
    }
    
    public void update(SanPham model) {
        String sql="UPDATE SanPham SET TenSP=?, MaLoaiSP=?, Soluong=?, GiaCa=?, HinhSP=?, MoTa=? WHERE MaSP=?";
        JdbcHelper.update(sql,
                 model.getTenSP(),
                 model.getMaLoaiSP(),
                 model.getSoLuong(),
                 model.getGiaCa(),
                 model.getHinhSP(),
                 model.getMoTa(),
                 model.getMaSP());
                 
    }
    
    public void delete(String masp) {
        String sql="DELETE FROM SanPham WHERE MaSP=?";
        JdbcHelper.update(sql, masp);
    }
    
    public List<SanPham> selectAll(){
        String sql="SELECT * FROM SanPham";
        return selectBySql(sql);
    }
    
    public SanPham selectById(String manh){
        String sql="SELECT * FROM SanPham WHERE MaSP=?";
        List<SanPham> list = selectBySql(sql, manh);
        return list.size() > 0 ? list.get(0) : null;
    }
        public List<SanPham> selectAllMaSP(){
        String sql="SELECT * FROM LoaiSP";
        return selectBySql(sql);
    }
     public List<SanPham> selectBySanPham(String masp){
        String sql="SELECT * FROM SanPham WHERE MaSP=?";
        return this.selectBySql(sql, masp);
    }
    protected List<SanPham> selectBySql(String sql, Object...args){
        List<SanPham> list= new ArrayList<>();
        try {
            ResultSet rs = null;
            try{
                rs = JdbcHelper.query(sql, args);
                while(rs.next()){
                    SanPham entity = new SanPham();
                    entity.setMaSP(rs.getString("MaSP"));
                    entity.setTenSP(rs.getString("TenSp"));
                    entity.setMaLoaiSP(rs.getString("MaLoaiSP"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setGiaCa(rs.getFloat("GiaCa"));
                    entity.setHinhSP(rs.getString("HinhSp"));
                    entity.setMoTa(rs.getString("MoTa"));                   
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
    
    public List<SanPham> selectByKeyword(String keyword){
        String sql="SELECT * FROM SanPham WHERE TenSp LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
    
//    public List<SanPham> selectNotInCourse(int makh, String keyword){
//        String sql="SELECT * FROM SanPham WHERE TenSp LIKE ? AND MaSP NOT IN (SELECT MaSP FROM LoaiSanPham WHERE MaSp=?)";
//        return this.selectBySql(sql, "%"+keyword+"%", makh);
//    }
}

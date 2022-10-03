/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Dao;

import edu.QLCH.Entity.HDCT;
import edu.QLCH.Entity.HoaDon;
import edu.QLCH.Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FPT
 */
public class HDCTDAO extends EduSysDao<HDCT, String>{
    public void insert(HDCT model){
        String sql="INSERT INTO HDCT (MaHDCT, MaHD, MaSP, Soluong, GiaBan, ThanhTien ) VALUES(?, ?, ?, ?,?, ?)";
        JdbcHelper.update(sql, 
                model.getMaHDCT(),
                model.getMaHD(),
                model.getMaSP(),
                model.getSoluong(),
                model.getGiaBan(),
                model.getThanhtien());
    }
    public void update(HDCT model) {
        String sql="UPDATE HDCT SET MaHD = ?, MaSP=?, Soluong=?, GiaBan=?, ThanhTien =? WHERE MaHDCT=?";
        JdbcHelper.update(sql,                
                model.getMaHD(),
                model.getMaSP(),
                model.getSoluong(),
                model.getGiaBan(),
                model.getThanhtien(),
                model.getMaHDCT());
    }
    public void delete(String MaHD){
     String sql="DELETE FROM HDCT WHERE MaHDCT=?";
     JdbcHelper.update(sql, MaHD);
     }
    
    public List<HDCT> selectAll(){
        String sql = "SELECT * FROM HDCT";
        return selectBySql(sql);
    }
    
    public HDCT selectById(String mahd){
        String sql="SELECT * FROM HDCT WHERE MaHDCT=?";
        List<HDCT> list = selectBySql(sql, mahd);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    protected List<HDCT> selectBySql(String sql, Object...args){
        List<HDCT> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try{
                rs = JdbcHelper.query(sql, args);
                while (rs.next()) {
                    HDCT entity = new HDCT();
                    entity.setMaHDCT(rs.getString("MaHDCT"));
                    entity.setMaHD(rs.getString("MaHD"));
                    entity.setMaSP(rs.getString("MaSP"));
                    entity.setSoluong(rs.getInt("Soluong"));
                    entity.setGiaBan(rs.getFloat("GiaBan"));
                    entity.setThanhtien(rs.getFloat("ThanhTien"));
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
    public List<HDCT> selectByHoaDon(String MaHDCT){
        String sql = "SELECT * FROM HDCT WHERE MaHDCT=?";
        return this.selectBySql(sql, MaHDCT);
    }
        public List<HDCT> selectByKeyword(String keyword){
        String sql="SELECT * FROM HDCT WHERE MaHDCT LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
}


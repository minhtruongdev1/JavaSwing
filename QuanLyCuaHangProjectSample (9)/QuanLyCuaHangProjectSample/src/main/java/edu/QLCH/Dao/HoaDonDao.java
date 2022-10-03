/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Dao;

import edu.QLCH.Entity.HoaDon;
import edu.QLCH.Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class HoaDonDao extends EduSysDao<HoaDon, String>{
    public void insert(HoaDon model){
        String sql="INSERT INTO HoaDon(MaHD, MaNV, MaKH, NgayBan ) VALUES(?, ?, ?, ?)";
        JdbcHelper.update(sql, 
                model.getMaHD(),
                model.getMaNV(),
                model.getMaKH(),
                model.getNgayTao());
    }
    public void update(HoaDon model) {
        String sql="UPDATE HoaDon SET MaNV=?, MaKH=?, NgayBan=? WHERE MaHD=?";
        JdbcHelper.update(sql,                
                model.getMaNV(),
                model.getMaKH(),
                model.getNgayTao(), 
                model.getMaHD());
    }
    public void delete(String MaHD){
     String sql="DELETE FROM HoaDon WHERE MaHD=?";
     JdbcHelper.update(sql, MaHD);
     }
    
    public List<HoaDon> selectAll(){
        String sql = "SELECT * FROM HoaDon";
        return selectBySql(sql);
    }
    
    public HoaDon selectById(String mahd){
        String sql="SELECT * FROM HoaDon WHERE MaHD=?";
        List<HoaDon> list = selectBySql(sql, mahd);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    protected List<HoaDon> selectBySql(String sql, Object...args){
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try{
                rs = JdbcHelper.query(sql, args);
                while (rs.next()) {
                    HoaDon entity = new HoaDon();
                    entity.setMaHD(rs.getString("MaHD"));
                    entity.setMaKH(rs.getString("MaKH"));
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setNgayTao(rs.getDate("NgayBan"));
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
    public List<HoaDon> selectByHoaDon(String MaHD){
        String sql = "SELECT * FROM HoaDon WHERE MaHD=?";
        return this.selectBySql(sql, MaHD);
    }
        public List<HoaDon> selectByKeyword(String keyword){
        String sql="SELECT * FROM HoaDon WHERE MaHD LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
}

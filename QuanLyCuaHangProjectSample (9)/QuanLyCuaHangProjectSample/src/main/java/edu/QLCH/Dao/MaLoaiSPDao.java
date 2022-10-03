/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Dao;

import edu.QLCH.Entity.LoaiSP;
import edu.QLCH.Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class MaLoaiSPDao extends EduSysDao<LoaiSP, String>{

    public void insert(LoaiSP model){
        String sql="INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP, SoLuong, GiaCa, MoTa) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.update(sql,
        model.getMaLoaiSP(),
        model.getTenLoaiSP(),
        model.getSoLuong(),
        model.getGiaCa(),
        model.getMoTa()
        );
    }
    
    public void update(LoaiSP model){
        String sql="UPDATE LoaiSanPham SET TenLoaiSP=?, SoLuong=?, GiaCa=?, MoTa=? WHERE MaLoaiSP=?";
        JdbcHelper.update(sql, 
        model.getTenLoaiSP(),
        model.getSoLuong(),
        model.getGiaCa(),
        model.getMoTa(),
        model.getMaLoaiSP()
        );
    }
    public void delete(String MaLoaiSP){
        String sql="DELETE FROM LoaiSanPham WHERE MaLoaiSP=?";
        JdbcHelper.update(sql, MaLoaiSP);
    }
    public List<LoaiSP> selectAll(){
        String sql="SELECT * FROM LoaiSanPham";
        return selectBySql(sql);
    }
    public LoaiSP selectById(String maLoaiSP){
        String sql="SELECT * FROM LoaiSanPham WHERE MaLoaiSP=?";
        List<LoaiSP> list = selectBySql(sql, maLoaiSP);
        return list.size() > 0 ? list.get(0) : null;
    }
    protected  List<LoaiSP> selectBySql(String sql, Object...args){
        List<LoaiSP> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try{
                rs = JdbcHelper.query(sql, args);
                while(rs.next()){
                    LoaiSP entity = new LoaiSP();
                    entity.setMaLoaiSP(rs.getString("MaLoaiSP"));
                    entity.setTenLoaiSP(rs.getString("TenLoaiSP"));
                    entity.setSoLuong(rs.getInt("SoLuong"));
                    entity.setGiaCa(rs.getFloat("GiaCa"));
                    entity.setMoTa(rs.getString("MoTa"));
                    list.add(entity);
                }
            }
            finally{
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
            
        }
        return list;
    }
    
}

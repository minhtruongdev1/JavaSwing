/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Dao;

import edu.QLCH.Entity.NhanVien;
import edu.QLCH.Helper.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author ASUS
 */
public class NhanVienDao extends EduSysDao<NhanVien, String>{

    public void insert(NhanVien model){
        String sql="INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro, Gioitinh) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.update(sql, 
                model.getMaNV(),
                model.getMatKhau(),
                model.getHoTen(),
                model.isVaiTro(),
                model.isGioiTinh());
    }
    
    public void update(NhanVien model){
        String sql="UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=?, Gioitinh=? WHERE MaNV=?";
        JdbcHelper.update(sql, 
                model.getMatKhau(),
                model.getHoTen(),
                model.isVaiTro(),
                model.isGioiTinh(),
                model.getMaNV());
    }
    
    public void delete(String MaNV){
        String sql="DELETE FROM NhanVien WHERE MaNV=?";
        JdbcHelper.update(sql, MaNV);
    }
    
    public List<NhanVien> selectAll(){
        String sql = "SELECT * FROM NhanVien";
        return this.selectBySql(sql);
    }
    
    public NhanVien selectById(String manv ){
        String sql="SELECT * FROM NhanVien WHERE MaNV= ?";
        List<NhanVien> list = this.selectBySql(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }
    protected List<NhanVien> selectBySql(String sql, Object...args){
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try{
                rs = JdbcHelper.query(sql, args);
                while (rs.next()) {
                    NhanVien entity = new NhanVien();
                    entity.setMaNV(rs.getString("MaNV"));
                    entity.setMatKhau(rs.getString("MatKhau"));
                    entity.setHoTen(rs.getString("HoTen"));
                    entity.setVaiTro(rs.getBoolean("VaiTro"));
                    entity.setGioiTinh(rs.getBoolean("Gioitinh"));
                    list.add(entity);
                }
            }
            finally{
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return list;
    }
}


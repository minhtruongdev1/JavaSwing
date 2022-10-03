/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Helper;

import edu.QLCH.Dao.getConnection;
import edu.QLCH.Entity.NhanVien;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author TuanDuc
 */
public class DangNhapDAO extends getConnection{
    public String checkLogin(NhanVien nv){
        try {
            String sql = "select * from NhanVien where MaNhanVien = ? and MatKhau = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nv.getMaNV());
            ps.setString(2, nv.getMatKhau());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

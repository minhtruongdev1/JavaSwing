/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Helper;

import edu.QLCH.Dao.getConnection;

import java.awt.Cursor;
import java.awt.Font;
import static java.awt.Frame.HAND_CURSOR;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.design.JRDesignQuery;
//import net.sf.jasperreports.engine.design.JasperDesign;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author TuanDuc
 */
public class DoanhThuTheoNamDAO extends getConnection {
    public int getDoanhThu() {
        try {
            String sql = "select sum(Soluong) from HDCT where Soluong >0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }
    public int getTongNV() {
        try {
            String sql = "select count(*) from NHANVIEN";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }

    public int getTongHK() {
        try {
            String sql = "select count(*) from KhachHang";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }
        public int getTongSP() {
        try {
            String sql = "select count(*) from SanPham";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }

       public int getTongSPDaBan() {
        try {
            String sql = "select sum(Soluong) from HDCT where Soluong >0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }
       public int getTongloaisp() {
        try {
            String sql = "select count(*) from LoaiSanPham";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }
          public int getTongHoaDOn() {
        try {
            String sql = "select count(*) from HoaDon";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }

    public int getTongTien() {
        try {
            int tong = 0;
            String sql = "select sum(ThanhTien) from HDCT where ThanhTien >0";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return 0;
    }

}

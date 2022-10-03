/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Helper;

import edu.QLCH.Entity.NhanVien;

/**
 *
 * @author ASUS
 */
public class AuthHelper {
    public static NhanVien user = null;
    
    public static void clear(){
        AuthHelper.user = null;
    }
    public static boolean isLogin(){
        return AuthHelper.user != null;
    }
    public static boolean isManager(){
        return AuthHelper.isLogin() && user.isVaiTro();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Helper;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;

/**
 *
 * @author ASUS
 */
public class ShareHelper {
       /**
 * Ảnh biểu tượng của ứng dụng, xuất hiện trên mọi cửa sổ
 */
  public static Image getAppIcon(){
        URL url = ShareHelper.class.getResource("/icons/");
        return new ImageIcon(url).getImage();
    }

  /**
   * Sao chep file logo chuyen de vao thu muc logo
   * @param src la doi tuong file anh
   */
  public static boolean save(File src){
      File dst = new File("src\\main\\resources\\logos", src.getName());
      if(!dst.getParentFile().exists()){
          dst.getParentFile().mkdirs();
      }try{
          Path from = Paths.get(src.getAbsolutePath());
          Path to = Paths.get(dst.getAbsolutePath());
          Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
          return true;
      }catch (Exception ex){
          return false;
      }
  }
  
  /**
   * Doc hinh anh logo chuyen de
   * @param filename la ten file logo
   * @return anh doc duoc
   */
  public static ImageIcon read(String fileName){
      File path = new File("src\\main\\resources\\logos", fileName);
      return new ImageIcon(path.getAbsolutePath());
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.QLCH.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class DateHelper {
    static SimpleDateFormat formater = new SimpleDateFormat();
    
    
    public static Date toDate(String date,String pattern){
        try{
            formater.applyPattern(pattern);
            return formater.parse(date);
        }catch(ParseException ex){
            throw new RuntimeException(ex);
        }
    }
    
    public static String toString(Date date,String pattern){
        formater.applyPattern(pattern);
        return formater.format(date);
    }
    
    public static Date now(){
        return new Date();
    }
    
    public static Date addDays(Date date,long days){
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
}

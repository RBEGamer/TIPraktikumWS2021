package de.prodevmo.w0parser;

import java.io.*;
//IMPORT GENERATED PARSER PACKAGE
import de.prodevmo.w0parser.wh0parser;
public class Main {


    public static Boolean run_wh0program(String fpath) {
        System.out.println("LOADED PROGRAM = " + fpath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fpath);
        }catch (Exception e){
            System.out.println("---- WHILE0 PROGRAM ERROR ----");
            System.out.println(e.toString());
            return false;
        }

        //PARSE PROGRAM
        try {
            wh0parser.parse(fis);
            System.out.println("---- WHILE0 PROGRAM OK ----");
            return true;
        }catch (Exception e){
            System.out.println("---- WHILE0 PROGRAM ERROR ----");
            System.out.println(e.toString());
            return false;
        }
    }

    public static void main(String args[]) throws FileNotFoundException, ParseException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String BASE_PATH = "./resources/wh0programs/";

        run_wh0program(BASE_PATH +  "test0.wh0");
        run_wh0program(BASE_PATH +  "test1.wh0");
        run_wh0program(BASE_PATH +  "test2_err.wh0");




    }
}

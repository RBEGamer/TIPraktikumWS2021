package de.prodevmo.w0parser;

import java.io.*;
//IMPORT GENERATED PARSER PACKAGE
import de.prodevmo.w0parser.wh0parser;
public class Main {


    public static void run_wh0program(String fpath) throws FileNotFoundException, ParseException {
        System.out.println("LOADED PROGRAM = " + fpath);
        FileInputStream fis = new FileInputStream(fpath);

        //PARSE PROGRAM
        String parse_result =  wh0parser.parse(fis);
        //CHECK PARSE RESULT
        if(parse_result != null){
            System.out.println("---- WHILE0 PROGRAM ERROR ----");
            System.out.println(parse_result);
        }else{
            System.out.println("---- WHILE0 PROGRAM OK ----");
        }
    }

    public static void main(String args[]) throws FileNotFoundException, ParseException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String BASE_PATH = "./resources/wh0programs/";

        run_wh0program(BASE_PATH +  "test0.wh0");
        run_wh0program(BASE_PATH +  "test1.wh0");

    }
}

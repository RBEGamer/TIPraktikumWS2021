package de.prodevmo.w0parser;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class While0ParserTest {

    @Test
    @DisplayName("while0_noerror")
    public void while0_noerror() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String BASE_PATH = "./resources/wh0programs/";

        try {
            assertTrue(Main.run_wh0program(BASE_PATH +  "test0.wh0"));
            assertTrue(Main.run_wh0program(BASE_PATH +  "test1.wh0"));
        }catch (Exception e){
            fail(e.toString());
        }
    }


}
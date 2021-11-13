package de.prodevmo.w0parser;


import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class While0ParserTest {

    @Test
    @DisplayName("while0_noerror")
    public void while0_noerror() {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String BASE_PATH = "./resources/wh0programs/";

        try {
            final File folder = new File(BASE_PATH);
            Main.listFilesForFolder(folder);

            for (File f : folder.listFiles()) {
                Main.run_wh0program(f.getPath());
            }
        }catch (Exception e){
            fail(e.toString());
        }
    }


}
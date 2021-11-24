package de.prodevmo.w0parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;


public class URMBuilder {


    private String program_name = "";
    private HashMap<String, String> idents_name = new HashMap<String, String>(); // INDET => REGISTER
    private HashMap<String, URMTypeDef.REGTYPE> idents_type = new HashMap<String, URMTypeDef.REGTYPE>();
    private int used_register_index = 0;
    private ArrayList<String> program_lines = new ArrayList<String>();
    final String REGISTER_PREFIX = "R";

    public URMBuilder() {

    }


    public String get_next_free_register() {
        used_register_index++;
        return REGISTER_PREFIX + String.valueOf(used_register_index);
    }


    public void reg_reg(String _name) {
        noex_register_reg(_name, URMTypeDef.REGTYPE.VAR);
    }

    public void reg_reg_in(String _name) {
        noex_register_reg(_name, URMTypeDef.REGTYPE.IN);
    }

    public void reg_reg_out(String _name) {
        noex_register_reg(_name, URMTypeDef.REGTYPE.IN);
    }


    public void noex_register_reg(String _name, URMTypeDef.REGTYPE _type) {
        try {
            register_reg(_name, _type);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void register_reg(String _name, URMTypeDef.REGTYPE _type) throws URMExceptions {
        // TODO CHECK FOR DOUBLE
        if (idents_name.get(_name) != null) {
            throw new URMExceptions("DUPLICATE IDENT DECLARATION");
        }

        if (idents_name.containsKey(_name)) {
            throw new URMExceptions("DUPLICATE IDENT IN OUT DELCARATION");
        }
        /*
        AtomicBoolean dup_in_out = new AtomicBoolean(false);
        idents_type.forEach((key, value) -> {

            if (value.equals(_type) ) {
                if(key == _name){
                    dup_in_out.set(true);
                }
            }
        });

        if(dup_in_out.get()){
            throw new URMExceptions("DUPLICATE IDENT IN OUT DELCARATION");
        }
        */

        idents_name.put(_name, get_next_free_register());
        idents_type.put(_name, _type);

        System.out.println("REGISTER IDENT " + _name + " => " + idents_name.get(_name));
    }


    public void set_program_name(String _name) {
        program_name = _name;
    }

    public void write_urm_to_file(String _basepath) throws URMGeneratorExceptionWriteOut {
        System.out.println("--- WRITE_URM_TO_FILE ---");


        if (program_name == null || program_name == "") {
            throw new URMGeneratorExceptionWriteOut("program_name is not defined");
        }

        //if(program_lines.size() <= 0){
        //    throw new URMGeneratorExceptionWriteOut("program has no content or no lines");
        //}

        if (_basepath == null || _basepath == "") {
            throw new URMGeneratorExceptionWriteOut("output path is empty");
        }

        if (!_basepath.endsWith("/")) {
            _basepath += "/";
        }
        _basepath += program_name.replace(' ', '_').replace('/', (char) 0) + ".urm";
        System.out.println("final pitput path is: " + _basepath);

        try {
            FileOutputStream fos = new FileOutputStream(_basepath);
            final PrintStream printStream = new PrintStream(fos);
            for (String line : program_lines) {
                printStream.println(line);
            }
            printStream.close();

            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new URMGeneratorExceptionWriteOut(e.getMessage());
        }
    }
}

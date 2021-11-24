package de.prodevmo.w0parser;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;


class URMBuilderRegisterDef{




    public String name;
    public URMTypeDef.REGTYPE type;


    public URMBuilderRegisterDef(String _name, URMTypeDef.REGTYPE _type){
        name = _name;
        type = _type;
    }
}

public class URMBuilder {




    private String program_name = "";
    private HashMap<String, URMBuilderRegisterDef > idents = new HashMap<String, URMBuilderRegisterDef >();
    private int used_register_index = 0;
    private ArrayList<String > program_lines = new ArrayList<String >();


    public URMBuilder(){

    }

    public void register_reg(String _name, URMTypeDef.REGTYPE _type){
    idents.put(_name, new URMBuilderRegisterDef(_name,_type));
    }
    public void set_program_name(String _name){
        program_name = _name;
    }

    public void write_urm_to_file(String _basepath) throws URMGeneratorExceptionWriteOut{
        System.out.println("--- WRITE_URM_TO_FILE ---");


        if(program_name == null || program_name == ""){
            throw new URMGeneratorExceptionWriteOut("program_name is not defined");
        }

        if(program_lines.size() <= 0){
            throw new URMGeneratorExceptionWriteOut("program has no content or no lines");
        }

        if(_basepath == null || _basepath == ""){
            throw new URMGeneratorExceptionWriteOut("output path is empty");
        }

        if(!_basepath.endsWith("/")){
            _basepath += "/";
        }
        //_basepath += program_name.replace(' ', '_').replace('/', '') + ".urm";
        System.out.println("final pitput path is: " + _basepath);

        try{
            FileOutputStream fos = new FileOutputStream(_basepath);
            final PrintStream printStream = new PrintStream(fos);
            for (String line : program_lines) {
                printStream.println(line);
            }
            printStream.close();
        }catch (IOException e){
            throw new URMGeneratorExceptionWriteOut(e.getMessage());
        }
    }
}

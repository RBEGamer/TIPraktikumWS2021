package de.prodevmo.w0parser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class URMBuilder {


    private String program_name = "";
    private HashMap<String, String> idents_name = new HashMap<String, String>(); // INDET => REGISTER
    private HashMap<String, URMTypeDef.REGTYPE> idents_type = new HashMap<String, URMTypeDef.REGTYPE>();
    private int used_register_index = 0;
    private int used_helper_register_index = 0;
    private ArrayList<String> program_lines = new ArrayList<String>();
    private Stack<Integer> while_lut = new Stack<Integer>();
    final String REGISTER_PREFIX = "R";


    final String HELPER_IDENT_PREFIX =  "_HELPERREGISTER_";

    public URMBuilder() {

    }


    public String get_next_free_register() {
        used_register_index++;
        return REGISTER_PREFIX + String.valueOf(used_register_index);
    }

    public String get_next_free_helper_register(){
        used_helper_register_index++;
        return HELPER_IDENT_PREFIX + String.valueOf(used_helper_register_index);
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


        idents_name.put(_name, get_next_free_register());
        idents_type.put(_name, _type);

       program_lines.add("; REGISTER IDENT " + _name + " => " + idents_name.get(_name));
    }

    public void insert_comment(String _text){
        program_lines.add("; " + _text);
    }


    public void set_program_name(String _name) {
        program_name = _name;
        program_lines.add("; ###### " + program_name +" ######");
    }


    private Integer get_next_line_number()
    {
        int line_number = 1;
        for(String line : program_lines)
        {
            if(line.startsWith(";"))
                continue;
            else
                line_number++;
        }
        return line_number;

    }

    private void copy_register_value(String xReg, String yReg)
    {
        final String zReg = get_next_free_register();
        int base_line = get_next_line_number();

        program_lines.add(String.format("%s = 0", xReg));
        program_lines.add(String.format("if %s == 0 goto %d", yReg, base_line + 5));
        program_lines.add(String.format("%s--", yReg));
        program_lines.add(String.format("%s++", zReg));
        program_lines.add(String.format("goto %d", base_line + 1));
        program_lines.add(String.format("if %s == 0 goto %d", zReg, base_line + 10)); // base_line + 5
        program_lines.add(String.format("%s--", zReg));
        program_lines.add(String.format("%s++", xReg));
        program_lines.add(String.format("%s++", yReg));
        program_lines.add(String.format("goto %d", base_line + 5));
    }




    public void assignment(String _ident, int _val){
        program_lines.add(String.format("; %s[%s] = %s", _ident, idents_name.get(_ident), String.valueOf(_val)));
        program_lines.add(idents_name.get(_ident) + " = " + _val);
    }

    public void increment(String _ident){
        program_lines.add(String.format("; %s[%s]++", _ident, idents_name.get(_ident)));
        program_lines.add(idents_name.get(_ident) + "++");

    }

    public void decrement(String _ident){
        program_lines.add(String.format("; %s[%s]--", _ident, idents_name.get(_ident)));
        program_lines.add(idents_name.get(_ident) + "--");
    }

    public void assignment(String _l_ident, String _r_ident, int _val){
       final String l_reg = idents_name.get(_l_ident);
       final String r_reg = idents_name.get(_r_ident);
       //REGISTER NEW HELPER REGISTER
       String h_reg = get_next_free_helper_register();
       reg_reg(h_reg);

       program_lines.add(String.format("; COPY %s[%s] => %s[%s]",_l_ident, l_reg, _r_ident,r_reg));

        copy_register_value(idents_name.get(h_reg), r_reg);
        increment(h_reg);
        copy_register_value(l_reg, idents_name.get(h_reg));
    }


    public void while_ne_begin(String _l_ident, String _r_ident){
        while_begin(_l_ident,_r_ident, URMTypeDef.WHILE_TYPE.NOT_EQUAL);
    }

    public void while_begin(String _l_ident, String _r_ident, URMTypeDef.WHILE_TYPE _type){

        program_lines.add(String.format("; WHILE NOT EQUAL WHILE %s[%s] != %s[%s] do begin",_l_ident, idents_name.get(_l_ident), _r_ident,idents_name.get(_l_ident)));
        //REGISTER TWO HELPER REGISTER
        final String l_copy_reg = get_next_free_helper_register();
        final String r_copy_reg = get_next_free_helper_register();
        reg_reg(l_copy_reg);
        reg_reg(r_copy_reg);

        final int copy_base_line = get_next_line_number();
        copy_register_value(idents_name.get(l_copy_reg), idents_name.get(_l_ident));
        copy_register_value(idents_name.get(r_copy_reg), idents_name.get(_r_ident));

        int compare_l_copy_reg = get_next_line_number();
        program_lines.add(String.format("if %s == 0 goto %s", idents_name.get(l_copy_reg), compare_l_copy_reg + 2));
        program_lines.add(String.format("goto %s", compare_l_copy_reg + 4));

        // We only need the index of the program to replace the <?? > with the next command after the while.
        while_lut.push(program_lines.size());
        program_lines.add(String.format("if %s == 0 goto _WHNE_", idents_name.get(r_copy_reg)));
        program_lines.add(String.format("goto %s", compare_l_copy_reg + 8));
        program_lines.add(String.format("if %s == 0 goto %s", idents_name.get(r_copy_reg), compare_l_copy_reg + 8));
        program_lines.add(String.format("%s--", idents_name.get(l_copy_reg)));
        program_lines.add(String.format("%s--", idents_name.get(r_copy_reg)));
        program_lines.add(String.format("goto %d", compare_l_copy_reg));


        while_lut.push(copy_base_line);
    }

    public void while_end() {//throws URMExceptions {
        program_lines.add(String.format("; WHILE END"));
        //REPLACE MARKE
        if(while_lut.size() <= 2){
            //throw new URMExceptions("WHILE NOT BE STARTED");
        }

        final Integer jmp_copy = while_lut.pop();
        final Integer jmp_end_line_index = while_lut.pop();

        program_lines.add("goto " + jmp_copy.toString());


        String while_replace_line = program_lines.get(jmp_end_line_index);
        while_replace_line = while_replace_line.replace("_WHNE_", get_next_line_number().toString());
        program_lines.set(jmp_end_line_index, while_replace_line);
    }


    public void write_urm_to_file(String _basepath) throws URMGeneratorExceptionWriteOut {
        System.out.println("--- WRITE_URM_TO_FILE ---");


        if (program_name == null || program_name == "") {
            throw new URMGeneratorExceptionWriteOut("program_name is not defined");
        }

        //if(program_lines.size() <= 0){
        //    throw new URMGeneratorExceptionWriteOut("program has no content or no lines");
        //}
        program_lines.add("; ##############");

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

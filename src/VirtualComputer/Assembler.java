/*
 *
 * ~~Assembler~~
 * Turning human-made/readable instructions into a sequence of bytes.
 * The instructions can either be just raw numbers given to makeInstruction(),
 * or read from a file in the human-readable syntax.
 *
 * (Probably the largest, most complicated, and most important part of this program)
 *
*/

package VirtualComputer;

import VirtualComputer.Assemble.Instruction;
import VirtualComputer.Assemble.Label;
import VirtualComputer.Assemble.Variable;
import VirtualComputer.Util.Logger;
import VirtualComputer.Util.OutputUser;
import VirtualComputer.Util.TimeKeeper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class Assembler {

  private static final String PREFIX = "[ASSEMBLER]";

  private ArrayList<String> lines = new ArrayList<String>();
  private ArrayList<Instruction> instructions = new ArrayList<Instruction>();
  private ArrayList<Variable> variables = new ArrayList<Variable>();
  private ArrayList<Label> labels = new ArrayList<Label>();

  private final Logger logger;
  private OutputUser outputUser;

  public Assembler() {
    logger = new Logger("Assembler", "Assembler");

    debug("Constructed");
  }

  public void setOutputUser(OutputUser u) { outputUser = u; }
  public OutputUser getOutputUser() { return outputUser; }

  public void printInstrList() {
    for(InstructionSet e : InstructionSet.values()) {
      out(e.getName() + " " + e.getId());
    }
  }

  public Variable findVariable(String s) {
    for(Variable v : variables) {
      if(v.getName().equals(s)) return v;
    }

    return null;
  }

  public boolean hasVariable(String s) {
    if(findVariable(s) == null) return false;
    else return true;
  }

  public int makeVariables() {
    return variables.size();
  }

  public Label getLabel(String n) {
    for(Label l : labels) {
      if(n.equals(l.getName())) {
        return l;
      }
    }

    return null;
  }

  public boolean hasLabel(String n) {
    if(getLabel(n) == null) return false;
    else return true;
  }

  public boolean assemble(String fileName) {
    return assemble( new File(fileName) );
  }

  public boolean assemble(File f) {
    out("Assembling " + f.getName());

    //Read the file

    out("Reading file...");
    lines = readFile(f);

    if(lines == null) {
      out("Error on reading file");
      return false;
    }
    out("File read successfully");

    //Perform initial encode, basically "pre-process"

    out("Beginning initial encode of instructions...");
    for(int i=0; i < lines.size(); i++) {
      String line = lines.get(i);
      Instruction instr = encode(line);

      if(instr == null) {
        continue;
      }
      else if(instr.isBad()) {
        out("ERROR ON LINE " + (i + 1) + ": Invalid Instruction");
        return false;
      }
      else {
        instr.setLineNumber(i + 1);
        instructions.add(instr);
        debug(instr.toString());
      }
    }

    out("Made " + instructions.size() + " instruction" + ( instructions.size() > 1 ? "s" : "" ) );

    //Assemble the code

    out("Beginning assembly process...");

    for(int i = 0; i < instructions.size(); i++) {
      Instruction instr = instructions.get(i);
      //Basics
      if( instr.getCode() == InstructionSet.EXIT.getId() ) {
        //I don't think anything needs to be done..
      }
      else if( instr.getCode() == InstructionSet.DISPLOC.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": DISPLOC has wrong number of arguments");
          return false;
        }

        if(instr.getParts()[1].equals("register")) {
          instr.setParam1(-1);
        }
        else if( hasVariable( instr.getParts()[1] ) ) {
          Variable v = findVariable( instr.getParts()[1] );
          instr.setParam1( v.getLoc() );
        }
        else {
          out("Line " + instr.getLineNumber() + ": DISPLOC given invalid variable/location");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.DISPVAL.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": DISPVAL has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.ADD.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": ADD has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.SUBTRACT.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": SUBTRACT has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.MULTIPLY.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": MULTIPLY has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.DIVIDE.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": DIVIDE has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.POW.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": POW has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.MOD.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": MOD has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.LSHIFT.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": LSHIFT has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.RSHIFT.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": RSHIFT has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.AND.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": AND has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.OR.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": OR has wrong number of arguments");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.XOR.getId() ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": XOR has wrong number of arguments");
          return false;
        }

      }

      //Data Manipulation
      else if( instr.getCode() == InstructionSet.PUT.getId() ) {

        if(instr.getParts().length < 3) {
          out("Line " + instr.getLineNumber() + ": PUT has wrong number of arguments");
          return false;
        }

        //Find where data goes
        if(instr.getParts()[2].equals("register")) {
          instr.setParam2(-1);
        }
        else if( hasVariable( instr.getParts()[2] ) ) {
          Variable var = findVariable( instr.getParts()[2] );
          instr.setParam2( var.getLoc() );
        }
        else {
          out("Line " + instr.getLineNumber() + ": PUT does not appear to specify valid location");
          return false;
        }

        if( !canParseInt( instr.getParts()[1] ) ) {
          out("Line " + instr.getLineNumber() + ": argument one is not an int");
          return false;
        }

      }

      else if( instr.getCode() == InstructionSet.MOV.getId() ) {

        if(instr.getParts().length < 3) {
          out("Line " + instr.getLineNumber() + ": MOV has wrong number of arguments");
          return false;
        }

        //Parameter 1 Variable Handling
        if(instr.getParts()[1].equals("register")) {
          instr.setParam1(-1);
        }
        else if( hasVariable( instr.getParts()[1] ) ) {
          Variable var = findVariable( instr.getParts()[1] );
          instr.setParam1( var.getLoc() );
        }
        else {
          out("Line " + instr.getLineNumber() + ": MOV does not appear to specify valid location");
          return false;
        }

        //Parameter 1 Variable Handling
        if(instr.getParts()[2].equals("register")) {
          instr.setParam2(-1);
        }
        else if( hasVariable( instr.getParts()[2] ) ) {
          Variable var = findVariable( instr.getParts()[2] );
          instr.setParam2( var.getLoc() );
        }
        else {
          out("Line " + instr.getLineNumber() + ": MOV does not appear to specify valid location");
          return false;
        }

      }

      else if(instr.getCode() == InstructionSet.DATA.getId()) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": DATA has wrong number of arguments");
          return false;
        }

        Variable v = new Variable();
        v.setName(instr.getParts()[1]);
        v.setLoc(variables.size() * 4);
        variables.add(v);
        instructions.remove(i);
        i--;

      }

      else if(instr.getCode() == InstructionSet.LABEL.getId()) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": LABEL has wrong number of arguments");
          return false;
        }

        Label l = new Label( instr.getParts()[1], i );
        labels.add( l );
        instructions.remove(i);
        i--;

      }

      else if(instr.getCode() == InstructionSet.GOTO.getId()) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": GOTO has wrong number of arguments");
          return false;
        }

        /*
        if( !hasLabel(instr.getParts()[1]) ) {
          out("Line " + instr.getLineNumber() + ": Label not found");
          return false;
        }
        */

      }

      else if(instr.getCode() == InstructionSet.CGOTO.getId()) {

        if(instr.getParts().length < 3) {
          out("Line " + instr.getLineNumber() + ": CGOTO has wrong number of arguments");
          return false;
        }

        if( hasVariable( instr.getParts()[1] ) ) {
          Variable var = findVariable( instr.getParts()[1] );
          instr.setParam1( var.getLoc() );
        }

        /*
        if( !hasLabel(instr.getParts()[2]) ) {
          out("Line " + instr.getLineNumber() + ": Label not found");
          return false;
        }
        */

      }

      //Conditions

      else if(instr.getCode() == InstructionSet.EQ.getId()
           || instr.getCode() == InstructionSet.GREQ.getId()
           || instr.getCode() == InstructionSet.LEAQ.getId()
           || instr.getCode() == InstructionSet.GREATER.getId()
           || instr.getCode() == InstructionSet.LESS.getId()
      ) {

        if(instr.getParts().length < 3) {
          out("Line " + instr.getLineNumber() + ": conditional has wrong number of arguments");
          return false;
        }

        if( hasVariable( instr.getParts()[2] ) ) {
          Variable var = findVariable( instr.getParts()[2] );
          instr.setParam2( var.getLoc() );
        }
        else {
          out("Line " + instr.getLineNumber() + ": variable does not exist");
          return false;
        }

      }

      else if(instr.getCode() == InstructionSet.EQV.getId()
           || instr.getCode() == InstructionSet.GREQV.getId()
           || instr.getCode() == InstructionSet.LEAQV.getId()
           || instr.getCode() == InstructionSet.GREATERV.getId()
           || instr.getCode() == InstructionSet.LESSV.getId()
      ) {

        if(instr.getParts().length < 3) {
          out("Line " + instr.getLineNumber() + ": conditional has wrong number of arguments");
          return false;
        }

        if( hasVariable( instr.getParts()[1] ) ) {
          Variable var = findVariable( instr.getParts()[1] );
          instr.setParam1( var.getLoc() );
        }
        else {
          out("Line " + instr.getLineNumber() + ": variable does not exist");
          return false;
        }

        if( hasVariable( instr.getParts()[2] ) ) {
          Variable var = findVariable( instr.getParts()[2] );
          instr.setParam2( var.getLoc() );
        }
        else {
          out("Line " + instr.getLineNumber() + ": variable does not exist");
          return false;
        }

      }

      //Math with variables

      else if(instr.getCode() == InstructionSet.ADDV.getId()
           || instr.getCode() == InstructionSet.SUBTRACTV.getId()
           || instr.getCode() == InstructionSet.MULTIPLYV.getId()
           || instr.getCode() == InstructionSet.DIVIDEV.getId()
           || instr.getCode() == InstructionSet.POWV.getId()
           || instr.getCode() == InstructionSet.MODV.getId()
      ) {

        if(instr.getParts().length < 2) {
          out("Line " + instr.getLineNumber() + ": math with variable has wrong number of arguments");
          return false;
        }

        if( hasVariable( instr.getParts()[1] ) ) {
          Variable var = findVariable( instr.getParts()[1] );
          instr.setParam1( var.getLoc() );
        }
        else {
          out("Line " + instr.getLineNumber() + ": variable does not exist");
          return false;
        }

      }


      //If all else fails, somehow...
      else {
        out("Line " + instr.getLineNumber() + ": Instruction not understood");
        return false;
      }

    }

    out("Setting locations...");

    int varSize = variables.size() * 4;
    int instrSize = instructions.size() * InstructionSet.getInstructionSize();

    debug("Setting locations for variables...");

    //Set variable locations
    for(int i=0; i<variables.size(); i++) {
      variables.get(i).setLoc( instrSize + i*4 );
      debug( variables.get(i).toString() );
    }

    debug("Adjusting instructions that use variables...");

    //All instructions that use variables must be adjusted here
    //(unless there are circumstances which have yet to be brought to my attention)
    for(int i=0; i<instructions.size(); i++) {
      Instruction instr = instructions.get(i);

      if(instr.getCode() == InstructionSet.MOV.getId()) {
        if(instr.getParam1() > -1) {
          instr.setParam1( instr.getParam1() + instrSize );
        }
        if(instr.getParam2() > -1) {
          instr.setParam2( instr.getParam2() + instrSize );
        }
      }

      else if(instr.getCode() == InstructionSet.PUT.getId()) {
        if(instr.getParam2() > -1) {
          instr.setParam2( instr.getParam2() + instrSize );
        }
      }

      else if(instr.getCode() == InstructionSet.CGOTO.getId()) {
        instr.setParam1( instr.getParam1() + instrSize );
      }

      else if(instr.getCode() == InstructionSet.DISPLOC.getId()) {
        if(instr.getParam1() > -1) {
          instr.setParam1( instr.getParam1() + instrSize );
        }
      }

      else if(instr.getCode() == InstructionSet.EQ.getId()
           || instr.getCode() == InstructionSet.GREQ.getId()
           || instr.getCode() == InstructionSet.LEAQ.getId()
           || instr.getCode() == InstructionSet.GREATER.getId()
           || instr.getCode() == InstructionSet.LESS.getId()
      ) {
        instr.setParam2( instr.getParam2() + instrSize );
      }

      else if(instr.getCode() == InstructionSet.EQV.getId()
           || instr.getCode() == InstructionSet.GREQV.getId()
           || instr.getCode() == InstructionSet.LEAQV.getId()
           || instr.getCode() == InstructionSet.GREATERV.getId()
           || instr.getCode() == InstructionSet.LESSV.getId()
      ) {
        instr.setParam1( instr.getParam1() + instrSize );
        instr.setParam2( instr.getParam2() + instrSize );
      }

      else if(instr.getCode() == InstructionSet.ADDV.getId()
           || instr.getCode() == InstructionSet.SUBTRACTV.getId()
           || instr.getCode() == InstructionSet.MULTIPLYV.getId()
           || instr.getCode() == InstructionSet.DIVIDEV.getId()
           || instr.getCode() == InstructionSet.POWV.getId()
           || instr.getCode() == InstructionSet.MODV.getId()
      ) {
        instr.setParam1( instr.getParam1() + instrSize );
      }

    }

    debug("Performing set location pass...");

    //Go over all instructions and set the locations of the next instruction
    //(potentially depending on a conditional, in the case of CGOTO)
    for(int i=0; i<instructions.size(); i++) {
      Instruction instr = instructions.get(i);

      // GOTO
      if(instr.getCode() == InstructionSet.GOTO.getId()) {
        Label l = getLabel(instr.getParts()[1]);
        if(l == null) {
          out("Line " + instr.getLineNumber() + ": Label not found");
          return false;
        }
        instr.setNextInstrLoc( l.getPos() * InstructionSet.getInstructionSize() );
      }

      // CGOTO Conditional Goto
      else if(instr.getCode() == InstructionSet.CGOTO.getId()) {
        Label l = getLabel(instr.getParts()[2]);
        if(l == null) {
          out("Line " + instr.getLineNumber() + ": Label not found");
          return false;
        }
        instr.setParam2( l.getPos() * InstructionSet.getInstructionSize() );
        instr.setNextInstrLoc( (i+1) * InstructionSet.getInstructionSize() );
      }

      // Default
      else {
        instr.setNextInstrLoc( (i+1) * InstructionSet.getInstructionSize() );
      }

    }

    //Assembly process finished

    out("Finished assembling");
    out( variables.size() + " variables, " + instructions.size() + " instructions");

    //Pack into bytes and output to file

    byte[] output = new byte[instrSize];
    out(output.length + " bytes");

    out("Merging bytes...");

    //Copy the bytes from each instruction
    for(int i=0; i<instructions.size(); i++) {
      Instruction instr = instructions.get(i);
      debug(instr.toString());

      byte[] instrData = instr.getBytes();

      for(int j = 0; j < instrData.length; j++ ) {
        //Variable offset, instruction start, current spot
        int k = i * 13 + j;
        output[k] = instrData[j];
      }

    }

    out("Writing to file...");

    //It's all on you: Write it to the file
    FileOutputStream outStream = null;
    try{
      outStream = new FileOutputStream( new File("run.vbin") );
      outStream.write(output);
    }catch(Exception e) {
      out("SEVERE ERROR: Could not write to output file");
      return false;
    }

    try{
      if(outStream != null) {
        outStream.close();
      }
    }catch(Exception e) {
      out("Error: Could not close file (unsure of what this implies)");
    }

    //yay, it worked!
    out("Finished.");
    return true;
  }

  public Instruction encode(String line) {
    debug("Encoding \"" + line + "\"");
    line = line.trim();

    //If line contains a comment, remove the comment
    if(line.contains(";")) {
      debug("Line contains comment, removing...");
      int loc = line.indexOf(";");
      line = line.substring(0, loc);
      debug("Line now says \""  + line + "\"");
    }

    String[] parts = line.split(" ");
    Instruction instr = null;

    for(int i=0; i<parts.length; i++) {
      parts[i] = parts[i].trim();
    }

    //Empty
    if( parts.length < 1 || parts[0].equals("") ) {
      debug("Empty line");
      return instr;
    }

    boolean made = false;
    for(InstructionSet e : InstructionSet.values()) {
      if(parts[0].equalsIgnoreCase(e.getName())) {
        instr = new Instruction();
        instr.setCode( e.getId() );
        instr.setLine(line);
        instr.setParts(parts);
        made = true;
        debug("Made instruction code " + instr.getCode());
      }
    }

    if(!made) {
      debug("Did not match valid instruction");
      return new Instruction(true);
    }

    int param1, param2;

    if( parts.length > 1 && canParseInt(parts[1]) ) {
      param1 = parseInt(parts[1]);
      instr.setParam1(param1);
    }

    if( parts.length > 2 && canParseInt(parts[2]) ) {
      param2 = parseInt(parts[2]);
      instr.setParam2(param2);
    }

    return instr;
  }

  public ArrayList<String> readFile(File f) {
    BufferedReader input;
    try{
      input = new BufferedReader(new FileReader(f));
    }catch(Exception e) {
      out("Could not open file");
      return null;
    }

    ArrayList<String> toRet = new ArrayList<String>();
    String line;

    try{
      while( (line = input.readLine()) != null) {
        toRet.add(line);
      }
    }catch(Exception e) {
      out("Error during read");
      return null;
    }

    out("Read " + toRet.size() + " lines");

    try{
      input.close();
    }catch(Exception e) {
      //Not sure if anything should be done??
      out("Error on close. Ignoring...");
      return toRet;
    }

    return toRet;
  }

  public static byte[] makeInstruction(byte code, int param1, int param2, int next) {
    byte[] toRet = new byte[InstructionSet.getInstructionSize()];

    toRet[0] = code;

    byte[] param1b = intToBytes(param1);
    byte[] param2b = intToBytes(param2);
    byte[] nextb = intToBytes(next);

    for(int i = 1; i < 5; i++) {
      toRet[i] = param1b[i-1];
    }

    for(int i = 5; i < 9; i++) {
      toRet[i] = param2b[i-5];
    }

    for(int i = 9; i < 13; i++) {
      toRet[i] = nextb[i-9];
    }

    return toRet;

  }

  public static byte[] intToBytes(int num) {
    byte[] toRet = new byte[4];

    for(int i = 0; i < 4; i++) {
      toRet[3-i] = (byte) ( num & 0xff );
      num = num >> 8;
    }

    return toRet;
  }

  public static int bytesToInt(byte[] bytes, int begin, int end) {
    int toRet = 0;
    int current;

    for(int i = begin; i < end; i++) {
      toRet = toRet << 8;
      current = ( (int) bytes[i] ) & 0xff;
      toRet = toRet | current;
    }

    return toRet;
  }

  public boolean canParseInt(String s) {
    try{
      Integer.parseInt(s);
      return true;
    }catch(Exception e) {
      //...
    }
    return false;
  }

  public int parseInt(String s) {
    try{
      return Integer.parseInt(s);
    }catch(Exception e) {
      //...
    }
    return 0;
  }

  public void out(String message) {
    System.out.println(PREFIX + ": " + message);
    if(logger != null) logger.log(PREFIX + ": " + message);
    if(outputUser != null) outputUser.inputString(PREFIX + ": " + message);
  }

  public void debug(String message) {
    if(logger != null) logger.log("[DEBUG] " + PREFIX + ": " + message);
    if(outputUser != null) outputUser.inputDebug(PREFIX + ": " + message);
  }

  public static void main(String[] args) {
    String filename = "main.vasm";

    if(args != null && args.length > 0) {
      filename = args[0] + ".vasm";
    }

    Assembler assembler = new Assembler();

    TimeKeeper time = new TimeKeeper();

    assembler.assemble(filename);

    time.stop();

    double elapsed = time.getElapsedInSeconds();

    System.out.println("Assembling took " + elapsed + " seconds");
  }

}

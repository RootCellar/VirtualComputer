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

import VirtualComputer.InstructionSet;
import VirtualComputer.Util.*;
import VirtualComputer.Assemble.*;

import java.util.ArrayList;
import java.io.*;

public class Assembler {

  private static final String PREFIX = "[ASSEMBLER]";

  private ArrayList<String> lines = new ArrayList<String>();
  private ArrayList<Instruction> instructions = new ArrayList<Instruction>();
  private Logger toLog;

  Assembler() {
    toLog = new Logger("Assembler", "Assembler");

    debug("Constructed");
  }

  public boolean assemble(File f) {
    out("Assembling " + f.getName());

    lines = readFile(f);

    if(lines == null) {
      out("Error on reading file");
      return false;
    }

    for(String line : lines) {
      Instruction instr = encode(line);

      if(instr != null) {
        instructions.add(instr);
      }
    }

    out("Made " + instructions.size() + " instructions");

    //yay, it worked!
    out("Finished");
    return true;
  }

  public Instruction encode(String line) {
    String[] parts = line.split(" ");
    Instruction instr = null;

    if(parts.length < 1) return instr;

    for(InstructionSet e : InstructionSet.values()) {
      if(parts[0] == e.getName()) {
        instr = new Instruction();
        instr.setCode( e.getId() );
      }
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
      out("Error on close. Circumventing...");
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

  public void out(String s) {
    System.out.println(PREFIX + ": " + s);
    if(toLog != null) toLog.log(PREFIX + ": " + s);
  }

  public void debug(String s) {
    if(toLog != null) toLog.log("[DEBUG] " + PREFIX + ": " + s);
  }

  public static void main(String[] args) {
    String filename = "main.vasm";

    if(args != null && args.length > 0) {
      filename = args[0];
    }

    Assembler assembler = new Assembler();

    assembler.assemble(new File(filename));
  }

}

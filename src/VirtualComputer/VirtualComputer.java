/*
 * RootCellar (github.com/RootCellar)
 * 12/07/2020
 * VirtualComputer.java
 *
 * Creating virtual hardware in hopes of emulating a machine
 *
 *
*/

package VirtualComputer;

import java.io.*;

import VirtualComputer.Hardware.*;
import VirtualComputer.Util.*;
import VirtualComputer.GUI.*;

public class VirtualComputer implements Runnable, OutputUser {

  //Static
  private static boolean debug = false;
  private static boolean usingGUI = true;

  //Regular members
  private Thread thread;
  private int ticksPerSecond = 60;
  private boolean going = false;
  private MainGUI gui;

  public VirtualComputer() {

  }

  //Main method to simulate the entire computer
  //TODO: Implement
  public void run() {

    while(going) {

    }

    going = false;
  }

  private void start() {
    thread = new Thread(this);
    thread.start();
  }

  public void inputString(String s) {
    if(gui != null) gui.out(s);
  }

  public void inputDebug(String s) {

  }

  private static void out(String s) {
    System.out.println("[CONTROLLER] " + s);
  }

  private static void debug(String s) {
    if(debug) System.out.println("[DEBUG] [CONTROLLER] " + s);
  }

  public static void main(String[] args) {

    MainGUI tgui = null;

    //Handle Arguments
    for(String s : args) {

      if(s.equals("-debug")) {
        debug = true;
      }

      if(s.equals("-nogui")) {
        usingGUI = false;
      }

    }

    if(usingGUI) {
      tgui = new MainGUI();
      //tgui.setUser(this);
    }

    //Motherboard Setup
    debug("Setting up motherboard...");
    Motherboard motherboard = new Motherboard(debug);

    //CPU Setup
    debug("Setting up CPU...");
    CPU processor = new CPU(motherboard);

    //RAM Setup
    debug("Setting up RAM...");
    RAM memory = new RAM(motherboard, 1024 * 1024 * 1);

    //Finish setup with other procedures
    debug("Completing setup...");

    if(usingGUI && tgui != null) {
      motherboard.setOutputHandler(tgui);
    }

    SimulatedObject.setSimDebugMode(debug);

    //Test Statements
    //These are used to set some test info up, ie maybe a basic program

    //Test bytesToInt()
    /*
    byte[] bytes = {-128, -1, -1, -1};
    int ans = processor.bytesToInt(bytes, 0, bytes.length);
    debug("ANS: " + ans);
    */

    //Test the RAM
    /*

    //Should NEVER work
    memory.writeByte(-1, 1);
    memory.readBytes(-1, 6);

    //Should not work with memory size 1024
    memory.readBytes(1020, 6);
    memory.writeByte(1025, 6);

    //Should work (memory size 1024)
    memory.readBytes(0, 4);
    memory.writeByte(0, 1);
    memory.writeByte(1, 2);

    */

    //Code Injection
    //This little block is used to test some basic programs on the CPU to make sure
    //everything works
    //(it's also hacking)

    //Assembler.makeInstruction( instruction code, parameter1, parameter2, location of next instruction )

    ///*

    byte[] fileData = new byte[1024 * 1024 * 4];

    try{
      FileInputStream in = new FileInputStream("run.vbin");
      in.read(fileData);
      in.close();
    }catch(Exception e) {
      out("Exception on file read");
    }

    memory.writeBytes(0, fileData);
    //*/

    /*

    byte[] instr = Assembler.makeInstruction(InstructionSet.ADD.getId(), 9, 0, 13);
    memory.writeBytes(0, instr);

    instr = Assembler.makeInstruction(InstructionSet.MOD.getId(), 2, 0, 26);
    memory.writeBytes(13, instr);

    instr = Assembler.makeInstruction(InstructionSet.DISPLOC.getId(), -1, 0, 0);
    memory.writeBytes(26, instr);

    instr = Assembler.makeInstruction(InstructionSet.EXIT.getId(), 2, 0, 0);
    memory.writeBytes(39, instr);

    */

    processor.setClockRate(5);

    //End test statements

    //Run the simulation
    debug("Beginning simulation...");
    while(true) {
      motherboard.simulate();
      TimeKeeper.sleep(1);

      if(tgui != null) {
        tgui.label.setText("Register: " + processor.getRegister());
        tgui.label2.setText("nextInstructionLoc: " + processor.getNextInstructionLoc());
      }

      //What if the program ends?
      if(!processor.isExecuting()) {
        debug("Processor has stopped execution. Quitting...");
        return;
      }

    }

  }

}

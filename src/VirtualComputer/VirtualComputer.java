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

import VirtualComputer.Hardware.*;
import VirtualComputer.Util.*;

public class VirtualComputer implements Runnable {

  //Static
  private static boolean debug = false;

  //Regular members
  private Thread thread;
  private int ticksPerSecond = 60;
  private boolean going = false;

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

  private static void out(String s) {
    System.out.println("[CONTROLLER] " + s);
  }

  private static void debug(String s) {
    if(debug) System.out.println("[DEBUG] [CONTROLLER] " + s);
  }

  public static void main(String[] args) {

    //Handle Arguments
    for(String s : args) {

      if(s.equals("-debug")) {
        debug = true;
      }

    }

    //Motherboard Setup
    debug("Setting up motherboard...");
    Motherboard motherboard = new Motherboard(debug);

    //CPU Setup
    debug("Setting up CPU...");
    CPU processor = new CPU(motherboard);

    //RAM Setup
    debug("Setting up RAM...");
    RAM memory = new RAM(motherboard, 1024);

    //Finish setup with other procedures
    debug("Completing setup...");

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

    processor.setClockRate(3);

    //Assembler.makeInstruction( instruction code, parameter1, parameter2, location of next instruction )

    byte[] instr = Assembler.makeInstruction(InstructionSet.ADD.getId(), 9, 0, 13);
    memory.writeBytes(0, instr);

    instr = Assembler.makeInstruction(InstructionSet.MOD.getId(), 2, 0, 26);
    memory.writeBytes(13, instr);

    instr = Assembler.makeInstruction(InstructionSet.DISPLOC.getId(), -1, 0, 0);
    memory.writeBytes(26, instr);

    instr = Assembler.makeInstruction(InstructionSet.EXIT.getId(), 2, 0, 0);
    memory.writeBytes(39, instr);

    //End test statements

    //Run the simulation
    debug("Beginning simulation...");
    while(true) {
      motherboard.simulate();
      TimeKeeper.sleep(1);

      //What if the program ends?
      if(!processor.isExecuting()) {
        debug("Processor has stopped execution. Quitting...");
        return;
      }

    }

  }

}

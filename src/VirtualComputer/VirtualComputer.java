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

    //End test statements

    //Run the simulation
    debug("Beginning simulation...");
    while(true) {
      motherboard.simulate();
      //processor.simulate();
      TimeKeeper.sleep(1);
    }

  }

}

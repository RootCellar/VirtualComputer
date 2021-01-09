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

    for(String s : args) {

      if(s.equals("-debug")) {
        debug = true;
      }

    }

    debug("Setting up motherboard...");

    Motherboard motherboard = new Motherboard(debug);

    debug("Setting up CPU...");

    CPU processor = new CPU(motherboard);

    debug("Setting up RAM...");

    RAM memory = new RAM(motherboard);

    debug("Completing setup...");

    SimulatedObject.setSimDebugMode(debug);

    debug("Beginning simulation...");

    while(true) {
      motherboard.simulate();
      processor.simulate();
      TimeKeeper.sleep(1);
    }

  }

}

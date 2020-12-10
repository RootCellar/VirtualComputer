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

  public static void main(String[] args) {

    Motherboard motherboard = new Motherboard(true);

    CPU processor = new CPU(motherboard);

    RAM memory = new RAM(motherboard);

    SimulatedObject.setSimDebugMode(true);

    while(true) {
      motherboard.simulate();
      processor.simulate();
      TimeKeeper.sleep(1);
    }

  }

}

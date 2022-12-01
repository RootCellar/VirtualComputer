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
    int cpuRate = 5;

    boolean mbLogging = true;

    //Handle Arguments
    for(String s : args) {

      if(s.equals("-debug")) {
        debug = true;
      }
      else if(s.equals("-nogui")) {
        usingGUI = false;
      }
      else if(s.equals("-nolog")) {
        mbLogging = false;
      }
      else {
        try{
          cpuRate = Integer.parseInt(s);
        }catch(Exception e) {}
      }

    }

    if(usingGUI) {
      try {
        tgui = new MainGUI();
      } catch(Exception e) {
        out("Creating the GUI failed, running without GUI...");
      }
      //tgui.setUser(this);
    }

    //Motherboard Setup
    debug("Setting up motherboard...");
    Motherboard motherboard = new Motherboard(debug);
    if(!mbLogging) motherboard.disableLogging();

    //CPU Setup
    debug("Setting up CPU...");
    CPU processor = new CPU(motherboard);
    processor.setClockRate(cpuRate);

    //RAM Setup
    debug("Setting up RAM...");
    RAM memory = new RAM(motherboard, 1024 * 1024 * 8);

    //Finish setup with other procedures
    debug("Completing setup...");

    if(usingGUI && tgui != null) {
      motherboard.setOutputHandler(tgui);
    }

    SimulatedObject.setSimDebugMode(debug);

    //Possible code injection can be done this way:
    //Assembler.makeInstruction( instruction code, parameter1, parameter2, location of next instruction )

    //This section reads the code from a file and puts it into the RAM
    //TODO: Make this better, and probably a method

    debug("Loading the program into the RAM...");

    byte[] fileData = new byte[1024 * 1024 * 4];

    try{
      FileInputStream in = new FileInputStream("run.vbin");
      in.read(fileData);
      in.close();
    }catch(Exception e) {
      out("Exception on file read");
    }

    memory.writeBytes(0, fileData);

    //Run the simulation
    debug("Beginning simulation...");
    while(true) {

      //Simulate
      motherboard.simulate();
      TimeKeeper.sleep(50);

      //GUI
      if(tgui != null) {
        tgui.label.setText("Register: " + processor.getRegister());
        tgui.label2.setText("nextInstructionLoc: " + processor.getNextInstructionLoc());
        tgui.label3.setText("CPU clock rate: " + processor.getClockRate() + " hz");
      }

      //What if the program ends?
      if(!processor.isExecuting()) {
        debug("Processor has stopped execution. Quitting...");
        return;
      }

    }

  }

}

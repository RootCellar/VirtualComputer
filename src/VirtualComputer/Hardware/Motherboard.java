package VirtualComputer.Hardware;

import VirtualComputer.Util.*;

public class Motherboard extends SimulatedObject {

  private String PREFIX = "[MOTHERBOARD]";
  private boolean DEBUG = false;
  private boolean going = false;
  private Thread thread;

  public Motherboard() {
    debug("Constructing...");

    setup();
  }

  public Motherboard(boolean b) {
    DEBUG = b;
    debug("Constructing...");

    setup();
  }

  private void setup() {
    setTicksPerSecond(50);
    setObjectName("Motherboard");
  }

  public boolean isDebugMode() { return DEBUG; }
  public void setDebugMode(boolean b) {
    DEBUG = b;
  }

  //Tick the entire computer
  public void tick() {

  }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
  }

  private void debug(String n) {
    if(DEBUG) System.out.println("[DEBUG] " + PREFIX + " " + n);
  }

}

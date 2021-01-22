package VirtualComputer.Hardware;

import VirtualComputer.Util.*;

public class Motherboard extends SimulatedObject {

  private String PREFIX = "[MOTHERBOARD]";
  private boolean DEBUG = false;
  private boolean going = false;
  private Thread thread;
  private Logger toLog;

  private CPU cpu;
  private RAM ram;

  public Motherboard() {
    debug("Constructing...");

    setup();
  }

  public Motherboard(boolean b) {
    DEBUG = b;
    debug("Constructing...");

    setup();
  }

  public void setCPU(CPU c) {
    cpu = c;

    cpu.activate();
  }

  public void setRAM(RAM r) {
    ram = r;
  }

  public CPU getCPU() { return cpu; }
  public RAM getRAM() { return ram; }

  public boolean hasRAM() { return ram != null; }
  public boolean hasCPU() { return cpu != null; }

  private void setup() {
    toLog = new Logger("Motherboard", "Motherboard");
    setTicksPerSecond(50);
    setObjectName("Motherboard");
  }

  public boolean isDebugMode() { return DEBUG; }
  public void setDebugMode(boolean b) {
    DEBUG = b;
  }

  //Tick the entire computer
  public void tick() {

    if(!hasRAM()) {
      error("SEVERE: DO NOT HAVE RAM");
      return;
    }

    if(!hasCPU()) {
      error("SEVERE: DO NOT HAVE CPU");
      return;
    }

    cpu.simulate();

  }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
  }

  public void debug(String n) {
    if(DEBUG) System.out.println("[DEBUG] " + PREFIX + " " + n);
    if(toLog!=null) toLog.log("[DEBUG] " + PREFIX + " " + n);
  }

  public void error(String n) {
    System.out.println("[ERROR] " + PREFIX + " " + n);
    if(toLog!=null) toLog.log("[DEBUG] " + PREFIX + " " + n);
  }

  public void verbose(String n) {
    if(toLog!=null) toLog.log("[VERBOSE] " + PREFIX + " " + n);
  }

}

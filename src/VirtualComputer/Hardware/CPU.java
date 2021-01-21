package VirtualComputer.Hardware;

import VirtualComputer.Util.*;

public class CPU extends SimulatedObject {

  private String PREFIX = "[CPU]";

  private Motherboard motherboard;
  private int clockRate;

  public CPU(Motherboard mb) {
    motherboard = mb;
    debug("Constructing...");

    setup();

  }

  private void setup() {
    setClockRate(20); //20 Hz
    setObjectName("CPU");
  }

  //This is the cpu emulation method of exactly one clock cycle
  //Some virtual CPUs may have intelligent implementations that allow them
  //to effectively handle multiple things at once however, just like real CPUs.
  public void tick() {
    //debug("tick");
  }

  public void setMotherboard(Motherboard mb) {
    motherboard = mb;
  }

  public Motherboard getMotherboard() { return motherboard; }
  public int getClockRate() { return clockRate; }

  //Not really recommended for use outside of this class, but I suppose you can if you want...
  //It is preferrable if the CPU handles it's own clock rate
  public void setClockRate(int n) {
    clockRate = n;
    setTicksPerSecond(n);
  }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
  }

  private void debug(String n) {
    if(motherboard != null && motherboard.isDebugMode()) System.out.println("[DEBUG] " + PREFIX + " " + n);
  }

}

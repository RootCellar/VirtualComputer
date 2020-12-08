package VirtualComputer.Hardware;

public class CPU {

  private String PREFIX = "[CPU]";

  private Motherboard motherboard;
  private int clockRate;

  public CPU(Motherboard mb) {
    motherboard = mb;
    debug("Constructing...");

    clockRate = 20; //20 Hz

  }

  //This is the cpu emulation method of exactly one clock cycle
  //Some virtual CPUs may have intelligent implementations that allow them
  //to effectively handle multiple things at once however, just like real CPUs.
  public void tick() {

  }

  public void setMotherboard(Motherboard mb) {
    motherboard = mb;
  }

  public Motherboard getMotherboard() { return motherboard; };
  public int getClockRate() { return clockRate; };

  //Not really recommended for use, but I suppose you can if you want...
  //It is preferrable if the CPU handles it's own clock rate
  public void setClockRate(int n) {
    clockRate = n;
  }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
  }

  private void debug(String n) {
    if(motherboard.isDebugMode()) System.out.println("[DEBUG] " + PREFIX + " " + n);
  }

}

package VirtualComputer.Hardware;

public class Motherboard {

  private String PREFIX = "[MOTHERBOARD]";
  private boolean DEBUG = false;
  private boolean going = false;
  private Thread thread;

  public Motherboard() {
    debug("Constructing...");
  }

  public Motherboard(boolean b) {
    DEBUG = b;
    debug("Constructing...");
  }

  public boolean isDebugMode() { return DEBUG; }
  public void setDebugMode(boolean b) {
    DEBUG = b;
  }

  public void simulate() {

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

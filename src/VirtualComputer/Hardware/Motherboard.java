package VirtualComputer.Hardware;

public class Motherboard {

  private String PREFIX = "[MOTHERBOARD]";
  private boolean DEBUG = false;

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

  //Main method to simulate the entire computer
  public void run() {

  }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
  }

  private void debug(String n) {
    if(DEBUG) System.out.println("[DEBUG] " + PREFIX + " " + n);
  }

}

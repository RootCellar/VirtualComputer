
//TODO: Byte array, or some other method?
//If it's a byte array, that's great for writing neat logic, but
//might not be necessary
//Plus, the emulated CPU might end up wasting time coverting four separate bytes
//into one int every time it wants to make a calculation...

package VirtualComputer.Hardware;

public class RAM {

  private String PREFIX = "[RAM]";

  private Motherboard motherboard;

  public RAM(Motherboard mb) {
    motherboard = mb;
    debug("Constructing...");
  }

  public void setMotherboard(Motherboard mb) {
    motherboard = mb;
  }

  public Motherboard getMotherboard() { return motherboard; }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
  }

  private void debug(String n) {
    if(motherboard.isDebugMode()) System.out.println("[DEBUG] " + PREFIX + " " + n);
  }
}

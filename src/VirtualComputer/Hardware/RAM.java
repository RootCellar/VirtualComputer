
//TODO: Byte array, or some other method?
//If it's a byte array, that's great for writing neat logic, but
//might not be necessary
//Plus, the emulated CPU might end up wasting time coverting four separate bytes
//into one int every time it wants to make a calculation...

package VirtualComputer.Hardware;

public class RAM {

  //Constants
  private String PREFIX = "[RAM]";

  //Objects
  private Motherboard motherboard;

  //Data
  private byte[] memory;
  private int memorySize;

  public RAM(Motherboard mb, int memSize) {
    motherboard = mb;
    debug("Constructing...");

    debug("Creating memory of size " + memSize);

    memorySize = memSize;
    memory = new byte[memorySize];
  }

  public void setMotherboard(Motherboard mb) {
    motherboard = mb;
  }

  public Motherboard getMotherboard() { return motherboard; }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
  }

  private void debug(String n) {
    if(motherboard != null && motherboard.isDebugMode()) System.out.println("[DEBUG] " + PREFIX + " " + n);
  }
}

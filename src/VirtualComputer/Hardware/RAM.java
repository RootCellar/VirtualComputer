
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
    mb.setRAM(this);

    debug("Constructing...");

    if(memSize < 1) memSize = 1;

    debug("Creating memory of size " + memSize + " {0.." + (memSize - 1) + "}");

    memorySize = memSize;
    memory = new byte[memorySize];
  }

  /*
   * Return count bytes starting at position
   * Returns an empty byte array if invalid read,
   * otherwise returns a byte array of size count
  */
  public byte[] readBytes(int position, int count) {
    //Invalid read checks
    //Can't read before array, start reading past it, or start in array and try to read past it
    if(position < 0 || position >= memorySize || position + count >= memorySize) {
      debug("readBytes( " + position + ", " + count + " ) Attempted to make an invalid read. Returning nothing....");
      return new byte[0];
    }

    //Count checks
    if(count < 0) {
      debug("readBytes( " + position + ", " + count + " ) Attempted to make an invalid read. Returning nothing....");
      return new byte[0];
    }

    byte[] toRet = new byte[count];
    for(int i=0; i < count; i++) {
      toRet[i] = memory[position + i];
    }

    return toRet;
  }

  public boolean writeBytes(int position, byte[] toWrite) {
    int writeCount = toWrite.length;

    //Invalid write checks
    if(position < 0 || position >= memorySize || position + writeCount >= memorySize) {
      debug("writeBytes( " + position + " ) (Bytes: " + writeCount + ") Attempted to make an invalid write. Doing nothing....");
      return false;
    }

    for(int i=0; i < writeCount; i++) {
      memory[position + i] = toWrite[i];
    }

    return true;

  }

  public byte readByte(int pos) {
    return readBytes(pos, 1)[0];
  }

  public boolean writeByte(int pos, byte data) {
    byte[] toWrite = new byte[1];
    toWrite[0] = data;

    return writeBytes( pos, toWrite );
  }

  //Ease of access method
  public boolean writeByte(int pos, int data) {
    return writeByte(pos, (byte) data);
  }

  public int getMemorySize() { return memorySize; }

  public void setMotherboard(Motherboard mb) {
    motherboard = mb;
  }

  public Motherboard getMotherboard() { return motherboard; }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
  }

  private void debug(String n) {
    if(motherboard != null) motherboard.debug(PREFIX + " " + n);
  }

  private void error(String n) {
    if(motherboard != null) motherboard.error(PREFIX + " " + n);
  }

  private void verbose(String n) {
    if(motherboard != null) motherboard.verbose(PREFIX + " " + n);
  }

}

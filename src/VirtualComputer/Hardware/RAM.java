/*
 *
 * The RAM
 * Holds data in an array of bytes, and the size is determined when constructed
 * The size cannot be changed after construction, but the underlying data
 * is always fully accessible.
 *
 * In order to remain useful, the RAM should probably be at least 32 bytes,
 * for the smallest programs.
 *
 * The maximum size of the RAM is determined by the largest 32 bit integer,
 * which is 2,147,483,647 or about 2 GB.
 * No program made with this will probably ever need that though!
 *
*/


package VirtualComputer.Hardware;

import VirtualComputer.Util.*;

public class RAM {

  //Constants
  private String PREFIX = "[RAM]";

  //Objects
  private Motherboard motherboard;
  private OutputUser output;

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

  public void setOutputHandler(OutputUser u) {
    output = u;
  }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
    if(output != null) output.inputString(PREFIX + " " + n);
  }

  private void debug(String n) {
    if(motherboard != null) motherboard.debug(PREFIX + " " + n);
    if(output != null) output.inputDebug(PREFIX + " " + n);
  }

  private void error(String n) {
    if(motherboard != null) motherboard.error(PREFIX + " " + n);
  }

  private void verbose(String n) {
    if(motherboard != null) motherboard.verbose(PREFIX + " " + n);
  }

}

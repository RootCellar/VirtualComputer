package VirtualComputer.Hardware;

import VirtualComputer.Util.*;
import VirtualComputer.InstructionSet;

public class CPU extends SimulatedObject {

  private String PREFIX = "[CPU]";

  //Internals
  private Motherboard motherboard;
  private int clockRate;
  private int instructionSize;

  //Execution Data
  private int nextInstructionLoc = 0;
  private boolean executing = false;
  private int register = 0;

  public CPU(Motherboard mb) {
    motherboard = mb;
    mb.setCPU(this);

    debug("Constructing...");

    setup();

  }

  private void setup() {
    setClockRate(20); //20 Hz
    setObjectName("CPU");

    instructionSize = InstructionSet.getInstructionSize();
  }

  //This is the cpu emulation method of exactly one clock cycle
  //Some virtual CPUs may have intelligent implementations that allow them
  //to effectively handle multiple things at once however, just like real CPUs.
  public void tick() {

    //CPU must be in execution state (environment must be GUARANTEED to be set up)
    if(!executing) return;

    //Now we may begin...

    byte[] instruction = motherboard.getRAM().readBytes(nextInstructionLoc, instructionSize);

    byte code = instruction[0];
    int parameter = bytesToInt(instruction, 1, 5);
    int parameter2 = bytesToInt(instruction, 5, 9);
    int next = bytesToInt(instruction, 9, 13);

    nextInstructionLoc = next;

    verbose("Executing code " + code);

    if( code == InstructionSet.NOOP.getId() ) {
      //Do nothing!
    }
    //Math
    else if( code == InstructionSet.ADD.getId() ) {
      verbose("Performing addition. Register was: " + register);
      register += parameter;
      verbose ("Register is now: " + register);
    }
    else {
        error("UNKNOWN/UNIMPLEMENTED OPERATION " + code);
    }


  }

  public byte[] intToBytes(int num) {
    byte[] toRet = new byte[4];

    for(int i = 0; i < 4; i++) {
      toRet[3-i] = (byte) ( num & 0xff );
      num = num >> 8;
    }

    return toRet;
  }

  public int bytesToInt(byte[] bytes, int begin, int end) {
    int toRet = 0;
    int current;

    for(int i = begin; i < end; i++) {
      toRet = toRet << 8;

      current = ( (int) bytes[i] ) & 0xff;

      toRet = toRet | current;

    }

    return toRet;

  }

  public void activate(){
    executing = true;
  }

  public void setNextInstruction(int i) {
    nextInstructionLoc = i;
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
    //if(motherboard != null && motherboard.isDebugMode()) System.out.println("[DEBUG] " + PREFIX + " " + n);
    if(motherboard != null) motherboard.debug(PREFIX + " " + n);
  }

  private void error(String n) {
    if(motherboard != null) motherboard.error(PREFIX + " " + n);
  }

  private void verbose(String n) {
    if(motherboard != null) motherboard.verbose(PREFIX + " " + n);
  }

}

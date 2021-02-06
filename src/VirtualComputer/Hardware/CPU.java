/*
 *
 * Where instructions are executed
 * it does seem somewhat wasteful converting things back and forth from
 * byte arrays and ints, but the (real) CPU doesn't seem to care...
 *
*/

//TODO: Handling of conditionals


package VirtualComputer.Hardware;

import VirtualComputer.Util.*;
import VirtualComputer.InstructionSet;

public class CPU extends SimulatedObject {

  private String PREFIX = "[CPU]";

  //Internals
  private Motherboard motherboard;
  private int clockRate;
  private int instructionSize;
  private OutputUser output;

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
    verbose("Next loc is " + next);

    //General Instructions
    if( code == InstructionSet.NOOP.getId() ) {
      verbose("Received NO-OP. Doing nothing...");
      //Do nothing!
    }
    else if( code == InstructionSet.PUT.getId() ) {
      verbose("Received put. Putting " + parameter + " at " + parameter2);
      byte[] toPut = intToBytes(parameter);
      motherboard.getRAM().writeBytes(parameter2, toPut);
      verbose("Memory at " + parameter2 + " now says " + readIntFromRAM(parameter2) );
    }
    else if( code == InstructionSet.MOV.getId() ) {
      //Do nothing if moving data to itself
      if(parameter == parameter2){
        verbose("Received mov. Parameters same, doing nothing...");
      }
      //Move to/from register
      else if(parameter == -1) {
        verbose("Received mov. moving REGISTER (" + register + ") to " + parameter2);
        motherboard.getRAM().writeBytes(parameter2, intToBytes(register) );
        verbose("Memory at " + parameter2 + " now says " + readIntFromRAM(parameter2) );
      }
      else if(parameter2 == -1) {
        verbose("Received mov. moving " + parameter + " (" + readIntFromRAM(parameter) + ") to REGISTER");
        register = bytesToInt( motherboard.getRAM().readBytes( parameter, 4 ), 0, 4 );
        verbose("Memory at REGISTER now says " + register);
      }
      //Move variable to variable
      else {
        verbose("Received mov. moving " + parameter + " (" + readIntFromRAM(parameter) + ") to " + parameter2);
        //int toMove = bytesToInt( motherboard.getRAM().readBytes(parameter, 4), 0, 4 );
        motherboard.getRAM().writeBytes( parameter2, motherboard.getRAM().readBytes(parameter, 4) );
        verbose("Memory at " + parameter2 + " now says " + readIntFromRAM(parameter2) );
      }
    }

    //Math
    else if( code == InstructionSet.ADD.getId() ) {
      verbose("Performing addition. Register was: " + register);
      register += parameter;
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.SUBTRACT.getId() ) {
      verbose("Performing subtraction. Register was: " + register);
      register -= parameter;
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.MULTIPLY.getId() ) {
      verbose("Performing multiplication. Register was: " + register);
      register *= parameter;
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.DIVIDE.getId() ) {
      verbose("Performing division. Register was: " + register);
      register /= parameter;
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.POW.getId() ) {
      verbose("Performing Power. " + register + " ^ " + parameter);
      register = (int) Math.pow(register, parameter);
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.MOD.getId() ) {
      verbose("Performing MOD. Register was: " + register);
      register %= parameter;
      verbose("Register is now: " + register);
    }

    //Bitwise ops
    else if( code == InstructionSet.LSHIFT.getId() ) {
      verbose("Performing LSHIFT. " + register + " << " + parameter);
      register = register << parameter;
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.RSHIFT.getId() ) {
      verbose("Performing LSHIFT. " + register + " >> " + parameter);
      register = register >> parameter;
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.AND.getId() ) {
      verbose("Performing AND. " + register + " & " + parameter);
      register = register & parameter;
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.OR.getId() ) {
      verbose("Performing OR. " + register + " | " + parameter);
      register = register | parameter;
      verbose("Register is now: " + register);
    }
    else if( code == InstructionSet.XOR.getId() ) {
      verbose("Performing XOR. Was: " + register );
      register = register ^ parameter;
      verbose("Register is now: " + register);
    }

    //Process
    else if( code == InstructionSet.DISPVAL.getId() ) {
      out("" + parameter);
    }
    else if( code == InstructionSet.DISPLOC.getId() ) {
      if(parameter == -1) out("DATA: " + register);
      else out("DATA: " + readIntFromRAM(parameter) );
    }
    else if( code == InstructionSet.EXIT.getId() ) {
      verbose("EXIT Command. Stopping execution...");
      executing = false;
    }

    else {
        error("UNKNOWN/UNIMPLEMENTED OPERATION " + code);
    }

  }

  public boolean intToBoolean(int i) { return ( i & 1 ) == 0 ? false : true; }
  public int booleanToInt(boolean b) { return b ? 1 : 0; }

  public int readIntFromRAM(int pos) {
    return bytesToInt( motherboard.getRAM().readBytes(pos, 4), 0, 4 );
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

  public boolean isExecuting() { return executing; }

  public void setMotherboard(Motherboard mb) {
    motherboard = mb;
  }

  public Motherboard getMotherboard() { return motherboard; }
  public int getClockRate() { return clockRate; }
  public int getRegister() { return register; }
  public int getNextInstructionLoc() { return nextInstructionLoc; }

  //Not really recommended for use outside of this class, but I suppose you can if you want...
  //It is preferrable if the CPU handles it's own clock rate
  public void setClockRate(int n) {
    clockRate = n;
    setTicksPerSecond(n);
  }

  public void setOutputHandler(OutputUser u) {
    output = u;
  }

  private void out(String n) {
    System.out.println(PREFIX + " " + n);
    if(output != null) output.inputString(PREFIX + " " + n);
  }

  private void debug(String n) {
    //if(motherboard != null && motherboard.isDebugMode()) System.out.println("[DEBUG] " + PREFIX + " " + n);
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

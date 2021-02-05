package VirtualComputer.Assemble;

public class Instruction {
  private String line;

  private byte code;
  private int param1;
  private int param2;
  private int nextLoc;

  public Instruction() {

  }

  public void setCode(byte b) {
    code = b;
  }

  public void setParam1(int i) {
    param1 = i;
  }

  public void setParam2(int i) {
    param2 = i;
  }

  public void setNextInstrLoc(int i) {
    nextLoc = i;
  }
}

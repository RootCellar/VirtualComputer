package VirtualComputer.Assemble;

public class Instruction {
  private String line;
  private String[] parts;

  private byte code;
  private int param1;
  private int param2;
  private int nextLoc;

  public Instruction() {

  }

  public byte getCode() { return code; }
  public int getParam1() { return param1; }
  public int getParam2() { return param2; }
  public int getNextLoc() { return nextLoc; }
  public String[] getParts() { return parts; }
  public String getLine() { return line; }

  public void setParts(String[] p) {
    parts = p;
  }

  public void setLine(String s) {
    line = s;
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

package VirtualComputer.Assemble;

public class Variable {
  private int location = -1;
  private String name = "NULL";

  public String getName() {
    return name;
  }

  public void setName(String s) {
    name = s;
  }

  public int getLoc() { return location; }
  public void setLoc(int i) {
    location = i;
  }

  public void setLocByNum(int i) {
    location = i * 4;
  }
}

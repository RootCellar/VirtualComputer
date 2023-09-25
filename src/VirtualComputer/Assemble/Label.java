package VirtualComputer.Assemble;

public class Label {

    //Private Data
    private final String name;
    private int position;

    public Label(String n, int pos) {
        name = n;
        position = pos;
    }

    public String getName() {
        return name;
    }

    public int getPos() {
        return position;
    }

    public void setPos(int p) {
        position = p;
    }

}

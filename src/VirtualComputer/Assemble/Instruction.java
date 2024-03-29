package VirtualComputer.Assemble;

import VirtualComputer.Assembler;


public class Instruction {

    private String line;
    private String[] parts;
    private byte[] data;

    private int lineNum = -1;
    private boolean bad = false;
    private byte code = 0;
    private int param1 = 0;
    private int param2 = 0;
    private int nextLoc = 0;

    public Instruction() {

    }

    public Instruction(boolean b) {
        bad = b;
    }

    public void make() {
        data = Assembler.makeInstruction(code, param1, param2, nextLoc);
    }

    public byte[] getBytes() {
        make();
        return data;
    }

    public int getLineNumber() {
        return lineNum;
    }

    public void setLineNumber(int i) {
        lineNum = i;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte b) {
        code = b;
    }

    public int getParam1() {
        return param1;
    }

    public void setParam1(int i) {
        param1 = i;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int i) {
        param2 = i;
    }

    public int getNextLoc() {
        return nextLoc;
    }

    public String[] getParts() {
        return parts;
    }

    public void setParts(String[] p) {
        parts = p;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String s) {
        line = s;
    }

    public boolean isBad() {
        return bad;
    }

    public void setIsBad(boolean b) {
        bad = b;
    }

    public void setNextInstrLoc(int i) {
        nextLoc = i;
    }

    public String toString() {
        return "{ " + code + ", " + param1 + ", " + param2 + ", " + nextLoc + " }";
    }

}

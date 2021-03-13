package VirtualComputer;

/*
 * Default instruction set
 * Each instruction is 13 bytes and are ordered as follows:
 * - 1 byte Type/ID
 * - 4 bytes (int) Data/Location parameter
 * - 4 bytes (int) Data/Location parameter
 * - 4 bytes (int) Location of next instruction to execute
 *
*/

public enum InstructionSet {

  //The grand instruction set
  NOOP("Do nothing. This still consumes a clock cycle."),
  PUT("Put hard-coded data somewhere"),
  MOV("Move one piece of data somewhere else"),
  LABEL("Define a label that the program can go to later"),
  GOTO("Go to a label"),
  DATA("Define a variable"),

  //Conditions
  IF("Check if a piece of data is true"),
  ELIF("Else-If"),
  ELSE("Do this if the previous if was false"),
  END("The end if an if-then block"),
  EQ("Check if the data in the register equals the given value"),
  LEAQ,
  GREQ,
  GREATER,
  LESS,
  EQV,
  LEAQV,
  GREQV,
  GREATERV,
  LESSV,

  //Math
  ADD,
  SUBTRACT,
  MULTIPLY,
  DIVIDE,
  POW,
  MOD,

  ADDV,
  SUBTRACTV,
  MULTIPLYV,
  DIVIDEV,
  POWV,
  MODV,

  //Bit Ops
  LSHIFT,
  RSHIFT,
  AND,
  OR,
  XOR,

  //Process
  EXIT("End execution of the program"),

  //Display
  DISPVAL("Display a hard coded value"),
  DISPLOC("Display data at a location (register/RAM)"),
  ;


  //Static
  private static int INSTRUCTION_SIZE = 13;

  //Object info
  private String name;
  private String description;
  private byte id;

  InstructionSet() {
    name = name();
    id = (byte) ordinal();
  }

  InstructionSet(String desc) {
    name = name();
    id = (byte) ordinal();

    description = desc;
  }

  public static int getInstructionSize() { return INSTRUCTION_SIZE; }

  public byte getId() { return id; }
  public String getName() { return name; }
  public String getDescription() { return description; };

}

package VirtualComputer;

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
  EQ("Check if the data in the register equals the given value"),
  LEAQ,
  GREQ,
  GREATER,
  LESS,

  //Math
  ADD,
  SUBTRACT,
  MULTIPLY,
  DIVIDE,
  SQUARE,

  //Bit Ops
  LSHIFT,
  RSHIFT,
  AND,
  OR,
  XOR,

  //Display
  DISPVAL("Display a hard coded value"),
  DISPLOC("Display data at a location (register/RAM)"),
  ;


  //Object info
  private String name;
  private String description;
  private int id;

  InstructionSet() {
    name = name();
    id = ordinal();
  }

  InstructionSet(String desc) {
    name = name();
    id = ordinal();

    description = desc;
  }

  public int getId() { return id; }
  public String getName() { return name; }
  public String getDescription() { return description; };

}

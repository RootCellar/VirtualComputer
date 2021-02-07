# The Language

**Not Finished**

For this language, all instructions are **case sensitive**.

"NOOP" is a valid command, while "noop" is not. Variables are also case sensitive.

Summary of Instructions:

## Data

In places where a data location is specified, "register" can be used to denote that the data should be moved to/from the CPU's register.

- NOOP - Do nothing, still consumes a clock cycle
- PUT - 2 arguments, place argument 1 (hard coded data) at argument 2
- MOV - Move one piece of data somewhere else. Both arguments are locations
- LABEL - define a label
- GOTO - go to a label
- DATA - define a variable

## Conditionals

## Math

Each of these takes one argument, and performs its operation on the CPU's register.

- ADD
- SUBTRACT
- MULTIPLY
- DIVIDE
- POW
- MOD

## Bit Operations

Just like the math functions, these take one argument and operate on the CPU's register.

- LSHIFT
- RSHIFT
- AND
- OR
- XOR

## Process

- EXIT - end execution of the program

## Display

- DISPVAL - display a hard coded value
- DISPLOC - display the data at a given location

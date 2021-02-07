# VirtualComputer

Creating a virtual computer using Java, that uses implemented computer hardware to emulate a machine.

## The Language

Check out `Language.md` to read about the language and it's syntax

## Using the Virtual Computer

First, you'll want to make sure the repository is built (See section "Building" below)

Programs are written files with the extension ".vasm". These can be assembled with `execasm.sh <name` in `Scripts`.
The assembler assumes `main.vasm` if no argument is given. The assembler also runs in the `build` folder, so you'll have to
tell the assembler where your program is if it isn't in that folder. On Linux, you can do something like `./execasm.sh ../Examples/Test` to assemble `Test.vasm` in `Examples`. The assembler will always output `run.vbin` in `build`.

After your program is assembled, you can execute it with `execmain.sh <args>` in `Scripts`. If you supply `-nogui` as an argument, the program will not open a GUI, which may be useful when you don't want/need one. The program will always (or at least attempt to) execute `run.vbin` in `build`.

## Development of the Virtual Computer

### Building

As long as you have a Java compiler installed (and are on Linux), you should just be able to:

```
cd Scripts
./build.sh
```

This project does not use any special external libraries of any kind, so it should compile easily.

The `build.sh` script takes all java source files in `src` and places the generated class files in `build`.

### Scripts

The scripts folder houses scripts useful for development.

- `build.sh` is used to build the project

- `execmain.sh` simply runs main in VirtualComputer.java. This will load `run.vbin` and execute it on the virtual hardware.

- `execasm.sh` runs the assembler. Command line arguments can be given to assemble the specified program.

- `info.sh` makes a few files in `info` containing some repository information, some of which could be useful, and some could just be interesting (eg. line counts).

- `tail.sh` actively follows the motherboard log file in `build/Logs/Motherboard`. Useful for development when you want to see what is happening as it happens.

- `tailasm.sh` is just like `tail.sh`, only for the assembler logs in `build/Logs/Assembler`.

- `jar.sh` packages the whole project into a .jar file, in `build`.

- `rm.sh` cleans up the repository. Deletes files like *.class, *.jar, *.temp, and the logs.

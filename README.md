# VirtualComputer

Creating a virtual computer using Java, that uses implemented computer hardware to emulate a machine.

## The Language

Check out `Language.md` to read about the language and it's syntax

## Building

As long as you have a Java compiler installed (and are on Linux), you should just be able to:

```
cd Scripts
./build.sh
```

This project does not use any special external libraries of any kind, so it should compile easily.

## Scripts

The scripts folder houses scripts useful for development.

- `build.sh` is used to build the project

- `execmain.sh` simply runs main in VirtualComputer.java

- `info.sh` makes a few files in `info` containing some repository information, some of which could be useful, and some could just be interesting (eg. line counts).

- `tail.sh` actively follows the motherboard log file in `build/Logs/Motherboard`. Useful for development when you want to see what is happening as it happens.

- `jar.sh` packages the whole project into a .jar file, in `build`.

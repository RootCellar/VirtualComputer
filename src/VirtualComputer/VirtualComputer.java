/*
 * RootCellar (github.com/RootCellar)
 * 12/07/2020
 * VirtualComputer.java
 *
 * Creating virtual hardware in hopes of emulating a machine
 *
 *
 */

package VirtualComputer;

import VirtualComputer.GUI.MainGUI;
import VirtualComputer.Hardware.CPU;
import VirtualComputer.Hardware.Motherboard;
import VirtualComputer.Hardware.RAM;
import VirtualComputer.Util.OutputUser;
import VirtualComputer.Util.SimulatedObject;
import VirtualComputer.Util.TimeKeeper;

import java.io.FileInputStream;


public class VirtualComputer implements Runnable, OutputUser {

    //Static
    private static boolean debug = false;
    private static boolean usingGUI = true;
    private final int ticksPerSecond = 60;
    private final boolean going = false;
    //Regular members
    private Thread thread;
    private MainGUI gui;

    public VirtualComputer() {

    }

    private static void out(String message) {
        System.out.println("[CONTROLLER] " + message);
    }

    private static void debug(String message) {
        if( debug ) System.out.println("[DEBUG] [CONTROLLER] " + message);
    }

    public static void main(String[] args) {

        MainGUI tgui = null;
        int cpuRate = 5;

        boolean mbLogging = true;

        //Handle Arguments
        for( String s : args ) {

            if( s.equals("-debug") ) {
                debug = true;
            } else if( s.equals("-nogui") ) {
                usingGUI = false;
            } else if( s.equals("-nolog") ) {
                mbLogging = false;
            } else {
                try {
                    cpuRate = Integer.parseInt(s);
                } catch(Exception e) {
                    out("Error parsing CPU clock rate");
                    return;
                }
            }

        }

        if( usingGUI ) {
            try {
                tgui = new MainGUI();
            } catch(Exception e) {
                out("Creating the GUI failed, running without GUI...");
            }
            //tgui.setUser(this);
        }

        //Motherboard Setup
        debug("Setting up motherboard...");
        Motherboard motherboard = new Motherboard(debug);
        if( !mbLogging ) motherboard.disableLogging();

        //CPU Setup
        debug("Setting up CPU...");
        CPU processor = new CPU(motherboard);
        processor.setClockRate(cpuRate);

        //RAM Setup
        debug("Setting up RAM...");
        RAM memory = new RAM(motherboard, 1024 * 1024 * 8);

        //Finish setup with other procedures
        debug("Completing setup...");

        if( usingGUI && tgui != null ) {
            motherboard.setOutputHandler(tgui);
        }

        SimulatedObject.setSimDebugMode(debug);

        //Possible code injection can be done this way:
        //Assembler.makeInstruction( instruction code, parameter1, parameter2, location of next instruction )

        //This section reads the code from a file and puts it into the RAM
        //TODO: Make this better, and probably a method

        debug("Loading the program into the RAM...");

        byte[] fileData = new byte[1024 * 1024 * 4];

        try {
            FileInputStream in = new FileInputStream("run.vbin");
            in.read(fileData);
            in.close();
        } catch(Exception e) {
            out("Exception on file read");
        }

        memory.writeBytes(0, fileData);

        //Run the simulation
        out("Beginning simulation...");
        while( true ) {

            //Simulate
            motherboard.simulate();
            TimeKeeper.sleep(50);

            //GUI
            if( tgui != null ) {
                tgui.label.setText("Register: " + processor.getRegister());
                tgui.label2.setText("nextInstructionLoc: " + processor.getNextInstructionLoc());
                tgui.label3.setText("CPU clock rate: " + processor.getClockRate() + " hz");
            }

            //What if the program ends?
            if( !processor.isExecuting() ) {
                out("Processor has stopped execution. Quitting...");
                System.exit(0);
            }

        }

    }

    //Main method to simulate the entire computer
    //TODO: Implement
    public void run() {

        /*
        while( going ) {

        }
        */

    }

    private void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void inputString(String s) {
        if( gui != null ) gui.out(s);
    }

    public void inputDebug(String s) {

    }

}

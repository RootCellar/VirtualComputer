/*
 * RootCellar (github.com/RootCellar)
 * 12/09/2020
 * SimulatedObject.java
 *
 * This class is used for ticking an object very precisely, and ensuring
 * that all execution time is accounted for.
 *
*/

//This is being used for this project, but it could be used for anything!
package VirtualComputer.Util;

public abstract class SimulatedObject {

  private static boolean SIMULATION_DEBUG = false;

  private String objectName = "<name>";
  private int ticksPerSecond = 20;

  private double timePerTick = 1000000000 / ticksPerSecond;
  private double unprocessedTicks = 0;

  private int ticksPassed = 0;
  private double ticksPassedTime = System.nanoTime();
  private int actualTicksPerSecond = 0;

  private TimeKeeper timer = new TimeKeeper();

  public void simulate() {
    simulate(false);
  }

  //Helpful method to simulate an object, keeping the ticks up with the current timestamp
  public void simulate(boolean tickOnce) {

    //Give the option to simply just tick once, no matter what.
    //Useful in the case that something is being debugged,
    //where this allows something to be forcefully ticked once
    //to "step" through something
    if(tickOnce) {
      tick();

      //TODO: Might add unprocessedTicks-- here, as it would make sense with the logic

    }
    else {

      //No point in doing anything if the time hasn't changed...
      if(timer.timeIsEqual()) return;

      //Stop the timer, add that unprocessed time, then "roll" the timer's
      //end time to the start time to account for all execution time
      timer.stop();
      unprocessedTicks += timer.getElapsedInTicks( ticksPerSecond );
      timer.resume();

      //Keep ticks up to speed
      //TODO: See if there's some way to make sure slower hardware works alright with this
      //If a machine, for some reason, can't tick fast enough to keep up with the passed time,
      //It will start falling farther and farther behind, resulting in longer and longer lock-ups
      while(unprocessedTicks >= 1) {
        tick();

        ticksPassed++;
        unprocessedTicks--;
      }

      //Keep track of Real TPS possibly for debugging or just helpful to know
      if(System.nanoTime() - ticksPassedTime > 1000000000) {
        ticksPassedTime = System.nanoTime();

        //debugSim("TPS: " + ticksPassed);
        actualTicksPerSecond = ticksPassed;
        ticksPassed = 0;

      }

    }
  }

  //This is what the user wants to do
  public abstract void tick();

  //Reset the timer to continue going from now
  //This is useful when simulating has stopped for a while,
  //and you don't want the program to hang catching up on ticks
  public void resetTimer() {
    timer.stop();
    timer.resume();
  }

  public int getTicksPerSecond() { return ticksPerSecond; }

  public void setTicksPerSecond(int i) {
    ticksPerSecond = i;
    timePerTick = 1000000000 / ticksPerSecond;
  }

  public static void setSimDebugMode(boolean b) {
    SIMULATION_DEBUG = b;
  }

  protected void setObjectName(String n) {
    objectName = n;
  }

  public int getRealTPS() { return actualTicksPerSecond; }

  protected void debugSim(String n) {
    if(SIMULATION_DEBUG) System.out.println("[SIMULATED OBJECT] [" + objectName + "] " + n);
  }

}

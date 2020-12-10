package VirtualComputer.Util;

public abstract class SimulatedObject {

  private static boolean SIMULATION_DEBUG = false;

  private String objectName = "[SIMULATED OBJECT]";
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
    if(tickOnce) tick();
    else {

      if(timer.timeIsEqual()) return;

      timer.stop();
      unprocessedTicks += timer.getElapsedInTicks( ticksPerSecond );
      timer.resume();

      while(unprocessedTicks >= 1) {
        tick();

        ticksPassed++;
        unprocessedTicks--;
      }

      if(System.nanoTime() - ticksPassedTime > 1000000000) {
        ticksPassedTime = System.nanoTime();

        //debugSim("TPS: " + ticksPassed);
        actualTicksPerSecond = ticksPassed;
        ticksPassed = 0;

      }

    }
  }

  public abstract void tick();

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

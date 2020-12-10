package VirtualComputer.Util;

public abstract class SimulatedObject {

  private int ticksPerSecond = 20;

  private double timePerTick = 1000000000 / ticksPerSecond;
  private double unprocessedTicks = 0;

  private TimeKeeper timer = new TimeKeeper();

  public void simulate() {
    simulate(false);
  }

  //Helpful method to simulate an object, keeping the ticks up with the current timestamp
  public void simulate(boolean tickOnce) {
    if(tickOnce) tick();
    else {
      timer.stop();
      unprocessedTicks += timer.getElapsedInTicks( ticksPerSecond );
      timer.start();

      while(unprocessedTicks >= 1) {
        tick();

        unprocessedTicks--;
      }

    }
  }

  public abstract void tick();

  public int getTicksPerSecond() { return ticksPerSecond; }

  public void setTicksPerSecond(int i) {
    ticksPerSecond = i;
    timePerTick = 1000000000 / ticksPerSecond;
  }

}

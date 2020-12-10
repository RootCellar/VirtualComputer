package VirtualComputer.Util;

public class TimeKeeper {

  private double timeStarted = 0;
  private double timeEnded = 0;

  public TimeKeeper() {
    start();
  }

  public void start() {
    timeStarted = System.nanoTime();
  }

  public void stop() {
    timeEnded = System.nanoTime();
  }

  public double getElapsed() {
    return timeEnded - timeStarted;
  }

  public double getElapsedInTicks(int tps) {
    return ( timeEnded - timeStarted ) / ( 1000000000 / tps );
  }

}

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

  public void restart() {
    timeStarted = timeEnded;
  }

  public double getElapsed() {
    return timeEnded - timeStarted;
  }

  public double getElapsedInTicks(int tps) {
    return ( (double) ( timeEnded - timeStarted ) ) / ( (double) ( 1000000000.0 / tps ) );
  }

  public boolean timeIsEqual() { return timeStarted == System.nanoTime(); }

  public static void sleep(int n) {

    try{

      Thread.sleep(n);

    }catch(Exception e) {
      //Ignore
    }

  }

}

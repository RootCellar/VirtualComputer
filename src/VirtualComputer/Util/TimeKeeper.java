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

  //TODO: 12/09/2020 Come up with a better name for this method
  //This method keeps the time going in a "rolling" fashion by setting
  //the start time to the previous end time. This makes time keeping more precise,
  //if that's what you need.
  public void resume() {
    timeStarted = timeEnded;
  }

  public double getElapsed() {
    return timeEnded - timeStarted;
  }

  //Convenience method so the user doesn't have to do this themselves :)
  //Given a tickrate, returns how many ticks would occur in that timeframe
  public double getElapsedInTicks(int tps) {
    return ( (double) ( timeEnded - timeStarted ) ) / ( (double) ( 1000000000.0 / tps ) );
  }

  public double getElapsedInSeconds() {
    return getElapsed() / 1000000000.0;
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

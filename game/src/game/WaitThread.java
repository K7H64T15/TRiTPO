package game;

public class WaitThread implements Runnable {

  public Thread waitThread;

  public WaitThread() {
    waitThread = new Thread(this);
    waitThread.start();
  }

  public void run() {
    //System.out.println("wait");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException waitError) {
      waitError.printStackTrace();
    }
  }

}

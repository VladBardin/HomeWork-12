package Task1;
import static Task1.TaskOne.*;
public class TaskOne {
    static final Object lock = new Object();
    public static void main(String[] args) {
        App app = new App();
        Thread timer = new Thread(new Timer(app));
        Thread messager = new Thread(new Messanger(app));
        timer.start();
        messager.start();
    }
}
class App {
    int seconds = 0;
    public void timer() {
        synchronized (TaskOne.lock) {
            System.out.println(seconds);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            seconds++;
            while (seconds % 5 == 0) {
                try {
                    System.out.println(seconds);
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            TaskOne.lock.notify();
        }
    }
    public void message() {
        synchronized (lock) {
            while (seconds % 5 != 0) {
                try {
                    lock.wait();
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            seconds++;
            System.out.println("Five seconds have passed");
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                throw new RuntimeException();
            }
            lock.notify();
        }
    }
}
class Timer implements Runnable {
    App app;
    public Timer(App app) {
        this.app = app;
    }
    @Override
    public void run() {
        while (true) {
            app.timer();
        }
    }
}
class Messanger implements Runnable {
    App app;
    public Messanger(App app) {
        this.app = app;
    }
    @Override
    public void run() {
        while (true) {
            app.message();
        }
    }
}



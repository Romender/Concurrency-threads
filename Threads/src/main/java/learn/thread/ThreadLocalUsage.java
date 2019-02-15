package learn.thread;

/**
 *
 * The main aim of this class to check the usage of the Thread local.
 */
public class ThreadLocalUsage {

    public static void main(String args[]) {
        Thread t1 = new Thread(new MyThread());
        Thread t2 = new Thread(new MyThread());
        Thread t3 = new Thread(new MyThread());
        t1.start();
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.start();
    }
}


class MyThread implements  Runnable {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public void run() {
        init();
        print();
        work();
        print();
        completed();
        print();

    }

    private void init() {
        this.threadLocal.set( "Initializing the values");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void work() {
        this.threadLocal.set(" start working");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void completed() {
        this.threadLocal.set(" Mark completed");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void print() {
        System.out.println(Thread.currentThread().getName()+"-->"+this.threadLocal.get());
    }
}
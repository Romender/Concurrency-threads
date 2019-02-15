package learn.thread;

import java.util.List;
import java.util.Stack;

/**
 * Communication between threads using wait, notify and notify all.
 */
public class ThreadCommunication {

    public static void main(String args[]) {

        Stack<String> stack = new Stack<>();
        Thread p1 = new Thread(new Producer(stack));
        Thread p2 = new Thread(new Producer(stack));
        Thread p3 = new Thread(new Producer(stack));
        Thread c1 = new Thread(new Consumer(stack));
        Thread c2 = new Thread(new Consumer(stack));
        Thread c3 = new Thread(new Consumer(stack));
        p1.start();
        c1.start();
        p2.start();
        c2.start();
        p3.start();
        c3.start();
    }

}

/**
 * Producer Thread : Creates the sequence number.
 */
class Producer implements Runnable {

    private List<String> list;

    public Producer (List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < 10; i ++ ) {
                Thread.sleep(100);
                synchronized (list) {
                    System.out.println("Produced :" +(i +" ->"+ Thread.currentThread().getName()));
                    list.add("Produced by : "+Thread.currentThread().getName() +" -->"+ i);
                    list.notify();
                }
            }
        } catch (InterruptedException e) {

        }
    }
}

/**
 * Looks for the list and consumes the messages.
 */
class Consumer implements Runnable {

    private Stack<String> stack;

    public Consumer (Stack<String> stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < 10; i ++ ) {
                synchronized (stack) {
                    stack.wait();
                    System.out.println("Consumed : " +stack.pop() +" -> "+ Thread.currentThread().getName());
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


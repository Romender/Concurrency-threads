package learn.thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Aim of this class to understand the Executor framework and its all functions.
 */
public class ExecutorFramework {

    public static void main(String args[]) {
        executorUsingNewSingleThreadExecutor();
        runningThreadAtConstantDuration();
    }

    /**
     *  Creating the singleThreadExecutor pool and executing the Thread.
     */
    public static void executorUsingNewSingleThreadExecutor() {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->{
            System.out.println("Running Thread --> "+ Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println( "Completed Executing -->" + Thread.currentThread().getName());
        });
    }

    public static void runningThreadAtConstantDuration() {
        Runnable runnable = ()-> {
            System.out.println ("Running -> " +Thread.currentThread().getName());
        };
        ScheduledFuture<?> scheduler = Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(
                runnable, 0, 1, TimeUnit.MICROSECONDS);
       System.out.println (((ScheduledFuture) scheduler).isDone());
       scheduler.cancel(true);

    }
}

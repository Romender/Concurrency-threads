package learn.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * This task will idenitfy the maximum number in given Array.
 */
public class MaxNumberTask extends RecursiveTask<Integer> {

    /**
     * Input array
     */
    private int[] array;

    /**
     * Inclusive start location
     */
    private int start;

    /**
     * Exclusive end location
     */
    private int end;

    public MaxNumberTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    /**
     * In this method Identify the maximum number from the given input array.
     * @return maximum Integer Number
     */
    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread().getName()+"Input--> Start :" + start +" end :" +end);
        int maxRecords = array.length/4;
        if(end-start <= maxRecords ) {
            Integer result = 0;
            long startTime = System.currentTimeMillis();
//            System.out.println("Start : " + Thread.currentThread().getName() +" --> "+ start+" ,"+end+" -->"
//             +System.currentTimeMillis());
            for(int i = start ;i < end; i++)
                result = Math.max(result,array[i]);
            long endTime = System.currentTimeMillis();
            System.out.println("End : " + Thread.currentThread().getName() +" --> "+ start+" ,"+end+" --> " +(endTime-startTime));
            return result;
        } else{
            MaxNumberTask task1 =new MaxNumberTask(array,start,(end+start)/2);
            MaxNumberTask task2 =new MaxNumberTask(array,(end+start)/2,end);
            task1.fork();
            task2.fork();
            return Math.max(task1.join(),task2.join());
        }
    }
}

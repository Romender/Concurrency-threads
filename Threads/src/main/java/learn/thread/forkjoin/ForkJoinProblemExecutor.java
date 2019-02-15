package learn.thread.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class ForkJoinProblemExecutor {

    private int size = 1024*1024*600;

    public int[]  createRandomArray() {
        int [] array = new int[size];
        Random random = new Random();
        for(int i= 0; i < size ;i++)  {
            array[i] = random.nextInt();
        }
        return array;
    }

    public static void main(String args[]) throws Exception {
        ForkJoinProblemExecutor forkJoinProblemExecutor = new ForkJoinProblemExecutor();
        int input[] = forkJoinProblemExecutor.createRandomArray();

        System.out.println(" Start");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        long startTime = System.currentTimeMillis();
        Integer max = forkJoinProblemExecutor.findMaximum(input);
        long endTime = System.currentTimeMillis();
        System.out.println(" Total time taken  findMaximum: "+max +" --> " + (endTime - startTime));
        startTime = System.currentTimeMillis();
        max = forkJoinProblemExecutor.findMaximumWithForkJoin(input);
        endTime = System.currentTimeMillis();
        System.out.println(" Total time taken findMaximumWithForkJoin : "+max +" --> " + (endTime - startTime));
        startTime = System.currentTimeMillis();
        max = forkJoinProblemExecutor.findMaximumWithConstantPoolThread(input);
        endTime = System.currentTimeMillis();
        System.out.println(" Total time taken findMaximumWithConstantPoolThread : "+max +" --> " + (endTime - startTime));
        System.out.println(Runtime.getRuntime().availableProcessors());


    }

    public Integer findMaximum(int input[]) {
        int result = 0 ;
        for(int i = 0 ; i < input.length ;i++) {
            result = input[i] > result ? input[i] : result;
        }
        return result;
    }

    public Integer findMaximumWithForkJoin(int input[]) throws Exception {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(new MaxNumberTask(input,0,input.length));
    }

    public Integer findMaximumWithConstantPoolThread(int input[]) throws Exception {
        int threadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Callable<Integer>> callableList = new ArrayList<>();
        for (int i = 0; i < input.length; i+=(input.length/threadCount)) {
            final int limit = i;
            callableList.add(()->{
               // System.out.println("Thread : "+ Thread.currentThread().getName()+" start :"+ limit +" end :"+ (
                // (input.length/5)+limit));
                int max = 0;
                for( int j = limit; j < (Math.min((input.length/threadCount)+limit,input.length)) ; j++)
                    max = Math.max(max,input[j]);
                return max;
            });
        }
        List<Future<Integer>> futureList = executor.invokeAll(callableList);
        while(futureList.stream().map(Future::isDone).collect(Collectors.toList()).size()!= callableList.size());
        executor.shutdown();
        return futureList.stream().map(x -> {
            try {
                return x.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                return 0;
            }
        }).collect(Collectors.toList()).stream().max(Integer::compareTo).get();
    }
}

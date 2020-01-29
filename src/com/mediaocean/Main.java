package com.mediaocean;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        System.out.println("Multi Threading Demo");

        //Basic Thread
        DemoThread thread = new DemoThread();
        thread.run();

        //ThreadPool Handling Using Executor Service: Can Execute Runnable and Callable Tasks
        ExecutorService executorService = new ThreadPoolExecutor(10, 100, 5L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        Runnable runnableTask = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                System.out.println("Runnable Task Time :: " + LocalTime.now());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(1000);
            return "Callable Task Time :: " + LocalTime.now();
        };

        //Execute single runnable Task
        executorService.execute(runnableTask);

        //List of Callable Tasks
        List<Callable<String>> taskList = Arrays.asList(callableTask, callableTask);

        //Execute Callable TaskList using invokeAll()
        try {
            List<Future<String>> results = executorService.invokeAll(taskList);
            for (Future<String> result : results) {
                System.out.println(result.get());
            }
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //To Execute Single Callable Task and Wait for result
        Future<String> result = executorService.submit(callableTask);

        while (result.isDone() == false) {
            try {
                System.out.println("Single Callable Task Value : " + result.get());
                break;
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            //Sleep to Check again after 1 Second
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Must Shutdown Service
        executorService.shutdownNow();
    }
}

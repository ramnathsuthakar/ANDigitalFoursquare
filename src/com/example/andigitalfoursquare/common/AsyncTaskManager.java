package com.example.andigitalfoursquare.common;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;


/**
 * |
 *
 * @author Ramnath Suthakar
 *         Date: 23/06/2015
 */


public class AsyncTaskManager {

    private static final int CORE_POOL_SIZE;
    private static final int MAXIMUM_POOL_SIZE;
    private static final int KEEP_ALIVE;
    private static final TimeUnit TIME_UNIT;

    private static final BlockingQueue<Runnable> concurrentPoolWorkQueue;
    private static final ThreadFactory concurrentThreadFactory;
    private static final ThreadPoolExecutor concurrentExecutor;

    private AsyncTaskManager() {}

    static {
        CORE_POOL_SIZE    = 10;
        MAXIMUM_POOL_SIZE = 128;
        KEEP_ALIVE        = 1;
        TIME_UNIT         = TimeUnit.SECONDS;

        concurrentPoolWorkQueue = new LinkedBlockingQueue<Runnable>(10);
        concurrentThreadFactory = new AsyncTaskThreadFactory();
        concurrentExecutor      = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TIME_UNIT,
                concurrentPoolWorkQueue,
                concurrentThreadFactory
        );
    }

    /**
     * Concurrently executes AsyncTask on any Android version
     * @param task to execute
     * @param params for task
     * @return executing AsyncTask 
     */
    @SuppressLint("NewApi") 
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> 
        executeConcurrently(AsyncTask<Params, Progress, Result> task, Params... params) {
    	if(ANDigitalAppConfig.getInstance().isOnline()) {
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            task.executeOnExecutor(concurrentExecutor, params);
	        } else {
	            task.execute(params);
	        }
    	}

        return task;
    }

    /**
     * Thread factory for AsyncTaskExecutor
     * 
     */
    private static class AsyncTaskThreadFactory implements ThreadFactory {
        private final AtomicInteger count = new AtomicInteger(1);;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask Ram #" + count.getAndIncrement());
        }
    }



	public static void executeConcurrently(AsyncTask<String, Void, String> execute, String i) {
		if(ANDigitalAppConfig.getInstance().isOnline()) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				execute.executeOnExecutor(concurrentExecutor, i);
	        } else {
	        	execute.execute("Calls" + i);
	        }
		}
	}
}
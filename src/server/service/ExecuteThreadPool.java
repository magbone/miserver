package server.service;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteThreadPool {

    /**
     * @see Executors;
     */

    private ExecutorService executorService = Executors.newCachedThreadPool();



    public ExecuteThreadPool(){

    }

    public final void addThread(Runnable runnable){

        executorService.submit(runnable);
    }
}

package server.response.parser;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParserContainer {

    //private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    private Lock readLock = reentrantReadWriteLock.readLock();
    private Lock writeLock = reentrantReadWriteLock.writeLock();

    public String handleRead(String html){
        String handleHtml;
        readLock.lock();
        //do something
        handleHtml = html;
        readLock.unlock();
        return handleHtml;
    }

    public String handleWrite(String html,String oldValue,String newValue){
        writeLock.lock();
        String afterWrite = html.replace(oldValue,newValue);
        //do something
        writeLock.unlock();
        return afterWrite;
    }
}

package com.seungho.reactor;

import com.seungho.reactor.Demultiplexer;
import com.seungho.reactor.Dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPoolDispatcher implements Dispatcher {
  static final String NUMTHREADS = "8";
  static final String THREADPROP =  "Threads";

  private int numThreads;

  public ThreadPoolDispatcher() {
    numThreads = Integer.parseInt(System.getProperty(THREADPROP, NUMTHREADS));
  }

  public void dispatch(ServerSocket serverSocket, HandleMap handleMap) {
    for (int i = 0; i < (numThreads - 1); i++) {
      Thread thread = new Thread(() -> {
        dispatchLoop(serverSocket, handleMap);
      });

      thread.start();
      System.out.println("Created and started Thread = " + thread.getName());
    }
    System.out.println("iterative server starting in main thread " + Thread.currentThread().getName());
    dispatchLoop(serverSocket, handleMap);
  }

  private void dispatchLoop(ServerSocket serverSocket, HandleMap handleMap) {
    while (true) {
      try {
        Socket socket = serverSocket.accept();

        Runnable demultiplexer = new Demultiplexer(socket, handleMap);
        Thread thread = new Thread(demultiplexer);
        thread.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

package com.seungho.reactor;

import com.seungho.reactor.Demultiplexer;
import com.seungho.reactor.Dispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPerDispatcher implements Dispatcher {

  public void dispatch(ServerSocket serverSocket, HandleMap handleMap) {
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
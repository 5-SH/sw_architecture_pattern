package com.seungho;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerInitializer {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    int port = 5000;
    System.out.println("Server ON : " + port);

    Reactor reactor = new Reactor(port);

    reactor.registerHandler(new StreamSayHelloHandler());
    reactor.registerHandler(new StreamUpdateProfileHandler());

    reactor.startServer();
  }

}

package com.seungho;

public class ServerInitializer {
  public static void main(String[] args) {
    com.seungho.reactor.ServerInitializer reactorServer = new com.seungho.reactor.ServerInitializer();
    reactorServer.startServer();

//    com.seungho.proactor.ServerInitializer proactorServer = new com.seungho.proactor.ServerInitializer();
//    proactorServer.startServer();
  }
}

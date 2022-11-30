package com.seungho;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerInitializer {

  private static int PORT = 5000;
  private static int threadPoolSize = 8;
  private static int initialSize = 4;
  private static int backlog = 50;

  public static void main(String[] args) {
    System.out.println("SERVER START");
  }
}

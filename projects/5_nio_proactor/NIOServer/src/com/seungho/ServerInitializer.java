package com.seungho;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerInitializer {

  private static int PORT = 6000;
  private static int threadPoolSize = 8;
  private static int initialSize = 4;
  private static int backlog = 50;

  public static void main(String[] args) {
    System.out.println("SERVER START");

    NioHandleMap handleMap = new NioHandleMap();

    NioEventHandler sayHelloHandler = new NioSayHelloEventHandler();
    NioEventHandler updateProfileHandler = new NioUpdateProfileEventHandler();

    handleMap.put(sayHelloHandler.getHandle(), sayHelloHandler);
    handleMap.put(updateProfileHandler.getHandle(), updateProfileHandler);

    // 고정 스레드 풀 생성 threadPoolSize 만큼의 스레드만 사용한다.
    ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

    // 캐시 스레드 풀 생성 - 초기에 스레드를 만들고 새 스레드는 필요한 만큼 생성한다.
    // 이전에 만든 스레드를 이용할 수 있다면 재사용할 수도 있다.
    try {
      AsynchronousChannelGroup group = AsynchronousChannelGroup.withCachedThreadPool(executor, initialSize);

      // 스트림 지향의 리스닝 소켓을 위한 비동기 채널
      AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open(group);
      listener.bind(new InetSocketAddress(PORT), backlog);

      // 접속의 결과를 CompletionHandler 으로 비동기 IO 작업에 콜백 형식으로 작업 결과를 받는다.
      listener.accept(listener, new Dispatcher(handleMap));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

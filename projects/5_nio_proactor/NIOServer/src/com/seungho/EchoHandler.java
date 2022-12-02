package com.seungho;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class EchoHandler implements CompletionHandler<Integer, ByteBuffer> {

  private AsynchronousSocketChannel channel;

  public EchoHandler(AsynchronousSocketChannel channel) {
    this.channel = channel;
  }

  @Override
  public void completed(Integer result, ByteBuffer buffer) {
    if (result == -1) {
      try {
        channel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (result > 0) {
      buffer.flip();

      String msg = new String(buffer.array());
      System.out.println("echo: " + msg);

      // echo 하기
      buffer = ByteBuffer.wrap(msg.getBytes());
      Future<Integer> w = channel.write(buffer);
      try {
        w.get(); // 값을 읽어올 때 까지 블로킹
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
      try {
        buffer.clear();
        channel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void failed(Throwable exc, ByteBuffer attachment) {

  }
}

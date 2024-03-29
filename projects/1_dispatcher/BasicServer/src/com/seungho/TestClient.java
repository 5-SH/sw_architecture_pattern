package com.seungho;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
	public static void main(String[] args) {
		System.out.println("Client ON");
		
		try {
			// Server 에 telnet 으로 추가 접속하면 telnet 이 메시지를 보내기 전까지 블로킹 된다.
			while (true) {
				String message;

				Socket socket = new Socket("127.0.0.1", 5000);
				OutputStream out = socket.getOutputStream();
				message = "0x5001|홍길동|22";
				out.write(message.getBytes());
				socket.close();

				Socket socket2 = new Socket("127.0.0.1", 5000);
				OutputStream out2 = socket2.getOutputStream();
				message = "0x6001|hong|1234|홍길동|22|남성";
				out2.write(message.getBytes());
				socket2.close();

				Thread.sleep(1000);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}

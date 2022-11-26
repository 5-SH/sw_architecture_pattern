package com.seungho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInitializer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int port = 5000;
		System.out.println("Server ON : " + port);
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			Dispatcher dispatcher = new Dispatcher();
//			Socket connection;
			while (true) {
//				connection = serverSocket.accept(); // Dispatcher
//				InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
//				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//				String line = bufferedReader.readLine();
//				System.out.println("READ:" + line);
				
				dispatcher.dispatch(serverSocket);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

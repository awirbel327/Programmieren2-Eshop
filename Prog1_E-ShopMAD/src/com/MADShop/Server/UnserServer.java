package com.MADShop.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class UnserServer {
	private int port;
	
	public UnserServer (int port) {
		this.port = port;
	}
	
	public void startListening () {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("[Server] verbindung starten..");
						ServerSocket serverSocket = new ServerSocket(port);
						System.out.println("[Server] warten auf verbindung..");
						Socket remoteClientSocket = serverSocket.accept();
						System.out.println("[Server] Client verbunden..");
						
						Scanner s = new Scanner(new BufferedReader(new InputStreamReader(remoteClientSocket.getInputStream())));
						if (s.hasNextLine()) {
							System.out.println("[Server] Neue Nachricht vom Client: " + s.nextLine());
						}
						//Verbindung schlieﬂen
						s.close();
						remoteClientSocket.close();
						serverSocket.close();
					} catch (Exception e) {
					e.printStackTrace();
					}
				}
			}
		}).start();
	}
}

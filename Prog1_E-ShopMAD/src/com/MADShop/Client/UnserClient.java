package com.MADShop.Client;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class UnserClient {
	private InetSocketAddress address;
	
	public UnserClient (String hostname, int port) {
		address = new InetSocketAddress(hostname, port);
	}
	
	public void sendMessage (String msg) {
		try {
			Socket socket = new Socket();
			socket.connect(address, 5000);
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			pw.println(msg);
			pw.flush();
			
			//verbindung schlieﬂen
			pw.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.charlie.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

// 메시지 수신용 Thread
class ClientReceiver implements Runnable {
	private Socket socket;
	private DataInputStream in;
	private String name;
	private Logger logger = Logger.getLogger("Chatting");

	// 생성자
	ClientReceiver(Socket socket, String name) {
		this.socket = socket;
		this.name = name;

		try {
			// 서버로 부터 데이터를 받을 수 있도록 DataInputStream 생성
			this.in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
		}
	}

	public void run() {
		while (in != null) {
			try {
				// 서버로 부터 전송되는 데이터를 출력
				System.out.println(in.readUTF());
			} catch (IOException e) {
				logger.error("서버와의 연결이 끊어졌습니다. 다시 연결을 시도합니다.", e);
				try {
					this.socket = Client.getConnection(10);
					Client.setStart(this.socket, this.name);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
	}
}
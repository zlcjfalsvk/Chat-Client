package com.charlie.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.apache.log4j.Logger;

// 메시지 전송용 Thread
class ClientSender implements Runnable {
	private Socket socket;
	private DataOutputStream out;
	private String name;
	private Scanner scanner;
	private Logger logger = Logger.getLogger("Chatting");

	ClientSender(Socket socket, String name) {
		this.socket = socket;
		this.name = name;

		try {
			this.out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		scanner = new Scanner(System.in);
		try {
			
			// 시작하자 마자, 자신의 대화명을 서버로 전송
			if (out != null) {
				out.writeUTF(name);
			}
			while (out != null) {
				String message = "[" + name + "]" + scanner.nextLine();
//				logger.info(message);
				// 키보드로 입력받은 데이터를 서버로 전송
				out.writeUTF(message);
			}
		} catch (IOException e) {
		}
	}
}
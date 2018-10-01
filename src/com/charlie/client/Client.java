package com.charlie.client;

import java.io.IOException;
import java.net.*;

import org.apache.log4j.Logger;

public class Client {

	private static Logger logger = Logger.getLogger(Client.class.getName());
	private static Thread sender;
	private static Thread receiver;

	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			logger.error("USAGE : java MultichatClient 대화명");
			System.exit(0);
		}

		try {
			Socket socket = getConnection(10);

			setStart(socket, args[0]);

		} catch (ConnectException ce) {
			ce.printStackTrace();
		}
	}

	static void setStart(Socket socket, String name) {
		logger.info("연결에 되었습니다 : " + name);
		sender = setSender(socket, name);
		receiver = setReceiver(socket, name);
		sender.start();
		receiver.start();
	}
	
	static void stopThread() {
		sender.interrupt();
		receiver.interrupt();
	}

	static Thread setSender(Socket socket, String name) {
		return new Thread(new ClientSender(socket, name));
	}

	static Thread setReceiver(Socket socket, String name) {
		return new Thread(new ClientReceiver(socket, name));
	}

	@SuppressWarnings("resource")
	static Socket getConnection(int trycount) throws UnknownHostException, IOException {
		if (trycount == 0) {
			IOException e = new IOException("서버 연결에 실패했습니다. 다시 시도하여 주십시오.");
			logger.error(e.getMessage(), e);
			throw e;
		}
		Socket socket = null;

		Config conf = new Config();
		String serverIp = conf.getCONNECT_SERVER();
		try {
			socket = new Socket(serverIp, conf.getCONNECT_PORT());
		} catch (UnknownHostException e) {
			logger.error("호스트 정보가 맞지 않습니다.", e);
			throw new UnknownHostException("호스트 정보가 맞지 않습니다.");
		} catch (IOException e) {
			try {
				logger.info("연결을 다시 시도합니다.");
				Thread.sleep(5000);
				socket = getConnection(trycount - 1);
			} catch (InterruptedException inE) {
				// TODO Auto-generated catch block
				inE.printStackTrace();
				return socket;
			}
		}
		return socket;
	}

}

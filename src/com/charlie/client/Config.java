package com.charlie.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

public class Config {

	protected static String C_configFile = "conf/client.properties";

	protected String CONNECT_SERVER;
	protected int CONNECT_PORT;

	Config() {
		Properties properties = new Properties();

		try (FileInputStream file = new FileInputStream(C_configFile);) {
			properties.load(file);
			setConfig(properties);
			PropertyConfigurator.configure("conf/log4j.properties");
		} catch (IOException ee) {
		}
	}

	private void setConfig(Properties properties) {
		this.CONNECT_SERVER = properties.getProperty("socket.server").trim();
		this.CONNECT_PORT = Integer.parseInt(properties.getProperty("socket.port").trim());
	}

	public String getCONNECT_SERVER() {
		return CONNECT_SERVER;
	}

	public int getCONNECT_PORT() {
		return CONNECT_PORT;
	}
	
	
	
}

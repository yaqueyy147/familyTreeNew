package com.witkey.jetty;

import cq.hlideal.jetty.main.server.JettyWebServer;

public class JettyTest {
	//实际部署时删除
	public static int port = 8181;
	public static String host = "10.2.12.174";
	public static String tempdir = "d://temp14";
	public static String logdir = "d://temp14";
	public static String webdir = "src/main/webapp/";
	public static String contextpath = "";
	
	public static void main(String[] args) throws Exception{		
		JettyWebServer server = new JettyWebServer(
				port,
                host,
                tempdir,
                webdir,
                logdir,
                contextpath);		
		server.start();
        server.join();
	}
}

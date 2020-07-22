package cc.darhao.fund.main;

import com.jfinal.server.undertow.UndertowServer;


public class Main {
	
	public static void main(String[] args) throws Exception {
		//配置web服务器	
		UndertowServer undertowServer = UndertowServer.create(UndertowBoot.class);
		undertowServer.setPort(8080);
		undertowServer.start();
	}

}


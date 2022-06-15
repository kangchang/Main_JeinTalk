package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class threadStrartServer extends Thread{
	
	protected static List<ObjectOutputStream> list = new ArrayList<ObjectOutputStream>();
	static ServerSocket server_socket;
	
	@Override
	public void run() {
		// 서버 GUI 의 Port 의 입력값 가져오기
		int PORT = Integer.parseInt(MainServer.jtextArea.getText());
		
		// 서버에 접속하는 클라이언트 대기 및 쓰레드 할당
		try {
			server_socket = new ServerSocket(PORT);
			while (true) {
				try {
					Socket client_socket = server_socket.accept();
					System.out.println("접속자 : " + client_socket.getInetAddress());

					ServerInThread serverinthread;
					try {
						serverinthread = new ServerInThread(client_socket);
						serverinthread.start();
					} catch (ClassNotFoundException e1) {

					}
				} catch (IOException IOE) {

				}
			}
		} catch (IOException IOE2) {
			IOE2.printStackTrace();
		} catch (Exception e22) {

		} finally { // 서버 소켓 정리 및 종료
			if (server_socket != null) {
				try {
					server_socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}

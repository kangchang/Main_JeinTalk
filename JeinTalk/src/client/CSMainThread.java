package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CSMainThread extends Thread {

	private ServerSocket server_socket = null;
	private CSOutputthread output;
	private CSInputThread input;
	private String ip;
	private String port;
	
	public CSMainThread(String IP, String Port) {
		this.ip = IP;
		this.port = Port;
	}
	
	public CSMainThread(String port) {
		this.port = port;
	}

	@Override
	public void run() {
		try {
			server_socket = new ServerSocket(Integer.parseInt(port));
			ChatRoom.exTextArea.append("서버를 실행하였습니다.\n");

			Socket client_socket = server_socket.accept();
			System.out.println("접속자 : " + client_socket.getInetAddress());
			output = new CSOutputthread(client_socket, ChatRoom.name, ChatRoom.exTextArea2, ChatRoom.exTextArea,
					ChatRoom.jButton, ChatRoom.exScrollPane);
			output.start();
			input = new CSInputThread(client_socket, ChatRoom.exTextArea, ChatRoom.exScrollPane);
			input.start();

		} catch (IOException IOE2) {
			IOE2.printStackTrace();
		} finally {
			try {
				input.interrupt();
				output.interrupt();
				server_socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
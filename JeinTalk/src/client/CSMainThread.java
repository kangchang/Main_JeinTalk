package client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JLabel;

public class CSMainThread extends Thread {

	private ServerSocket server_socket = null;
	private CSOutputthread output = null;
	private CSInputThread input = null;
	private String ip;
	private String port;
	private JLabel exlblipLabe;
	private JLabel exlblportLabe;

	public CSMainThread(String IP, String Port) {
		this.ip = IP;
		this.port = Port;
	}

	public CSMainThread(String port) {
		this.port = port;
	}

	public void setexlblipLabe(JLabel exlblipLabe) {
		this.exlblipLabe = exlblipLabe;
	}

	public void setexlblportLabe(JLabel exlblportLabe) {
		this.exlblportLabe = exlblportLabe;
	}

	@Override
	public void run() {
		try {
			server_socket = new ServerSocket(Integer.parseInt(port));
			ChatRoom.exTextArea.append("서버를 실행하였습니다.\n");

			Socket client_socket = server_socket.accept();
			output = new CSOutputthread(client_socket, ChatRoom.name, ChatRoom.exTextArea2, ChatRoom.exTextArea,
					ChatRoom.jButton, ChatRoom.exScrollPane);
			output.start();
			input = new CSInputThread(client_socket, ChatRoom.exTextArea, ChatRoom.exScrollPane);
			input.start();

		} catch (IOException IOE2) {
			exlblipLabe.setText("IP 오류");
			exlblportLabe.setText("Port 오류");
		} finally {
			try {
				if (input != null && output != null && server_socket != null) {
					input.interrupt();
					output.interrupt();
					server_socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
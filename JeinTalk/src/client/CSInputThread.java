package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CSInputThread extends Thread {
	private Socket socket;
	private ObjectInputStream thr_in_socket = null;
	private String userto;
	private JTextArea jTextArea1;
	private JScrollPane scrollPane;

	public CSInputThread(Socket socket, JTextArea jTextArea1, JScrollPane scrollPane) {
		this.socket = socket;
		this.jTextArea1 = jTextArea1;
		this.scrollPane = scrollPane;
		try {
			thr_in_socket = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				userto = (String) thr_in_socket.readObject();
					jTextArea1.append(userto + "\n"); // 채팅
					scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			} catch (ClassNotFoundException e) {

			} catch (IOException e) {
				try {
					if ( thr_in_socket != null) {
						thr_in_socket.close();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}

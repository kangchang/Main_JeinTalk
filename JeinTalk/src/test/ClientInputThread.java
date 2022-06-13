package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientInputThread extends Thread {
	private Socket socket;
	private ObjectInputStream thr_in_socket = null;
	private String userto;
	private JFrame jframe;
	private JTextArea jTextArea1;
	private JTextArea jTextArea2;
	private JButton jButton;
	private JScrollPane scrollPane;
	private JTextArea jFrameListTextArea;

	public ClientInputThread(Socket socket, JTextArea jTextArea1, JTextArea jTextArea2, JButton jButton,
			JScrollPane scrollPane, JTextArea jFrameListTextArea) {
		this.socket = socket;
		this.jframe = jframe;
		this.jTextArea1 = jTextArea1;
		this.jTextArea2 = jTextArea2;
		this.jButton = jButton;
		this.scrollPane = scrollPane;
		this.jFrameListTextArea = jFrameListTextArea;
		try {
			thr_in_socket = new ObjectInputStream(socket.getInputStream());
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
				String userName = "";
			} catch (ClassNotFoundException e) {

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
}

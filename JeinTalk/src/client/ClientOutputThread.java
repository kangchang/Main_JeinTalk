package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class ClientOutputThread extends Thread {
	private Socket socket;
	private String name;
	private String message;
	private ObjectOutputStream thr_out_socket = null;
	private User user = new User();
	private JTextArea jTextArea2;

	private JButton jButton;

	public ClientOutputThread(Socket socket, String name, JTextArea jTextArea2, JButton jButton) {
		this.socket = socket;
		this.name = name;
		this.jTextArea2 = jTextArea2;
		this.jButton = jButton;
		try {
			thr_out_socket = new ObjectOutputStream(this.socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jTextArea2.getText().equals("") != true) {
					try {
						message = jTextArea2.getText();
						user.setMessage(message);
						user.setName(name);
						thr_out_socket.writeObject((Object) (user.toString()));// 읽는거
						jTextArea2.setText("");
						thr_out_socket.flush();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		jTextArea2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (jTextArea2.getText().equals("") != true) {
						if (e.isControlDown()) { // Ctrl + Enter = 줄 바꿈
							jTextArea2.append(System.lineSeparator());
						} else { // Enter = 전송
							try {
								message = jTextArea2.getText();
								user.setMessage(message);
								user.setName(name);
								thr_out_socket.writeObject((Object) (user.toString()));// 읽는거
								jTextArea2.setText("");
								thr_out_socket.flush();

							} catch (Exception e1) {
								e1.printStackTrace();
							}
							e.consume();
						}
					}
				}
			}
		});
	}

}
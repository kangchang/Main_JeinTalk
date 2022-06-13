package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
public class MainServer {
	private static int PORT;
	protected static List<ObjectOutputStream> list = new ArrayList<ObjectOutputStream>();
	private static JFrame jframe;
	private static JTextArea jtextArea;
	private static JLabel jLabel;

	public static void main(String[] args) {
		runServer();

	}

	public static void runServer() {
		jframe = new JFrame("서버");
		jframe.setBounds(100, 100, 300, 200);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.getContentPane().setLayout(null);
		jframe.setLocationRelativeTo(null);
		jframe.setResizable(false);
		jframe.setVisible(true);

		jLabel = new JLabel("Port");
		jLabel.setBounds(50, 50, 50, 20);
		jframe.add(jLabel);

		jtextArea = new JTextArea("0000");
		jtextArea.setBounds(100, 50, 50, 20);
		jframe.add(jtextArea);
		jtextArea.revalidate();
		jtextArea.repaint();

		JButton jButton = new JButton("Enter");
		jButton.setBounds(175, 50, 100, 20);
		jframe.add(jButton);
		jButton.revalidate();
		jButton.repaint();


		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PORT = Integer.parseInt(jtextArea.getText());
					try (ServerSocket server_socket = new ServerSocket(PORT)) {
						while (true) {
							try {
								System.out.println("클라이언트 접속 대기중...\n");

								Socket client_socket = server_socket.accept();
								System.out.println("접속자 : " + client_socket.getInetAddress());

								ServerInThread serverinthread = new ServerInThread(client_socket, client_socket);
								serverinthread.start();

							} catch (Exception ee) {
								ee.getStackTrace();
							}
						}
					} catch (Exception eee) {
						eee.getStackTrace();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				} finally {

				}
			}

		});

	}
}
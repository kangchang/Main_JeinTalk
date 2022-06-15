package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class MainServer {
	protected static JFrame jframe;
	protected static JTextArea jtextArea;
	protected static JLabel jLabel;
	protected static JLabel strStatusMessage;
	
	public static void main(String[] args) {
		// 서버 GUI 동작
		runServer();
	}

	public static void runServer() {
		// Frame 생성 및 설정
		jframe = new JFrame("서버");
		jframe.setBounds(100, 100, 300, 200);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.getContentPane().setLayout(null);
		jframe.setLocationRelativeTo(null);
		jframe.setResizable(false);
		jframe.setVisible(true);

		// 서버 실행 유무 문구
		strStatusMessage = new JLabel("");
		strStatusMessage.setBounds(70, 100, 100, 20);
		jframe.add(strStatusMessage);
		
		jLabel = new JLabel("Port");
		jLabel.setBounds(50, 50, 50, 20);
		jframe.add(jLabel);

		// Port 입력 필드
		jtextArea = new JTextArea("0000");
		jtextArea.setBounds(100, 50, 50, 20);
		jframe.add(jtextArea);
		jtextArea.revalidate();
		jtextArea.repaint();

		// 서버 실행 버튼
		JButton jButton = new JButton("On");
		jButton.setBounds(175, 50, 100, 20);
		jframe.add(jButton);
		jButton.revalidate();
		jButton.repaint();
		
		// 서버 종료 버튼
		JButton jButton2 = new JButton("Off");
		jButton2.setBounds(175, 70, 100, 20);
		jframe.add(jButton2);
		jButton2.revalidate();
		jButton2.repaint();
		
		// 서버 실행 메소드 이벤트
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				threadStrartServer threadStrartServer = new threadStrartServer();
				strStatusMessage.setText("서버 실행중......");
				threadStrartServer.start();
				
			}
		});
		
		// 서버 종료 메소드 이벤트
		jButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				threadStrartServer.interrupted();
				strStatusMessage.setText("서버 종료됨.");
			}
		});
		
		
		// X버튼으로 채팅프로그램 종료 이벤트
		jframe.addWindowListener((WindowListener) new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				jframe.getContentPane().invalidate();
				jframe.getContentPane().validate();
				jframe.getContentPane().repaint();
				threadStrartServer.interrupted();
			}
		});
	}
}
package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import db.ConnectionPool;

public class ChatRoom {
	private String server_IP;
	private int server_PORT;

	private CSOutputthread threadout;
	private CSInputThread threadin;

	// private static JFrame jFrameList = new JFrame("참여자 리스트");
	private static JTextArea jFrameListTextArea = new JTextArea();
	protected static JTextArea textArea;
	protected static JTextArea textArea_1;

	protected static JTextArea exTextArea;
	protected static JButton jButton;
	protected static JTextArea exTextArea2;
	protected static JScrollPane exScrollPane; // +버튼 스크롤 채팅창
	protected static JFrame exFrame;
	protected static JScrollPane scrollPane2;

	private static JFrame Uframe;
	private static JTextField UtextField;
	private static JLabel UlblNewLabel_2;
	private static JTextField UtextField_1;
	private static JLabel UlblNewLabel_3;
	private static JTextField UtextField_2;
	private static JLabel UlblNewLabel_4;
	private static JTextField UtextField_3;
	private static JLabel UlblNewLabel_5;
	private static JTextField UtextField_4;
	private static JLabel UlblNewLabel_6;

	String getUserNameFromDb = null;
	String getIdFromDb = null;
	String getMailFromDb = null;
	String getPwFromDb = null;

	private String id;
	protected static String name;

	// 로그인한 시간
	private static String loginDate;

	private ConnectionPool cp;

	public ChatRoom(String id, String name, String loginDate, String ip, int port, ConnectionPool cp) {
		this.id = id;
		this.name = name;
		this.server_IP = ip;
		this.server_PORT = port;
		this.cp = cp;

		this.loginDate = loginDate;
	}

	public void runChatRoom() throws SQLException {
		try {
			
			
			// =====================================Swing=====================================
			JFrame jframe = new JFrame(name + " " + loginDate);
			jframe.setBounds(100, 100, 1120, 600);
			jframe.getContentPane().setLayout(null);
			jframe.setLocationRelativeTo(null);
			jframe.setResizable(false);
			jframe.setVisible(true);

			JTextArea textArea1 = new JTextArea();
			textArea1.setBounds(45, 10, 690, 387);
			textArea1.revalidate();
			textArea1.repaint();
			textArea1.setLineWrap(true);
			textArea1.setWrapStyleWord(true);
			textArea1.setEditable(false);

			JScrollPane scrollPane = new JScrollPane(textArea1);
			scrollPane.setBounds(45, 80, 690, 340);
			scrollPane.revalidate();
			scrollPane.repaint();

			jframe.getContentPane().add(scrollPane);
			jframe.getContentPane().add(scrollPane);

			// 입력창
			JTextArea textArea2 = new JTextArea();
			textArea2.setBounds(45, 440, 479, 93);
			jframe.getContentPane().add(textArea2);
			textArea2.setLineWrap(true);
			textArea2.revalidate();
			textArea2.repaint();

			JButton btnNewButton = new JButton("SEND");
			btnNewButton.setFont(new Font("굴림", Font.BOLD, 18));
			btnNewButton.setBounds(555, 440, 180, 92);

			jframe.getContentPane().add(btnNewButton);

			btnNewButton.revalidate();
			btnNewButton.repaint();

			// 온라인
			textArea = new JTextArea();
			textArea.setBounds(773, 82, 323, 204);
			jframe.getContentPane().add(textArea);
			textArea.setEditable(false);

			// 오프라인
			textArea_1 = new JTextArea();
			textArea_1.setBounds(773, 329, 323, 204);
			jframe.getContentPane().add(textArea_1);
			textArea_1.setEditable(false);

			JButton btnNewButton_1 = new JButton("회원 정보 수정");
			btnNewButton_1.setBounds(45, 7, 131, 23);
			jframe.getContentPane().add(btnNewButton_1);
			// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
			// 회원정보수정 ( 개선 요밍 ) 이벤트
			btnNewButton_1.addActionListener(new ActionListener() {
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet result = null;
				StringBuffer sql = null;

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						conn = cp.getConnection();
						sql = new StringBuffer();
						sql.setLength(0);
						sql.append("SELECT id, pw, username, email FROM user WHERE id=?");
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, id);
						result = pstmt.executeQuery();

						while (result.next()) {
							getIdFromDb = result.getString("id");
							getPwFromDb = result.getString("pw");
							getUserNameFromDb = result.getString("username");
							getMailFromDb = result.getString("email");
						}

					} catch (SQLException exc) {
						exc.printStackTrace();
					} finally {
						try {
							cp.releaseConnection(conn);
							pstmt.close();
							result.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					try {
						Uframe = new JFrame();
						Uframe.setBounds(100, 100, 720, 555);
						Uframe.getContentPane().setLayout(null);
						Uframe.setResizable(false);
						Uframe.setVisible(true);

						JLabel UlblNewLabel = new JLabel("회원정보수정");
						UlblNewLabel.setFont(new Font("굴림", Font.PLAIN, 18));
						UlblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
						UlblNewLabel.setBounds(278, 50, 120, 60);
						Uframe.getContentPane().add(UlblNewLabel);

						JLabel UlblNewLabel_1 = new JLabel("이름");
						UlblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14));
						UlblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
						UlblNewLabel_1.setBounds(188, 140, 133, 30);
						Uframe.getContentPane().add(UlblNewLabel_1);

						UtextField = new JTextField(getUserNameFromDb);
						UtextField.setBounds(333, 145, 125, 21);
						UtextField.setColumns(10);
						Uframe.getContentPane().add(UtextField);

						UlblNewLabel_2 = new JLabel("ID");
						UlblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
						UlblNewLabel_2.setFont(new Font("굴림", Font.PLAIN, 14));
						UlblNewLabel_2.setBounds(188, 175, 133, 30);
						Uframe.getContentPane().add(UlblNewLabel_2);

						UtextField_1 = new JTextField(getIdFromDb);
						UtextField_1.setColumns(10);
						UtextField_1.setBounds(333, 180, 125, 21);
						Uframe.getContentPane().add(UtextField_1);
						UtextField_1.setEditable(false);

						UlblNewLabel_3 = new JLabel("E-Mail");
						UlblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
						UlblNewLabel_3.setFont(new Font("굴림", Font.PLAIN, 14));
						UlblNewLabel_3.setBounds(188, 218, 133, 30);
						Uframe.getContentPane().add(UlblNewLabel_3);

						UtextField_2 = new JTextField(getMailFromDb);
						UtextField_2.setColumns(10);
						UtextField_2.setBounds(333, 223, 125, 21);
						Uframe.getContentPane().add(UtextField_2);

						UlblNewLabel_4 = new JLabel("Password");
						UlblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
						UlblNewLabel_4.setFont(new Font("굴림", Font.PLAIN, 14));
						UlblNewLabel_4.setBounds(188, 257, 133, 30);
						Uframe.getContentPane().add(UlblNewLabel_4);

						UtextField_3 = new JTextField(getPwFromDb);
						UtextField_3.setColumns(10);
						UtextField_3.setBounds(333, 262, 125, 21);
						Uframe.getContentPane().add(UtextField_3);

						UlblNewLabel_5 = new JLabel("Password 확인");
						UlblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
						UlblNewLabel_5.setFont(new Font("굴림", Font.PLAIN, 14));
						UlblNewLabel_5.setBounds(188, 306, 133, 30);
						Uframe.getContentPane().add(UlblNewLabel_5);

						UtextField_4 = new JTextField(getPwFromDb);
						UtextField_4.setColumns(10);
						UtextField_4.setBounds(333, 311, 125, 21);
						Uframe.getContentPane().add(UtextField_4);

						UlblNewLabel_6 = new JLabel("");
						UlblNewLabel_6.setBounds(333, 205, 133, 15);
						Uframe.getContentPane().add(UlblNewLabel_6);

						JButton UbtnNewButton = new JButton("중복확인");
						UbtnNewButton.setBounds(503, 179, 91, 23);
						Uframe.getContentPane().add(UbtnNewButton);
						UbtnNewButton.setEnabled(false);

						JButton UbtnNewButton_1 = new JButton("수정완료");
						UbtnNewButton_1.setBounds(230, 386, 91, 23);
						Uframe.getContentPane().add(UbtnNewButton_1);
						UbtnNewButton_1.setEnabled(false);

						// PW 리스너
						UtextField_3.getDocument().addDocumentListener(new DocumentListener() {

							@Override
							public void insertUpdate(DocumentEvent e) {
								Check();
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								Check();
							}

							@Override
							public void changedUpdate(DocumentEvent e) {
								Check();
							}

							public void Check() {
								if (UtextField_3.getText().equals(UtextField_4.getText())
										&& !UtextField.getText().equals("") && !UtextField_2.getText().equals("")
										&& !UtextField_3.getText().equals("") && !UtextField_4.getText().equals("")) {
									UbtnNewButton_1.setEnabled(true);
								} else if (!UtextField_3.getText().equals(UtextField_4.getText())
										&& !UtextField.getText().equals("") && !UtextField_2.getText().equals("")
										&& !UtextField_3.getText().equals("") && !UtextField_4.getText().equals("")) {
									UbtnNewButton_1.setEnabled(false);
								}
							}
						});

						// PW 확인 리스너
						UtextField_4.getDocument().addDocumentListener(new DocumentListener() {

							@Override
							public void insertUpdate(DocumentEvent e) {
								Check();
							}

							@Override
							public void removeUpdate(DocumentEvent e) {
								Check();
							}

							@Override
							public void changedUpdate(DocumentEvent e) {
								Check();
							}

							public void Check() {
								if (UtextField_3.getText().equals(UtextField_4.getText())
										&& !UtextField.getText().equals("") && !UtextField_2.getText().equals("")
										&& !UtextField_3.getText().equals("") && !UtextField_4.getText().equals("")) {
									UbtnNewButton_1.setEnabled(true);
								} else if (!UtextField_3.getText().equals(UtextField_4.getText())
										&& !UtextField.getText().equals("") && !UtextField_2.getText().equals("")
										&& !UtextField_3.getText().equals("") && !UtextField_4.getText().equals("")) {
									UbtnNewButton_1.setEnabled(false);
								}
							}
						});

						// 등록 리스너
						UbtnNewButton_1.addActionListener(new ActionListener() {
							Connection conn = cp.getConnection();
							PreparedStatement pstmt = null;
							StringBuffer sql = new StringBuffer();

							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									if (!UtextField.equals(getUserNameFromDb)) {
										sql.setLength(0);
										sql.append("UPDATE user SET username=? WHERE id=?");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, UtextField.getText());
										pstmt.setString(2, id);
										pstmt.executeUpdate();
									}
									if (!UtextField_2.equals(getMailFromDb)) {
										sql.setLength(0);
										sql.append("UPDATE user SET email=? WHERE id=?");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, UtextField_2.getText());
										pstmt.setString(2, id);
										pstmt.executeUpdate();
									}
									if (!UtextField_3.equals(getMailFromDb)) {
										sql.setLength(0);
										sql.append("UPDATE user SET pw=? WHERE id=?");
										pstmt = conn.prepareStatement(sql.toString());
										pstmt.setString(1, UtextField_3.getText());
										pstmt.setString(2, id);
										pstmt.executeUpdate();
									}

									Uframe.dispose();
								} catch (SQLException exc) {
									exc.printStackTrace();
								} finally {
									try {
										cp.releaseConnection(conn);
										pstmt.close();
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
								}
							}

						});

						JButton UbtnNewButton_1_1 = new JButton("취소");
						UbtnNewButton_1_1.setBounds(379, 386, 91, 23);
						Uframe.getContentPane().add(UbtnNewButton_1_1);

						// 취소
						UbtnNewButton_1_1.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								Uframe.dispose();
							}

						});
					} catch (SQLException exc) {
						exc.printStackTrace();
					}
				}

			});
			// ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
			JButton btnNewButton_1_1 = new JButton("로그아웃");
			btnNewButton_1_1.setBounds(188, 7, 131, 23);
			jframe.getContentPane().add(btnNewButton_1_1);

			JLabel lblNewLabe01 = new JLabel("온라인");
			lblNewLabe01.setFont(new Font("굴림", Font.PLAIN, 14));
			lblNewLabe01.setBounds(773, 55, 81, 17);
			jframe.getContentPane().add(lblNewLabe01);

			JLabel lblNewLabe02 = new JLabel("오프라인");
			lblNewLabe02.setFont(new Font("굴림", Font.PLAIN, 14));
			lblNewLabe02.setBounds(773, 304, 81, 15);
			jframe.getContentPane().add(lblNewLabe02);

			// 1:1 채팅 기능
			JLabel exlblipLabe = new JLabel("IP 입력");
			exlblipLabe.setFont(new Font("굴림", Font.PLAIN, 10));
			exlblipLabe.setBounds(440, 30, 108, 23);
			jframe.getContentPane().add(exlblipLabe);

			JLabel exlblportLabe = new JLabel("포트 입력");
			exlblportLabe.setFont(new Font("굴림", Font.PLAIN, 10));
			exlblportLabe.setBounds(440, 53, 108, 23);
			jframe.getContentPane().add(exlblportLabe);

			JLabel exlblerrorLabe = new JLabel("");
			exlblerrorLabe.setFont(new Font("굴림", Font.PLAIN, 10));
			exlblerrorLabe.setBounds(520, 15, 108, 23);
			exlblerrorLabe.setForeground(Color.RED);
			jframe.getContentPane().add(exlblerrorLabe);

			JTextField exInputIPTextField = new JTextField("192.168.0.###");
			exInputIPTextField.setBounds(520, 30, 108, 23);
			exInputIPTextField.setColumns(10);
			jframe.getContentPane().add(exInputIPTextField);

			JTextField exInputPortTextField = new JTextField("0000");
			exInputPortTextField.setBounds(520, 53, 108, 23);
			exInputPortTextField.setColumns(10);
			jframe.getContentPane().add(exInputPortTextField);

			JButton btnNewButton_2 = new JButton("생성");
			btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 14));
			btnNewButton_2.setBounds(633, 51, 100, 30);
			jframe.getContentPane().add(btnNewButton_2);

			JButton btnNewButton_2_1 = new JButton("접속");
			btnNewButton_2_1.setFont(new Font("굴림", Font.BOLD, 14));
			btnNewButton_2_1.setBounds(633, 21, 100, 30);
			jframe.getContentPane().add(btnNewButton_2_1);

			JButton btnNewButton_3 = new JButton("채팅방1");
			btnNewButton_3.setBounds(45, 57, 91, 23);
			jframe.getContentPane().add(btnNewButton_3);

			JButton btnNewButton_4 = new JButton("X");
			btnNewButton_4.setFont(new Font("굴림", Font.PLAIN, 7));
			btnNewButton_4.setBounds(135, 57, 38, 23);
			jframe.getContentPane().add(btnNewButton_4);

			// + 버튼 클릭 후 1:1 서버 생성
			btnNewButton_2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String strExPortName = exInputPortTextField.getText(); // 1:1 채팅방 초대 유저 이름

					exFrame = new JFrame("포트 " + strExPortName + "번 1:1 채팅방.");
					exFrame.setBounds(100, 100, 650, 480);
					exFrame.setLocationRelativeTo(null);
					exFrame.setResizable(false);
					exFrame.getContentPane().setLayout(null);

					// 채팅창
					exTextArea = new JTextArea();
					exTextArea.setLineWrap(true);
					exTextArea.setBounds(12, 10, 612, 262);
					exTextArea.setWrapStyleWord(true);
					exTextArea.setEditable(false);

					// 채팅창 생성 스크롤
					exScrollPane = new JScrollPane(exTextArea);
					exScrollPane.setBounds(12, 10, 612, 262);
					exScrollPane.revalidate();
					exScrollPane.repaint();

					exFrame.getContentPane().add(exScrollPane);

					// 입력창
					exTextArea2 = new JTextArea();
					exTextArea2.setLineWrap(true);
					exTextArea2.setBounds(12, 293, 455, 129);
					exTextArea2.setWrapStyleWord(true);

					JScrollPane scrollPane2 = new JScrollPane(exTextArea2);
					scrollPane2.setBounds(12, 293, 455, 129);
					scrollPane2.revalidate();
					scrollPane2.repaint();

					exFrame.getContentPane().add(scrollPane2);

					// SEND 버튼
					jButton = new JButton("SEND");
					jButton.setFont(new Font("굴림", Font.BOLD, 18));
					jButton.setBounds(482, 294, 142, 128);
					exFrame.getContentPane().add(jButton);
					exFrame.setVisible(true);

					// 서버 시작
					CSMainThread test = new CSMainThread(strExPortName);
					test.setexlblipLabe(exlblipLabe);
					test.setexlblportLabe(exlblportLabe);
					test.start();

					// X버튼으로 채팅프로그램 종료 이벤트
					exFrame.addWindowListener((WindowListener) new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							exFrame.getContentPane().invalidate();
							exFrame.getContentPane().validate();
							exFrame.getContentPane().repaint();
							test.interrupt();
						}
					});
				}
			});

			btnNewButton_2_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String strExUserName = exInputIPTextField.getText(); // 1:1 채팅방 초대 유저 이름
					String strExPortName = exInputPortTextField.getText(); // 1:1 채팅방 초대 유저 이름

					// 소켓 연결하기
					Socket client;
					try {
						client = new Socket(strExUserName, Integer.parseInt(strExPortName));
						if (client.isConnected() == true) {
							exFrame = new JFrame("포트 " + strExPortName + "번 1:1 채팅방.");
							exFrame.setBounds(100, 100, 650, 480);
							exFrame.setLocationRelativeTo(null);
							exFrame.setResizable(false);
							exFrame.getContentPane().setLayout(null);
							exlblerrorLabe.setText("");

							// 채팅창
							exTextArea = new JTextArea();
							exTextArea.setLineWrap(true);
							exTextArea.setBounds(12, 10, 612, 262);
							exTextArea.setWrapStyleWord(true);
							exTextArea.setEditable(false);

							exScrollPane = new JScrollPane(exTextArea);
							exScrollPane.setBounds(12, 10, 612, 262);
							exScrollPane.revalidate();
							exScrollPane.repaint();

							exFrame.getContentPane().add(exScrollPane);

							// 입력창
							exTextArea2 = new JTextArea();
							exTextArea2.setLineWrap(true);
							exTextArea2.setBounds(12, 293, 455, 129);
							exTextArea2.setWrapStyleWord(true);

							scrollPane2 = new JScrollPane(exTextArea2);
							scrollPane2.setBounds(12, 293, 455, 129);
							scrollPane2.revalidate();
							scrollPane2.repaint();

							exFrame.getContentPane().add(scrollPane2);

							// SEND 버튼
							jButton = new JButton("SEND");
							jButton.setFont(new Font("굴림", Font.BOLD, 18));
							jButton.setBounds(482, 294, 142, 128);
							exFrame.getContentPane().add(jButton);
							exFrame.setVisible(true);
							exTextArea.append("개인서버와 연결 되었습니다.\n");
							threadout = new CSOutputthread(client, name, exTextArea2, exTextArea, jButton, exScrollPane);
							threadout.start();
							threadin = new CSInputThread(client, exTextArea, exScrollPane);
							threadin.start();
						} else {
							exlblerrorLabe.setText("다른사람이 접속중입니다.");
						}

						if (!exFrame.equals(null)) {
							// X버튼으로 채팅프로그램 종료 이벤트
							exFrame.addWindowListener((WindowListener) new WindowAdapter() {
								public void windowClosing(WindowEvent e) {
									exFrame.getContentPane().invalidate();
									exFrame.getContentPane().validate();
									exFrame.getContentPane().repaint();
									if (threadin == null && threadout == null) {
										threadin.interrupt();
										threadout.interrupt();
									}
								}
							});
						}

					} catch (UnknownHostException e1) {
						exlblerrorLabe.setText("IP 및 포트를 확인해 주세요");
					} catch (IOException e1) {

					}
				}
			});
			// =====================================Swing=====================================
			
			Socket client = new Socket(server_IP, server_PORT);
			textArea1.append("서버와 연결 되었습니다.\n");
			ClientOutputThread threadout = new ClientOutputThread(client, name, textArea2, btnNewButton);
			threadout.start();
			ClientInputThread threadin = new ClientInputThread(client, textArea1, scrollPane);
			threadin.start();
			UserOnOffline useronoffline = new UserOnOffline(cp);
			useronoffline.start();
			
			btnNewButton_1_1.addActionListener(new ActionListener() {
				Connection conn = cp.getConnection();
				PreparedStatement pstmt = null;
				StringBuffer sql = new StringBuffer();

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						sql.append("UPDATE user SET flag=0 WHERE id=?");
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, id);
						pstmt.executeUpdate();
						
						threadout.interrupt();
						threadin.interrupt();
						useronoffline.interrupt();

						jframe.dispose();
						Client.textField.setText("");
						Client.textField_1.setText("");
						Client.frame.setVisible(true);
					} catch (SQLException exc) {
						exc.printStackTrace();
					} finally {
						try {
							cp.releaseConnection(conn);
							pstmt.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			
			// X 채팅프로그램 종료 이벤트
			jframe.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					Connection conn = null;
					PreparedStatement pstmt = null;
					StringBuffer sql = new StringBuffer();
					try {
						conn = cp.getConnection();
						sql.append("UPDATE user SET flag=0 WHERE id=?");
						pstmt = conn.prepareStatement(sql.toString());
						pstmt.setString(1, id);
						pstmt.executeUpdate();
						
						threadout.interrupt();
						threadin.interrupt();
						useronoffline.interrupt();
						jframe.dispose();
					} catch (SQLException exc) {
						exc.printStackTrace();
					} finally {
						try {
							cp.releaseConnection(conn);
							pstmt.close();
							System.exit(0);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});

		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerInThread extends Thread {

	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private String user, message;

	// 생성자
	public ServerInThread(Socket socket) throws IOException, ClassNotFoundException {
		this.in = new ObjectInputStream(socket.getInputStream());
		this.out = new ObjectOutputStream(socket.getOutputStream());
		threadStrartServer.list.add(this.out);
	}

	@Override
	public void run() {
		
		// 메시지 In/Out put 동작
		try {
			while (true) {
				this.message = (String) this.in.readObject();
				send();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
		}
	}

	// 클라이언트로 전송하는 동작
	public void send() {
		for (int i = 0; i < (threadStrartServer.list).size(); i++) {
			try {
				(threadStrartServer.list.get(i)).writeObject((Object) (message));
				(threadStrartServer.list.get(i)).flush();
			} catch (IOException e) {
				threadStrartServer.list.remove(i);
				i--;
			}
		}
	}
}

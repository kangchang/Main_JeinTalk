package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerInThread extends Thread {

	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private String user;

	public ServerInThread(Socket socket1, Socket socket2) {

		try {
			this.in = new ObjectInputStream(socket1.getInputStream());
			this.out = new ObjectOutputStream(socket1.getOutputStream());

			MainServer.list.add(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
				try {
					user = (String) in.readObject();
					send();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}			
		}
	}

	public void send() {
		for (int i = 0; i < (MainServer.list).size(); i++) {
			try {
				(MainServer.list.get(i)).writeObject((Object) (user));
				(MainServer.list.get(i)).flush();
			} catch (IOException e) {
				MainServer.list.remove(i);
				i--;
			}
		}
	}
}

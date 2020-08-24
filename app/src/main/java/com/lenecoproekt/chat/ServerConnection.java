package com.lenecoproekt.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ServerConnection {
    private int PORT = 8189;
    private String IP_ADDRESS;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;



    public ServerConnection() throws IOException {
        this.PORT = 8189;
        this.IP_ADDRESS = "10.0.2.2";
        this.socket = new Socket(IP_ADDRESS, PORT);
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }


    public String registration(String login, String nickname, String password) throws InterruptedException, IOException {
        final String[] msg = {""};
        Thread connect = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/regresult ")) {
                            String result = str.split("\\s")[1];
                            if (result.equals("ok")) {
                                msg[0] = "Регистрация прошла успешно";
                            } else {
                                msg[0] = "Регистрация не получилась, возможно логин или никнейм заняты";
                            }
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        connect.start();
        try {
            out.writeUTF(String.format("/reg %s %s %s", login, password, nickname));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connect.join();
        socket.close();
        in.close();
        out.close();
        return msg[0];
    }
    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setIn(DataInputStream in) {
        this.in = in;
    }

    public void setOut(DataOutputStream out) {
        this.out = out;
    }
}

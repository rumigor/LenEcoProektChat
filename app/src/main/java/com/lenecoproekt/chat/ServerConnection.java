package com.lenecoproekt.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public final class ServerConnection {
    private static ServerConnection serverConnection;
    int PORT = 8189;
    String IP_ADDRESS;
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private ServerConnection() throws IOException {
        this.PORT = 8189;
        this.IP_ADDRESS = "localhost";
        this.socket = new Socket(IP_ADDRESS, PORT);
        this.in = new DataInputStream(socket.getInputStream());;
        this.out = new DataOutputStream(socket.getOutputStream());;
    }

    public static ServerConnection getServerConnection() throws IOException {
        if (serverConnection == null) {
            serverConnection = new ServerConnection();
        }
        return serverConnection;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferFile;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author abdalla
 */
public class ClientMain {
private DirectoryTxr transmitter = null;
Socket clientSocket = null;
private boolean connectedStatus = false;
private String ipAddress;
String srcPath = null;
String dstPath = "D:/iti/reciver/abdalla.jpg";
public ClientMain() {

}

public void setIpAddress(String ip) {
    this.ipAddress = ip;
}

public void setSrcPath(String path) {
    this.srcPath = path;
}

public void setDstPath(String path) {
    this.dstPath = path;
}

private void createConnection() {
    Runnable connectRunnable = new Runnable() {
        public void run() {
            while (!connectedStatus) {
                try {
                    clientSocket = new Socket(ipAddress, 3339);
                    connectedStatus = true;
                    transmitter = new DirectoryTxr(clientSocket, srcPath, dstPath);
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }

        }
    };
    Thread connectionThread = new Thread(connectRunnable);
    connectionThread.start();
}
}
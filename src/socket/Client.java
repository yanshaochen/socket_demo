package socket;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        //the server's ip,port
        Socket client = new Socket("192.168.0.105",50000);
        //prepare os to send data to server
        OutputStream os = client.getOutputStream();
        //read local files
        FileInputStream fis = new FileInputStream("C:\\Users\\yansh\\Pictures\\tcpip.png");
        byte[] bytes=new byte[1024];
        int data;
        while ((data=fis.read(bytes))!=-1){
            os.write(bytes,0,data);
        }
        os.close();
        fis.close();
        client.close();
        System.out.println("发送成功!");
    }
}

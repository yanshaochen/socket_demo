package socket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Service {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(50001);
        //create listener..
        System.out.println("listening..");
        Socket accept = server.accept();
        InputStream is = accept.getInputStream();
        //save to local files
        FileOutputStream fos = new FileOutputStream("C:\\Users\\yansh\\Downloads\\abc.txt");
        byte[] bytes=new byte[1024];
        int data;
        while ((data=is.read(bytes))!=-1){
            fos.write(bytes,0,data);
        }
        System.out.println("成功接收!");
        fos.close();
        is.close();
        server.close();
    }
}

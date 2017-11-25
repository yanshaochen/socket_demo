package socket.httpserver.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPClient {
    public static void main(String[] args) {
        //确定HTTP请求的uri
        String uri="hello1.htm";
        if (args.length!=0)
            uri=args[0];
        doGet("localhost",8080,uri);
    }

    private static void doGet(String host, int post, String uri) {
        Socket socket=null;
        try{
            socket=new Socket(host,post);
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            /*创建HTTP请求*/
            StringBuffer sb=new StringBuffer("GET "+uri+" HTTP/1.1\r\n");//HTTP请求的第一行
            //HTTP请求头
            sb.append("Accept: */*\r\n");
            sb.append("Accept-Language: zh-cn\r\n");
            sb.append("Accept-Encoding: gzip, deflate\r\n");
            sb.append("User-Agent: HTTPClient\r\n");
            sb.append("Host: localhost:8080\r\n");
            sb.append("Connection: Keep-Alive\r\n\r\n");

            /*发送HTTP请求*/
            OutputStream socketOut = socket.getOutputStream();//获取请求的输出流
            socketOut.write(sb.toString().getBytes());

            Thread.sleep(2000);//感受一下这个停顿

            /*接收响应结果*/
            InputStream socketIn=socket.getInputStream();//获得响应输入流
            byte[] buffer=new byte[128];
            int data;
            StringBuffer response=new StringBuffer();
            while ((data=socketIn.read(buffer))!=-1)
                response.append(new String(buffer,0,data));
            System.out.println(response);//打印响应结果
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

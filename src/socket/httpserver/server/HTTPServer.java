package socket.httpserver.server;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
    public static void main(String[] args) {
        int port;
        ServerSocket serverSocket;
        try{
            port=Integer.parseInt(args[0]);
        }catch (Exception e){
            System.out.println("port=8080(默认)");
            port=8080;
        }
        try{
            serverSocket=new ServerSocket(port);
            System.out.println("服务器正在监听端口："+serverSocket.getLocalPort());
            while (true){
                try {
                    //等待客户端的TCP连接
                    final Socket socket=serverSocket.accept();
                    System.out.println("建立了与客户的一个新的TCP连接，该客户的地址为："+socket.getInetAddress()+":"+socket.getPort());
                    service(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 响应客户的HTTP请求
     * @param socket 客户的TCP连接
     */
    private static void service(Socket socket) throws Exception {
        //获得读取HTTP请求的输入流
        InputStream socketIn=socket.getInputStream();
        //Thread.sleep(500);//等待HTTP请求
        byte[] buffer=new byte[128];
        int data;
        StringBuilder request = new StringBuilder();
        while ((data=socketIn.read(buffer))!=-1)
            request.append(new String(buffer,0,data));
        System.out.println(request);//打印HTTP请求数据

        /*解析HTTP请求*/
        //获得HTTP请求的第一行
        String firstLineOfRequest=request.substring(0,request.indexOf("\r\n"));
        //解析第一行
        String[] parts = firstLineOfRequest.split(" ");
        String uri=parts[1];//获取HTTP请求中的uri
        /*决定HTTP响应正文的类型,此处简化处理*/
        String contentType;
        if (uri.contains("htm"))
            contentType="text/html";
        else if (uri.contains("jpg") || uri.contains("jpeg"))
            contentType="image/jpeg";
        else if (uri.contains("gif"))
            contentType="image/gif";
        else
            contentType="application/octet-stream";

        /*创建HTTP响应结果*/
        //HTTP响应的第一行
        String responseFirstLine="HTTP/1.1 200 OK\r\n";
        //HTTP向应头
        String responseHeader="Content-Type:"+contentType+"\r\n\r\n";
        //获得读取响应正文的输入流,和HTTPServer类同级别往下找
        InputStream in = HTTPServer.class.getResourceAsStream(uri);

        /*发送HTTP响应结果*/
        OutputStream socketOut = socket.getOutputStream();
        //发送HTTP响应第一行和响应头
        socketOut.write(responseFirstLine.getBytes());
        socketOut.write(responseHeader.getBytes());

        //发送HTTP响应正文
        int len;
        buffer=new byte[128];
        while ((len=in.read(buffer))!=-1)
            socketOut.write(buffer,0,len);
        socketOut.close();
        Thread.sleep(1000);//睡眠1s,等待客户接收HTTP响应结果
        socket.close();
    }
}

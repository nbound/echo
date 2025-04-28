package echo;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.*;
import java.net.*;

public class Server {

    protected ServerSocket mySocket;
    protected int myPort;
    public static boolean DEBUG = true;
    protected Class<?> handlerType;

    public Server(int port, String handlerTypeName) {
        try {
            myPort = port;
            mySocket = new ServerSocket(myPort);
            this.handlerType = Class.forName(handlerTypeName);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } // catch
    }


    public void listen() throws IOException {
        try {
            while (true) {
                // accept a connection
                Socket clientSocket = mySocket.accept();
                System.out.println("Server listening at port "+myPort);
                // make handler
                RequestHandler handler = makeHandler(clientSocket);
                // start handler in its own thread
                Thread thread = new Thread(handler);
                thread.start();
                //handler.run();
            } // while
        }catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public RequestHandler makeHandler(Socket s) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        try{
            RequestHandler handler =(RequestHandler) handlerType.getDeclaredConstructor().newInstance();
            handler.setSocket(s);
            return handler;
        } catch(Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }



    public static void main(String[] args) throws IOException {
        int port = 5555;
        //String service = "echo.RequestHandler";
        //String service = "casino.CasinoHandler";
        String service = "math.MathHandler";
        if (1 <= args.length) {
            service = args[0];
        }
        if (2 <= args.length) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port, service);
        server.listen();
    }
}
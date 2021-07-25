package Server;
import Server.Strategy.IServerStrategy;
import Server.Strategy.ServerReachableStrategy;
import Server.Strategy.ServerShortestPathStrategy;
import Server.Strategy.ServerSubmarineStrategy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {
    private final int port;
    private boolean stopServer;
    private ThreadPoolExecutor threadPool;
    private IServerStrategy requestStrategy;

    public Server(int port){
        this.port=port;
        this.stopServer=false;
        this.threadPool=null;
        this.requestStrategy = null;
    }
    public void run(IServerStrategy iServerStrategy) {
        this.requestStrategy = iServerStrategy;
        new Thread(() -> {
            this.threadPool = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>());

            try {

                ServerSocket serverSocket = new ServerSocket(this.port);
                while (!stopServer) {
                    Socket clientServer = serverSocket.accept();
                    Runnable clientHandler = () -> {
                        try {
                           handleClient(clientServer);
                            clientServer.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    };
                        threadPool.execute(clientHandler);
                }
                serverSocket.close();

            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }).start();
    }

    public void setStopServer(){
        stopServer=true;
        if(threadPool!=null){threadPool.shutdown();}

        System.out.println("*-*-* Server disconnected *-*-*");
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(8010);
        new Thread(()->{
            server.run(new ServerReachableStrategy());
        }).start();
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("This is the MainServer class");
        System.out.println("We will now start the operation of the Server class using the strategy you chose");
        System.out.println("In order to stop the server, please write 'exit' in the console");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println();

        String operation;
        Scanner scanner = new Scanner(System.in);
        do{
            operation = scanner.nextLine();
        } while (!(operation.equalsIgnoreCase("exit")));
        server.setStopServer();


    }

    private void handleClient(Socket clientSocket) {
        try {
            requestStrategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException | ClassNotFoundException | ExecutionException | InterruptedException e){}
    }

}





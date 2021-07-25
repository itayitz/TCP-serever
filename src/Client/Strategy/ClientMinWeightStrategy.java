package Client.Strategy;

import java.io.*;

public class ClientMinWeightStrategy implements IClientStrategy{
    @Override
    public void applyStrategy(InputStream inFromServer, OutputStream outToServer) throws IOException {
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter toServer = new BufferedWriter(new PrintWriter(outToServer));




    }
}

package Part3HW;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Server {
    
//yc73
private int firstNum;
private int secondNum;
private Random random = new Random();
private int sum = -1;

private int toss;


    int port = 3001;
    // connected clients
    private List<ServerThread> clients = new ArrayList<ServerThread>();

    private void start(int port) {
        this.port = port;
        // server listening
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            Socket incoming_client = null;
            System.out.println("Server is listening on port " + port);
            do {
                System.out.println("waiting for next client");
                if (incoming_client != null) {
                    System.out.println("Client connected");
                    ServerThread sClient = new ServerThread(incoming_client, this);
                    
                    clients.add(sClient);
                    sClient.start();
                    incoming_client = null;
                    
                }
            } while ((incoming_client = serverSocket.accept()) != null);
        } catch (IOException e) {
            System.err.println("Error accepting connection");
            e.printStackTrace();
        } finally {
            System.out.println("closing server socket");
        }
    }
    protected synchronized void disconnect(ServerThread client) {
		long id = client.getId();
        client.disconnect();
		broadcast("Disconnected", id);
	}
    
    protected synchronized void broadcast(String message, long id) {
        if(processCommand(message, id)){

            return;
        }
        // let's temporarily use the thread id as the client identifier to
        // show in all client's chat. This isn't good practice since it's subject to
        // change as clients connect/disconnect
        message = String.format("User[%d]: %s", id, message);
        // end temp identifier
        
        // loop over clients and send out the message
        Iterator<ServerThread> it = clients.iterator();
        while (it.hasNext()) {
            ServerThread client = it.next();
            boolean wasSuccessful = client.send(message);
            if (!wasSuccessful) {
                System.out.println(String.format("Removing disconnected client[%s] from list", client.getId()));
                it.remove();
                broadcast("Disconnected", id);
            }
        }
    }

    private boolean processCommand(String message, long clientId){
        System.out.println("Checking command: " + message);
        if(message.equalsIgnoreCase("disconnect")){
            Iterator<ServerThread> it = clients.iterator();
            while (it.hasNext()) {
                ServerThread client = it.next();
                if(client.getId() == clientId){
                    it.remove();
                    disconnect(client);
                    
                    break;
                }
            }
            return true;
        }

        /*
         * yc73
         * 10-9-23
         * Math Game
         */
        //Start of Math Game
        else if (message.trim().equalsIgnoreCase("start game")) {
            //Genderate two random numbers (between 0-10) to be added in the equation
            firstNum = random.nextInt(11);
            secondNum = random.nextInt(11);
            //Calculate the sum of the two numbers
            sum = firstNum + secondNum;

            //broadcast the math problem and instructions to all the clients
            broadcast(String.format('\n' + "Math Game: %d + %d = ?" + '\n' + "To guess, type 'answer' then a number. Ex: answer 1" + '\n' + "To stop, type 'stop game'", firstNum, secondNum), clientId);
            return true;
        }

        else if (message.trim().equalsIgnoreCase("stop game")) {
            //broadcasts message telling all clients the game has stopped
            broadcast("Game has stopped.", clientId);
            //the sum is reset to -1 to indicate that the game is not active
            sum = -1;
            return true;
        }
        
        else if (message.startsWith("answer") && sum > -1) {
            try {
                //splits the users number from the word "answer"
                //extracts and converts the number to an integer
                int mathGuess = Integer.parseInt(message.split(" ")[1]);
                //checks if the answer is correct
                if (mathGuess == sum) {
                    //broadcasts to everyone that the answer is correct
                    broadcast(String.format("User[%s] answered %d and it's correct!", clientId, mathGuess), clientId);
                    //generates a new math problem
                    firstNum = random.nextInt(10);
                    secondNum = random.nextInt(10);
                    sum = firstNum + secondNum;
                    //broadcatss the new math problem
                    broadcast(String.format("Math Game: %d + %d = ?", firstNum, secondNum), clientId);
                }
                //checks if the answer is incorrect
                else if (mathGuess > sum || mathGuess < sum) {
                    //broadcasts to everyone that the answer is incorrect
                    broadcast(String.format("User[%s] answered %d but it's incorrect.", clientId, mathGuess), clientId);
                }
            }
            //Handles when a user's input is not a valid number
            catch (NumberFormatException e) {
                broadcast("Invalid answer format. Please type a number.", clientId);
            }
            //Handles when a user's input does not contain a valid answer
            catch (ArrayIndexOutOfBoundsException e) {
                broadcast("Invalid answer format. Please type a number.", clientId);
            }
            return true;
        }
        //End of Math Game

        /*
         * yc73
         * 10-9-23
         * Coin toss command
         */
        //Start of Coin Toss
        else if (message.trim().equalsIgnoreCase("flip coin")) {
            //Generates a random number (0 or 1) to represent heads or tails
            toss = random.nextInt(2);
            //if toss is 0 it's heads, otherwise it's false
            //broadcasts the result of the coin flip to everyone
            broadcast(String.format("User[%s] flipped %s!", clientId, toss == 0 ? "heads" : "tails"), clientId);
            return true;
        }
        //End of Coin Toss

        return false;
    }
    public static void main(String[] args) {
        System.out.println("Starting Server");
        Server server = new Server();
        int port = 3000;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            // can ignore, will either be index out of bounds or type mismatch
            // will default to the defined value prior to the try/catch
        }
        server.start(port);
        System.out.println("Server Stopped");
    }
}
package CR.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import CR.common.Payload;
import CR.common.PayloadType;
import CR.common.RoomResultPayload;

/**
 * A server-side representation of a single client
 */
public class ServerThread extends Thread {
    private Socket client;
    private String clientName;
    private boolean isRunning = false;
    private ObjectOutputStream out;// exposed here for send()
    // private Server server;// ref to our server so we can call methods on it
    // more easily
    private Room currentRoom;
    private static Logger logger = Logger.getLogger(ServerThread.class.getName());
    private long myId;

    public void setClientId(long id) {
        myId = id;
    }

    public long getClientId() {
        return myId;
    }

    public boolean isRunning() {
        return isRunning;
    }

    //yc73
    //11/18/23
    List<String> clientsMuted = new ArrayList<String>();

    public boolean isMuted(String clientName) {
        return clientsMuted.contains(clientName);
    }
    

    public void mute(String clientName) {
        if (!isMuted(clientName)) {
            clientsMuted.add(clientName);
        }
    }

    public void unmute(String clientName) {
        if (isMuted(clientName)) {
            clientsMuted.remove(clientName);
        }
    }


    //yc73
    //11/28/23
    //reads muted file
    public void addMutedUsersFromFile(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String mutedUser = scanner.nextLine().trim();
                if (!mutedUser.isEmpty()) {
                    mute(mutedUser); //adds each username to the muted list
                }
            }
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //yc73
    //11/28/23
    //writes muted file
    public void saveMutedList(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (String mutedUser : clientsMuted) {
                fileWriter.write(mutedUser + "\n");
            }
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    //yc73
    //12/4/23
    //updates file when user is unmuted (removes their name)
    public void removeMutedUsersFromFile (String clientName, String fileName) {
        //List<String> mutedUsersFromFile = new ArrayList<>();
        
        try {
            //help from:
            //https://howtodoinjava.com/java/io/java-append-to-file/
            //https://howtodoinjava.com/java8/read-file-line-by-line/
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            lines.removeIf(line -> line.trim().equals(clientName));
            Files.write(Paths.get(fileName), lines);

            /*
            Scanner scanner = new Scanner(new File(fileName));

            //reads the existing muted users from the file
            while (scanner.hasNextLine()) {
                String mutedUser = scanner.nextLine().trim();
                if (!mutedUser.isEmpty() && !mutedUser.equals(clientName)) {
                    mutedUsersFromFile.add(mutedUser);
                }
            }
            scanner.close();

            FileWriter fileWriter = new FileWriter(fileName);
            for (String mutedUser : clientsMuted) {
                fileWriter.write(mutedUser + "\n");
            }
            fileWriter.close();
            */
        } 
        
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void info(String message) {
        System.out.println(String.format("Thread[%s]: %s", getId(), message));
    }

    public ServerThread(Socket myClient, Room room) {
        info("Thread created");
        // get communication channels to single client
        this.client = myClient;
        this.currentRoom = room;

    }

    protected void setClientName(String name) {
        //yc73
        //11/16/23
        if (name == null || name.isBlank() || name.contains(" ")) {
        // added ^ || name.contains(" ")
            System.err.println("Invalid client name being set");
            return;
        }
        clientName = name;
    }

    protected String getClientName() {
        return clientName;
    }

    protected synchronized Room getCurrentRoom() {
        return currentRoom;
    }

    protected synchronized void setCurrentRoom(Room room) {
        if (room != null) {
            currentRoom = room;
        } else {
            info("Passed in room was null, this shouldn't happen");
        }
    }

    public void disconnect() {
        sendConnectionStatus(myId, getClientName(), false);
        info("Thread being disconnected by server");
        isRunning = false;
        cleanup();
    }

    // send methods
    public boolean sendRoomName(String name) {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.JOIN_ROOM);
        p.setMessage(name);
        return send(p);
    }

    public boolean sendRoomsList(String[] rooms, String message) {
        RoomResultPayload payload = new RoomResultPayload();
        payload.setRooms(rooms);
        //Fixed in Module7.Part9
        if(message != null){
            payload.setMessage(message);
        }
        return send(payload);
    }

    public boolean sendExistingClient(long clientId, String clientName) {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.SYNC_CLIENT);
        p.setClientId(clientId);
        p.setClientName(clientName);
        return send(p);
    }

    public boolean sendResetUserList() {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.RESET_USER_LIST);
        return send(p);
    }

    public boolean sendClientId(long id) {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.CLIENT_ID);
        p.setClientId(id);
        return send(p);
    }

    public boolean sendMessage(long clientId, String message) {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.MESSAGE);
        p.setClientId(clientId);
        p.setMessage(message);
        return send(p);
    }

    public boolean sendConnectionStatus(long clientId, String who, boolean isConnected) {
        Payload p = new Payload();
        p.setPayloadType(isConnected ? PayloadType.CONNECT : PayloadType.DISCONNECT);
        p.setClientId(clientId);
        p.setClientName(who);
        p.setMessage(isConnected ? "connected" : "disconnected");
        return send(p);
    }

    private boolean send(Payload payload) {
        // added a boolean so we can see if the send was successful
        try {
            // TODO add logger
            logger.log(Level.FINE, "Outgoing payload: " + payload);
            out.writeObject(payload);
            logger.log(Level.INFO, "Sent payload: " + payload);
            return true;
        } catch (IOException e) {
            info("Error sending message to client (most likely disconnected)");
            // comment this out to inspect the stack trace
            // e.printStackTrace();
            cleanup();
            return false;
        } catch (NullPointerException ne) {
            info("Message was attempted to be sent before outbound stream was opened: " + payload);
            return true;// true since it's likely pending being opened
        }
    }


    //yc73
    //12/4/23
    //got help from sajid
    public void sendMuteList() {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.MUTE_LIST);
        p.setMessage(String.join(",", clientsMuted));
        send(p);
    }

    // end send methods

    //yc73
    //12/6/23
    //got help from sajid
    public void sendMuteStatus() {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.MUTE);
        p.setMessage("");
        send(p);
    }

    //yc73
    //12/6/23
    public void sendUnmuteStatus() {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.UNMUTE);
        p.setMessage("");
        send(p);
    }

    @Override
    public void run() {
        info("Thread starting");
        try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());) {
            this.out = out;
            isRunning = true;
            Payload fromClient;
            while (isRunning && // flag to let us easily control the loop
                    (fromClient = (Payload) in.readObject()) != null // reads an object from inputStream (null would
                                                                     // likely mean a disconnect)
            ) {

                info("Received from client: " + fromClient);
                processPayload(fromClient);

            } // close while loop
        } catch (Exception e) {
            // happens when client disconnects
            e.printStackTrace();
            info("Client disconnected");
        } finally {
            isRunning = false;
            info("Exited thread loop. Cleaning up connection");
            cleanup();
        }
    }

    void processPayload(Payload p) {
        switch (p.getPayloadType()) {
            case CONNECT:
                setClientName(p.getClientName());

                //yc73
                //11/28/23
                addMutedUsersFromFile(this.getClientName() + "MutedList.txt"); 

                //yc73
                //12/6/23
                sendMuteList();

                break;
            case DISCONNECT:

                //yc73
                //11/28/23
                //saveMutedList(this.getClientName() + "MutedList.txt");

                Room.disconnectClient(this, getCurrentRoom());
                break;
            case MESSAGE:
                if (currentRoom != null) {
                    currentRoom.sendMessage(this, p.getMessage());
                } else {
                    // TODO migrate to lobby
                    logger.log(Level.INFO, "Migrating to lobby on message with null room");
                    Room.joinRoom("lobby", this);
                }
                break;
            case GET_ROOMS:
                Room.getRooms(p.getMessage().trim(), this);
                break;
            case CREATE_ROOM:
                Room.createRoom(p.getMessage().trim(), this);
                break;
            case JOIN_ROOM:
                Room.joinRoom(p.getMessage().trim(), this);
                break;
            default:
                break;

        }

    }

    private void cleanup() {
        info("Thread cleanup() start");
        try {
            client.close();
        } catch (IOException e) {
            info("Client already closed");
        }
        info("Thread cleanup() complete");
    }
}
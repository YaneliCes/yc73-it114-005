package CR.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;


import CR.common.Constants;
import CR.common.Payload;
import CR.common.PayloadType;
import CR.common.RoomResultPayload;

//yc73
//12/5/23
import java.util.Map.Entry;
import java.util.Set;

public enum Client {
    INSTANCE;

    Socket server = null;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    final String ipAddressPattern = "/connect\\s+(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:\\d{3,5})";
    final String localhostPattern = "/connect\\s+(localhost:\\d{3,5})";
    boolean isRunning = false;
    private Thread inputThread;
    private Thread fromServerThread;
    // private String clientName = "";
    private User myPlayer = new User();
    private long myClientId = Constants.DEFAULT_CLIENT_ID;
    private static Logger logger = Logger.getLogger(Client.class.getName());

    private Hashtable<Long, User> userList = new Hashtable<Long, User>();

    private static List<IClientEvents> events = new ArrayList<IClientEvents>();

    //yc73
    //12/4/23
    protected List<String> clientMutedList = new ArrayList<String>();

    public void addCallback(IClientEvents e) {
        events.add(e);
    }

    public boolean isConnected() {
        if (server == null) {
            return false;
        }
        // https://stackoverflow.com/a/10241044
        // Note: these check the client's end of the socket connect; therefore they
        // don't really help determine
        // if the server had a problem
        return server.isConnected() && !server.isClosed() && !server.isInputShutdown() && !server.isOutputShutdown();

    }

    /**
     * Takes an ip address and a port to attempt a socket connection to a server.
     * 
     * @param address
     * @param port
     * @param username
     * @param callback (for triggering UI events)
     * @return true if connection was successful
     */
    public boolean connect(String address, int port, String username, IClientEvents callback) {
        // TODO validate
        // this.clientName = username;
        myPlayer.setClientName(username);
        addCallback(callback);
        try {
            server = new Socket(address, port);
            // channel to send to server
            out = new ObjectOutputStream(server.getOutputStream());
            // channel to listen to server
            in = new ObjectInputStream(server.getInputStream());
            logger.info("Client connected");
            listenForServerPayload();
            sendConnect();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isConnected();
    }

    // Send methods

    public void sendListRooms(String query) throws IOException {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.GET_ROOMS);
        p.setMessage(query);
        out.writeObject(p);
    }

    public void sendJoinRoom(String roomName) throws IOException {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.JOIN_ROOM);
        p.setMessage(roomName);
        out.writeObject(p);
    }

    public void sendCreateRoom(String roomName) throws IOException {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.CREATE_ROOM);
        p.setMessage(roomName);
        out.writeObject(p);
    }

    protected void sendDisconnect() throws IOException {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.DISCONNECT);
        out.writeObject(p);
    }

    protected void sendConnect() throws IOException {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.CONNECT);
        p.setClientName(myPlayer.getClientName());
        out.writeObject(p);
    }

    public void sendMessage(String message) throws IOException {
        Payload p = new Payload();
        p.setPayloadType(PayloadType.MESSAGE);
        p.setMessage(message);
        p.setClientName(myPlayer.getClientName());
        out.writeObject(p);
    }

    // end send methods

    private void listenForServerPayload() {
        isRunning = true;
        fromServerThread = new Thread() {
            @Override
            public void run() {
                try {
                    Payload fromServer;

                    // while we're connected, listen for objects from server
                    while (isRunning && !server.isClosed() && !server.isInputShutdown()
                            && (fromServer = (Payload) in.readObject()) != null) {

                        logger.info("Debug Info: " + fromServer);
                        processPayload(fromServer);

                        //yc73
                        //12/6/23
                        onMutedStatus("mute");

                    }
                    logger.info("listenForServerPayload() loop exited");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    logger.info("Stopped listening to server input");
                    close();
                }
            }
        };
        fromServerThread.start();// start the thread
    }

    public String getClientNameById(long id) {
        if (userList.containsKey(id)) {
            return userList.get(id).getClientName();
        }
        if (id == Constants.DEFAULT_CLIENT_ID) {
            return "[Server]";
        }
        return "unkown user";
    }

    /**
     * Processes incoming payloads from ServerThread
     * 
     * @param p
     */
    private void processPayload(Payload p) {
        switch (p.getPayloadType()) {
            case CONNECT:
                if (!userList.containsKey(p.getClientId())) {
                    User cp = new User();
                    cp.setClientName(p.getClientName());
                    cp.setClientId(p.getClientId());
                    userList.put(p.getClientId(), cp);
                }
                System.out.println(String.format("*%s %s*",
                        p.getClientName(),
                        p.getMessage()));
                events.forEach(e -> {
                    e.onClientConnect(p.getClientId(), p.getClientName(), p.getMessage());
                });

                break;
            case DISCONNECT:
                if (userList.containsKey(p.getClientId())) {
                    userList.remove(p.getClientId());
                }
                if (p.getClientId() == myClientId) {
                    myClientId = Constants.DEFAULT_CLIENT_ID;
                }
                System.out.println(String.format("*%s %s*",
                        p.getClientName(),
                        p.getMessage()));
                events.forEach(e -> {
                    e.onClientDisconnect(p.getClientId(), p.getClientName(), p.getMessage());
                });

                break;
            case SYNC_CLIENT:
                if (!userList.containsKey(p.getClientId())) {
                    User cp = new User();
                    cp.setClientName(p.getClientName());
                    cp.setClientId(p.getClientId());
                    userList.put(p.getClientId(), cp);
                }
                events.forEach(e -> {
                    e.onSyncClient(p.getClientId(), p.getClientName());
                });

                break;
            case MESSAGE:
                System.out.println(String.format("%s: %s",
                        getClientNameById(p.getClientId()),
                        p.getMessage()));
                events.forEach(e -> {
                    e.onMessageReceive(p.getClientId(), p.getMessage());
                });

                //yc73
                //12/6/23
                events.forEach(e -> {
                    e.messageHighlight(p.getClientId());
                });

                break;
            case CLIENT_ID:
                if (myClientId == Constants.DEFAULT_CLIENT_ID) {
                    myClientId = p.getClientId();
                    myPlayer.setClientId(myClientId);
                    userList.put(myClientId, myPlayer);
                } else {
                    logger.warning("Receiving client id despite already being set");
                }
                events.forEach(e -> {
                    e.onReceiveClientId(p.getClientId());
                });

                break;
            case GET_ROOMS:
                RoomResultPayload rp = (RoomResultPayload) p;
                System.out.println("Received Room List:");
                if (rp.getMessage() != null) {
                    System.out.println(rp.getMessage());
                } else {
                    for (int i = 0, l = rp.getRooms().length; i < l; i++) {
                        System.out.println(String.format("%s) %s", (i + 1), rp.getRooms()[i]));
                    }
                }
                events.forEach(e -> {
                    e.onReceiveRoomList(rp.getRooms(), rp.getMessage());
                });

                break;
            case RESET_USER_LIST:
                userList.clear();
                events.forEach(e -> {
                    e.onResetUserList();
                });

                break;
                
            //yc73
            //12/4/23
            case MUTE_LIST:
                clientMutedList.clear();
                String[] mutedNames = p.getMessage().split(",");
                for (String user : mutedNames) {
                    clientMutedList.add(user);
                }
                break;

            //yc73
            //12/6/23
            case MUTE:
                onMutedStatus("mute");
                break;

            //yc73
            //12/6/23
            case UNMUTE:
                onMutedStatus("unmute");
                break;

            default:
                logger.warning(String.format("Unhandled Payload type: %s", p.getPayloadType()));
                break;

        }
    }

    private void close() {
        myClientId = Constants.DEFAULT_CLIENT_ID;
        userList.clear();
        try {
            inputThread.interrupt();
        } catch (Exception e) {
            System.out.println("Error interrupting input");
            e.printStackTrace();
        }
        try {
            fromServerThread.interrupt();
        } catch (Exception e) {
            System.out.println("Error interrupting listener");
            e.printStackTrace();
        }
        try {
            System.out.println("Closing output stream");
            out.close();
        } catch (NullPointerException ne) {
            System.out.println("Server was never opened so this exception is ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Closing input stream");
            in.close();
        } catch (NullPointerException ne) {
            System.out.println("Server was never opened so this exception is ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Closing connection");
            server.close();
            System.out.println("Closed socket");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            System.out.println("Server was never opened so this exception is ok");
        }
    }

    //yc73
    //12/5/23
    //got help from sajid & this website: https://www.geeksforgeeks.org/how-to-iterate-through-hashtable-in-java/
    private void onMutedStatus(String userMuteStatus) {
        Set<Entry<Long, User> > userSet = userList.entrySet();

        for (Entry<Long, User> userInfo : userSet) {
            if(userMuteStatus.equals("mute") && clientMutedList.indexOf(userInfo.getValue().getClientName()) > -1) {
                events.forEach(e -> {
                    e.onMutedUserInfo(userMuteStatus, userInfo.getValue().getClientId());
                });
            }

            else if(userMuteStatus.equals("unmute") && !clientMutedList.contains(userInfo.getValue().getClientName())) {
                events.forEach(e -> {
                    e.onMutedUserInfo(userMuteStatus, userInfo.getValue().getClientId());
                });
            }
 
        }
        
    }


}
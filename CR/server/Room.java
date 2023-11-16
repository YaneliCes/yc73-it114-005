package CR.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import CR.common.Constants;

public class Room implements AutoCloseable {
	private String name;
	private List<ServerThread> clients = Collections.synchronizedList(new ArrayList<ServerThread>());
	private boolean isRunning = false;
	// Commands
	private final static String COMMAND_TRIGGER = "/";
	private final static String CREATE_ROOM = "createroom";
	private final static String JOIN_ROOM = "joinroom";
	private final static String DISCONNECT = "disconnect";
	private final static String LOGOUT = "logout";
	private final static String LOGOFF = "logoff";
	private final static String FLIP = "flip";
    private final static String ROLL = "roll";
	private static Logger logger = Logger.getLogger(Room.class.getName());

	public Room(String name) {
		this.name = name;
		isRunning = true;
	}

	private void info(String message) {
		logger.log(Level.INFO, String.format("Room[%s]: %s", name, message));
	}

	public String getName() {
		return name;
	}

	public boolean isRunning() {
		return isRunning;
	}

	protected synchronized void addClient(ServerThread client) {
		if (!isRunning) {
			return;
		}
		client.setCurrentRoom(this);
		if (clients.indexOf(client) > -1) {
			info("Attempting to add a client that already exists");
		} else {
			clients.add(client);
			sendConnectionStatus(client, true);
			sendRoomJoined(client);
			sendUserListToClient(client);
		}
	}

	protected synchronized void removeClient(ServerThread client) {
		if (!isRunning) {
			return;
		}
		clients.remove(client);
		// we don't need to broadcast it to the server
		// only to our own Room
		if (clients.size() > 0) {
			// sendMessage(client, "left the room");
			sendConnectionStatus(client, false);
		}
		checkClients();
	}

	/***
	 * Checks the number of clients.
	 * If zero, begins the cleanup process to dispose of the room
	 */
	private void checkClients() {
		// Cleanup if room is empty and not lobby
		if (!name.equalsIgnoreCase("lobby") && clients.size() == 0) {
			close();
		}
	}

	/***
	 * Helper function to process messages to trigger different functionality.
	 * 
	 * @param message The original message being sent
	 * @param client  The sender of the message (since they'll be the ones
	 *                triggering the actions)
	 */
	private boolean processCommands(String message, ServerThread client) {
		boolean wasCommand = false;
		try {
			if (message.startsWith(COMMAND_TRIGGER)) {
				String[] comm = message.split(COMMAND_TRIGGER);
				String part1 = comm[1];
				String[] comm2 = part1.split(" ");
				String command = comm2[0];
				String roomName;
				wasCommand = true;
				switch (command) {
					case CREATE_ROOM:
						roomName = comm2[1];
						Room.createRoom(roomName, client);
						break;
					case JOIN_ROOM:
						roomName = comm2[1];
						Room.joinRoom(roomName, client);
						break;
					case DISCONNECT:
					case LOGOUT:
					case LOGOFF:
						Room.disconnectClient(client, this);
						break;

						
                    //yc73
                    //REMEMBER
                    case FLIP:
                        int toss = (int)(Math.random()*2);
			            sendMessage(null, String.format("%s flipped a coin and got %s!", client.getClientName(), toss == 0 ? "heads" : "tails"));
			            break;
                    //yc73
                    //REMEMBER
                    case ROLL:
                    int totalRolled = 0;
                        try {
                            
                            if (!message.contains("d")) {
                                int chosenDie = Integer.parseInt(message.split(" ")[1]);
                                int dieRolled = (int)((Math.random()*chosenDie) + 1);
                                sendMessage(null, String.format("%s rolled %d", client.getClientName(), dieRolled));
                                break;
                            }
                            
                            else if (message.contains("d")) {
                                int numOfDice = Integer.parseInt(message.split(" ")[1].split("d")[0]);
                                int numOfFace = Integer.parseInt(message.split(" ")[1].split("d")[1]);
                                
                                for(int i=0; i < numOfDice; i++) {
                                    int diceValue = (int)((Math.random()*numOfFace) + 1);
                                    totalRolled += diceValue;
                                }
                                sendMessage(null, String.format("%s chose %d" + "d" + "%d" + " and got %d!", client.getClientName(), numOfDice, numOfFace, totalRolled));
                                break;
                            }                        
                        }

                        catch (ArrayIndexOutOfBoundsException e) {
                            sendMessage(null, "Must include a number. (Ex: /roll 100 or /roll 1d10)");
                            break;
                        }
                        catch (NumberFormatException e) {
                            sendMessage(null, "Invalid input. You must type a number. (Ex: /roll 100 or /roll 1d10)");
                            break;
                        }


					default:
						wasCommand = false;
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wasCommand;
	}

	// Command helper methods

	protected static void getRooms(String query, ServerThread client) {
		String[] rooms = Server.INSTANCE.getRooms(query).toArray(new String[0]);
		client.sendRoomsList(rooms,(rooms!=null&&rooms.length==0)?"No rooms found containing your query string":null);
	}

	protected static void createRoom(String roomName, ServerThread client) {
		if (Server.INSTANCE.createNewRoom(roomName)) {
			Room.joinRoom(roomName, client);
		} else {
			client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s already exists", roomName));
			client.sendRoomsList(null, String.format("Room %s already exists", roomName));
		}
	}

	protected static void joinRoom(String roomName, ServerThread client) {
		if (!Server.INSTANCE.joinRoom(roomName, client)) {
			client.sendMessage(Constants.DEFAULT_CLIENT_ID, String.format("Room %s doesn't exist", roomName));
			client.sendRoomsList(null, String.format("Room %s doesn't exist", roomName));
		}
	}

	protected static void disconnectClient(ServerThread client, Room room) {
		client.setCurrentRoom(null);
		client.disconnect();
		room.removeClient(client);
	}
	// end command helper methods

	/***
	 * Takes a sender and a message and broadcasts the message to all clients in
	 * this room. Client is mostly passed for command purposes but we can also use
	 * it to extract other client info.
	 * 
	 * @param sender  The client sending the message
	 * @param message The message to broadcast inside the room
	 */
	protected synchronized void sendMessage(ServerThread sender, String message) {
		if (!isRunning) {
			return;
		}
		info("Sending message to " + clients.size() + " clients");
		if (sender != null && processCommands(message, sender)) {
			// it was a command, don't broadcast
			return;
		}

		
        //yc73
        //11/7/23
        message = replaceMessage(message);


		long from = (sender == null) ? Constants.DEFAULT_CLIENT_ID : sender.getClientId();
		synchronized (clients) {
			Iterator<ServerThread> iter = clients.iterator();
			while (iter.hasNext()) {
				ServerThread client = iter.next();
				boolean messageSent = client.sendMessage(from, message);
				if (!messageSent) {
					handleDisconnect(iter, client);
				}
			}
		}
	}

	
    //yc73
    //11/9/23
    private String replaceMessage(String message) {
        //calls "formatMessage" to replace triggers with html tags
        //bold
        message = formatMessage(message, "*", "<b>", "</b>");
        //italics
        message = formatMessage(message, "-", "<i>", "</i>");
        //underline
        message = formatMessage(message, "_", "<u>", "</u>");

        //calls "formatMessage" to replace color triggers with html tags
        //red
        message = formatMessage(message,"^", "<font color=\"red\">", "</font>");
        //green
        message = formatMessage(message, "%", "<font color=\"green\">", "</font>");
        //blue
        message = formatMessage(message, "$", "<font color=\"blue\">", "</font>");

        return message;
    }

    //yc73
    //11/9/23
    private String formatMessage(String message, String trigger, String openTag, String closeTag) {
        int index = -1;
        //loop searches through the user's message for pairs of triggers
        while ((index = message.indexOf(trigger, index + 1)) != -1) {
            //finds the index of the first starting trigger
            int startIndex = index;
            //finds the index of the closing trigger
            int endIndex = message.indexOf(trigger, startIndex + 1);
    
            //if the second, lcosing, trigger is not present, then it continues to the next pair
            if (endIndex == -1) {
                continue;
            }
            //replaces the triggers with the appropriate html tags
            //grabs the very start of the message up to (but not including) the index of where the starting trigger is
            //concatenates the first html tag right after the users unformatted text
            //then the text after the first trigger, up to before the closing trigger, is taken and put between the html tags
            //concatenates the closing html tag right after the formatted text
            //then it grabs the rest of the remaining unformatted text right after the closing trigger
            message = message.substring(0, startIndex) + openTag + message.substring(startIndex + 1, endIndex) + closeTag + message.substring(endIndex + 1);
            //to avoid an infinite loop, this updates to the index right after the closing html tag 
            //so it doesn't search through the same part of the message
            index = endIndex + closeTag.length();
        }
        return message;
    }



	protected synchronized void sendUserListToClient(ServerThread receiver) {
		logger.log(Level.INFO, String.format("Room[%s] Syncing client list of %s to %s", getName(), clients.size(),
				receiver.getClientName()));
		synchronized (clients) {
			Iterator<ServerThread> iter = clients.iterator();
			while (iter.hasNext()) {
				ServerThread clientInRoom = iter.next();
				if (clientInRoom.getClientId() != receiver.getClientId()) {
					boolean messageSent = receiver.sendExistingClient(clientInRoom.getClientId(),
							clientInRoom.getClientName());
					// receiver somehow disconnected mid iteration
					if (!messageSent) {
						handleDisconnect(null, receiver);
						break;
					}
				}
			}
		}
	}

	protected synchronized void sendRoomJoined(ServerThread receiver) {
		boolean messageSent = receiver.sendRoomName(getName());
		if (!messageSent) {
			handleDisconnect(null, receiver);
		}
	}

	protected synchronized void sendConnectionStatus(ServerThread sender, boolean isConnected) {
		// converted to a backwards loop to help avoid concurrent list modification
		// due to the recursive sendConnectionStatus()
		// this should only be needed in this particular method due to the recusion
		if (clients == null) {
			return;
		}
		synchronized (clients) {
			for (int i = clients.size() - 1; i >= 0; i--) {
				ServerThread client = clients.get(i);
				boolean messageSent = client.sendConnectionStatus(sender.getClientId(), sender.getClientName(),
						isConnected);
				if (!messageSent) {
					clients.remove(i);
					info("Removed client " + client.getClientName());
					checkClients();
					sendConnectionStatus(client, false);
				}
			}
		}
	}

	private synchronized void handleDisconnect(Iterator<ServerThread> iter, ServerThread client) {
		if (iter != null) {
			iter.remove();
		}
		info("Removed client " + client.getClientName());
		checkClients();
		sendConnectionStatus(client, false);
		// sendMessage(null, client.getClientName() + " disconnected");
	}

	public void close() {
		Server.INSTANCE.removeRoom(this);
		isRunning = false;
		clients = null;
	}
}
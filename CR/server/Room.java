package CR.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import CR.common.Constants;

//import java.io.FileWriter;

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

	//yc73
	//11/17/23
	private final static String PM = "@";

	//yc73
	//11/18/23
	private final static String MUTE = "mute";
	private final static String UNMUTE = "unmute";

	//yc73
	//11/28/23
	private final static String SAVE_MUTED = "savemuted";

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

		//yc73
		//11/28/23
		client.saveMutedList(client.getClientName() + "MutedList.txt");

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
                    //11/16/23
                    case FLIP:
                        int toss = (int)(Math.random()*2);
						//JEditorPane being configured to "text/html" allows the html tags to be rendered and displayed correctly
			            sendMessage(client, String.format("<font color='#7B68EE'>Flipped a coin and got %s </font>", toss == 0 ? "heads" : "tails" /*+ "</font>"*/));
			            break;

                    //yc73
                    //11/16/23
                    case ROLL:
                    int totalRolled = 0;
                        try {
                            if (!message.contains("d")) {
                                int chosenDie = Integer.parseInt(message.split(" ")[1]);
                                int dieRolled = (int)((Math.random()*chosenDie) + 1);
								//JEditorPane being configured to "text/html" allows the html tags to be rendered and displayed correctly
                                sendMessage(client, String.format("<font color='#1E90FF'>Rolled a %s sided die and got %s", chosenDie, dieRolled + "</font>"));
                                break;
                            }
                            
                            else if (message.contains("d")) {
                                int numOfDice = Integer.parseInt(message.split(" ")[1].split("d")[0]);
                                int numOfFace = Integer.parseInt(message.split(" ")[1].split("d")[1]);
                                
                                for(int i=0; i < numOfDice; i++) {
                                    int diceValue = (int)((Math.random()*numOfFace) + 1);
                                    totalRolled += diceValue;
                                }
								//JEditorPane being configured to "text/html" allows the html tags to be rendered and displayed correctly
                                sendMessage(client, String.format("<font color='#2E8B57'>Chose %s" + "d" + "%s" + " and rolled %s!", numOfDice, numOfFace, totalRolled) + "</font>");
                                break;
                            }                        
                        }

                        catch (ArrayIndexOutOfBoundsException e) {
							client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#696969'>Must include a number. (Ex: /roll 100 or /roll 1d10)</font>");
                            break;
                        }
                        catch (NumberFormatException e) {
                            client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#696969'>Invalid input. You must type a number. (Ex: /roll 100 or /roll 1d10)</font>");
                            break;
                        }

					//yc73
					//11/18/23
					case MUTE:
						try {
							//grabs the target user to mute
							String usersToMute = comm2[1];

							if (isValidUsername(usersToMute)) {
								//updates the mute lsit in ServerThread
								client.mute(usersToMute);

								//yc73
								//12/5/23
								client.sendMuteList();

								//yc73
								//12/6/23
								client.sendMuteStatus();

								//yc73
								//11/28/23
								client.saveMutedList(client.getClientName() + "MutedList.txt");

								//created to be used to send a private message to the muted user
								List<String> mutedUsersList = new ArrayList<>();
								mutedUsersList.add(usersToMute);
								
								//sends feedback to the sender
								client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#DC143C'>Users muted: " + usersToMute + "</font>");

								//sends a private message to the muted user
								sendPrivateMessage(null, mutedUsersList, "<font color='#DC143C'>You were muted by " + client.getClientName() + "</font>");
							
								break;
							} 

							else {
								//if the username is not found
								client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#696969'>Cannot /mute, client " + usersToMute + " does not exist</font>");
								break;
							}
						}

						catch (ArrayIndexOutOfBoundsException e) {
							//if no username is inputed
							client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#696969'>Cannot /mute, input an existing user</font>");
							break;
						}


					//yc73
					//11/18/23
					case UNMUTE:
						try {
							//grabs the target user to unmute
							String usersToUnmute = comm2[1];
							
							if (isValidUsername(usersToUnmute)) {
								//updates the mute list in ServerThread
								client.unmute(usersToUnmute);

								//yc73
								//12/5/23
								client.sendMuteList();

								//yc73
								//12/6/23
								client.sendUnmuteStatus();

								//yc73
								//11/28/23
								client.removeMutedUsersFromFile(usersToUnmute, client.getClientName() + "MutedList.txt");
								
								//created to be used to send a private message to the muted user
								List<String> unmutedUsersList = new ArrayList<>();
								unmutedUsersList.add(usersToUnmute);

								//sends feedback to the sender
								client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#006400'>Users unmuted: " + usersToUnmute + "</font>");

								//sends a private message to the muted user
								sendPrivateMessage(null, unmutedUsersList, "<font color='#006400'>You were unmuted by " + client.getClientName() + "</font>");

								break;
							}

							else {
								//if the username is not found
								client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#696969'>Cannot /unmute, client " + usersToUnmute + " does not exist</font>");
								break;
							}
						}

						catch (ArrayIndexOutOfBoundsException e) {
							//if no username is inputed
							client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#696969'>Cannot /unmute, input an existing user</font>");
							break;
						}

						//yc73
						//11/28/23
						case SAVE_MUTED:
					        try {
								client.saveMutedList(client.getClientName() + "MutedList.txt");

								client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#696969'>Your muted list has been saved.</font>");
								
								break;
							} 
									
							catch (Exception e) {
								e.printStackTrace();
								JOptionPane.showMessageDialog(new JFrame(), "Error occurred while saving mute list.");
								break;
							}

					default:
						wasCommand = false;
						break;
				}
			}


			//checks if the message starts with '@' which is the trigger for the private message command
			else if (message.startsWith(PM)) {
				//the message is split into two parts, the username in one part and the rest of the message in the other
				String[] splitPM = message.split(" ", 2);
				//splits the message to get '@username', then only grabs the 'username' next to the '@' 
				String targetUsername = splitPM[0].substring(1);

				//calls the method 'isValidUsername' to check if the username entered is a user that exists in the chatroom
				if (isValidUsername(targetUsername)) {
					//if the username exists, it considers the message as a private message
					wasCommand = true;
					//creates a new empty list to store usernames and keep track of who the private message is being sent to
					List<String> pmClients = new ArrayList<String>();
					//the username is added to the list 'pmClients'
					pmClients.add(targetUsername);
					//calls the 'sendPrivateMessage' method to actually send the message to the correct user

					//sends to sender
					client.sendMessage(client.getClientId(), "<font color='#5f5f5f'>(Whispered) </font>" + replaceMessage(message));

					//sends to client
					sendPrivateMessage(client, pmClients, "<font color='#5f5f5f'>(Whisper) </font>" + message);

				} else {
					//if the username is not found then the message will be considered a regular message (in case they did not want to use @ as a command but as a regular message)
					//tells the user who used the command that the username does not exist
					client.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#696969'>client @" + targetUsername + " does not exist</font>");
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
				//yc73
				//11/18/23
				//checks if the sender is muted by the client
				if (sender == null || !client.isMuted(sender.getClientName())) {
					boolean messageSent = client.sendMessage(from, message);
					if (!messageSent) {
						handleDisconnect(iter, client);
					}
				}
			}
		}
	}

	//yc73
	//11/17/23
	private boolean isValidUsername(String username) {
		synchronized (clients) {
			//goes trhough each client in the chatroom
			for (ServerThread client : clients) {
				//checks if the current client's name matches the username it is looking for
				if (client.getClientName().equals(username)) {
					//if it does match, it returns 'true' whihc means it found the username
					return true;
				}
			}
		}
		//if the username is not found after checking all the clients, it returns 'false'
		return false;
	}

	//yc73
	//11/17/23
	protected synchronized void sendPrivateMessage(ServerThread sender, List<String> ClientsList, String message) {
		Iterator<ServerThread> iter = clients.iterator();
		message = replaceMessage(message);
		long from = (sender == null) ? Constants.DEFAULT_CLIENT_ID : sender.getClientId();

		while (iter.hasNext()) {
			ServerThread client = iter.next();
			if (ClientsList.contains(client.getClientName())) {
				//checks if the sender is muted by the client
				if (sender == null || !client.isMuted(sender.getClientName())) {
					boolean messageSent = client.sendMessage(from, message);
					if (!messageSent) {
						handleDisconnect(iter, client);
					}
				} 
				else {
					//notifies the sender that they are muted
					sender.sendMessage(Constants.DEFAULT_CLIENT_ID, "<font color='#DC143C'>You cannot send a message to " + client.getClientName() + " as they have muted you.</font>");
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
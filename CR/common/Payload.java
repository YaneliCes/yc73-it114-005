package CR.common;

import java.io.Serializable;

public class Payload implements Serializable {
    // read https://www.baeldung.com/java-serial-version-uid
    private static final long serialVersionUID = 1L;// change this if the class changes

    /**
     * Determines how to process the data on the receiver's side
     */

    //yc73
    //11/12/23
    //this determines the type of payload, used to decide how to handle the data in the payload or how to route it (like a message, command, etc.)
    private PayloadType payloadType;

    public PayloadType getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(PayloadType payloadType) {
        this.payloadType = payloadType;
    }

    /**
     * Who the payload is from
     */
    
     //yc73
    //11/12/23
    //this represents the name or whatever the identifier is of a client, which is used to identify the sender of a payload
    //also useful to display the client who sent the message   
    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    //yc73
    //11/12/23
    //likely used internally as unique identifiers (whole numbers) for the clients; used for identification, management, etc. 
    //for cases where it is more efficient to use whole numbers to distinguish clients rather than a string like clientName
    private long clientId;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    /**
     * Generic text based message
     */

    //yc73
    //11/12/23
    //nessage holds the text as a string, which can be used for sending user messages, transmitting data between proccesses (like commands), or to log/record actions
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Type[%s],ClientId[%s,] ClientName[%s], Message[%s]", getPayloadType().toString(),
                getClientId(), getClientName(),
                getMessage());
    }
}
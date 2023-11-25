<table><tr><td> <em>Assignment: </em> IT114 Chatroom Milestone3</td></tr>
<tr><td> <em>Student: </em> Yaneli Cestona (yc73)</td></tr>
<tr><td> <em>Generated: </em> 11/24/2023 7:56:45 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-005-F23/it114-chatroom-milestone3/grade/yc73" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone3 from the proposal document:&nbsp;&nbsp;<a href="https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view">https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Connection Screens </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshots showing the screens with the following data</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.11.48Screenshot%202023-11-20%20193836.png.webp?alt=media&token=6866db9b-3064-4981-b04f-3a60cac7c9f9"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing UI Host &amp; Port<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.12.38Screenshot%202023-11-20%20193846.png.webp?alt=media&token=38a6f8d6-6a20-480f-94c5-af38f3f071c6"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing UI Username<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.13.58Screenshot%202023-11-20%20193933.png.webp?alt=media&token=da26c75c-d4a6-4966-b0e4-16dcfc86d533"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing UI Username not allowing spaces<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain the code for each step of the process</td></tr>
<tr><td> <em>Response:</em> <ul><li>The UI text field takes in the user input for the host address<br>and port number.</li><li>Then the user moves to the username UI interface, which they<br>also input into the text field.</li><li>The username gets checked, and if it contains<br>spaces an error message is displayed, if not it continues.</li><li>UI "Connect" button triggers<br>the client-server connection.</li><li>In the ClientUI file, the "connect()" method fetches those values.</li><li>The method<br>"Client.INSTANCE.connect(host, port, username, this);" is called, passes those validated parameters, and connects that<br>one user to the server.</li></ul><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Chatroom view </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshots showing the related UI</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.14.56Screenshot%202023-11-20%20193949.png.webp?alt=media&token=7f2cb7dc-212b-4445-a204-84dca3746125"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing a list of users in the room<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.15.29Screenshot%202023-11-20%20194248.png.webp?alt=media&token=b7742b78-5324-4811-bbf1-556bf03a655b"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing chat message area (with history) &amp; create message area and its send<br>button<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.16.25Screenshot%202023-11-21%20000633.png.webp?alt=media&token=b4b193c9-9938-4832-b836-e6674f0ebe24"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing code that lets the enter key send the message<br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Chat Activities </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show screenshots of the result of the following commands</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.17.53Screenshot%202023-11-20%20200510.png.webp?alt=media&token=6d16d1b3-888e-4876-ac30-7c2ef7f1c767"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing /flip, /roll #, and /roll #d# &amp; its output in a different<br>text format<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Show the code snippets for each command</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.24.49Screenshot%202023-11-24%20191752.png.webp?alt=media&token=bfce962e-6b2b-4747-afbb-163a56e151c4"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing code for /flip AND /roll<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.25.54Screenshot%202023-11-24%20191931.png.webp?alt=media&token=54008b68-9824-45ac-8db4-a50af79a599b"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing text formatting<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the code flow of each command</td></tr>
<tr><td> <em>Response:</em> <p>/flip<div><ul><li>The user types a command like &quot;/flip&quot; in the send message area. The<br>message is received and it is then handed over to &quot;Room&quot; where the<br>&quot;processCommand()&quot; method is called. It switches to the case &quot;FLIP&quot; where the appropriate<br>logic for &quot;/flip&quot; is executed. A random number between 0 and 1 is<br>generated and stored in the variable &quot;toss&quot;, then the condition &quot;toss == 0&quot;<br>? &quot;heads&quot; : &quot;tails&quot; is checked, and if &quot;toss&quot; is 0 (which is<br>true) it returns &quot;heads&quot;, or if is it 1 (which is false) it<br>returns &quot;tails&quot;, and the result is sent back to all users (if unmuted)<br>to be shown.</li></ul><div>/roll #</div><ul><li>The user types a command like &quot;/roll #&quot; in the<br>send message area. The message is received and it is then handed over<br>to &quot;Room&quot; where the &quot;processCommand()&quot; method is called. It switches to the case<br>&quot;ROLL&quot; where the appropriate logic for &quot;/roll #&quot; is executed. The message is<br>checked if it doesn&#39;t contain &quot;d&quot;, if not it proceeds to split the<br>message to get the number of sides chosen, and the number is converted<br>to an int. A random number is chosen between 1 and the user&#39;s<br>number and the result is sent back to all users (if unmuted) to<br>be shown.<br></li></ul><div>/roll #d#</div></div><div><ul><li>The user types a command like &quot;/roll #d#&quot; in the send<br>message area. The message is received and it is then handed over to<br>&quot;Room&quot; where the &quot;processCommand()&quot; method is called. It switches to the case &quot;ROLL&quot;<br>where the appropriate logic for &quot;/roll #d#&quot; is executed. The message is checked<br>if it contains &quot;d&quot;, if it does it proceeds to split the message<br>to get the first number which is the number of dice, then gets<br>the second number which is the number of faces (the numbers are converted<br>to an int). A random number is generated (separately) for each die rolled<br>and then added together, and the result of the total rolled&nbsp;is sent back<br>to all users (if unmuted) to be shown.</li></ul><div>formatting</div></div><div><ul><li>Since it is just the results<br>for flip and roll, they are allowed to have HTML tags to format<br>it. I used &lt;font color&gt; to change the color and differentiate each result<br>message. I am able to directly use the HTML tags like &lt;font color=&#39;#1E90FF&#39;&gt;<br>and &lt;/font&gt; because JEditorPane was set to &quot;text/html&quot; to render the HTML and<br>show the formatted message.</li></ul><div><span style="font-size: 13px;">client-&gt;server-&gt;client flow</span></div></div><div><ul><li><span style="font-size: 13px;">Client sends a message that<br>contains &quot;/&quot; at the start of the message along with either &quot;flip&quot; or<br>&quot;roll&quot;</span>&nbsp;to trigger the appropriate command. It gets captured in &quot;ChatPanel&quot;.</li><li>In &quot;ChatPanel&quot;, when the<br>enter or &quot;send&quot; button is pressed, it triggers an action listener which retrieves<br>the text and sends it to &quot;Client&quot; using &quot;Client.INSTANCE.sendMessage(text);&quot;.</li><li>&nbsp;&quot;Client&quot; handles serializing the message<br>as a payload and sends it to the server.</li><li>The payload is received in<br>&quot;ServerThread&quot; in the run() method and is passed to &quot;processPayload(fromClient)&quot;.</li><li>The message gets passed<br>to the user&#39;s current room with &quot;currentRoom.sendMessage(this, p.getMessage());&quot;.&nbsp;</li><li>In &quot;Room&quot;, the sendMessage() checks if<br>the message contains a command, if it does then the appropriate code is<br>executed. Then, it sends the message as a payload to the client from<br>&quot;ServerThread&quot;.</li><li>Client receives the message containing the result of /flip or /roll.</li><li>The message is<br>displayed in the chat area and the formatting gets rendered correctly as well.</li></ul></div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Custom Text </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Screenshots of examples</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.20.34Screenshot%202023-11-20%20201557.png.webp?alt=media&token=1a2a1b6d-a256-410f-a671-d710b92951f5"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing all formatting (in order: bold, italics, underline, blue color, green color, red<br>color, combination (3+blue), combination (3+green), combination (3+red). <br>The input string used is in<br>the chat send area.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Briefly explain how you got the UI side to render the text accordingly</td></tr>
<tr><td> <em>Response:</em> <div>The UI side is able to render text accordingly because the file "ChatPanel"<br>uses a "JEditorPane" and when I changed it from "text/plain" to "text/html" it<br>was now able to render the HTML. So, when a client uses the<br>correct triggers, the triggers are replaced by the corresponding HTML tags, and the<br>JEditor interprets and displays the specified formatting.</div><div><br></div><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 5: </em> Whisper </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshots showing a demo of whisper commands</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.24.52Screenshot%202023-11-20%20214205.png.webp?alt=media&token=701d55b0-3212-455f-8ffc-8b2f2a22f0ca"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing whisper (@) command. <br>Showing when someone @ a user who does not<br>exist. <br>Showing when a muted user attempts to @ the mutee.<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Show the server-side code snippets of how this feature works</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.52.51Screenshot%202023-11-24%20195218.png.webp?alt=media&token=d879702b-e208-4d9e-989a-910d328bb621"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing private messaging code <br>(how message is processed to find the target of<br>the whisper and how sender &amp; target receive the message)<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.27.59Screenshot%202023-11-24%20192123.png.webp?alt=media&token=dc4470c5-dae3-44f8-ac9d-7f502e11ce98"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing code of how sender &amp; target receive the message<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the code logic of how this was achieved</td></tr>
<tr><td> <em>Response:</em> <p>First, the code checks if the message starts with the private message trigger<br>&quot;@&quot;. The message is split into two parts; the first part is the<br>target username (with the @), and the second part is the actual message<br>they want sent. A second split is done to grab just the username<br>without the @. Next, a method is called to verify if the target<br>username is a user that exists in the room (handles error). If not<br>valid, the sender is notified that the client does not exist. If it<br>is valid, the code proceeds. A list is created (&quot;pmClients&quot;) to hold the<br>target username, then the username is added to the list. The &quot;sendPrivateMessage&quot; method<br>is called, it iterates over all the clients and checks if their name<br>is in the list (&quot;pmClients&quot;). If they are the intended client, the code<br>first checks if that client muted the sender. If they are not then<br>the message is sent, but if they are then they are notified that<br>they can&#39;t send a message to this client. The sender is also sent<br>a message as confirmation that the message was sent.<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 6: </em> Mute/Unmute </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Add screenshots demoing this feature</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-21T14.28.42Screenshot%202023-11-20%20213528.png.webp?alt=media&token=d674c60e-2cfb-4e2e-8377-02f5212b1acd"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing mute/unmute command outputs. <br>Showing mutee not receiving the muted user&#39;s regular messages<br>and flip/roll outputs. <br>Showing when someone mutes/unmutes a user who does not exist.<br><br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Add screenshots of the code snippets that achieve this feature</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.30.04Screenshot%202023-11-24%20192239.png.webp?alt=media&token=cbe578d3-a00b-46a8-b841-a0c582448a1b"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing mute list, mute, unmute, and isMuted code (ServerThread)<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.32.19Screenshot%202023-11-24%20192307.png.webp?alt=media&token=d7eec565-afb3-4992-876b-accb91bd886a"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing mute code (Room)<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.33.09Screenshot%202023-11-24%20192318.png.webp?alt=media&token=57623e0f-d0b8-45f8-93f2-e7917e388777"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing unmute code (Room)<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.39.54Screenshot%202023-11-24%20193939.png.webp?alt=media&token=ed1b24fb-b91b-473c-9dbe-161aac8fd5e4"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing Room using the mute list to prevent messages from muted users (sendPrivateMessage())<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-25T00.41.24Screenshot%202023-11-24%20194034.png.webp?alt=media&token=1a83f643-e1e4-44e3-a2a4-c45b7271df52"/></td></tr>
<tr><td> <em>Caption:</em> <p>Showing Room using the mute list to prevent messages from muted users (sendMessage())<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Briefly explain the code logic of how this was achieved</td></tr>
<tr><td> <em>Response:</em> <div>First, for either mute or unmute, it checks if the target username exists<br>in the room. If it is not valid, a message is sent to<br>the sender that the user does not exist. If it is valid, it<br>calls the mute or unmute method on the ServerThread instance of the sender.</div><div><ul><li>When<br>muting, it first checks if the target username is already muted, if not,<br>they are added to the "clientsMuted" list (of the sender). This means the<br>sender won’t receive messages from the muted user anymore. After muting, a confirmation<br>message is sent to the mutee who sent the command (from the Room).<br>The method “sendPrivateMessage” is called to send a message to the muted user,<br>informing them they have been muted.&nbsp;</li><li>When unmuting, it first checks if the target<br>username is already muted, if yes, they are&nbsp;removed from the "clientsMuted" list (of<br>the sender). This means the sender is allowing messages to be received again<br>from the previously muted client. After unmuting, a confirmation message is sent to<br>the mutee who sent the command (from the Room). The method “sendPrivateMessage” is<br>called to send a message to the unmuted user, informing them they have<br>been unmuted.&nbsp;</li></ul></div><div>If the muted user were to send a regular or private message<br>to the mutee, the "sendMessage" or “sendPrivateMessage” method checks whether the sender is<br>on the receiver's "clientMuted" list. If the sender is muted, the message is<br>not delivered and they are notified that they cannot send a message since<br>they were muted.</div><div>There are also error-handling code that catches if a username is<br>not specified along with the /mute or /unmute command.</div><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 7: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Pull request from milestone3 to main</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/yaneliii/yc73-it114-005/pull/7">https://github.com/yaneliii/yc73-it114-005/pull/7</a> </td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-005-F23/it114-chatroom-milestone3/grade/yc73" target="_blank">Grading</a></td></tr></table>
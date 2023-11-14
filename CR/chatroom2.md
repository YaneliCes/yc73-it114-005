<table><tr><td> <em>Assignment: </em> IT114 Chatroom Milestone 2</td></tr>
<tr><td> <em>Student: </em> Yaneli Cestona (yc73)</td></tr>
<tr><td> <em>Generated: </em> 11/13/2023 10:53:48 PM</td></tr>
<tr><td> <em>Grading Link: </em> <a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-005-F23/it114-chatroom-milestone-2/grade/yc73" target="_blank">Grading</a></td></tr></table>
<table><tr><td> <em>Instructions: </em> <p>Implement the features from Milestone2 from the proposal document:&nbsp; <a href="https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view">https://docs.google.com/document/d/1ONmvEvel97GTFPGfVwwQC96xSsobbSbk56145XizQG4/view</a></p>
</td></tr></table>
<table><tr><td> <em>Deliverable 1: </em> Payload </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Payload Screenshots</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T01.12.41Screenshot%202023-11-13%20201036.png.webp?alt=media&token=f31e96af-e699-40dd-b950-cfd3ab5945a3"/></td></tr>
<tr><td> <em>Caption:</em> <p>comments for Payload code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T01.12.52Screenshot%202023-11-13%20201128.png.webp?alt=media&token=63a83d6a-db82-4f86-b2f0-1b22620813ce"/></td></tr>
<tr><td> <em>Caption:</em> <p>comments for Payload methods code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T01.12.59Screenshot%202023-11-13%20201141.png.webp?alt=media&token=fa82d264-362e-477f-a2e2-41fd7d5adb47"/></td></tr>
<tr><td> <em>Caption:</em> <p>comments for Payload methods code<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T02.37.22Screenshot%202023-11-13%20212636.png.webp?alt=media&token=0690be84-726c-4d06-b31d-2861a2887cd2"/></td></tr>
<tr><td> <em>Caption:</em> <p>payload content from the terminal <br></p>
</td></tr>
</table></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 2: </em> Server-side commands </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show the code for the mentioned commands</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T01.17.21Screenshot%202023-11-13%20201502.png.webp?alt=media&token=673e905d-7f6a-4abe-a3ad-61df3412ab54"/></td></tr>
<tr><td> <em>Caption:</em> <p>code for /flip<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T01.19.05Screenshot%202023-11-13%20201512.png.webp?alt=media&token=d2d5ae39-6b18-4a2d-8e2c-854e6c093a86"/></td></tr>
<tr><td> <em>Caption:</em> <p>code for /roll both &quot;/roll #&quot; and &quot;/roll #d#&quot;<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Explain the logic/implementation of each commands</td></tr>
<tr><td> <em>Response:</em> <p>What determines which format of /roll to use is if the message contains<br>&quot;d.&quot; For /roll #, it works by checking if the message does not<br>contain the letter &quot;d&quot;, if it does not then it understands the user<br>wants to roll a single die, the code splits the user&#39;s message to<br>grab their number of choice (which will be the number of sides), the<br>number from the string message is converted to an int, the a number<br>is randomly chosen (Math.random()) between 1 and the number of sides, and then<br>the result of what was rolled is shown. For /roll #d#, it works<br>by checking if the message does contain the letter &quot;d&quot;, if it does<br>then it understands you want to roll multiple dice, the code splits the<br>user&#39;s message to grab the first number, how many dice, and the second<br>number, how many sides for each die, each die is rolled separately (randomized<br>Math.random())&nbsp;with and then added together, and then the result is of the total<br>rolled is shown.<div><br></div><div>For /flip, a random number is chosen between 0 and 1<br>using Math.random(),&nbsp; to represent heads and tails, then the condition &quot;toss == 0&quot;<br>? &quot;heads&quot; : &quot;tails&quot; is checked and if toss is 0 then it<br>is true and &quot;heads&quot; is returned, or if false (not 0) then &quot;tails&quot;<br>is returned, and the result is shown.</div><br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 3: </em> Text Display </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Show the code for the various style handling via markdown or special characters</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T02.11.35Screenshot%202023-11-13%20210552.png.webp?alt=media&token=ebb241a3-0f50-457e-bd18-70666c202170"/></td></tr>
<tr><td> <em>Caption:</em> <p>Shows how bold, italics, underline, and color is processed<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 2: </em> Show source message and the result output in the terminal (note, you won't actually see the styles until Milestone3)</td></tr>
<tr><td><table><tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T03.02.40Screenshot%202023-11-13%20215636.png.webp?alt=media&token=5bc5e1d7-9acf-49b4-b926-67bd66945846"/></td></tr>
<tr><td> <em>Caption:</em> <p>Show bold, italics, underline, color<br></p>
</td></tr>
<tr><td><img width="768px" src="https://firebasestorage.googleapis.com/v0/b/learn-e1de9.appspot.com/o/assignments%2Fyc73%2F2023-11-14T03.02.49Screenshot%202023-11-13%20215802.png.webp?alt=media&token=c4f63191-ded9-4d7e-8791-d4d2b342c044"/></td></tr>
<tr><td> <em>Caption:</em> <p>Shows a mix of all options together<br></p>
</td></tr>
</table></td></tr>
<tr><td> <em>Sub-Task 3: </em> Explain how you got each style applied</td></tr>
<tr><td> <em>Response:</em> <p>The &quot;replaceMessage&quot; method takes in the user&#39;s message and goes though each type<br>of formatting trigger multiple times (bold, italics, underline, red, green, and blue); it<br>is responsible for finding a specific trigger with the corresponding html. Next, once<br>the correct trigger and tag variables are set up, the method &quot;formatMessage&quot; is<br>called, index is set to -1 and the method enters a while loop.<br>The &quot;indexOf&quot; begins searching at the start of the string, if &quot;indexOf&quot;&nbsp; finds<br>a trigger then it returns the index of where it was found and<br>sets it to &quot;startIndex&quot;. Then, it continues searching for the closing trigger right<br>after that index, if it was found &quot;endTrigger&quot; is set to that closing<br>triggers index; this repeats until another trigger cannot be found, &#39;&quot;index&quot; becomes -1,<br>and the loop stops. Then, the triggers are actually replaces with the correct<br>html tags based on the positions of the triggers. So, &quot;message.substring(0, startIndex)&quot; grabs<br>the very start of the message up to (but not including) the index<br>of where the starting trigger is, after the first html tag is concatenated&nbsp;right<br>after the users unformatted text. Then the text that is going to be<br>formatted (after the first trigger and up to before the closing trigger) is<br>taken and put between the html tags and the closing html tag is<br>concatenated right after the formatted text. If there is more text, it can<br>grabs the rest of the remaining unformatted text right after the closing trigger<br>or it can begin the process again with another to-be formatted text. Also,<br>to avoid an infinite loop, &quot;index = endIndex + closeTag.length();&quot; updates the index<br>to the position that is right after the closing html tag. The loop<br>continues until no more triggers are found; the method then returns the formatted<br>string. This process occurs for all options.&nbsp;<br></p><br></td></tr>
</table></td></tr>
<table><tr><td> <em>Deliverable 4: </em> Misc </td></tr><tr><td><em>Status: </em> <img width="100" height="20" src="https://user-images.githubusercontent.com/54863474/211707773-e6aef7cb-d5b2-4053-bbb1-b09fc609041e.png"></td></tr>
<tr><td><table><tr><td> <em>Sub-Task 1: </em> Include the pull request for Milestone2 to main</td></tr>
<tr><td> <a rel="noreferrer noopener" target="_blank" href="https://github.com/yaneliii/yc73-it114-005/pull/6">https://github.com/yaneliii/yc73-it114-005/pull/6</a> </td></tr>
</table></td></tr>
<table><tr><td><em>Grading Link: </em><a rel="noreferrer noopener" href="https://learn.ethereallab.app/homework/IT114-005-F23/it114-chatroom-milestone-2/grade/yc73" target="_blank">Grading</a></td></tr></table>
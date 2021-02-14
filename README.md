# Mr. Turing

There are many Discord bots out there.  
And they can do impressive things:  
they help you by providing reminders,  
they calculate difficult arithmetic expressions for you,  
they can play games like TicTacToe or chess against you.  

But what about a Discord bot, that can do everything?  
What about a Discord Bot, which can solve any solvable problem?  
A Bot which can compute anything that is computable?  

**This** is Mr. Turing™  

A Discord bot, that can and will solve any physically computable problem.  
A Discord bot, that can simulate Turing machines.  


## How to use
### Invite the bot to your server
You can use this invite link to add my instance of this bot to your Discord server:  
[discord.com/oauth2/authorize?client_id=803325974582132736&permissions=2112&scope=bot](https://discord.com/oauth2/authorize?client_id=803325974582132736&permissions=2112&scope=bot)  

The prefix of the bot is `!tm`.  

### Run your own instance
You can run this bot using the gradle wrapper included in this repository with a valid token as argument:
```cmd
gradlew run --args="bot_token"
```

## Commands
These are all commands the bot supports. You can get this list on Discord anytime by using the `help`-command.

- **New Turing machine** (`new`)  
Creates a new modifiable Turing Machine. If a Turing machine was created on this channel before, the old one will get deleted.

- **Add Transition** (`add`)  
Creates a new transition and adds it to the Turing machine.  
A transition has to be in this form: 
```
(currentState, scannedSymbol) -> (nextState, printSymbol, tapeMotion)
```
with TapeMotion being either left, right or none.


- **Build Turing machine** (`build`)  
Builds the Turing machine using the defined data. Once a TM was built, it can no longer be modified. If data like initial/accepting states or the blank symbol was not specified, the default will be used.

- **Perform a calculation step** (`step`)  
Performs one calculation step of the Turing machine.

- **Run the Turing machine** (`run`)  
Executes the Turing machine until it terminates.

- **Print the Turing machines current configuration.** (`config`)  
Prints out the current configuration, including the state, tape content and head position of the Turing machine.

- **Execute all given commands** (`execute`)  
With this command you can put multiple commands in one message, each one in its individual line. They will be parsed one after another.  
Using execute all, you can define a whole Turing machine using one message, making it easy to save and share your creations!  
You can also wrap the commands in code-blocks for better readability.

- **Set Input** (`input`)  
Sets the input of a Turing machine. The given string will be written to the TMs tape when the TM is build.

- **Set the blank symbol** (`blank`)  
Sets the blank symbol with which the initial empty tape will be filled.

- **Define the set of final, accepting states** (`accept`)  
If the Turing machine is in one of the given states, it accepts. The states must be given as a list, separated by whitespaces and/or commas.

- **Help** (`help`)  
Shows this help text.

## Defaults
If you do not specify otherwise, the Turing machine uses these default values:
- Initial state `S`
- Accepting state `F`
- Blank symbol `_`
- Empty input string

## Examples
### Invert a binary string
This is a simple Turing machine that accepts a binary input consisting of `0`s and `1`s and inverts this binary string. Means it replaces all `0`s with `1`s and vice versa.  

You can build the Turing machine with this command:
```
!tm execute
new
add (S, 0) -> (S, 1, R)
add (S, 1) -> (S, 0, R)
add (S, _) -> (F, _, N)
```
And then specify an input string like this:
```
!tm input 11010001101
```
Finally you can execute the Turing machine. You can do that step by step and check the current configuration from time to time, or just run all steps until the TM terminates like this:
```
!tm run
```
**Explaination:**  
The Turing machine starts in the initial State `S`.  
If it reads a `0`, it writes a `1` to the tape and moves one symbol to the right. This is the first transition we define. The second transition does the same, except it accepts the `1` and replaces it with a `0`.  
The third transition reads the default blank symbol `_`, meaning we reached the end of the input string, and therefore goes into the default accepting state `F`.  
If any character is read that is not a `0`, `1` or blank, the Turing machine automatically terminates and does not accept the input, because there were no valid transitions found and the TM is not in an accepting state.

## Hello World
The following Turing machine is a very simple machine that just writes a hardcoded string to the tape. To keep it short, the TM that is shown here, only writes "Hey" to the tape, but you can easily expand it to write any word you like.  
```
!tm execute
new
add (S,  _) -> (q1, H, R)
add (q1, _) -> (q2, e, R)
add (q2, _) -> (F,  y, N)
run
```

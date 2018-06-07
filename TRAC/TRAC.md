


                               _"... the facilities provided by the language should be
                               constructed from as few basic ideas as possible, and
                               ... these should be general-purpose and interrelated
                               in the language in a way which avoided special cases
                               wherever possible."_ 
                                         (Harrison, Data-Structures and Programming)

## TRAC language

`TRAC` (for Text Reckoning And Compiling) Language is a programming language developed
between 1959-1964 by Calvin Mooers and implemented on a PDP-10 in 1964 by L. Peter
Deutsch.

### Why TRAC?

1. It is extremely easy to learn, at least for beginners. Experienced programmers often
   have trouble with it.
2. It is extremely powerful for **non-numeric tasks**. In fact, it is ideal for building
   your own personal language.
3. It offers perhaps the best control of mass storage, and your own style of input-output,
   of any language.
4. Programs can be moved from computer to computer without difficulty.

TRAC Language is great for creating highly interactive systems for special purposes,
including turnkey systems for inexperienced users and "good-guy" systems. It combines this
with good facilities for handling text, and what is needed along with that, terrific
control over mass storage. It is also excellent for simulating complex on-off systems; rum
or has it that TRAC Language was used for simulating a major computer before it was built.

### Why not TRAC?

For numerical operations it is extremely slow, if not terrible, compared to the most
popular languages. The same applies to handling numerical arrays and controlling loops,
which are comparatively awkward in TRAC Language.

### TRAC language description

The fundamental idea of TRAC Language, which has been worked out in detail with the
deepest care, thought and consistency, is this:
```
ALL IS TEXT.
```
 That is, all programs and data are stored as strings of characters, in the same
manner. They are labelled, stored, retrieved, and otherwise treated in the same way, as
strings of text characters.
 Data and programs are not kept in binary form, but remain stored in character form, much
the way they were originally put in. The programs are examined for execution as text
strings, and they call data in the form of text strings.

This gives rise to certain interesting kinds of compatibility.

- Complete compatibility exists in the command structure: the results of one command can
  become another command or can become data for another command.  ```ALMOST NOTHING
  CREATES AN ERROR CONDITION.``` **If enough information is not supplied to execute a
  command, the command is ignored. If too much information is supplied, the extra is
  ignored.**
  
- Complete compatibility exists in the data: **letters and numbers and spaces may be
  freely intermixed.** Special terminal characters (like carriage returns and backspaces)
  are handled just like other characters, giving the programmer complete control of the
  arrangement of output on the page.
  
#### Command format

A TRAC command has the following form. The cross hatch or sharp-sign is the way this
language identifies a command's beginning.

**\#(** `NM`, `arg2`, `arg3`, `arg4`,..**)**

`NM` is the name of any TRAC command. It counts as the first "argument," or piece of
information supplied. `Arg2`, `arg3`, etc. are whatever else the command needs to know to
be carried out.

We will look first at examples that use the arithmetic commands of TRAC Language, not
because it is particularly good at arithmetic, which it isn't, but because they're the
_simplest_ commands. The arithmetic commands are `AD` (add), `SU` (subtract), `ML`
(multiply), `DV` (divide). Each arithmetic command takes three arguments, the command name
and two numbers. Examples:

```
#(ADD, 1, 2)   ; add 1 and 2
#(SU, 4, 2)    ; subtract 4 and 2
#(ML, 12, 42)  ; multiply 12 and 42
#(DV, 100, 10) ; divide 100 by 10
```

The way TRAC commands may be combined provides the language's extraordinary power. This is
based on the way that the TRAC processor examines the program, which Is a string of
character codes. Watch as we combine two `AD` instructions:

`#(AD, 3, #(AD, 2, 5)) ; the answer is 10`

#### The magic scan

The secret of combining TRAC commands is that every command, when executed, **is replaced
by its answer;** and whatever may result is in turn executed. There is exact procedure
for that:

```
   1. SCAN FROM LEFT TO RIGHT UNTIL A RIGHT PARENTHESIS;
+> 2. RESOLVE THE CONTENTS OF THE PAIRED COMMAND PARENTHESISES
|    (execute and replace by the command's result);
|
|  3. STARTING AT THE BEGINNING OF THE RESULT, KEEP SCANNING LEFT 
|     - TO RIGHT UNTIL A RIGHT PARENTHESIS, --+
+---------------------------------------------|

4. WHEN YOU GET TO THE END, PRINT OUT WHAT'S LEFT.
```

Hint: Evaluate inner first.
Example: `#(AD, #(SU, #(AD,3,4), #(SU,7,3)), 1)`

Here is the interesting part:

`#(AD, 1)`

There's no third argument to add to 1 - but that's OK for TRAC **1 its remain!**

#### Pulling in other stuff

The core memory available to the use is divided into two areas, which we may call
`WORKSPACE` and `STANDBY`.
The `STANDBY` area contains strings of characters with names. Here could be some examples:

```
name                    strings
----                    -------

HAROLD                   54321
SUE                      ?!*
PROGRAM                  #(PS, HELP: I'M TRAPPED IN A LOOP) #(CL, PROGRAM)
GALOSHES                 I MUSTEN'T FORGET MY GALOSHES
```

There is an instruction that moves things from the `STANDBY` area to the `WORKSPACE`. This
is the `CALL` instruction: `#(CL, whatever)`

The `CALL` instruction pulls In a copy of the named string to replace it, the call
instruction, in the work area. The string named in the call instruction also stays in the
`STANDBY` area until you want to get rid of it.

Example: `#(CL, HAROLD)` would be replaced by 54321.
Suppose we say in a program: `#(AD, l, #CL, HAROLD))` then the result is 54322.

Now let's do a program loop using the `CALL`. If we type in to our TRAC processor:
`#(CL, PROGRAM)` it should type
```
HELP: I'M TRAPPED IN A LOOP
HELP: I'M TRAPPED IN A LOOP
HELP: I'M TRAPPED IN A LOOP
....
```
Indefinitely.

  Why is this? Let's go through the steps.  We noted that in our `STANDBY` area we had a
string named PROGRAM which consisted of `#(PS, HELP: I'M TRAPPED IN A LOOP) #(CL,
PROGRAM)` `PS` stands for PRINT STRING instruction. PRINT STRING prints out second
argument and forget the rest. NOTE: If the strung was `HELP, I'M TRAPPED IN A LOOP` only
`HELP` will be printed.
  Now, the PRINT STRING command leaves no result, so it is vaporized; all we have left in
the work area is `#(CL, PROGRAM)` which do the same and again, again...

Another example of TRAC Language's consistency: suppose It executes the command `#(CL,
EBENEZER)` when there is no string called EBENEZER. The result is nothing; so that command
disappears, leaving no residue.

### The FORM commands

Let us be a little more precise. The `STANDBY` area Is really called by Mooers "forms
storage," and a string-with- name that is kept there is called a **form**. One reason for
this terminology is that these strings can consist of programs or **arrangements** that we
may want to fit together and combine. Thus they are "forms".

#### Creating a FORM

To create a **form**, you use the DEFINING STRING command: `#(DS, formname, contents)` The
arguments used by `DS` give a name to the form and specify what you want to have stored in
it. Example: `#(DS, ELVIS, 1234)` This will create a form named `ELVIS` with content
`1234`

NOTE: To get _program_ into a form without it's being executed on the way
requires some preparation. For this "protection" is used; see end of the article.

It turns out that DEFINING STRING is the closest TRAC Language has to an **assignment
statement**.  If you want to use a variable `A`, say, to store the current result of
something, in TRAC Language you create a form named A. `#(DS, A , WHATEVER)`. Whenever the
value of `A` is changed, you redefine form `A`.

#### Calling a FORM

As noted already, `#(CL, ELVIS)` will then be replaced by 1234 But a wonderful extension
of this, that hasn't been mentioned yet, is `THE IMPLICIT CALL`

#### Implicit call

  You don't even have to say `CL` to call a form. If the first argument of a command - that
is, the first string inside the command parentheses - is not a command known to TRAC
Language, why, the TRAC processor concludes that the first argument maybe the name of a
form. So now if you type: `#(AD, #(HAROLD), #(ELVIS))`
  It will first note, on reaching the right paren of the HAROLD command , that since
HAROLD is 54321, you evidently wanted this: `#(AD, 54321, '(ELVIS))` then `#(AD, 54321,1234)` then 55555

ATTENTION: 
This language is marvellously suited to data base management, management information
systems, interactive query systems, and the broad spectrum of "business" programming.  For
large-scale scientific number crunching, not so good.  With one exception: "Infinite
precision" arithmetic, when people want things lo hundreds of decimal places.

This implicit call is the trick that allows people to create their own languages very
quickly. In not very long, you could create your own commands - say `ZAPP`, `MELVIN` and some
more; and while at first it is more convenient to type in the TRAC format

`#(ZAPP, #(MELVIN))` it is very little trouble in TRAC Language to create new syntaxes of
your own like `ZAPP | MELVIN` that are interpreted by the TRAC processor as meaning the
same thing.

#### Filling in holes

Another thing the `CALL` command in TRAC Language does is fill in holes that exist In
forms. Let us represent a hole as follows: `[ ]` Now suppose there is a TRAC form with a
hole in it, like this:
```
name                    strings
----                    -------
WORD                     H[ ]T
```
**Additional arguments in the call get plugged into holes in the form**
Example:
```
call                    result
----                    ------
#(CL, WORD)             HT
#(CL, WORD, O)          HOT
#(WORD, A)              HAT
#(WORD, OO)             HOOT
```

Now, a form can have a number of different holes. Let us denote these by
`[1][2][3]...`. Now suppose we have a form:
```
name                    strings
----                    -------
WORD                    [1]H[2]T[3]
```
which we might call numerous ways:
```
call                        result
----                        ------
#(WORD, W, I, E)            WHITE
#(WORD, , OO, OWL)          HOOTOWL
(Note that putting nothing between two commas made nothing the argument.)
#(WORD, #(WORD, , O), S, O) HOTSHOT
```

This fill-in technique is obviously useful for programming.  If a form contains a
program. Its holes can be made to accept varying numbers, form names, text strings, other
programs. Example: Suppose we want to create a new TRAC command, `ADD`, that adds three
numbers instead of just two. Fair enough:

```
name                    strings
----                    -------
ADD                     #(AD, [1], #(AD, [2], [3]))
```
This brings up another example of how nicely TRAC Language works out. Suppose you have the
following In forms storage:

```
name                    strings
----                    -------
ZOWIE                   #(ZIP, [1], [2])
ZIP                     #(ZAP, [1], [2])
ZAP                     #(AD, [1], [2])
```
Try this one: `#(ZOWIE, 5, 7)` It happens that the arguments 5 and 7 will be passed neatly
from `ZOWIE` to `ZIP` to `ZAP` to the final execution of the `AD`; all through the smooth
plugging of holes by the implicit call and the Magic Scan procedure of the TRAC processor.

**TRAC language is:**
- an interpretive language (each step carried out directly by the processor without
  conversion to another form first);
   
- an extensible language (you can add your own commands for your own purposes);

- a list-processing language (for handling complex and amorphous forms of data that don't
  fit in boxes and arrays).

**It is one of the few such languages that fits in little computer.**

#### Drilling the holes

The holes (called by Mooers **segment gaps**) are created by the SEGMENT STRING
instruction.

`#(SS, formname, whateverl, whatever2 ...)`

where _"formname"_ is the form you want to put holes in and the whatever are things you
want to replace by holes. Example: Suppose you have a form

```
name                    strings
----                    -------
INSULT                  YOU ARE A CREEP
```
You make this more general by means of the SEGMENT STRING instruction:
`#(SS, INSULT, CREEP) ; in INSULT replace CREEP with a hole`
resulting in:

```
name                    strings
----                    -------
INSULT                  YOU ARE A [ ]
```
which can be filled in at a more appropriate time. Fuller example. Suppose we type into
the TRAC processor the following:

```
#(DS, THINGY , ONE FOR THE MONEY AND TWO FOR THE SHOW)
#(SS, THINGY, ONE, TWO, ) ; note the space!
```
And the result is:
```
name                    strings
----                    -------
THINGY                  [1][3]FOR[3]THE[3]MONEY[3]AND[3][2][3]FOR[3]THE[3]SHOW
```
We can get it to print out interestingly by typing `#(CL, THINGY, RUN, HIDE)` (since after
the call, the plugged-in form will still be in the forms storage.) This is printed:
`RUNFORTHEMONEYANDHLDEFORTHESHOW`

Or
```
#(CL, THINGY, RUN, HIDE,
)   ; with carriage return
```
NOTE:
In TRAC Language, every command is replaced by its result as the program's execution
proceeds This is ingenious, weird and highly effective

#### Test commands in TRAC language

There are test commands in TRAC Language, but like everything else they work on strings of
characters. Thus they may work on numbers or text. Consider the `EQ` command (test if
equal):

`#(EQ, firstthing, secondthing, ifso, if not)`

where _"firstthing"_ and _"secondthing"_ are the strings being compared, and **ifso** and
if not are the alternatives. If _firstthing_ is the same as _secondthing_, then **ifso**
is what the TRAC processor does, and if not is forgotten.
Example: `#(EQ, 3, #(SU, 5, 2), HOORAY, NUTS)`

If it turns out that 3 is equal to `#(SU, 5, 2)`, which it is, then all that would be left
of the whole string would be `HOORAY` while otherwise the TRAC processor would produce
`NUTS`.

To most computer people this looks completely insideout, with the thing to do next
appearing at **the center of the test instruction**. Others find this feature
at-**trac**-tive.

#### Disc operations

Now for the juicy disk operations. Storing things on disk can occur as an ordinary TRAC
command.

`#(SB, name, form1, form2, form3 ... )`

creates a place out somewhere on disk with the name you give it, and puts in it the forms
you've specified. Example:

`#(SB, JUNK, TOM, DICK, HARRY)`

and they're stored. If you want them later you say `#(FB, JUNK)` and they're back.
Because you can mix the disk operations in with everything else so nicely, you can chain
programs and changing environments with great ease to travel smoothly among different
systems, circumstances, setups.  Here is a stupid program that scans all incoming text for
the word `SHAZAM`. If the word `SHAZAM` appears, it clears out everything, calls a whole
nother disk block, and welcomes its new master. Otherwise nothing happens. If you have
access to a TRAC system (or really want to work on it), you may be able to figure it
out. (`RESTART` must be in the workspace to begin.)

```
name                    strings
----                    -------
RESTART                 #(DS, TEMP, #(RS))#(SS,TEMP, )#(RPT)
RPT                     #(EQ, SHAZAM, #(TEST), (#(EVENT)))#(RPT)
TEST                    #(CS, TEMP, (#(RESTART)))
EVENT                   #(DA)#(FB, MARVEL)#(PS, WELCOME O MASTER)
```

#### Protection and one shot

The last thing we'll talk about is the other two syntactic layouts.  We've already told
you about the main syntactic layout of TRAC Language, which is `#(  )` It turns out that
two more layouts are needed, which we may call `PROTECTION` and `ONE-SHOT`. Protection is
simply:
 
 `(   )`

which *prevents the execution of anything between the parentheses.* The TRAC processor
strips off these plain parentheses and moves on, leaving behind what was in them but not
having executed it. (But it may come back.)  An obvious use is to put around a program
you're designing:

`#(DS, PROG, (#(AD, A, B)))`

but other uses turn up after you've experimented a little.  The last TRAC command
arrangement looks like this `##( )` and you can put any command in it, except that its
result will only be carried one level `##(CL, ZOWIE,3,4)` results in (using the forms we
defined earlier), `#(ZIP, 3, 4)` which is allowed to survive as is, because the moving
finger of the TRAC scanner **does not re-scan the result.** It Is left to the very curious
to try to figure out why this is needed.

#### Conclusion

Whatever can be executed is replaced by its result.  This may or may not yield something
which is in turn executable. When nothing left is executable, what's left is
printed out. That's the TRAC language.
      
TRAC Language,is, besides being an easy language to learn, very powerful for text and
storage applications.
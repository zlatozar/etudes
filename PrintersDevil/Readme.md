## Automatic text formatting

**ATTENTION**: Assume that text is written with correct syntax and punctuation.
Do not try to correct it!

**Formattor** - sets up headlines, selects page sizes, tabulates tables, recognizes
paragraphs, and so on.  The formattor handles text as words, sentences, paragraphs that
is, at the level that humans read it.

- [Commands](#commands)
    - [?papersize](#papersize)
    - [?mode](#?mode)
    - [?paragraph](#paragraph)
    - [?margin](#margin)
    - [?linespacing](#linespacing)
    - [?space](#space)
    - [?blank](#blank)
    - [?center](#center)
    - [?page](#page)
    - [?testpage](#testpage)
    - [?heading](#heading)
    - [?number](#number)
    - [?break](#break)
    - [?footnote](#footnote)
    - [?alias](#alias)

## Commands

Commands must begin the **first** character of a record and always start with ```?``` to set
them off from ordinary text, at least in our example.

Arguments to commands come in two forms. Some arguments are integers and give either an
explicit value for some formattor parameter or the number of source lines to be affected by the
command. Other arguments are words or characters that are to be used in their literal senses. In
both forms, arguments are separated by blanks, and _extra blanks are ignored_. The ```?alias``` command
may have a missing second argument, which is then assumed to be blank (otherwise hard to 
represent under these conventions). _Be careful to have error messages for malformed commands._

NOTE: When we said that command cause ```break``` it means that current pagragraph ends and new paragraph begins.

### ?papersize

```?papersize``` _height width_ (default 40 72) (break: TRUE)

The ```?papersize``` command sets the limits on each page of text; a page may have height
lines and width characters per line. Every time height lines are output, the formattor
must create a new page. Text output lines will fit the entire space between columns 1 and
width as necessary. A new ```?papersize``` command may be issued at any time, but such a
command **automatically terminates the previous paragraph**. The broken paragraph is
finished with the old values of height and width before the new values take effect. 
Changing the paper size might also cause a new page if the new value of height is
less than the old one. At the start of each format run, _height_ should be set to **40**,
and _width_ to **72**, and no ```?papersize``` is necessary if these values are
satisfactory.

### ?mode

**NOTE** For tests use ```example-page.txt``` file located in resources directory.

```?mode``` _[unfilled | fill | justify]_ (default 'fill') (break: TRUE)

The ```?mode``` command sets the processing mode for text passed to the output. Argument
filltype may be one of the three strings ```unfilled```, ```fill```, or ```justify``` (any
other value is an error).  Use of ```?mode``` breaks the previous paragraph, which is
finished by using the old value of filltype. The initial mode is ```fill```; if this is
satisfactory, no ```?mode``` command need be issued.

Within any one paragraph, the source text may be passed to the output file in one of three
**modes**.

```Unfilled``` - the lines from the source text are passed to the output exactly as they
appear. This mode is most commonly used to pass tables or other preformatted material to the
output without change. 

NOTE: This means that long lines for example should not be divided - just ignored after page end.

```Filled``` - the lines from the source are packed as tightly as possible from left to
right in the output lines, and a new output line is not begun until the next word from the
source will not fit on the previous output line. Single spaces are left between words, and
double spaces after sentence-ending symbols like period, exclamation point, and question
mark.

```Justified``` - the lines from the source are first filled to produce a complete output
paragraph. Then each line of the filled paragraph, **except the last**, has enough extra
blanks added between words so that the last word of each line ends exactly on the right
margin.
 No interword gaps should have ```n + 1``` blanks added until all have ```n``` blanks, and
blanks should **not be added after sentence** terminators until all single gaps have two
blanks. In other words gaps length should be equal. The blanks should be added to **randomly**
selected gaps.

### ?paragraph

```?paragraph``` _indent gap_ (default 3 0) (break: TRUE, env: TRUE)

The ```?paragraph``` command breaks one paragraph off and begins another. The new
paragraph's first line is started _indent_ spaces in from the left margin (**indent might be**
**zero, and later we will see how it could be negative** see ```?margin```) and gap blank lines are left
between the old paragraph and the new. If _gap_, or _gap_ and _indent_, are not specified,
they retain their values from their **last previous settings**. The initial value of
indent is **3** and of gap is **0**; if these values are satisfactory, there is no need to
supply arguments when ```?paragraph`` is used. Notice that if **indent is 3, the first
line of the new paragraph starts in column 4.**

### ?margin

```?margin``` _left right_ (break: TRUE, env: TRUE)

The ```?margin``` command causes the left and right margins of the output text to be set into
columns left and right. Naturally the _left_ margin must be **1** or **more**, and the _right_ margin
must be **no more than the current paper width**. A ```?margin``` command breaks the previous
paragraph.

NOTE: With the introduction of ```?margin```, it makes sense to have negative values for
argument indent of the ```?paragraph``` command; simply outdent (a made-up but obvious word) the
first line of the paragraph toward the left edge of the paper. Example:

```
?margin 10
....
?paragraph -5 1
```

```
          Beginning of a text
          that continues ....
          At some point text ends here.
          
     Starts paragraph that is closer to
          the beginning.
```     

### ?linespacing

```?linespacing``` _gap_ (default 1) (break: TRUE, env: TRUE)

The ```?linespacing``` command causes _gap - 1_ blank lines to be left between output lines.
This command breaks the previous paragraph. By default output will be single spaced.

### ?space

```?space``` _n_ (default 0) (break: TRUE, env: FALSE)

The ```?space``` command breaks the previous paragraph and inserts _n_ times the current line-
spacing blank lines into the output. The action is similar to hitting the **carriage return**
```n + 1``` times on the typewriter. If the bottom of a page is reached before all of the blank
 lines have been printed, the excess ones are thrown away, so that all pages will normally start
 at the same first line. The default value of _n_ is zero.

### ?blank

```?blank``` _n_ (break: FALSE, env: FALSE)

The ```?blank``` command works like the ```?space``` command **except that exactly** _n_ blank lines are
inserted into the output; there is no interaction with the ```?linespacing``` argument. This
action is similar to rolling the typewriter platen ```n + 1``` clicks.

### ?center

```?center``` (break: FALSE, env: FALSE)

The ```?center``` command takes the **next source line**, _strips trailing and leading blanks_, and
centers the result between the _left and right margins_ of the next output line. The
previous paragraph is not completely broken, but the line before the centered one may be
short. The centered line does follow the normal linespacing. Naturally an **error occurs if
the centered text is too long to fit the current margins**.

### ?page

```?page```  (break: TRUE, env: FALSE)

The ```?page``` command breaks the current paragraph and, after the last line of the paragraph
has been moved to the output, causes a move to a new output page.

### ?testpage

```?testpage``` _n_ (break: TRUE, env: FALSE)

The ```?testpage``` command breaks the previous paragraph and moves it to the output. If there
are fewer than _n_ blank lines now on the current page, ```?testpage``` works like ```?page```;
otherwise it is completely ignored. Thus ```?testpage``` checks the space remaining on a page.

### ?heading

```?heading``` _depth place position_ (break: FALSE, env: TRUE)

_depth_ - how many lines should be heading
_place_ - in which line of heading should be placed page number
_position_ -  could be one of ```left```, ```right``` or ```center```

The ```?heading``` command sets a title to be used at the top of each page, beginning after the
next page - turn in the output. The next _depth_ lines of the source are taken exactly as is
for a heading occupying the top depth lines of each page. On the line numbered _place_ of
the heading, the page number is filled in on the left, right, or center as argument
_position_ has value ```left```, ```right```, or ```center```. The page number is incremented each time that a
page is turned and starts with value one. The heading lines always use the margins in
effect when the heading was defined. **A heading may be eliminated by using ?heading with a
depth of zero**. The ```?heading``` command does not cause a break.

### ?number

```?number``` _n_ (break: FALSE, env: TRUE)

The ```?number``` command sets the current page number to _n_ and does not cause a break in the
previous paragraph.

### ?break

```?break``` (break: TRUE, env: FALSE)

The ```?break``` command causes a break in the previous paragraph.

### ?footnote

```?footnote``` _depth_ (break: FALSE, env: TRUE)

_depth_ - number of following lines (including lines that contains commands)

The ```?footnote``` command causes the following depth lines of source text, including any
commands, to be placed **at the bottom of the page in footnote position**. The controlling
parameters of the formattor, margins, linespacing, and the like, are saved over the
footnote and are also used initially to provide an environment for the footnote. Enough
source is read from after the footnote to completely fill out the last source line
preceding the ```?footnote```. **Then the footnote is processed and fills the page from the**
**bottom up**. If there is footnote material on the current output page already, the new
material pushes the old material up from below. If the footnote material runs up into
formatted output, the page is finished, and the rest of the footnote goes on the next page
(which is why the last ordinary line before the footnote is filled before footnote
processing begins). Once all depth lines are on the output, processing reverts to the
ordinary text and to the original parameter values (although the page number may have
changed). Obviously ```?footnote``` must not cause a break, and one ```?footnote``` is **not allowed**
within another.

### ?alias

```?alias``` _fake real_ (break: FALSE, env: TRUE)

The ```?alias``` command sets the *single character* _fake_ to stand for the single character _real_
until ```?alias``` is issued again. As each line is passed to output, all instances of _fake_ are
changed to instances of _real_. Blanks have a special use as word separators; an ```?alias```
command can be used to force a blank into the output without causing a word break. An
```?alias``` does **not break** the previous paragraph, and all aliasing can be turned off by
invoking **?alias** with no arguments.

## A word on WORDS, letters, and ARGUMENTS

To fill and justify correctly, the formattor must recognize words and sentences.

```Words``` are rather easy; any nonblank string of characters terminated with a blank or an end
of line is a word. Notice that this category includes **trailing punctuation** as part of the
preceding word.

```Sentences``` end with full stops in English and are normally followed by a
**double** instead of a single space, but the stop may be inside quotes or parentheses.

English also requires that colons be followed by double spaces, so whenever a
word ends with

```
. ? ! .) ?) !) ." ?" !" .") ?") !") :

```
be sure to mark a sentence end as well.

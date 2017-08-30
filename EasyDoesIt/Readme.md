## Easy Language

This is the largest project in the book.

## Specification

```Easy``` is a general-purpose, procedural, algebraic programming language. Its roots lie in ALGOL,
ALGOL 68, and PASCAL. Like them, it is designed to be compiled, loaded, and executed on a reasonably
conventional computer.


- [Specification](#Specification)
    - [Compilations](#compilations)
    - [Programs](#programs)
    - [External Procedures](#external_procedures)
    - [Segments](#segments)
    - [Types](#types)
    - [Declarations](#declarations)
    - [Internal Procedures](#internal_procedures)
    - [Executable Statements](#executable_statements)
    - [Assignments](#assignments)
    - [Procedure Calls](#procedure_calls)
    - [Returns](#returns)
    - [Exits](#exits)
    - [Conditionals](#conditionals)
    - [Compounds](#compounds)
    - [Iterations](#iterations)
    - [Selection](#selection)
    - [REPEAT and REPENT](#repeat_and_repent)
    - [INPUT and OUTPUT](#input_and_output)
    - [NULLS and Labels](#nulls_and_labels)
    - [Expressions](#expressions)
    - [Variables](#variables)
    - [Constants](#constants)
    - [Function Calls](#function_calls)
    - [Lexical Items](#lexical_items)
    

### COMPILATIONS

```
<compilation>     ::=  <program segment>
                   |   <compilation> <program segment>
                 
<program segment> ::=  <main program>
                   |   <external procedure>
```
                   
### PROGRAMS

```                    
<main program> ::=  <program head> <program body> <program end>

<program head> ::=  PROGRAM <identifier> :
 
<program body> ::=  <segment body>

<program end>  ::=  END PROGRAM <indentifier> ;
```

Each ```<main program>``` is named and the **closing name must match the opening**. The ```<program body>```
is, in common with most of the other grouping statements, a ```<segment body>``` and may contain all
of the normal features of a program. We might as well note here that reserved words, identifiers,
and constants **must not** be broken over record boundaries and must be separated one from another
by blanks, operators, comments, or record ends.


### EXTERNAL_PROCEDURES

```
<external procedure>       ::=  <external subprogram>
                            |   <external function>

<external subprogram>      ::=  <external subprogram head> : 
                                <external subprogram body> <external subprogram end>

<external subprogram head> ::=  EXTERNAL PROCEDURE <external procedure name>

<external subprogram body> ::=  <segment body>

<external subprogram end>  ::=  END EXTERNAL PROCEDURE <identifier> ;
                          

<external function>        ::=  <external function head> : 
                                <external function body> <external function end>

<external function head>   ::=  EXTERNAL FUNCTION <external procedure name> <external type>

<external function body>   ::=  <segment body>

<external function end>    ::=  END EXTERNAL FUNCTION <identifier> ;

<external procedure name>  ::=  <identifier>
                            |   <identifier> <external parameter list>

<external parameter list>  ::=  <external parameter head> )

<external parameter head>  ::=  ( <external parameter>
                            |     <external parameter head> , <external parameter>
                           
<external parameter>       ::=  <identifier> <external type>
                            |   <identifier> <external type> NAME

<external type>            ::=  <basic type>

```

The ```<external parameter>```'s and the return values of ```<external function>```'s can only be of one of the
```<basic type>```'s. The ```<formal parameter>```'s are call by value unless tagged by the reserved word NAME,
in which case they are call by name. Each ```<external subprogram>``` has an implicit ```<return statement>```
before the ```<external subprogram end>```, but ```<external function>```'s must exit via an explicit
```<return statement>``` with a returned value; thus it is a semantic error, sometimes detectable during
compilation, to exit an ```<external function>``` through its end.

Easy programmer must explicitly distinguish between subroutine and function declarations. Although the compiler
could deduce the difference, the language forces the programmer to state clearly the intention of the coding.
Finally, Easy allows the compiler and loader to check all type matching, following the dictum that the run-time
system should do as little as possible.


### SEGMENTS

```
<segment body>              ::=  <type definition part> <variable declaration part>
                                 <procedure definition part> <executable statement part>

<type definition part>      ::= 
                             |   <type definition part> <type definition>
                               
<variable declaration part> ::=
                             |   <variable declaration part> <variable declaration>
                               
<procedure definition part> ::=
                             |   <procedure definition part> <procedure definition>

<executable statement part> ::=  <executable statement>
                             |   <executable statement part> <executable statement>
```

A ```<segment body>``` consists of at least one ```<executable statement>``` optionally preceded, in order, by
```<type definition>```'s, ```<variable declaration>```'s, and ```<procedure definition>```'s. The scope of any name is
the entire remaining body of the segment and may be used in the following definitions and declarations.
No name may be declared or defined more than once in a ```<segment body>```, and as in ALGOL, a name may be redefined
or redeclared in an inner ```<segment body>```.

### TYPES

```
<type definition>   ::=  TYPE <identifier> IS <type> ;

<type>              ::=  <basic type>
                     |   <arrayed type>
                     |   <structured type>
                     |   <type identifier>
                   
<basic type>        ::=  INTEGER
                     |   REAL
                     |   BOOLEAN
                     |   STRING
                    
<arrayed type>      ::=  ARRAY <bounds> OF <type>

<bounds>            ::=  [ <bounds expression> ]
                     |   [ <bounds expression> : <bounds expression> ]
                    
<bounds expression> ::=  <expression>

<structured type>   ::=  STRUCTURE <field list> END STRUCTURE

<field list>        ::=  <field>
                     |   <field list> , <field>
                     
<field>             ::=  FIELD <identifier> IS <type>

<type identifier>   ::=  <identifier>

```

A ```<type definition>``` abbreviates a ```<type>``` with a single ```<identifier>``` and the abbreviation can be used
in the future where a ```<type>``` could be. The types include the built-in ```<basic type>```'s, array building
```<arrayed type>```'s, structure building ```<structured type>```'s, and abbreviated ```<type identifier>```'s. The
INTEGER and REAL ```<basic type>```'s may use the hardware integer and real types and follow all
the normal rules. The BOOLEAN ```<basic type>``` consists only of the two constants **TRUE** and **FALSE**.
Items typed STRING are arbitrarily long strings of characters where "arbitrary" may be 
implementation dependent but should always be at least several thousand.

Arrays are single dimensional but may be of arbitrary ```<type>``` so that arrays of arrays of arrays
of .... may be declared. If no explicit lower bound for an array is given, the lower bound is one.
The ```<bound expression>```'s may be arbitrarily complicated as long as they are reducible to integers.
They may only contain variables declared in surrounding ```<segment body>```'s (not in the current
```<segment body>```) or in the formal parameters of a surrounding procedure. The upper bound of a pair
must be no less than the lower bound. The compiler should check where possible, but, in general,
this will require a run-time check. Different instances of the same ```<arrayed type>``` are not regarded as
the same ```<type>``` for the purposes of compile-time type checking. However, an ```<arrayed type>``` may be
named with a ```<type identifier>``` to allow such type reuse.

A ```<structured type>``` is similar to a record in PASCAL. The field ```<identifier>```'s are used as selectors
for items of the field ```<type>```'s. Because of the recursive definition, structures may have substructures.
A particular ```<identifier>``` can name only one ```<field>``` in a ```<structured type>``` but can be reused as a
variable name or the name of a field in another ```<even subordinate>``` ```<structured type>```.


### DECLARATIONS

```
<variable declaration> ::=  DECLARE <declared names> <type> ;

<declared names>       ::=  <identifier>
                        |   <declared names list> )
                        
<declared names list>  ::= ( <identifier>
                        |    <declared names list> , <identifier>
```

A ```<variable declaration>``` gives to the ```<identifier>```'s in its ```<declared names>``` its ```<type>```.
The ```<identifier>```'s are not initialized. A name ```<except for a field selector>``` can have at most one definition or 
declaration in a ```<segment body>```.

### INTERNAL_PROCEDURES

```
<procedure definition>           ::=  <subprogram definition>
                                  |   <function definition>
                                  |   <external subprogram definition>
                                  |   <external function definition>
                           
<subprogram definition>          ::=  <subprogram head> : <subprogram body> <subprogram end>

<function definition>            ::=  <function head> : <function body> <function end>

<external subprogram definition> ::=  <external subprogram head> ;

<external function definition>   ::=  <external function head> ;

<subprogram head>                ::=  PROCEDURE <procedure name>

<function head>                  ::=  FUNCTION <procedure name> <type>

<subprogram body>                ::=  <segment body>

<function body>                  ::=  <segment body>

<subprogram end>                 ::=  END PROCEDURE <identifier> ;

<function end>                   ::=  END FUNCTION <identifier> ;

<procedure name>                 ::=  <identifier>
                                  |   <identifier> <internal parameter list>
                                  
<internal parameter list>        ::=  <internal parameter head> )

<internal parameter head>        ::=  ( <internal parameter>
                                  |     <internal parameter head> , <internal parameter>
                                  
<internal parameter>             ::=  <identifier> <type>
                                  |   <identifier> <type> NAME
```

There may be only one procedure of a given name defined immediately in any one ```<segment body>```.
An ```<external subprogram>``` or ```<external function>``` definition supplies only the heading because an
```<external procedure>``` in the same or a different ```<compilation>``` will supply the body. The local 
definition and the eventually supplied procedure must match exactly in procedure name, order, type,
and mode of formal parameters, and this correspondence will be checked by the loader. Remember
that parameters to an ```<external procedure>``` must be of a ```<basic type>```.

The definition of local procedures is similar. The ```<internal parameter>```'s may be of any ```<type>```, as
may be the return value of a function. A subprogram has an implicit ```<return statement>``` before its
end, but a function must be exited by an explicit ```<return statement>``` with a value. The parameters
are normally call by value but are call by name if marked with NAME. The procedures themselves
are like ALGOL procedures and are fully recursive. A ```<procedure name>``` may not be used before it is
declared.


### EXECUTABLE_STATEMENTS

```
<executable statement> ::=  <assignment statement>
                        |   <call statement>
                        |   <return statement>
                        |   <exit statement>
                        |   <conditional statement>
                        |   <compound statement>
                        |   <iteration statement>
                        |   <selection statement>
                        |   <repeat statement>
                        |   <repent statement>
                        |   <input statement>
                        |   <output statement>
                        |   <null statement>

```

Statements are all terminated with semicolons.


### ASSIGNMENTS

```
<assignment statement> ::=  SET <target list> <expression> ;

<target list>          ::=  <target>
                        |   <target list> <target>
                        
<target>               ::=  <variable> <replace>

<replace>              ::=  :=
```

In an ```<assignment statement>```, the ```<type>``` of all the ```<target>```'s and of the assigned ```<expression>``` must be
the same. The ```<target>```'s are evaluated from **left to right** to find the storage locations and only then
is the expression evaluated to calculate the stored value. Structures and arrays may be assigned if
```<type>```'s are identical. The use of the keyword SET is an example of Easy's wordiness. This 
particular redundancy aids correction when other keywords are misspelled ```<a common user error>```.


### PROCEDURE_CALLS

```
<call statement>       ::=  CALL <procedure reference> ;

<procedure reference>  ::=  <procedure identifier>
                        |   <procedure identifier> <actual argument list>
                        
<procedure identifier> ::=  <identifier>

<actual argument list> ::=  <actual argument head> )

<actual argument head> ::=  ( <expression>
                        |     <actual argument head> , <expression>
```

Only defined procedures that include the ```<call statement>``` in the range of their names may be called.
The actual arguments must correspond exactly in number, order, and type with the procedure's
formal parameters. After the ```<return statement>``` enclosed in the called procedure is executed, 
control passes to the statement following the call. The keyword CALL is used for the same reason as
SET in the ```<assigned statement>```.


### RETURNS

```
<return statement> ::=  RETURN ;
                    |   RETURN <expression> ;
```

A ```<return statement>``` may occur only in a procedure and causes return of control to the calling
statement. There is an implicit ```<return statement>``` at the end of subprograms. Subprogram returns
must be without value and function returns must be with a value of the same type as the function.


### EXITS

```
<exit statement>  ::=  EXIT ;
```

This statement causes a tidy exit from the entire program and a return to the supervisor. It must be
the last statement executed - _not written_ in a ```<program>```.


### CONDITIONALS

```
<conditional statement>        ::=  <simple conditional statement>
                                |   <label> <simple conditional statement>

<simple conditional statement> ::=  <conditional clause> <true branch> FI ;
                                |   <conditional clause> <true branch> <false branch> FI ;

<conditional clause>           ::=  IF <expression>

<true branch>                  ::=  THEN <conditional body>

<false branch>                 ::=  <else> <conditional body>

<else>                         ::=  ELSE

<conditional body>             ::=  <segment body>
```

A ```<conditional statement>``` selects and executes its ```<true branch>``` or its ```<false branch>```, depending on
whether the ```<expression>```, which must be Boolean, is true or false. Each branch is a ```<segment body>```
and may contain all needful definitions, declarations, and statements without any further 
bracketing. Control passes to the statement following the conditional after execution of the selected
branch.


### COMPOUNDS

```
<compound statement> ::=  <simple compound>
                      |   <label> <simple compound>
                      
<simple compound>    ::=  <compound head> <compound body> <compound end>

<compound head>      ::=  BEGIN

<compound body>      ::=  <segment body>

<compound end>       ::=  END ;
                      |   END <identifier> ;
```
                      
There is little need for a ```<compound statement>``` in Easy because of the rest of syntax. However, it
is useful with REPEAT and REPENT statements. Declarations and definitions begin ```<optionally>``` a
compound. If a trailing ```<identifier>``` is included, there must be a ```<label>``` and the ```<identifier>``` must
match the ```<label>```.            
          

### ITERATIONS
          
```
<iteration statement>        ::=  <simple iteration statement>
                              |   <label> <simple iteration statement>
                              
<simple iteration statement> ::=  <iteration head> <iteration body> <iteration end> 

<iteration head>             ::=  <for> <iteration target> <control> DO

<iteration body>             ::=  <segment body>

<iteration end>              ::=  END FOR ;
                              |   END FOR <identifier> ;
                              
<for>                        ::=  FOR

<iteration target>           ::=  <variable> <replace>

<control>                    ::=  <step control>
                              |   <step control> <while control>
                              
<step control>               ::=  <initial value> <step>
                              |   <initial value> <limit>
                              |   <initial value> <step> <limit>
                              
<initial value>              ::=  <expression>

<step>                       ::=  BY <expression>

<limit>                      ::=  TO <expression>

<while control>              ::=  WHILE <expression>          
```

The easiest way to explain the _effect_ of the ```<iteration statement>``` is to write a small piece of "meta-
Easy" that will replace the ```<iteration statement>```. This "definition" as given in the example should be
applied to the ```<iteration statement>``` to find its effect. The "definition" does imply recalculation of
the ```<target>```, ```<limit>```, and ```<step>``` at each iteration. The predicate _"exists"_ is a meta-Easy way to ask
about the exact options used in a particular ```<iteration statement>```. If the closing ```<identifier>``` is used,
it must match the ```<necessarily existent> <label>```.


Example:
```
     SET <iteration target> := <initial value>;
    
top: IF <while control> exists
        THEN SET stoploop := NOT <while control>;
        ELSE SET stoploop := FALSE; FI;
     IF stoploop THEN GOTO end; FI;
     IF <limit> exists & (<iteration target> > <limit>)
        THEN GOTO end; FI;
     <iteration body>
     IF <step> exists
        THEN SET stepvalue := <step>;
        ELSE SET stepvalue := 1; FI;
     SET <iteration target> : = <iteration target> + stepvalue;
     GOTO top;
end: .....
```    

### SELECTION

```
<selection statement>  ::=  <simple selection>
                        |   <label> <simple selection>
                       
<simple selection>     ::=  <selection head> <selection body> <selection end>

<selection head>       ::=  SELECT <expression> OF

<selection body>       ::=  <case list>
                        |   <case list> <escape case>

<selection end>        ::=  END SELECT ;
                        |   END SELECT <identifier> ;

<case list>            ::=  <case>
                        |   <case list> <case>
                        
<case>                 ::=  <case head> <case body>

<case head>            ::=  CASE <selector> :

<selector>             ::=  <selector head> )

<selector head>        ::=  ( <expression>
                        |     <selector head> , <expression>
                        
<escape case>          ::=  <escape head> <case body>

<escape head>          ::=  OTHERWISE :
 
<case body>            ::=  <segment body>
```

A ```<selection statement>``` operates as follows:

The ```<expression>``` in the ```<selection head>``` is evaluated.
The ```<case>```'s in the ```<case list>``` are processed from first to last.
For each ```<case>```, the ```<expression>```'s in the ```<selector>``` are evaluated one by one from left
to right. As each ```<expression>``` is evaluated, its value is compared with the value of the
original ```<expression>``` in the ```<selection head>```. If the two are equal, the corresponding
```<case body>``` is executed and control then passes out of the ```<selection statement>``` to the
next ```<statement>``` in sequence without any further activity.
If no ```<case>``` is selected and if there is an ```<escape case>```, then the ```<case body>``` of the
```<escape case>``` is executed and control passes out of the ```<selection statement>```. Otherwise
the ```<selection statement>``` has no effect beyond side effects of the various expression
evaluations.
        
        
### REPEAT_AND_REPENT
        
```
<repeat statement> ::= REPEAT <identifier> ;
<repent statement> ::= REPENT <identifier> ;
```

A ```<repeat statement>``` causes a transfer of control back to the beginning of the _enclosing statement_
body labeled with ```<identifier>```. All intervening surrounding segment bodies and the statements of
which they are a part are terminated as if they had been exited normally from the bottom, and all
associated storage is destroyed. The ```<label>``` transferred to must be in the same procedure as the
```<repeat statement>```. If there is no such surrounding labeled statement, the ```<repeat statement>``` is
semantically in error. The ```<repent statement>``` has the same semantics with the exception that control
passes to the point immediately following the surrounding labeled statement rather than to the head
of that statement. Notice that a ```<repeat statement>``` causes reexecution of the statement to whose
head control was transferred.
        
        
### INPUT_AND_OUTPUT
        
```
<input statement>  ::=  INPUT <input list> ;

<input list>       ::=  <variable>
                    |   <input list> , <variable>
                   
<output statement> ::=  OUTPUT <output list> ;

<output list>      ::=  <expression>
                    |   <output list> , <expression>
```                            

The ```<input statement>``` causes transmission of data items from the input stream to the ```<variable>``` 's
in the ```<input list>```. Input items may be of only the basic types, and they must match in type the
corresponding variable. An input item has the same appearance as a constant of the same type.
Items on the input stream must be separated by blanks or new record characters.
The ```<output statement>``` similarly causes transmission of its ```<output list>``` ```<expression>```'s to the output
stream. The ```<expression>```'s must be of the basic types, and the exact format on the output stream is
up to the implementor as long as the values can be read back in again. Each ```<output statement>```
writes a new record character on completion.


### NULLS_AND_LABELS

```
<null statement> ::=  ;

<label>          ::=  <identifier> :
```

The ```<null statement>``` causes no action. A ```<label>``` is an ```<identifier>```, is declared by use, and may not be
declared or defined except as a field selector in the same ```<segment body>```.


### EXPRESSIONS

```
<expression>       ::=  <expression one>
                    |   <expression> | <expression one>
                    |   <expression> XOR <expression one>
                    
<expression one>   ::=  <expression two>
                    |   <expression one> & <expression two>
                    
<expression two>   ::=  <expression three>
                    |   NOT <expression three>

<expression three> ::=  <expression four>
                    |   <expression three> <relation> <expression four>

<expression four>  ::=  <expression five>
                    |   <expression four> || <expression five>
                    
<expression five>  ::=  <expression six>
                    |   <expression five> <adding operator> <expression six>
                    |   <adding operator> <expression six>
                    
<expression six>   ::=  <expression seven>
                    |   <expression six> <multiplying operator> <expression seven>
                    
<expression seven> ::=  FLOOR  ( <expression> )
                    |   LENGTH ( <expression> )
                    |   SUBSTR ( <expression> , <expression> , <expression> )
                    |   CHARACTER ( <expression> )
                    |   NUMBER ( <expression> )
                    |   FLOAT  ( <expression> )
                    |   FIX    ( <expression> )
                    |   <expression eight> 

<expression eight> ::=  <variable>
                    |   <constant>
                    |   <function reference>
                    |   ( <expression> )
```                    

Expressions operate in a fairly standard way. The operators |, XOR (exclusive OR), &, and NOT all
must have Boolean operands. The equality and inequality ```<relation>```'s may hold between any two
items of the same type. Strings may be compared to strings with any of the ```<relation>```'s. Two strings
are equal if and only if they are exactly the same, and string A is less than string B if a prefix of A
is equal to a prefix of B and there are no more characters in A or the next character of A is less than
the next character of B in the collating sequence. Any two integers or reals may be compared by
using any ```<relation>```; integers are implicitly converted to real if compared to reals. The result of any
comparison is always of Boolean type.

Only integers and reals may be combined by using ```<adding operator's and ```<multiplying operator's.
If an integer is combined with a real, the integer is converted to real prior to the operation. Real
numbers may not be used in the MOD operation; in a divide or MOD operation involving only
integers the quotient is always chosen so that the remainder is non negative. The operands of the
catenation operator || are normally strings and the value is a string; reals, integers, and Booleans are
converted to their output string form before the operation.

The FLOOR function takes a real as argument and returns as value the real both integral and not
more than the argument. The LENGTH function takes a string as argument and returns its length
as an integer. The first argument of the SUBSTR function is a string, and the value is a substring
whose first character ```<counting from zero>``` is named by the second integer argument and whose
length is given by the third integer argument. The CHARACTER function takes as argument an
integer and returns a single character string whose character is indexed by the argument in the
collating sequence. The NUMBER function returns as integer value the index in the collating 
sequence of the first character of the argument string. The FLOAT function converts its integer
argument to a real value, and the FIX function converts its real argument to an integer value.
SUBSTR, FIX, and CHARACTER may cause run-time errors.


### VARIABLES

```
<variable> ::=  <identifier>
            |   <variable> . <identifier>
            |   <variable> [ <expression> ]
```    
        
A ```<variable>``` is a simple ```<identifier>```, a ```<variable>``` with a field selector, or a ```<variable>``` with an array
subscript. Of course, all ```<variable>```'s must be declared. An ```<identifier>``` is a terminal syntactic item. It
must begin with an upper or lowercase alphabetic and may continue with an arbitrary number of
alphabetics and decimal digits. Reserved words may not be used as ```<identifier>```'s, and both reserved
words and ```<identifier>```'s must be separated from other nonoperator lexical items by at least one
blank, comment, or new line character. No lexical item may be broken across a record boundary.
        

### CONSTANTS
        
```
<constant>         ::=  <integer constant>
                    |   <real constant>
                    |   <boolean constant>
                    |   <string constant>
                    
<boolean constant> ::=  TRUE
                    |   FALSE
```

An ```<integer constant>``` is an unbroken string of decimal digits and must be separated from other non-
operator lexical items by at least one blank, comment, or new line character. A ```<real constant>``` is an
unbroken string of decimal digits, followed immediately by a period, followed optionally by
another decimal string. Otherwise ```<real constant>```'s follow the same rules as ```<integer constant>```'s.
A ```<string constant>``` is a double quote ", followed by an arbitrary string of characters not 
including a double quote, and terminated by a double quote. Double quotes may be included in
```<string constant>```'s by adding pairs; for example, """" is the string of exactly one double quote.
Otherwise ```<string constant>```'s operate like ```<identifier>```'s. In particular, new line characters may not
appear in strings.
        
        
### FUNCTION_CALLS
        
```
<function reference>  ::=  <function identifier> ( )
                       |   <function identifier> <actual argument list>
                       
<function identifier> ::=  <identifier>
```

A ```<function identifier>``` is an ordinary ```<identifier>``` that occurs in some function definition. Functions
with no arguments are called with the first form of the ```<function reference>```.
        

### LEXICAL_ITEMS
        
```
<relation>             ::=  <
                        |   >
                        |   <=
                        |   >=
                        |   <>
                        
<adding operator>      ::=  +
                        |   -
                        
<multiplying operator> ::=  *
                        |   /
                        |   MOD
```                        

Operators also include ```:, ;, (, ), „ [, ], &, I, ||```, and :=, and do not include XOR, NOT, and MOD
for separation purposes. Comments begin with ```/*```, continue with any string not including ```*/```, end
with ```*/```, and may appear wherever a separator blank may appear. Comments act as separators.

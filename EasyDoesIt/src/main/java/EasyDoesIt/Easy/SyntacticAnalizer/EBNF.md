## EBNF of the Easy language

To the specification are applied:

- left factorization: ```XY | XZ``` become ```X(Y|Z)```

- elimination of left recursion: ```N ::= X | NY``` become ```N ::= X|(Y)*

- substitution of nonterminal symbols: 
  In ```N ::= X``` we may substitute X for any occurrence of N on the right-hand side of 
  another production rule.

- a little bit simplification


## SCANNER

At the lexical level, the program text consists of tokens, comments, and blank space.
The tokens are literals, identifiers, operators, various reserved words, and various
punctuation marks. No reserved word may be chosen as an identifier. Comments and blank
space have no significance, but may be used freely to improve the readability of the
program text. However, two consecutive tokens that would otherwise be confused must be
separated by comments and/or blank space.

### Tokens table

Build **scanner** based on this _lexical grammar_:

```
<Program>           ::=  (<Token> | <Comment> | <Blank>)*
<Token>             ::=  <Integer-Literal> | <String-Literal> | <Identifier> | <Operator> |
                         ARRAY | BEGIN | BY | CALL | CASE | DECLARE | DO | ELSE | END | EXIT | FI |
                         FIELD | FOR | FUNCTION | IF | INPUT | IS | NAME | OF | OTHERWISE | OUTPUT |
                         PROCEDURE | PROGRAM | REPEAT | REPENT | RETURN | SELECT | STRUCTURE |
                         THEN | TO | TYPE | WHILE | . | : | ; | , | := | ( | ) | [ | ]
                        
<Integer-Literal>   ::=  <Digit>(<Digit>)*
<String-Literal>    ::=  " <Graphic>* "
<Identifier>        ::=  <Letter> (<Letter> | <Digit>)*
<Operator>          ::=  <Op-character>(<Op-character>)*
<Comment>           ::=  /* <Graphic> */
<Blank>             ::=  space | tab | end-of-line

<Graphic>           ::=  <Letter> | <Digit> | <Op-character> | space | tab | . | : | ; | , |
                         ~ | ( | ) | [ | ] | { | } | _ | ! | ' | ` | " | # | $ | % | ? | ^ | \
                         
<Letter>            ::=  A | B | C | D | E | F | G | H | I | J | K | L | M |
                         N | O | P | Q | R | S | T | U | V | W | X | Y | Z |
                         a | b | c | d | e | f | g | h | i | j | k | l | m |
                         n | o | p | q | r | s | t | u | v | w | x | y | z
                          
<Digit>             ::=  0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
<Op-character>      ::=  + | - | * | / | < | = | > | & | | |
```

### Micro syntax

```
<Integer-Literal>, <String-Literal>, <Identifier>, <Operator>
```

The syntax category ```<identifier>``` consists of strings that must start with a letter followed by any number
of letters and digits. Also, ```<identifier>``` includes none of the keywords.

### Comments

Comments can't be nested.


## PARSER

Here is some definitions that are important:

- A **command** is executed in order to update variables. (This includes _input-output_.)

- An **expression** is evaluated to yield a value. A record-aggregate is evaluated to construct
  a record value from its component values. An array-aggregate is evaluated to construct
  an array value from its component values.

- A **declaration** is elaborated to produce bindings. Elaborating a declaration may also have
  the side effect of creating and updating variables.
  
- A **value-or-variable-name** may be identified either to yield a value or to assign a
  value to a variable (as required by the context).  
  
- **Formal-parameters** are used to parameterize a procedure or function with respect to
  (some of) the free identifiers in its body.
    
- A **type-denoter** denotes a data type. Every value, constant, variable, and function has a
  specified type.    


### EXPRESSIONS

**Tip**: First rule suggests a recursion.

```
Expression         ::=  PrimaryExpression 
                    |   Expression Operator PrimaryExpression 

PrimaryExpression  ::=  Integer-Literal
                    |   Char-Literal
                    |   Variable
                    |   Operator PrimaryExpression
                    |   Function Reference
                    |   ( Expression )

```


### Variables

A value-or-variable-name identifies a value or variable.

```
<variable>          ::=  <identifier> <rest of variable>
<rest of variable>  ::= {. <identifier> | [ expression ] }*
```    

Implementation note: If RHS contains ORs they should have common interface.

### Function reference
        
```
<function reference>   ::=  <identifier> ( )
                        |   <identifier> <actual argument list>
                       
<actual argument list> ::=  ( <expression>  | {, <expression>}* )                       
```


### Program

```                    
<program>      ::=  <program head> <segment body> <program end>
<program head> ::=  PROGRAM <identifier> :
<program end>  ::=  END PROGRAM <indentifier> ;
```


### External procedures (not implemented)


### Segments

```
<segment body>  ::=  {<type definition>}*
                     {<variable declaration>}*
                     {<procedure definition>}*
                        
                     <executable statement> | {<executable statement>}*
```


### Types

```
<type definition>   ::=  TYPE <identifier> IS <type> ;

<type>              ::=  <type identifier>
                     |   <arrayed type>
                     |   <structured type>

<type identifier>   ::=  <identifier>
                   
<arrayed type>      ::=  ARRAY <bounds> OF <type>

<bounds>            ::=  [ <expression> ]
                     |   [ <expression> : <expression> ]

<structured type>   ::=  STRUCTURE <field list> END STRUCTURE
<field list>        ::=  <field> | {, <field>}*
<field>             ::=  FIELD <identifier> IS <type>

```


### Declarations

```
<variable declaration> ::=  DECLARE <declared names> <type> ;
<declared names>       ::=  <identifier>
                        |   ( <identifier> | {, <identifier>}* )
```                        


### Procedure definition

A function returns a value and a procedure just executes commands.

```
<procedure definition>    ::=  <subprogram definition>
                           |   <function definition>
                                  
<subprogram definition>   ::=  <subprogram head> : <segment body> <subprogram end>
<subprogram head>         ::=  PROCEDURE <procedure name>
<subprogram end>          ::=  END PROCEDURE <identifier> ;

<function definition>     ::=  <function head> : <segment body> <function end>
<function head>           ::=  FUNCTION <procedure name> <type>
<function end>            ::=  END FUNCTION <identifier> ;

<procedure name>          ::=  <identifier>
                           |   <identifier> <internal parameter list>

<internal parameter list> ::=  ( <internal parameter> | {, <internal parameter>}* )

<internal parameter>      ::=  <pass value>
                           |   <pass name>

<pass by value>           ::= <identifier> <type>
<pass by name>            ::= <identifier> <type> NAME            

```


### Executable statements

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


### Assignments

```
<assignment statement> ::=  SET <target list> := <expression> ;
<target list>          ::=  variable | {, variable}*
```

Example: ```SET a, b := 42;```

### Procedure calls

```
<call statement>       ::=  CALL <procedure reference> ;

<procedure reference>  ::=  <identifier> 
                        |   <actual argument list>
                        
<actual argument list> ::=  ( <expression>  | {, <expression>}* )

```

### Returns

```
<return statement> ::=  RETURN ;
                    |   RETURN <expression> ;
```


### Exits

```
<exit statement> ::=  EXIT ;
```


### Conditionals

```
<conditional statement> ::=  <conditional clause> <true branch> FI ;
                         |   <conditional clause> <true branch> <false branch> FI ;

<conditional clause>    ::=  IF <expression>
<true branch>           ::=  THEN <segment body>
<false branch>          ::=  ELSE <segment body>

```


### Compounds

```
<compound statement> ::=  BEGIN <segment body> <compound end>

<compound end>       ::=  END ;
                      |   END <identifier> ;
```


### Loops
          
```
<iteration statement> ::=  <iteration head> <segment body> <iteration end>
                             
<iteration head>      ::=  FOR <variable> := <control> DO

<iteration end>       ::=  END FOR ;
                       |   END FOR <identifier> ;

<control>             ::=  <step expression>
                       |   <step expression> <while>

<step expression>     ::=  <expression> <step>
                       |   <expression> <limit>
                       |   <expression> <step> <limit>

<while>               ::=  WHILE <expression>          
                              
<step>                ::=  BY <expression>
<limit>               ::=  TO <expression>
```


### Selection (switch statements)

NOTE: with this **naming convention** it is easy to name classes in AST

```
SelectionStmt  ::=  SelectionHead SelectionBody SelectionEnd
                       
SelectionHead  ::=  SELECT <expression> OF

SelectionBody  ::=  CaseList
                |   CaseList EscapeCase
                        
SelectionEnd   ::=  END SELECT ;
                |   END SELECT <identifier> ;

CaseList       ::=  Case | {Case}*
Case           ::=  CaseHead <segment body>
CaseHead       ::=  CASE Selector :

Selector       ::=  ( <expression> | {, <expression>}* )
                        
EscapeCase     ::=  OTHERWISE : <segment body>
```


### Repeat
        
```
<repeat statement> ::= REPEAT <identifier> ;
```


### Repent

```
<repent statement> ::= REPENT <identifier> ;
```


### Input
        
```
<input statement>  ::=  INPUT <input list> ;
<input list>       ::=  <variable> | {, <variable>}*
```   


### Output

```
<output statement> ::=  OUTPUT <output list> ;
<output list>      ::=  <expression> | {, <expression>}*
```


### Null

```
<null statement> ::=  ;
```         


## Standard environment

```
BOOLEAN TRUE, FALSE, XOR, NOT, AND, OR
INTEGER, FLOAT, REAL, FLOOR, FIX, MOD, <, >, <=, >=, +, -, *, /
CHARACTER, SUBSTR, LENGTH, ||
 =, <>,
```


## CHECKER

1. Checks whether the source program, represented by its AST, satisfies the
   language's scope rules and type rules.

2. Also decorates the AST as follows:
   (a) Each applied occurrence of an identifier or operator is linked to
       the corresponding declaration of that identifier or operator.
   (b) Each expression and value-or-variable-name is decorated by its type.
   (c) Each type identifier is replaced by the type it denotes.
 
3. Standard types are represented by small ASTs.

4. Reports that the identifier or operator used at a leaf of the AST has not been declared.


### Commands

- Check that the given command is well formed
- Always returns null and does not use the given subtree(phrase)

### Expression

- Checks that the expression is well formed
- Decorates the expression node with its inferred type
- Return that type

### Declaration

- Always returns null and does not use the given subtree(phrase)
- Enters all declared identifiers into the identification table

### Value or variable

- Checks that the value-or-variable-name is well-formed
- Decorates it with its inferred type
- Add indication of whether it is a variable or not
- The method's result is the inferred type

### Formal parameters

- Always returns null and does not use the given subtree(phrase)
- Enters all declared identifiers into the identification table

### Type denoters

- Return denoters (sub-tree)

#### Array aggregates

- Return type
- Decorate 'elemCount'

#### Literals, Identifiers and Operators

- Return bindings

#### Standard environment

- Creates 'small' ASTs to represent the standard types.
- Creates small ASTs to represent "declarations" of standard types, constants, procedures, functions, and operators.
- Enters these "declarations" in the identification table.
- This "declaration" summarises the operator's type info.
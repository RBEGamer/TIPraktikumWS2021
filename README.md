# TIPraktikumWS2021
The TI praktikum for Theoretische Informatik Master FH Aachen

## WHILE0 SYNTAX

### SEQUENCES

* _STATEMENT_ ; _STATEMENT_


### STATEMENTS

* IDENT = IDENT + 1
* IDENT = 0
* while IDENT != do begin _SEQUENCE_ end

### EXAMPLE

```java
TEST0(in X1, X2; out Y);
var();
Y = 0
Y = X1 + 1
X2 = 0

```

## TASKS


### PRAKTIKUM_0

Specify regular expressions for all lexical constructs of WHILE0 in javaCC token- 
notation. Identifiers may be defined to begin with a letter, 
followed by any number of alphanumeric characters.

* Define JavaCC tokens
* Define EBNF rules

### PRAKTIKUM_1

Start from the EBNF for the description of the syntax of WHILE0 programs from task 3 of the 
Lab 0 and implement it in javaCC.

* Create a .jj JavaCC file and insert the defined TOKENS and EBNF Regex rules
* Write a main method inside of the .jj file to parse a given program
* Read a example program from a textfile and check if the program can be parse by the parser



### PRAKTIKUM_2

In this experiment, you are to build on practical experiment 1 and develop a complete compiler for 
"WHILE0- to URM source code".  
This URM source code should be executable in the URM Simulator (see download area in ILIAS, 
"Organizational issues and tools/"). The URM Simulator assumes, 
that the URM source code does not contain any line numbers (these are implicitly set by the URM Simulator) and that only a finite number of lines is available. 
) and that only a finite, non-empty sequence of instructions follows (program memory). 
The compiler for "WHILE0- to URM source code" to be developed here is to be implemented according to these 
criteria.

* expand the EBNF rules according the WHILE0 syntax
* generate URM register access for all WHILE0 statements
* create Exceptions
* save generated URM program by the WHILE0 parser as new URM program without line-numbers



## SETUP

Install JetBrains IntelliJ and ant `$ sudo apt install ant` and open the project folder in the IDE:

* `./src/praktikum_1/w0parser` => first JavaCC While0 parser

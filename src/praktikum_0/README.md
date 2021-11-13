
# TOKEN


## JavaCC TOKEN DEFINITION
```java
TOKEN :
{
	< IN: "in" > |
	< OUT: "out"> |
	< VAR: "var"> |
	< EQUALS_ASSIGN: "=" > |
	< PLUS: "+" > |
	< ZERO: "0" > |
	< ONE: "1" > |
	< NOTEQUALS: "!=" > |
	< WHILE: "while" > |
	< DO: "do" > |
	< BEGIN: "begin" > |
	< END: "end" > |
	< SEMICOLON: ";" > |
	< COMMA: "," > |
	< LEFT_BRACKET_ROUND: "(" > |
	< RIGHT_BRACKET_ROUND: ")" > |
	< IDENT: (["a"-"z","A"-"Z"] (["0"-"9"])?)+ >
}
```

## JavaCC SKIP DEFINITION

```java
SKIP :
{
	" "  |
	"\t" |
	"\n" |
	"\r"
}
```

# EBNF


```java

// PROGRAM HEADER
program ::= IDENT LEFT_BRACKET_ROUND program_var_in SEMICOLON program_var_out RIGHT_BRACKET_ROUND SEMICOLON program_var sequence
program_var ::= VAR LEFT_BRACKET_ROUND program_var_help RIGHT_BRACKET_ROUND SEMICOLON
program_var_help ::= (IDENT (COMMA IDENT)*)?
program_var_in ::= IN (IDENT (COMMA IDENT)*)?
program_var_out ::= OUT IDENT

//PROGRAM SEQUENCE / LINES
sequence ::= (variable_assign | while_do) (SEMICOLON sequence)?

//STATEMENTS
variable_assign ::= IDENT EQUALS_ASSIGN ( ZERO | (IDENT PLUS ONE))
while_do ::= WHILE IDENT NOTEQUALS IDENT DO BEGIN sequence END

```
# Recursive Descent

### Authors:
#### Logan Lembke
#### Lawrence Hoffman

### Professor:
#### John M. Weiss, Ph.D.

### Course:
#### CSC 461
#### Fall 2017



```EBNF
<expr>    -> <term> { <addop> <term> }
<term>    -> <factor> { <mulop> <factor> }
<factor>  -> <integer> | <float> | <id> | '(' <expr> ')' | [-] <factor>
<integer> -> <digit> { <digit> }
<float>   -> <integer> . <integer>
<id>      -> <letter> { <letter> | <digit> }
<letter>  -> A | B | C | D | E | F | G | H | I | J | K | L | M |
             N | O | P | Q | R | S | T | U | V | W | X | Y | Z |
             a | b | c | d | e | f | g | h | i | j | k | l | m |
             n | o | p | q | r | s | t | u | v | w | x | y | z | _
<digit>   -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9
<addop>   -> + | -
<mulop>   -> * | / | %
```

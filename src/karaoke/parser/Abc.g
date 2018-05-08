// Grammar for ABC music notation 
@skip whitespace {
}









composer ::= "C:" ' '* first (' ' middle)? ' ' last;
first ::=[A-Z][a-z]* '.'?;
middle ::= [A-Z] '.';
last ::= [A-Z][a-z]* (' ' number)?;
number ::= [1-9][0-9]*;


whitespace ::= [ \t\r\n]+;
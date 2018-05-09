// Grammar for ABC music notation 
@skip whitespace {
}

abc-tune ::= abc-header abc-body

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Header

; ignore space-or-tab between terminals in the header
@skip space-or-tab {
    abc-header ::= number comment* title other-fields* field-key
	number ::= "X:" digit+ end-of-line
	title ::= "T:" text end-of-line
	other-fields ::= composer | default-length | meter | tempo | voice | comment
	composer ::= "C:" text end-of-line
	default-length ::= "L:" note-length end-of-line
	meter ::= "M:" meter end-of-line
	tempo ::= "Q:" tempo end-of-line
	voice ::= "V:" text end-of-line
    key ::= "K:" key end-of-line

	key ::= keynote "m"?
	keynote ::= basenote ("#" | "b")?
	
	meter ::= "C" | "C|" | meter-fraction
	meter-fraction ::= digit+ "/" digit+
	
    tempo ::= meter-fraction "=" digit+
}
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Body

; spaces and tabs have explicit meaning in the body, dont automatically ignore them

abc-body ::= abc-line+
abc-line ::= element+ end-of-line (lyric end-of-line)?  | middle-of-body-field | comment
element ::= note-element | rest-element | tuplet-element | barline | nth-repeat | space-or-tab 

;; notes
note-element ::= note | chord

note ::= pitch note-length?
pitch ::= accidental? basenote octave?
octave ::= "'"+ | ","+
note-length ::= (digit+)? ("/" (digit+)?)?
note-length-strict ::= digit+ "/" digit+

;; "^" is sharp, "_" is flat, and "=" is neutral
accidental ::= "^" | "^^" | "_" | "__" | "="

basenote ::= "C" | "D" | "E" | "F" | "G" | "A" | "B"
        | "c" | "d" | "e" | "f" | "g" | "a" | "b"

;; rests
rest-element ::= "z" note-length?

;; tuplets
tuplet-element ::= tuplet-spec note-element+
tuplet-spec ::= "(" digit 

;; chords
chord ::= "[" note+ "]"

barline ::= "|" | "||" | "[|" | "|]" | ":|" | "|:"
nth-repeat ::= "[1" | "[2"

; A voice field might reappear in the middle of a piece
; to indicate the change of a voice
middle-of-body-field ::= voice

lyric ::= "w:" lyrical-element*
lyrical-element ::= " "+ | "-" | "_" | "*" | "~" | backslash-hyphen | "|" | lyric-text
lyric-text ::= ([A-Z]|[a-z])*

backslash-hyphen = "\\" "-"
; backslash immediately followed by hyphen

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; General

comment ::= space-or-tab* "%" comment-text newline
comment-text ::= [^(newline)]*

end-of-line ::= comment | newline

digit ::= [0-9]
newline ::= "\n" | "\r" "\n"?
space-or-tab ::= " " | "\t"


//composer ::= "C:" ' '* first (' ' middle)? ' ' last;
//first ::=[A-Z][a-z]* '.'?;
//middle ::= [A-Z] '.';
//last ::= [A-Z][a-z]* (' ' number)?;
//number ::= [1-9][0-9]*;


whitespace ::= [ \t\r\n]+;
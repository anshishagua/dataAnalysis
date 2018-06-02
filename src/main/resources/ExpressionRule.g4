grammar ExpressionRule;

start: expression EOF;

Boolean
    : 'TRUE'
    | 'FALSE'
    ;

Equal
    : '='
    ;

NotEqual
    : '<>'
    | '!='
    ;

LessThan
    : '<'
    ;

LessThanOrEqual
    : '<='
    ;

GreaterThan
    : '>'
    ;

GreaterThanOrEqual
    : '>='
    ;

compare
    : Equal
    | NotEqual
    | LessThanOrEqual
    | LessThan
    | GreaterThan
    | GreaterThanOrEqual
    ;

numeric
    : decimal
    | intNumber
    | longNumber
    ;

sign
   : ('+' | '-')
   ;

Dot
   : '.'
   ;

Exp
  : 'E'
  | 'e'
  ;

decimal
    : sign? (Dot number | number Dot number)
    ;

string:
    STRING;

STRING
    : '\'' ~[']* '\''
    ;

intNumber
   : sign? number
   ;

longNumber
   : sign? number
   ;

number
    : Digit+ ;

condition
    : operand conditionRightHandSide?
    | Not condition
    ;

expression
    : andCondition (Or andCondition)*
    ;

andCondition
    : condition (And condition)*
    ;

conditionRightHandSide
    : compare operand
    | Is (Not)? Null
    | In LeftParen expression (Comma expression)* RightParen
    | Between operand And operand
    | Not? Like? operand
    ;

value
    : string
    | numeric
    | param
    | columnRef
    | Boolean
    | Null
    ;

param :
    '${' ID '}'
    ;

id : ID;
columnRef : ID Dot ID;

operand
    : summand
    ;

summand
    : factor (plusMinus factor)*
    ;

plusMinus
    : Plus
    | Minus
    ;

factor
    : term (mulDivMod term)*
    ;

mulDivMod
    : Multiply
    | Divide
    | Mod
    ;

term
   : function
   | value
   | (Plus | Minus) term
   | LeftParen expression RightParen
   | caseWhen
   ;

function
   : ID LeftParen (expression (Comma expression)*)? RightParen
   ;

caseWhen
   : Case (When expression Then expression)+ Else expression End
   ;

Case
   : 'CASE'
   ;

When
   : 'WHEN'
   ;

Then
   : 'THEN'
   ;

Else
   : 'ELSE'
   ;

End
   : 'END'
   ;

Digit
    : [0-9]
    ;

And
    : 'AND'
    | '&&'
    ;

Or
    : 'OR'
    | '||'
    ;

Not
    : 'NOT'
    ;

Multiply
    : '*'
    ;

Divide
    : '/'
    ;

Mod
    : '%'
    ;

Plus
    : '+'
    ;

Minus
    : '-'
    ;

In
    : 'In'
    ;

Is
    : 'IS'
    ;

Null
    : 'NULL'
    ;

LeftParen
    : '('
    ;

RightParen
    : ')'
    ;

Comma
    : ','
    ;

Between
    : 'BETWEEN'
    ;

Like
    : 'LIKE'
    ;


//SINGLE_QUOTE
//    : '\'';

ID
    : [a-zA-Z_\u4e00-\u9fa5]+
    ;

WS
   : [ \t\r\n]+ -> skip
   ;
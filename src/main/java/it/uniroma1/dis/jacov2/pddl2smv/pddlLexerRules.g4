/**
 * PDDL lexer rules for ANTLR v4 
 * Created by:
 * Valsamis Ntouskos
 * Department of Computer Control and Management Engineering
 * University of Rome "La Sapeinza"
 * 
 * $Id: pddl.g4 2013-10-14 18:40:00
 */
grammar pddl;
 

/************* LEXER ****************************/

PAR_OPEN : '(' ;
PAR_CLOSE : ')' ; 
DEFINE : 'define' ;
DOMAIN : 'domain' ;
REQUIREMENTS : ':requirements' ;
TYPES : ':types' ;
//TYPE_ASGN : '-' ;
EITHER_TYPE : 'either' ;
CONSTANTS : ':constants' ;
FUNCTIONS : ':functions' ;
FUN_NUM : 'number' ;
PREDICATES : ':predicates' ;
CONSTRAINTS : ':constraints' ;
ACTION : ':action' ;
PARAMETERS : ':parameters' ;
DURATIVE_ACTION : ':durative-action' ;
DURATION : ':duration' ;
CONDITION : ':condition' ;
DERIVED : ':derived' ;
PROBLEM : 'problem' ;
PROBLEM_DOMAIN : ':domain' ;
OBJECTS : ':objects' ;
INIT : ':init' ;
PRECONDITION : ':precondition' ;
EFFECT : ':effect' ;
GOAL : ':goal' ;

AND_OP : 'and' ;
OR_OP : 'or' ;
NOT_OP : 'not' ;
IMPLY_OP : 'imply' ;
EXISTS_OP : 'exists' ;
FORALL_OP : 'forall' ;
TIMED_AT : 'at' ;
TIMED_OVER : 'over' ;
TIME_START : 'start' ;
TIME_END : 'end' ;
INT_ALL : 'all' ;
PREFERENCE_GD : 'preference' ;
DURATION_CONST : '?duration' ;


PLUS_OP : '+' ;
MINUS_OP : '-' ;
MUL_OP : '*' ;
DIV_OP : '/' ;

EQ : '=' ;
GEQ: '>=' ;
LEQ: '<=' ;
GT : '>' ;
LT : '<' ;

SC_UP : 'scale-up' ;
SC_DOWN : 'scale-down' ;
INCR :  'increase' ;
DECR : 'decrease' ;
WHEN_EFFECT : 'when';
ASSIGN_EFFECT : 'assign';
OPT_MIN : 'minimize' ;
OPT_MAX : 'maximize' ;
PROBLEM_METRIC : ':metric' ;
METR_TOTAL : 'total-time' ;
IS_VIOL : 'is-violated' ;

GOAL_ALWAYS : 'always' ;
GOAL_SOMETIME : 'sometime' ;
GOAL_WITHIN : 'within' ;
GOAL_AMO : 'at-most-once' ;
GOAL_STA : 'sometime-after' ;
GOAL_STB : 'sometime-before' ;
GOAL_AW : 'always-within' ;
GOAL_HD : 'hold-during' ;
GOAL_HA : 'hold-after' ;


NAME:    LETTER ANY_CHAR* ;

fragment LETTER:	'a'..'z' | 'A'..'Z';

fragment ANY_CHAR: LETTER | '0'..'9' | '-' | '_';

VARIABLE : '?' LETTER ANY_CHAR* ;

NUMBER : DIGIT+ ('.' DIGIT+)? ;

fragment DIGIT: '0'..'9';

LINE_COMMENT
    : ';' ~('\n'|'\r')* '\r'? '\n'  -> channel(HIDDEN)
    ;

WHITESPACE
    :   (   ' '
        |   '\t'
        |   '\r'
        |   '\n'
        )+
        -> channel(HIDDEN)
    ;


REQUIRE_KEY
    : ':strips'
    | ':typing'
    | ':negative-preconditions'
    | ':disjunctive-preconditions'
    | ':equality'
    | ':existential-preconditions'
    | ':universal-preconditions'
    | ':quantified-preconditions'
    | ':conditional-effects'
    | ':fluents'
    | ':adl'
    | ':durative-actions'
    | ':derived-predicates'
    | ':timed-initial-literals'
    | ':preferences'
    | ':constraints'
    ;

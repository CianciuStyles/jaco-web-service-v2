/**
 * PDDL grammar for ANTLR v4
 *
 * Adapted from original version implemented by:
 * Zeyn Saigol
 * School of Computer Science
 * University of Birmingham
 *
 * #$Id: Pddl.g 120 2008-10-02 14:59:50Z zas $
 * 
 * Modified by:
 * Valsamis Ntouskos
 * Department of Computer Control and Management Engineering
 * University of Rome "La Sapeinza"
 *
 * $Id: pddl.g4 2013-10-14 18:40:00
 */
grammar pddl;

import pddlLexerRules;

/************* Start of grammar *******************/

pddlDoc : domain | problem;

/************* DOMAINS ****************************/

domain
    : '(' 'define' domainName
      requireDef?
      typesDef?
      constantsDef?
      predicatesDef?
      functionsDef?
      constraints?
      structureDef*
      ')'
    ;

domainName
    : '(' 'domain' NAME ')'    ;

requireDef
	: '(' ':requirements' REQUIRE_KEY+ ')'	
        ;

typesDef
	: '(' ':types' typedNameList ')'	
        ;

// If have any typed names, they must come FIRST!
typedNameList
    : (NAME* | singleTypeNameList+ NAME*)
    ;

singleTypeNameList
    : (NAME+ '-' t=type)	
    ;

type
	: ( '(' 'either' primType+ ')' )
	| primType
	;

primType : NAME ;

functionsDef
	: '(' ':functions' functionList ')'
	;

functionList
	: (atomicFunctionSkeleton+ ('-' functionType)? )*
	;

atomicFunctionSkeleton
	: '(' functionSymbol typedVariableList ')'
	;

functionSymbol : NAME ;

functionType : 'number' ; // Currently in PDDL only numeric functions are allowed

constantsDef
	: '(' ':constants' typedNameList ')'
	;

predicatesDef
	: '(' ':predicates' atomicFormulaSkeleton+ ')'
	;

atomicFormulaSkeleton
	: '(' predicate typedVariableList ')'
	;

predicate : NAME ;

// If have any typed variables, they must come FIRST!
typedVariableList
    : (VARIABLE* | singleTypeVarList+ VARIABLE*)
    ;

singleTypeVarList
    : (VARIABLE+ '-' t=type)
    ;

constraints
	: '(' ':constraints' conGD ')'
	;

structureDef
	: actionDef
	| durativeActionDef
	| derivedDef
	;


/************* ACTIONS ****************************/

actionDef
	: '(' ':action' actionSymbol
	      ':parameters' '(' typedVariableList ')'
           actionDefBody ')'
    ;

actionSymbol : NAME ;


// Should allow preGD instead of goalDesc for preconditions -
// but I can't get the LL(*) parsing to work
// This means 'preference' preconditions cannot be used
actionDefBody
	: ( ':precondition' (('(' ')') | goalDesc))?
	  ( ':effect' (('(' ')') | effect))?
	;

//preGD
//	: prefGD
//	| '(' 'and' preGD* ')'
//	| '(' 'forall' '(' typedVariableList ')' preGD ')'
//	;
//
//prefGD
//	: '(' 'preference' NAME? goalDesc ')'
//	| goalDesc
//	;

goalDesc
	: atomicTermFormula
	| '(' 'and' goalDesc* ')'
	| '(' 'or' goalDesc* ')'
	| '(' 'not' goalDesc ')'
	| '(' 'imply' goalDesc goalDesc ')'
	| '(' 'exists' '(' typedVariableList ')' goalDesc ')'
	| '(' 'forall' '(' typedVariableList ')' goalDesc ')'
    | fComp
    ;

fComp
	: '(' binaryComp fExp fExp ')'
	;

atomicTermFormula
	: '(' predicate term* ')'
	;

term : NAME | VARIABLE ;

/************* DURATIVE ACTIONS ****************************/

durativeActionDef
	: '(' ':durative-action' actionSymbol
	      ':parameters' '(' typedVariableList ')'
           daDefBody ')'
    ;

daDefBody
	: ':duration' durationConstraint
	| ':condition' (('(' ')') | daGD)
    | ':effect' (('(' ')') | daEffect)
    ;

daGD
	: prefTimedGD
	| '(' 'and' daGD* ')'
	| '(' 'forall' '(' typedVariableList ')' daGD ')'
	;

prefTimedGD
	: timedGD
	| '(' 'preference' NAME? timedGD ')'
	;

timedGD
	: '(' 'at' timeSpecifier goalDesc ')'
	| '(' 'over' interval goalDesc ')'
	;

timeSpecifier : 'start' | 'end' ;
interval : 'all' ;

/************* DERIVED DEFINITIONS ****************************/

derivedDef
	: '(' ':derived' typedVariableList goalDesc ')'
	;

/************* EXPRESSIONS ****************************/

fExp
	: NUMBER
	| '(' binaryOp fExp fExp2 ')'
	| '(' '-' fExp ')'
	| fHead
	;

// This is purely a workaround for an ANTLR bug in tree construction
// http://www.antlr.org/wiki/display/ANTLR3/multiple+occurences+of+a+token+mix+up+the+list+management+in+tree+rewrites
fExp2 : fExp ;

fHead
	: '(' functionSymbol term* ')'
	| functionSymbol
	;

effect
	: '(' 'and' cEffect* ')'
	| cEffect
	;

cEffect
	: '(' 'forall' '(' typedVariableList ')' effect ')'
    | '(' 'oneof'  effect* ')'
	| '(' 'when' goalDesc condEffect ')'
	| pEffect
	;

pEffect
	: '(' assignOp fHead fExp ')'
	| '(' 'not' atomicTermFormula ')'
	| atomicTermFormula
	;


// TODO: why is this different from the "and cEffect" above? Does it matter?
condEffect
	: '(' 'and' pEffect* ')'
	| pEffect
	;

// TODO: should these be uppercase & lexer section?
binaryOp : '*' | '+' | '-' | '/' ;

binaryComp : '>' | '<' | '=' | '>=' | '<=' ;

assignOp : 'assign' | 'scale-up' | 'scale-down' | 'increase' | 'decrease' ;


/************* DURATIONS  ****************************/

durationConstraint
	: '(' 'and' simpleDurationConstraint+ ')'
	| '(' ')'
	| simpleDurationConstraint
	;

simpleDurationConstraint
	: '(' durOp '?duration' durValue ')'
	| '(' 'at' timeSpecifier simpleDurationConstraint ')'
	;

durOp :  '<=' | '>=' | '=' ;

durValue : NUMBER | fExp ;

daEffect
	: '(' 'and' daEffect* ')'
	| timedEffect
	| '(' 'forall' '(' typedVariableList ')' daEffect ')'
	| '(' 'when' daGD timedEffect ')'
	| '(' assignOp fHead fExpDA ')'
	;

timedEffect
	: '(' 'at' timeSpecifier daEffect ')'     // BNF has a-effect here, but not defined anywhere
	| '(' 'at' timeSpecifier fAssignDA ')'
	| '(' assignOp fHead fExp ')'         // BNF has assign-op-t and f-exp-t here, but not defined anywhere
	;

fAssignDA
	: '(' assignOp fHead fExpDA ')'
	;

fExpDA
	: '(' ((binaryOp fExpDA fExpDA) | ('-' fExpDA)) ')'
	| '?duration'
	| fExp
	;

/************* PROBLEMS ****************************/

problem
	: '(' 'define' problemDecl
	  problemDomain
      requireDef?
      objectDecl?
      init
      goal
      probConstraints?
      metricSpec?
      // lengthSpec? This is not defined anywhere in the BNF spec
      ')'
    ;

problemDecl
    : '(' 'problem' NAME ')'
    ;

problemDomain
	: '(' ':domain' NAME ')'
	;

objectDecl
	: '(' ':objects' typedNameList ')'
	;

init
	: '(' ':init' initEl* ')'
	;

initEl
	: nameLiteral
	| '(' '=' fHead NUMBER ')'        
	| '(' 'at' NUMBER nameLiteral ')'
	;

nameLiteral
	: atomicNameFormula
	| '(' 'not' atomicNameFormula ')'
	;

atomicNameFormula
	: '(' predicate NAME* ')' 
	;

// Should allow preGD instead of goalDesc -
// but I can't get the LL(*) parsing to work
// This means 'preference' preconditions cannot be used
//goal : '(' ':goal' preGD ')'  -> ^(GOAL preGD);
goal : '(' ':goal' goalDesc ')' 
    ;

probConstraints
	: '(' ':constraints'  prefConGD ')'
	;

prefConGD
	: '(' 'and' prefConGD* ')'
	| '(' 'forall' '(' typedVariableList ')' prefConGD ')'
	| '(' 'preference' NAME? conGD ')'
	| conGD
	;

metricSpec
	: '(' ':metric' optimization metricFExp ')'
	;

optimization : 'minimize' | 'maximize' ;

metricFExp
	: '(' binaryOp metricFExp metricFExp ')'
	| '(' ('*'|'/') metricFExp metricFExp+ ')'
	| '(' '-' metricFExp ')'
	| NUMBER
	| '(' functionSymbol NAME* ')'
	| functionSymbol
    | 'total-time'
	| '(' 'is-violated' NAME ')'
	;

/************* CONSTRAINTS ****************************/

conGD
	: '(' 'and' conGD* ')'
	| '(' 'forall' '(' typedVariableList ')' conGD ')'
	| '(' 'at' 'end' goalDesc ')'
    | '(' 'always' goalDesc ')'
	| '(' 'sometime' goalDesc ')'
 	| '(' 'within' NUMBER goalDesc ')'
	| '(' 'at-most-once' goalDesc ')'
	| '(' 'sometime-after' goalDesc goalDesc ')'
	| '(' 'sometime-before' goalDesc goalDesc ')'
	| '(' 'always-within' NUMBER goalDesc goalDesc ')'
	| '(' 'hold-during' NUMBER NUMBER goalDesc ')'
	| '(' 'hold-after' NUMBER goalDesc ')'
	;




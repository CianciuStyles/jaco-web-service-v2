package it.uniroma1.dis.jacov2.pddl2smv;

// Generated from pddl.g4 by ANTLR 4.1
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link pddlParser}.
 */
public interface pddlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link pddlParser#fHead}.
	 * @param ctx the parse tree
	 */
	void enterFHead(@NotNull pddlParser.FHeadContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#fHead}.
	 * @param ctx the parse tree
	 */
	void exitFHead(@NotNull pddlParser.FHeadContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#pEffect}.
	 * @param ctx the parse tree
	 */
	void enterPEffect(@NotNull pddlParser.PEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#pEffect}.
	 * @param ctx the parse tree
	 */
	void exitPEffect(@NotNull pddlParser.PEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#durValue}.
	 * @param ctx the parse tree
	 */
	void enterDurValue(@NotNull pddlParser.DurValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#durValue}.
	 * @param ctx the parse tree
	 */
	void exitDurValue(@NotNull pddlParser.DurValueContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#primType}.
	 * @param ctx the parse tree
	 */
	void enterPrimType(@NotNull pddlParser.PrimTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#primType}.
	 * @param ctx the parse tree
	 */
	void exitPrimType(@NotNull pddlParser.PrimTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(@NotNull pddlParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(@NotNull pddlParser.PredicateContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#durationConstraint}.
	 * @param ctx the parse tree
	 */
	void enterDurationConstraint(@NotNull pddlParser.DurationConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#durationConstraint}.
	 * @param ctx the parse tree
	 */
	void exitDurationConstraint(@NotNull pddlParser.DurationConstraintContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#durOp}.
	 * @param ctx the parse tree
	 */
	void enterDurOp(@NotNull pddlParser.DurOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#durOp}.
	 * @param ctx the parse tree
	 */
	void exitDurOp(@NotNull pddlParser.DurOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#functionList}.
	 * @param ctx the parse tree
	 */
	void enterFunctionList(@NotNull pddlParser.FunctionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#functionList}.
	 * @param ctx the parse tree
	 */
	void exitFunctionList(@NotNull pddlParser.FunctionListContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull pddlParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull pddlParser.TypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#binaryOp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOp(@NotNull pddlParser.BinaryOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#binaryOp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOp(@NotNull pddlParser.BinaryOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#goalDesc}.
	 * @param ctx the parse tree
	 */
	void enterGoalDesc(@NotNull pddlParser.GoalDescContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#goalDesc}.
	 * @param ctx the parse tree
	 */
	void exitGoalDesc(@NotNull pddlParser.GoalDescContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#typedVariableList}.
	 * @param ctx the parse tree
	 */
	void enterTypedVariableList(@NotNull pddlParser.TypedVariableListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#typedVariableList}.
	 * @param ctx the parse tree
	 */
	void exitTypedVariableList(@NotNull pddlParser.TypedVariableListContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#fComp}.
	 * @param ctx the parse tree
	 */
	void enterFComp(@NotNull pddlParser.FCompContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#fComp}.
	 * @param ctx the parse tree
	 */
	void exitFComp(@NotNull pddlParser.FCompContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#predicatesDef}.
	 * @param ctx the parse tree
	 */
	void enterPredicatesDef(@NotNull pddlParser.PredicatesDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#predicatesDef}.
	 * @param ctx the parse tree
	 */
	void exitPredicatesDef(@NotNull pddlParser.PredicatesDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#durativeActionDef}.
	 * @param ctx the parse tree
	 */
	void enterDurativeActionDef(@NotNull pddlParser.DurativeActionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#durativeActionDef}.
	 * @param ctx the parse tree
	 */
	void exitDurativeActionDef(@NotNull pddlParser.DurativeActionDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#problemDomain}.
	 * @param ctx the parse tree
	 */
	void enterProblemDomain(@NotNull pddlParser.ProblemDomainContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#problemDomain}.
	 * @param ctx the parse tree
	 */
	void exitProblemDomain(@NotNull pddlParser.ProblemDomainContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#conGD}.
	 * @param ctx the parse tree
	 */
	void enterConGD(@NotNull pddlParser.ConGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#conGD}.
	 * @param ctx the parse tree
	 */
	void exitConGD(@NotNull pddlParser.ConGDContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#cEffect}.
	 * @param ctx the parse tree
	 */
	void enterCEffect(@NotNull pddlParser.CEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#cEffect}.
	 * @param ctx the parse tree
	 */
	void exitCEffect(@NotNull pddlParser.CEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#metricSpec}.
	 * @param ctx the parse tree
	 */
	void enterMetricSpec(@NotNull pddlParser.MetricSpecContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#metricSpec}.
	 * @param ctx the parse tree
	 */
	void exitMetricSpec(@NotNull pddlParser.MetricSpecContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#fExp2}.
	 * @param ctx the parse tree
	 */
	void enterFExp2(@NotNull pddlParser.FExp2Context ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#fExp2}.
	 * @param ctx the parse tree
	 */
	void exitFExp2(@NotNull pddlParser.FExp2Context ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#initEl}.
	 * @param ctx the parse tree
	 */
	void enterInitEl(@NotNull pddlParser.InitElContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#initEl}.
	 * @param ctx the parse tree
	 */
	void exitInitEl(@NotNull pddlParser.InitElContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#requireDef}.
	 * @param ctx the parse tree
	 */
	void enterRequireDef(@NotNull pddlParser.RequireDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#requireDef}.
	 * @param ctx the parse tree
	 */
	void exitRequireDef(@NotNull pddlParser.RequireDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#constraints}.
	 * @param ctx the parse tree
	 */
	void enterConstraints(@NotNull pddlParser.ConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#constraints}.
	 * @param ctx the parse tree
	 */
	void exitConstraints(@NotNull pddlParser.ConstraintsContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#daGD}.
	 * @param ctx the parse tree
	 */
	void enterDaGD(@NotNull pddlParser.DaGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#daGD}.
	 * @param ctx the parse tree
	 */
	void exitDaGD(@NotNull pddlParser.DaGDContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#optimization}.
	 * @param ctx the parse tree
	 */
	void enterOptimization(@NotNull pddlParser.OptimizationContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#optimization}.
	 * @param ctx the parse tree
	 */
	void exitOptimization(@NotNull pddlParser.OptimizationContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#init}.
	 * @param ctx the parse tree
	 */
	void enterInit(@NotNull pddlParser.InitContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#init}.
	 * @param ctx the parse tree
	 */
	void exitInit(@NotNull pddlParser.InitContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#timeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterTimeSpecifier(@NotNull pddlParser.TimeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#timeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitTimeSpecifier(@NotNull pddlParser.TimeSpecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#daEffect}.
	 * @param ctx the parse tree
	 */
	void enterDaEffect(@NotNull pddlParser.DaEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#daEffect}.
	 * @param ctx the parse tree
	 */
	void exitDaEffect(@NotNull pddlParser.DaEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#derivedDef}.
	 * @param ctx the parse tree
	 */
	void enterDerivedDef(@NotNull pddlParser.DerivedDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#derivedDef}.
	 * @param ctx the parse tree
	 */
	void exitDerivedDef(@NotNull pddlParser.DerivedDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#daDefBody}.
	 * @param ctx the parse tree
	 */
	void enterDaDefBody(@NotNull pddlParser.DaDefBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#daDefBody}.
	 * @param ctx the parse tree
	 */
	void exitDaDefBody(@NotNull pddlParser.DaDefBodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#fExp}.
	 * @param ctx the parse tree
	 */
	void enterFExp(@NotNull pddlParser.FExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#fExp}.
	 * @param ctx the parse tree
	 */
	void exitFExp(@NotNull pddlParser.FExpContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(@NotNull pddlParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(@NotNull pddlParser.GoalContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#atomicNameFormula}.
	 * @param ctx the parse tree
	 */
	void enterAtomicNameFormula(@NotNull pddlParser.AtomicNameFormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#atomicNameFormula}.
	 * @param ctx the parse tree
	 */
	void exitAtomicNameFormula(@NotNull pddlParser.AtomicNameFormulaContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#objectDecl}.
	 * @param ctx the parse tree
	 */
	void enterObjectDecl(@NotNull pddlParser.ObjectDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#objectDecl}.
	 * @param ctx the parse tree
	 */
	void exitObjectDecl(@NotNull pddlParser.ObjectDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#fExpDA}.
	 * @param ctx the parse tree
	 */
	void enterFExpDA(@NotNull pddlParser.FExpDAContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#fExpDA}.
	 * @param ctx the parse tree
	 */
	void exitFExpDA(@NotNull pddlParser.FExpDAContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#functionSymbol}.
	 * @param ctx the parse tree
	 */
	void enterFunctionSymbol(@NotNull pddlParser.FunctionSymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#functionSymbol}.
	 * @param ctx the parse tree
	 */
	void exitFunctionSymbol(@NotNull pddlParser.FunctionSymbolContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#singleTypeNameList}.
	 * @param ctx the parse tree
	 */
	void enterSingleTypeNameList(@NotNull pddlParser.SingleTypeNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#singleTypeNameList}.
	 * @param ctx the parse tree
	 */
	void exitSingleTypeNameList(@NotNull pddlParser.SingleTypeNameListContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#typedNameList}.
	 * @param ctx the parse tree
	 */
	void enterTypedNameList(@NotNull pddlParser.TypedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#typedNameList}.
	 * @param ctx the parse tree
	 */
	void exitTypedNameList(@NotNull pddlParser.TypedNameListContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#interval}.
	 * @param ctx the parse tree
	 */
	void enterInterval(@NotNull pddlParser.IntervalContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#interval}.
	 * @param ctx the parse tree
	 */
	void exitInterval(@NotNull pddlParser.IntervalContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#binaryComp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryComp(@NotNull pddlParser.BinaryCompContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#binaryComp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryComp(@NotNull pddlParser.BinaryCompContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#singleTypeVarList}.
	 * @param ctx the parse tree
	 */
	void enterSingleTypeVarList(@NotNull pddlParser.SingleTypeVarListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#singleTypeVarList}.
	 * @param ctx the parse tree
	 */
	void exitSingleTypeVarList(@NotNull pddlParser.SingleTypeVarListContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#atomicTermFormula}.
	 * @param ctx the parse tree
	 */
	void enterAtomicTermFormula(@NotNull pddlParser.AtomicTermFormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#atomicTermFormula}.
	 * @param ctx the parse tree
	 */
	void exitAtomicTermFormula(@NotNull pddlParser.AtomicTermFormulaContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#functionsDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionsDef(@NotNull pddlParser.FunctionsDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#functionsDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionsDef(@NotNull pddlParser.FunctionsDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#atomicFunctionSkeleton}.
	 * @param ctx the parse tree
	 */
	void enterAtomicFunctionSkeleton(@NotNull pddlParser.AtomicFunctionSkeletonContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#atomicFunctionSkeleton}.
	 * @param ctx the parse tree
	 */
	void exitAtomicFunctionSkeleton(@NotNull pddlParser.AtomicFunctionSkeletonContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#actionSymbol}.
	 * @param ctx the parse tree
	 */
	void enterActionSymbol(@NotNull pddlParser.ActionSymbolContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#actionSymbol}.
	 * @param ctx the parse tree
	 */
	void exitActionSymbol(@NotNull pddlParser.ActionSymbolContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#actionDef}.
	 * @param ctx the parse tree
	 */
	void enterActionDef(@NotNull pddlParser.ActionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#actionDef}.
	 * @param ctx the parse tree
	 */
	void exitActionDef(@NotNull pddlParser.ActionDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#timedEffect}.
	 * @param ctx the parse tree
	 */
	void enterTimedEffect(@NotNull pddlParser.TimedEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#timedEffect}.
	 * @param ctx the parse tree
	 */
	void exitTimedEffect(@NotNull pddlParser.TimedEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#typesDef}.
	 * @param ctx the parse tree
	 */
	void enterTypesDef(@NotNull pddlParser.TypesDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#typesDef}.
	 * @param ctx the parse tree
	 */
	void exitTypesDef(@NotNull pddlParser.TypesDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#actionDefBody}.
	 * @param ctx the parse tree
	 */
	void enterActionDefBody(@NotNull pddlParser.ActionDefBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#actionDefBody}.
	 * @param ctx the parse tree
	 */
	void exitActionDefBody(@NotNull pddlParser.ActionDefBodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(@NotNull pddlParser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(@NotNull pddlParser.FunctionTypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#nameLiteral}.
	 * @param ctx the parse tree
	 */
	void enterNameLiteral(@NotNull pddlParser.NameLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#nameLiteral}.
	 * @param ctx the parse tree
	 */
	void exitNameLiteral(@NotNull pddlParser.NameLiteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#condEffect}.
	 * @param ctx the parse tree
	 */
	void enterCondEffect(@NotNull pddlParser.CondEffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#condEffect}.
	 * @param ctx the parse tree
	 */
	void exitCondEffect(@NotNull pddlParser.CondEffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#domain}.
	 * @param ctx the parse tree
	 */
	void enterDomain(@NotNull pddlParser.DomainContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#domain}.
	 * @param ctx the parse tree
	 */
	void exitDomain(@NotNull pddlParser.DomainContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#simpleDurationConstraint}.
	 * @param ctx the parse tree
	 */
	void enterSimpleDurationConstraint(@NotNull pddlParser.SimpleDurationConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#simpleDurationConstraint}.
	 * @param ctx the parse tree
	 */
	void exitSimpleDurationConstraint(@NotNull pddlParser.SimpleDurationConstraintContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#timedGD}.
	 * @param ctx the parse tree
	 */
	void enterTimedGD(@NotNull pddlParser.TimedGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#timedGD}.
	 * @param ctx the parse tree
	 */
	void exitTimedGD(@NotNull pddlParser.TimedGDContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#atomicFormulaSkeleton}.
	 * @param ctx the parse tree
	 */
	void enterAtomicFormulaSkeleton(@NotNull pddlParser.AtomicFormulaSkeletonContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#atomicFormulaSkeleton}.
	 * @param ctx the parse tree
	 */
	void exitAtomicFormulaSkeleton(@NotNull pddlParser.AtomicFormulaSkeletonContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#metricFExp}.
	 * @param ctx the parse tree
	 */
	void enterMetricFExp(@NotNull pddlParser.MetricFExpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#metricFExp}.
	 * @param ctx the parse tree
	 */
	void exitMetricFExp(@NotNull pddlParser.MetricFExpContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#structureDef}.
	 * @param ctx the parse tree
	 */
	void enterStructureDef(@NotNull pddlParser.StructureDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#structureDef}.
	 * @param ctx the parse tree
	 */
	void exitStructureDef(@NotNull pddlParser.StructureDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#problem}.
	 * @param ctx the parse tree
	 */
	void enterProblem(@NotNull pddlParser.ProblemContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#problem}.
	 * @param ctx the parse tree
	 */
	void exitProblem(@NotNull pddlParser.ProblemContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#domainName}.
	 * @param ctx the parse tree
	 */
	void enterDomainName(@NotNull pddlParser.DomainNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#domainName}.
	 * @param ctx the parse tree
	 */
	void exitDomainName(@NotNull pddlParser.DomainNameContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#fAssignDA}.
	 * @param ctx the parse tree
	 */
	void enterFAssignDA(@NotNull pddlParser.FAssignDAContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#fAssignDA}.
	 * @param ctx the parse tree
	 */
	void exitFAssignDA(@NotNull pddlParser.FAssignDAContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#pddlDoc}.
	 * @param ctx the parse tree
	 */
	void enterPddlDoc(@NotNull pddlParser.PddlDocContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#pddlDoc}.
	 * @param ctx the parse tree
	 */
	void exitPddlDoc(@NotNull pddlParser.PddlDocContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#constantsDef}.
	 * @param ctx the parse tree
	 */
	void enterConstantsDef(@NotNull pddlParser.ConstantsDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#constantsDef}.
	 * @param ctx the parse tree
	 */
	void exitConstantsDef(@NotNull pddlParser.ConstantsDefContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#effect}.
	 * @param ctx the parse tree
	 */
	void enterEffect(@NotNull pddlParser.EffectContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#effect}.
	 * @param ctx the parse tree
	 */
	void exitEffect(@NotNull pddlParser.EffectContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#assignOp}.
	 * @param ctx the parse tree
	 */
	void enterAssignOp(@NotNull pddlParser.AssignOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#assignOp}.
	 * @param ctx the parse tree
	 */
	void exitAssignOp(@NotNull pddlParser.AssignOpContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#problemDecl}.
	 * @param ctx the parse tree
	 */
	void enterProblemDecl(@NotNull pddlParser.ProblemDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#problemDecl}.
	 * @param ctx the parse tree
	 */
	void exitProblemDecl(@NotNull pddlParser.ProblemDeclContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(@NotNull pddlParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(@NotNull pddlParser.TermContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#probConstraints}.
	 * @param ctx the parse tree
	 */
	void enterProbConstraints(@NotNull pddlParser.ProbConstraintsContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#probConstraints}.
	 * @param ctx the parse tree
	 */
	void exitProbConstraints(@NotNull pddlParser.ProbConstraintsContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#prefTimedGD}.
	 * @param ctx the parse tree
	 */
	void enterPrefTimedGD(@NotNull pddlParser.PrefTimedGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#prefTimedGD}.
	 * @param ctx the parse tree
	 */
	void exitPrefTimedGD(@NotNull pddlParser.PrefTimedGDContext ctx);

	/**
	 * Enter a parse tree produced by {@link pddlParser#prefConGD}.
	 * @param ctx the parse tree
	 */
	void enterPrefConGD(@NotNull pddlParser.PrefConGDContext ctx);
	/**
	 * Exit a parse tree produced by {@link pddlParser#prefConGD}.
	 * @param ctx the parse tree
	 */
	void exitPrefConGD(@NotNull pddlParser.PrefConGDContext ctx);
}
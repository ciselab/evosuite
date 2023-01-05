package org.evosuite.ga.metaheuristics.mosa.structural;

import org.evosuite.Properties;
import org.evosuite.TestGenerationContext;
import org.evosuite.coverage.branch.Branch;
import org.evosuite.coverage.branch.BranchCoverageGoal;
import org.evosuite.coverage.branch.BranchPool;
import org.evosuite.graphs.GraphPool;
import org.evosuite.graphs.ccg.ClassCallGraph;
import org.evosuite.graphs.ccg.ClassCallNode;
import org.evosuite.graphs.cfg.*;
import org.evosuite.testcase.TestFitnessFunction;

import java.util.*;

public class SingleLineCoverageUtils {
    private static BytecodeInstructionPool bytecodeInstructionPool = BytecodeInstructionPool.getInstance(TestGenerationContext.getInstance().getClassLoaderForSUT());
    private static GraphPool graphPool = GraphPool.getInstance(TestGenerationContext.getInstance().getClassLoaderForSUT());
    private static ClassCallGraph classCallGraph = graphPool.getCCFG(Properties.TARGET_CLASS).getCcg();
    private static ClassCallNode targetMethodNode = classCallGraph.getNodeByMethodName(getTargetMethod());




    public static boolean isSingleCoverageMode(){
        return (Properties.TARGET_LINE != null);
    }


    public static Map<String, List<Integer>> getTargetLines() {

        Map<String,List<Integer>> targetLines = new HashMap<>();
        // Nodes to visit in the class call graph
        Stack<TargetLinesInCallGraph> nodesToVisit = initializeNodesToVisit();
        // A set containing the visited nodes (initially empty)
        Set<TargetLinesInCallGraph> visitedNodes = new HashSet<>();

        // start the loop for finding interesting lines
        while (!nodesToVisit.isEmpty()) {
            TargetLinesInCallGraph currentNodeObject = nodesToVisit.pop();
            ClassCallNode currentNode = currentNodeObject.getClassCallNode();
            List<BytecodeInstruction> currentTargetInstructions = currentNodeObject.getTargetInstrucitons();
            // get CFG of current node in call graph
            ActualControlFlowGraph currentCFG = graphPool.getActualCFG(Properties.TARGET_CLASS, currentNode.getMethod());
            // get instructions in the current node in call graph
            List<BytecodeInstruction> candidateInstructions = bytecodeInstructionPool.getInstructionsIn(Properties.TARGET_CLASS, currentNode.getMethod());

            // for each target instruction
            for (BytecodeInstruction targetInstruction : currentTargetInstructions) {
                // save the lines that we should consider as testing goals. the lines that their coverage does not mean that we missed the chance to cover the target line
                for (BytecodeInstruction instruction : candidateInstructions) {
                    if (currentCFG.containsInstruction(instruction) &&
                            currentCFG.getDistance(instruction, targetInstruction) != -1) {
                        // Adding the line to the map
                        if (!targetLines.containsKey(currentNode.getMethod())) {
                            targetLines.put(currentNode.getMethod(), new ArrayList<>());
                        }
                        if (instruction.getLineNumber() > 0 &&
                                !targetLines.get(currentNode.getMethod()).contains(instruction.getLineNumber())) {
                            targetLines.get(currentNode.getMethod()).add(instruction.getLineNumber());
                        }
                    }
                }
            }

            // We are done with analyzing lines in the current node object. So, we save it in visited nodes
            visitedNodes.add(currentNodeObject);

            // Now, we take a look at the parents of current node.
            for (TargetLinesInCallGraph parentNodeObject : getParentNodes(currentNode)){
                if (!visitedNodes.contains(parentNodeObject)){
                    nodesToVisit.push(parentNodeObject);
                }
            }
        }

        return targetLines;
    }

    public static Set<BranchCoverageGoal> getTargetBranchGoals(){

        Set<BranchCoverageGoal> targetBranchGoals  = new HashSet<>();
        // Nodes to visit in the class call graph
        Stack<TargetLinesInCallGraph> nodesToVisit = initializeNodesToVisit();

        // A set containing the visited nodes (initially empty)
        Set<TargetLinesInCallGraph> visitedNodes = new HashSet<>();


        // start the loop for finding interesting branches
        while (!nodesToVisit.isEmpty()){

            TargetLinesInCallGraph currentNodeObject = nodesToVisit.pop();
            ClassCallNode currentNode = currentNodeObject.getClassCallNode();
            List<BytecodeInstruction>  currentTargetInstructions = currentNodeObject.getTargetInstrucitons();
            // get CFG of current node in call graph
            ActualControlFlowGraph currentCFG = graphPool.getActualCFG(Properties.TARGET_CLASS,currentNode.getMethod());

            // for each target instruction
            for (BytecodeInstruction targetInstruction : currentTargetInstructions){
                // Save the control dependencies that we should consider as the testing goals
                Stack<ControlDependency> controlDependenciesToVisit = new Stack<>();
                controlDependenciesToVisit.addAll(targetInstruction.getBasicBlock().getControlDependencies());
                Set<ControlDependency> visitedControlDependencies = new HashSet<>();
                Set<Branch> visitedBranches = new HashSet<>();


                // First, we add the control dependent branches
                while (!controlDependenciesToVisit.isEmpty()){
                    ControlDependency currentCD = controlDependenciesToVisit.pop();

                    BranchCoverageGoal branchCoverageGoal = new BranchCoverageGoal(currentCD.getBranch(),currentCD.getBranchExpressionValue(),currentCD.getBranch().getClassName(),currentCD.getBranch().getMethodName());
                    targetBranchGoals.add(branchCoverageGoal);


                    visitedControlDependencies.add(currentCD);
                    visitedBranches.add(currentCD.getBranch());

                    // add parents
                    Set<ControlDependency> parentCDs = currentCD.getBranch().getInstruction().getBasicBlock().getControlDependencies();
                    for (ControlDependency parentCD : parentCDs){
                        if (!visitedControlDependencies.contains(parentCD)){
                            controlDependenciesToVisit.add(parentCD);
                        }
                    }
                }

                // Then, we add the other branches that can reach to the target instruction(s)
                List<Branch> candidateBranches = BranchPool.getInstance(TestGenerationContext.getInstance().getClassLoaderForSUT()).retrieveBranchesInMethod(Properties.TARGET_CLASS,currentNode.getMethod());
                for (Branch branch : candidateBranches){
                    BytecodeInstruction branchInstruction = branch.getInstruction();
                    if(!visitedBranches.contains(branch) &&
                            currentCFG.containsInstruction(branchInstruction) &&
                            currentCFG.getDistance(branchInstruction,targetInstruction) != -1){

                        ControlDependency cdTrue = new ControlDependency(branch,true);
                        ControlDependency cdFalse = new ControlDependency(branch,false);

                        BranchCoverageGoal branchCoverageGoalTrue = new BranchCoverageGoal(cdTrue.getBranch(),cdTrue.getBranchExpressionValue(),cdTrue.getBranch().getClassName(),cdTrue.getBranch().getMethodName());
                        BranchCoverageGoal branchCoverageGoalFalse = new BranchCoverageGoal(cdFalse.getBranch(),cdFalse.getBranchExpressionValue(),cdFalse.getBranch().getClassName(),cdFalse.getBranch().getMethodName());

                        targetBranchGoals.add(branchCoverageGoalTrue);
                        targetBranchGoals.add(branchCoverageGoalFalse);
                    }
                }
            }


            // We are done with analyzing branches in the current node object. So, we save it in visited nodes
            visitedNodes.add(currentNodeObject);

            // Now, we take a look at the parents of current node.
            for (TargetLinesInCallGraph parentNodeObject : getParentNodes(currentNode)){
                if (!visitedNodes.contains(parentNodeObject)){
                    nodesToVisit.push(parentNodeObject);
                }
            }
        }
        return targetBranchGoals;
    }

    // return the method in which the given target line is
    private static String getTargetMethod() {
        Set<String> knownMethods =  BytecodeInstructionPool.getInstance(TestGenerationContext.getInstance().getClassLoaderForSUT()).knownMethods(Properties.TARGET_CLASS);

        for (String method : knownMethods){
            if (BytecodeInstructionPool.getInstance(TestGenerationContext.getInstance().getClassLoaderForSUT()).
                    getFirstInstructionAtLineNumber(Properties.TARGET_CLASS,method,Integer.parseInt(Properties.TARGET_LINE)) != null){
                return method;
            }
        }

        throw new IllegalStateException(Properties.TARGET_LINE + " is not available in the bytecode pool");
    }

    private static Stack<TargetLinesInCallGraph> initializeNodesToVisit(){
        Stack<TargetLinesInCallGraph> nodesToVisit = new Stack<>();

        // First node to visit is the one containing the target line and the target instruction is the first one appearing in the target line
        List<BytecodeInstruction> targetInstructions = new ArrayList<>();
        targetInstructions.add(bytecodeInstructionPool.getFirstInstructionAtLineNumber(Properties.TARGET_CLASS,targetMethodNode.getMethod(),Integer.parseInt(Properties.TARGET_LINE)));
        nodesToVisit.push(new TargetLinesInCallGraph(targetMethodNode,targetInstructions));

        return nodesToVisit;
    }


    private static Set<TargetLinesInCallGraph> getParentNodes(ClassCallNode currentNode){

        Set<TargetLinesInCallGraph> parentNodes = new HashSet<>();
        // Now, we take a look at the parents of current node.
        Set<ClassCallNode> currentParents = classCallGraph.getParents(currentNode);
        for (ClassCallNode parent : currentParents){

            // Here, we need to make node object (containing all the target instructions) for each parent.
            List<BytecodeInstruction> parentTargetLines = new ArrayList<>();

            List<BytecodeInstruction> parentInstructions = bytecodeInstructionPool.getInstructionsIn(Properties.TARGET_CLASS,parent.getMethod());

            for (BytecodeInstruction parentInstruction : parentInstructions ){
                if (parentInstruction.isMethodCall()){

                    String calledMethodClass = parentInstruction.getCalledMethodsClass();

                    if (calledMethodClass.equals(Properties.TARGET_CLASS)){
                        String calledMethod = parentInstruction.getCalledMethod();

                        if (calledMethod.equals(currentNode.getMethod())){
                            // This is an instruction that invokes the current method (the method that we just analyzed). So, it is a target instruction.
                            parentTargetLines.add(parentInstruction);
                        }
                    }
                }
            }

            if (parentTargetLines.isEmpty()){
                throw new IllegalStateException("target lines cannot be empty");
            }

            // Now that we have all target instructions in the parent method that calls the current method, we can make the object.
            parentNodes.add(new TargetLinesInCallGraph(parent,parentTargetLines));
        }
        return parentNodes;
    }


    public static boolean isTargetRootBranch(TestFitnessFunction fitness) {
        return (classCallGraph.getDistance(classCallGraph.getNodeByMethodName(fitness.getTargetMethod()), targetMethodNode) != -1);
    }
}

package org.evosuite.ga.metaheuristics.mosa.structural;

import org.evosuite.graphs.ccg.ClassCallNode;
import org.evosuite.graphs.cfg.BytecodeInstruction;

import java.util.List;

public class TargetLinesInCallGraph {

    private ClassCallNode classCallNode;
    private List<BytecodeInstruction> targetLines;

    public TargetLinesInCallGraph(ClassCallNode classCallNode, List<BytecodeInstruction> targetLines) {
        this.classCallNode = classCallNode;
        this.targetLines = targetLines;

    }


    public ClassCallNode getClassCallNode() {
        return classCallNode;
    }

    public List<BytecodeInstruction> getTargetInstrucitons() {
        return targetLines;
    }

    @Override
    public boolean equals(Object object) {
        if (! (object instanceof TargetLinesInCallGraph)){
            return false;
        }

        TargetLinesInCallGraph givenTargetLinesInCallGraph = (TargetLinesInCallGraph) object;

        return  ( this.classCallNode.equals(givenTargetLinesInCallGraph.getClassCallNode()) &&
                this.targetLines.equals(givenTargetLinesInCallGraph.targetLines));
    }
}

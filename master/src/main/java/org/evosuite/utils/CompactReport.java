package org.evosuite.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

import org.evosuite.result.BranchInfo;
import org.evosuite.result.MutationInfo;
import org.evosuite.result.TestGenerationResultImpl;

public class CompactReport implements Serializable {
    public String UUT;
    public String testSuiteCode;
    public Set<Integer> allCoveredLines;
    public Set<Integer> allUncoveredLines;
    public Set<BranchInfo> allCoveredBranches;
    public Set<BranchInfo> allUncoveredBranches;
    public Set<MutationInfo> allCoveredMutation;
    public Set<MutationInfo> allUncoveredMutation;

    public HashMap<String, CompactTestCase> testCaseList;

    public CompactReport(TestGenerationResultImpl<?> testGenerationResult) {
        this.allCoveredLines = testGenerationResult.getCoveredLines();
        this.UUT = testGenerationResult.getClassUnderTest();
        this.testSuiteCode = testGenerationResult.getTestSuiteCode();
        this.testCaseList = CompactTestCase.buildTestCaseList(testGenerationResult);
        this.allUncoveredLines = testGenerationResult.getUncoveredLines();
        this.allCoveredBranches = testGenerationResult.getCoveredBranches();
        this.allUncoveredBranches = testGenerationResult.getUncoveredBranches();
        this.allCoveredMutation = testGenerationResult.getCoveredMutants();
        this.allUncoveredMutation = testGenerationResult.getUncoveredMutants();
    }
}


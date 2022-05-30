package org.evosuite.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.evosuite.coverage.mutation.Mutation;
import org.evosuite.result.BranchInfo;
import org.evosuite.result.MutationInfo;
import org.evosuite.result.TestGenerationResultImpl;
import org.evosuite.testcase.TestCase;

public class CompactTestCase implements Serializable {
    public String testName;
    public String testCode;
    public Set<Integer> coveredLines;
    public Set<BranchInfo> coveredBranches;
    public Set<MutationInfo> coveredMutants;

    public CompactTestCase(String testName, String testCode, Set<Integer> coveredLines, Set<BranchInfo> coveredBranches, Set<MutationInfo> coveredMutants) {
        this.testName = testName;
        this.testCode = testCode;
        this.coveredLines = coveredLines;
        this.coveredBranches = coveredBranches;
        this.coveredMutants = coveredMutants;
    }

    public static HashMap<String, CompactTestCase> buildTestCaseList(TestGenerationResultImpl<?> testGenerationResult) {
        HashMap<String, CompactTestCase> map = new HashMap<>();

        Set<String> keySet = testGenerationResult.getTestCaseKeySet();

        for (String key : keySet) {
            // see what is exposed by TestCase
            String testCode = testGenerationResult.getTestCode(key);
            Set<Integer> coveredLines = testGenerationResult.getCoveredLines(key);
            Set<BranchInfo> coveredBranches = testGenerationResult.getCoveredBranches(key);
            Set<MutationInfo> coveredMutants = testGenerationResult.getCoveredMutants(key);

            CompactTestCase compactTestCase = new CompactTestCase(key, testCode, coveredLines, coveredBranches, coveredMutants);
            map.put(key, compactTestCase);
        }


        return map;
    }


}

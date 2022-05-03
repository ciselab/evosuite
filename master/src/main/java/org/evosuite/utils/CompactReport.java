package org.evosuite.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import org.evosuite.result.TestGenerationResultImpl;

public class CompactReport implements Serializable {
    public String UUT;
    public String testSuiteCode;
    public Set<Integer> allCoveredLines;

    public HashMap<String, CompactTestCase> testCaseList;

    public CompactReport(TestGenerationResultImpl<?> testGenerationResult) {
        this.allCoveredLines = testGenerationResult.getCoveredLines();
        this.UUT = testGenerationResult.getClassUnderTest();
        this.testSuiteCode = testGenerationResult.getTestSuiteCode();
        this.testCaseList = CompactTestCase.buildTestCaseList(testGenerationResult);
    }
}


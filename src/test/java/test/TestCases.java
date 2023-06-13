package test;

import execution.ExecutionByKeyword;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestCases {
    @Test
    public void loginTest() throws IOException {
        ExecutionByKeyword execution = new ExecutionByKeyword();
        execution.startExecution("login"); // Change the scenario name as needed
    }
}

package test;

import execution.KeywordDrivenFrameworkOrange;

public class TestRunner {
    public static void main(String[] args) {
        KeywordDrivenFrameworkOrange framework = new KeywordDrivenFrameworkOrange();
        framework.initialize();
        framework.executeTest();
        framework.closeBrowser();
    }
}

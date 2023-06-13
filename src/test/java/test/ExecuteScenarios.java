package test;

public class ExecuteScenarios
{
    KeywordDrivenFrameworkOrange2 framework = new KeywordDrivenFrameworkOrange2();
    public  void login()
    {
        framework.initialize();
        framework.executeTest();
        framework.closeBrowser();
    }
    public static void main(String[] args)
    {
        ExecuteScenarios executeScenarios = new ExecuteScenarios();
        executeScenarios.login();

    }
}

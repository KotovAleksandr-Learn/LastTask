package PagesAndForm;

import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class TestPage extends Form {


    public TestPage(String name) {
        super(By.xpath("//div[(text())='Common info']"), name);
    }

    private String projectNameTxtBoxLocator="//*[(text())='Project name']/following-sibling::p";
    private String testNameTxtBoxLocator="//*[(text())='Test name']/following-sibling::p";
    private String testMethodNameLocator="//*[(text())='Test method name']/following-sibling::p";
    private String statusTxtBoxLocator="//*[(text())='Status']/following-sibling::p/span";
    private String startTimeTxtBoxLocator="//*[(text())='Time info']/following-sibling::p[contains(text(),'Start time')]";
    private String endTimeTxtBoxLocator="//*[(text())='Time info']/following-sibling::p[contains(text(),'End time')]";
    private String environmentTxtBoxLocator="//*[(text())='Environment']/following-sibling::p";
    private String browserTxtBoxLocator="//*[(text())='Browser']/following-sibling::p";


    public String getProjectName() {
        ITextBox projectNameTxtBox=getElementFactory().getTextBox(By.xpath(projectNameTxtBoxLocator),"project name");
        return projectNameTxtBox.getText();
    }

    public String getTestName() {
        ITextBox testNameTxtBox=getElementFactory().getTextBox(By.xpath(testNameTxtBoxLocator),"test name");
        return testNameTxtBox.getText();
    }

    public String getTestMethodName() {
        ITextBox testMethodNameTxtBox=getElementFactory().getTextBox(By.xpath(testMethodNameLocator),"test method name");
        return testMethodNameTxtBox.getText();
    }

    public String getStatus() {
        ITextBox statusTestTxtBox=getElementFactory().getTextBox(By.xpath(statusTxtBoxLocator),"test  status");
        return statusTestTxtBox.getText();
    }

    public String getStartTime() {
        ITextBox startTimeTxtBox=getElementFactory().getTextBox(By.xpath(startTimeTxtBoxLocator),"start time test");
        return startTimeTxtBox.getText();
    }

    public String getEndTime() {
        ITextBox endTimeTxtBox=getElementFactory().getTextBox(By.xpath(endTimeTxtBoxLocator),"end time test");
        return endTimeTxtBox.getText();
    }

    public String getEnvironment() {
        ITextBox environmentTxtBox=getElementFactory().getTextBox(By.xpath(environmentTxtBoxLocator),"environment");
        return environmentTxtBox.getText();
    }

    public String getBrowser() {
        ITextBox browserTxtBox=getElementFactory().getTextBox(By.xpath(browserTxtBoxLocator),"browser");
        return browserTxtBox.getText();
    }







}

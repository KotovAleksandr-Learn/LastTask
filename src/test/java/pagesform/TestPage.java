package pagesform;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class TestPage extends Form {
    private ITextBox projectNameTxtBox = getElementFactory().getTextBox(By.xpath("//*[(text())='Project name']/following-sibling::p"),"project name");
    private ITextBox testNameTxtBox = getElementFactory().getTextBox(By.xpath("//*[(text())='Test name']/following-sibling::p"),"test name");
    private ITextBox testMethodNameTxtBox = getElementFactory().getTextBox(By.xpath("//*[(text())='Test method name']/following-sibling::p"),
            "test method name");
    private ITextBox statusTestTxtBox = getElementFactory().getTextBox(By.xpath("//*[(text())='Status']/following-sibling::p/span"),"test  status");
    private ITextBox startTimeTxtBox = getElementFactory()
            .getTextBox(By.xpath("//*[(text())='Time info']/following-sibling::p[contains(text(),'Start time')]"), "start time test");
    private ITextBox endTimeTxtBox = getElementFactory()
            .getTextBox(By.xpath("//*[(text())='Time info']/following-sibling::p[contains(text(),'End time')]"),"end time test");
    private ITextBox environmentTxtBox = getElementFactory().getTextBox(By.xpath("//*[(text())='Environment']/following-sibling::p"),"environment");
    private ITextBox browserTxtBox = getElementFactory().getTextBox(By.xpath("//*[(text())='Browser']/following-sibling::p"),"browser");
    private ITextBox attachmentTypeTxtBox = getElementFactory().getTextBox(By.xpath("//tr/td[2]"),"attchment type");
    private ITextBox logTxtBox = getElementFactory().getTextBox(By.xpath("//div[@class='panel panel-default']//table//td[1]"),"textBox log");
    private ILink imgLink = getElementFactory().getLink(By.xpath("//td//a[@target='_blank']"),"imageLink");

    public TestPage(String name) {
        super(By.xpath("//div[(text())='Common info']"), name);
    }

    public String getTestData(String param) {
        switch (param) {
            case "projectName":
                return projectNameTxtBox.getText();
            case "testName":
                return testNameTxtBox.getText();
            case "testMethodName":
                return testMethodNameTxtBox.getText();
            case "status":
                return statusTestTxtBox.getText();
            case "startTime":
                return startTimeTxtBox.getText();
            case "endTime":
                return endTimeTxtBox.getText();
            case "environment":
                return environmentTxtBox.getText();
            case "browser":
                return browserTxtBox.getText();
            case "attachmentType":
                return attachmentTypeTxtBox.getText();
            case "logInfo":
                return logTxtBox.getText();
            default:
                return null;
        }
    }

    public String downloadImg() {
        return imgLink.getHref();
    }
}

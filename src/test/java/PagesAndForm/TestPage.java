package PagesAndForm;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

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
    private String logTxtBoxLocator="//div[@class='panel panel-default']//table//td[1]";
    private String attachmentTypeLocator="//tr/td[2]";
    private String imgLinkLocator="//td//a[@target='_blank']";



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

    public String getAttachmentType(){
        ITextBox attachmentTypeTxtBox=getElementFactory().getTextBox(By.xpath(attachmentTypeLocator),"attchment type");
        return attachmentTypeTxtBox.getText();
    }


    public boolean checkLog(String fileName) throws IOException {
        ITextBox logTxtBox=getElementFactory().getTextBox(By.xpath(logTxtBoxLocator),"textBox log");
        return logTxtBox.getText().equals(converteFileDataToSiteLogFormat(fileName));
    }

    public String downloadImg() throws IOException, AWTException {
        ILink imgLink=getElementFactory().getLink(By.xpath(imgLinkLocator),"imageLink");

/*
        Robot robot= new Robot();
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_S);

        StringSelection stringSelection=new StringSelection("C:\\Users\\aleks\\IdeaProjects\\LastTask\\src\\test\\resources\\screenshotFromSite.png");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);*/

        return imgLink.getHref();
    }





//тоже в utils

    public static boolean compareImgs(String firstFilePath, String seconFilePath) throws IOException {
        byte[] arrayBytesFirstImg = Files.readAllBytes(Paths.get(firstFilePath));
        byte[] arrayBytesSecondImg = Files.readAllBytes(Paths.get(seconFilePath));
        return Arrays.equals(arrayBytesFirstImg,arrayBytesSecondImg);
    }

    public static void saveImgFromBrowserPage(String filePath) throws AWTException {
        Robot robot= new Robot();
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_S);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_S);

        StringSelection stringSelection=new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection,null);
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        robot.setAutoDelay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.setAutoDelay(2000);

    }





    public static String converteFileDataToSiteLogFormat(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder builder = new StringBuilder();
        String currentLine = reader.readLine();
        while (currentLine != null) {
            builder.append(currentLine);
            builder.append(" ");
            currentLine = reader.readLine();
        }
        String fileString=builder.toString().replaceAll("  "," ");
        fileString=fileString.substring(1,fileString.length()-1);
        return fileString;

    }






}

package pagesform;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;

public class NewProjectForm extends Form {
    private ITextBox projectNameTxtBox = getElementFactory().getTextBox(By.xpath("//input[@class='form-control']"),"projectName label");
    private IButton saveNewProjectBtn = getElementFactory().getButton(By.xpath("//button[@type='submit']"),"saveNewProjectBtn");
    private ITextBox alertSuccessTxtBox = getElementFactory().getTextBox(By.xpath("//div[@class='alert alert-success']"),"alert success text box");

    public NewProjectForm() {
        super(By.xpath("//form[@id='addProjectForm']"),"add new project form");
    }

    public void setProjectName(String projectName) {
        projectNameTxtBox.type(projectName);
    }

    public void saveNewProject() {
        saveNewProjectBtn.click();
    }

    public boolean checkSuccessSave(String nameProject) {
        return alertSuccessTxtBox.getText().equals("Project " + nameProject + " saved");
    }

    public void closeNewProjectForm(String closeScriptFilePath) {
        AqualityServices.getBrowser().getDriver().switchTo().defaultContent();
        try {
            AqualityServices.getBrowser().executeScript(new File(closeScriptFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.state().waitForNotDisplayed();
    }
}

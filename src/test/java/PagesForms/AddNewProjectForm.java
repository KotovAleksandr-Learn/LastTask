package PagesForms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class AddNewProjectForm extends Form {
    public AddNewProjectForm() {
        super(By.xpath("//div[@class='modal-content']"),"add new project form");
    }

    private String projectNameTxtBoxLocator="//input[@class='form-control']";
    private String saveNewProjectBtnLocator="//button[@type='submit']";
    private String alertSuccessLocator="//div[@class='alert alert-success']";


    public void setProjectName(String projectName){
        ITextBox projectNameTxtBox=getElementFactory().getTextBox(By.xpath(projectNameTxtBoxLocator),"projectName label");
        projectNameTxtBox.type(projectName);
    }
    public void saveNewProject(){
        IButton saveNewProjectBtn=getElementFactory().getButton(By.xpath(saveNewProjectBtnLocator),"saveNewProjectBtn");
        saveNewProjectBtn.click();
    }

    public boolean checkSuccessSave(String nameProject){
        ITextBox alertSuccessTxtBox=getElementFactory().getTextBox(By.xpath(alertSuccessLocator),"alert success text box");
        return alertSuccessTxtBox.getText().equals("Project " + nameProject + " saved");
    }

}

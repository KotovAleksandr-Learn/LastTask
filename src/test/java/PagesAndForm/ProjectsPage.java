package PagesAndForm;

import aquality.selenium.browser.AqualityServices.*;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProjectsPage extends Form {
    public ProjectsPage() {
        super(By.xpath("//p[@class='text-muted text-center footer-text']/span[contains(text(),'Version: 0')]"),"ProjectsPage");
    }

    public String footerVariantNumberLocator="//p[@class='text-muted text-center footer-text']/span";
    public String linkToProjectNexagePageLocator="//a[contains(text(),'Nexage')]";
    public String addNewProjectBtnLocator="//button[@class='btn btn-xs btn-primary pull-right']";
    public String newProjectLinkLocator="//div[@class='list-group']//a[(text())='";


    public ListGroupForm listGroupForm;
    public AddNewProjectForm newProjectForm;


    public String getFooterVariantNumberLocator(){
        ITextBox footerVariantNumber=getElementFactory().getTextBox(By.xpath(footerVariantNumberLocator),"footer txtBox");
        return footerVariantNumber.getText();
    }


    public void addNewProjectToList(){
        IButton addNewProjectBtn=getElementFactory().getButton(By.xpath(addNewProjectBtnLocator),"add new project button");
        addNewProjectBtn.click();
    }

    public boolean checkNewProjectAtList(String nameProject){
        ILink newProjectLink=getElementFactory().getLink(By.xpath(newProjectLinkLocator+nameProject+"']"),"new Project Link Locator");
        return newProjectLink.state().isDisplayed();
    }










}

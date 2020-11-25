package pagesform;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProjectsPage extends Form {
    private ITextBox footerVariantNumber = getElementFactory().getTextBox(By.xpath("//p[@class='text-muted text-center footer-text']/span"),
            "footer txtBox");
    private String linkToProjectPageLocator = "//a[(text()='";
    private IButton addNewProjectBtn = getElementFactory().getButton(By.xpath("//button[@class='btn btn-xs btn-primary pull-right']"),
            "add new project button");
    public NewProjectForm newProjectForm;

    public ProjectsPage() {
        super(By.xpath("//div[contains(@class,'list-group')]"),"ProjectsPage");
    }

    public String getFooterVariantNumberLocator() {
        return footerVariantNumber.getText();
    }

    public void goToProjectPage(String nameProject, Form projectPage) {
        ILink linkToProjectPage = getElementFactory().getLink(By.xpath(linkToProjectPageLocator + nameProject + "')]"),"link to a project");
        linkToProjectPage.click();
        projectPage.state().waitForDisplayed();
    }

    public void clickAddNewProjectToListBtn(Form newForm) {
        addNewProjectBtn.click();
        AqualityServices.getBrowser().getDriver().switchTo().frame(0);
        newForm.state().waitForDisplayed();
    }
    public boolean checkNewProjectAtList(String nameProject) {
        ILink newProjectLink = getElementFactory().getLink(By.xpath(linkToProjectPageLocator + nameProject + "')]"),"new Project Link Locator");
        return newProjectLink.state().isDisplayed();
    }
}

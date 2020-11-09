package PagesForms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ProjectsPage extends Form {
    public ProjectsPage() {
        super(By.xpath("//p[@class='text-muted text-center footer-text']/span[contains(text(),'Version: 0')]"),"ProjectsPage");

    }

    public AddNewProjectForm newProjectForm;


    private String footerVariantNumberLocator="//p[@class='text-muted text-center footer-text']/span";
    private String addNewProjectBtnLocator="//button[@class='btn btn-xs btn-primary pull-right']";
    private String linkToProjectPageLocator="//a[(text()='";


    //Проверка футера вариант
    public String getFooterVariantNumberLocator(){
        ITextBox footerVariantNumber=getElementFactory().getTextBox(By.xpath(footerVariantNumberLocator),"footer txtBox");
        return footerVariantNumber.getText();
    }

    //переход на страницу проекта, аргумент имя проекта
    public void goToProjectPage(String nameProject ){
        ILink linkToProjectPage=getElementFactory().getLink(By.xpath(linkToProjectPageLocator+nameProject+"')]"),"link to a project");
        linkToProjectPage.click();
    }

    //проверка наличия проекта в спсике, аргумент имя проекта
    public boolean checkNewProjectAtList(String nameProject){
        ILink newProjectLink=getElementFactory().getLink(By.xpath(linkToProjectPageLocator+nameProject+"')]"),"new Project Link Locator");
        return newProjectLink.state().isDisplayed();
    }
    //нажатие на кнопку добавления нового проекта в список
    public void clickAddNewProjectToListBtn(){
        IButton addNewProjectBtn=getElementFactory().getButton(By.xpath(addNewProjectBtnLocator),"add new project button");
        addNewProjectBtn.click();
    }







}

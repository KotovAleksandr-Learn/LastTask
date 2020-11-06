package PagesAndForm;

import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class ListGroupForm extends Form {
    public ListGroupForm() {
        super(By.className("list-group"), "list group of projects");
    }

    public void goToProjectPage(String linkToProjectPageLocator ){
        ILink linkToProjectPage=getElementFactory().getLink(By.xpath(linkToProjectPageLocator),"link to a project");
        linkToProjectPage.click();
    }



}

package pl.uncleglass.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.uncleglass.cloudstorage.model.Credential;
import pl.uncleglass.cloudstorage.model.Note;

import java.util.List;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "add-note-button")
    private WebElement addNewNoteButton;

    @FindBy(id = "add-credential-button")
    private WebElement addNewCredentialButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialTab;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-submit-button")
    private WebElement noteSubmit;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credential-submit-button")
    private WebElement credentialSubmit;

    @FindBy(css = "#userTable tbody")
    private WebElement noteTable;

    @FindBy(css = "#credentialTable tbody")
    private WebElement credentialTable;

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        wait = new WebDriverWait(webDriver, 5000);
        this.driver = webDriver;
    }

    public void logout() {
        logoutButton.click();
    }

    public void addNote(String title, String description) {
        noteTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(title);
        noteDescription.sendKeys(description);
        noteSubmit.click();
    }

    public Note getDisplayedNote() {
        WebElement th = noteTable.findElement(By.tagName("th"));
        String title = wait.until(ExpectedConditions.elementToBeClickable(th)).getText();
        List<WebElement> elements = noteTable.findElements(By.tagName("td"));
        String description = elements.get(1).getText();
        Note note = new Note();
        note.setNoteTitle(title);
        note.setNoteDescription(description);
        return note;
    }

    public void updateNote(String newNoteTitle, String newNoteDescription) {
        noteTable.findElement(By.tagName("button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).clear();
        noteTitle.sendKeys(newNoteTitle);
        noteDescription.clear();
        noteDescription.sendKeys(newNoteDescription);
        noteSubmit.click();
    }

    public void deleteAddedNote() {
        WebElement a = noteTable.findElement(By.tagName("a"));
        wait.until(ExpectedConditions.elementToBeClickable(a)).click();
    }

    public void addCredential(String url, String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addNewCredentialButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).sendKeys(url);
        credentialUsername.sendKeys(username);
        credentialPassword.sendKeys(password);

        System.out.println(credentialUrl.getText());
        credentialSubmit.click();
    }

    public Credential getDisplayedCredential() {
        WebElement th = credentialTable.findElement(By.tagName("th"));
        String url = wait.until(ExpectedConditions.elementToBeClickable(th)).getText();
        List<WebElement> td = credentialTable.findElements(By.tagName("td"));
        String username = td.get(1).getText();
        String password = td.get(2).getText();
        Credential credential = new Credential();
        credential.setUrl(url);
        credential.setUsername(username);
        credential.setPassword(password);
        return credential;
    }

    public Credential getViewedCredential() {
        credentialTable.findElement(By.tagName("button")).click();

        String url = credentialUrl.getAttribute("value");
        String username = credentialUsername.getAttribute("value");
        String password = credentialPassword.getAttribute("value");

        Credential credential = new Credential();
        credential.setUrl(url);
        credential.setUsername(username);
        credential.setPassword(password);
        return credential;
    }

    public void updateCredential(String newUrl, String newUsername, String newPassword) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).clear();
        credentialUrl.sendKeys(newUrl);
        credentialUsername.clear();
        credentialUsername.sendKeys(newUsername);
        credentialPassword.clear();
        credentialPassword.sendKeys(newPassword);
        credentialSubmit.click();
    }

    public void deleteAddedCredential() {
        WebElement a = credentialTable.findElement(By.tagName("a"));
        wait.until(ExpectedConditions.elementToBeClickable(a)).click();
    }
}

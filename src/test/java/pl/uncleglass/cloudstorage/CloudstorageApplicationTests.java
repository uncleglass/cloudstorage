package pl.uncleglass.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import pl.uncleglass.cloudstorage.model.Credential;
import pl.uncleglass.cloudstorage.model.Note;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudstorageApplicationTests {
    private static final String LOCALHOST = "http://localhost:";
    private static final String FIRST_NAME = "Bartek";
    private static final String LAST_NAME = "Kiliszek";
    private static final String USERNAME = "uncleglass";
    private static final String NEW_USERNAME = "bart";
    private static final String PASSWORD = "badpassword";
    private static final String NEW_PASSWORD = "anotherbadpassword";
    private static final String NOTE_TITLE = "Test title";
    private static final String UPDATED_TITLE = "Updated title";
    private static final String NOTE_DESCRIPTION = "Test note description";
    private static final String UPDATED_DESCRIPTION = "Updated note description";
    private static final String URL = "https://www.udacity.com/";
    private static final String NEW_URL = "https://www.udacity.com/course/java-developer-nanodegree--nd035";
    private static final String URL_HOME = "/home";
    private static final String URL_LOGIN = "/login";
    private static final String URL_SIGNUP = "/signup";
    private static final String HOME_TITLE = "Home";
    private static final String SIGN_UP_TITLE = "Sign Up";
    private static final String LOGIN_TITLE = "Login";

    @LocalServerPort
    public int port;
    public static WebDriver driver;
    public String baseURL;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        baseURL = LOCALHOST + port;
    }

    @Test
    @Order(1)
    void unauthorizedUserCanOnlyAccessTheLoginAndSignupPages() {
        driver.get(baseURL + URL_HOME);
        assertNotEquals(HOME_TITLE, driver.getTitle());
        driver.get(baseURL + URL_LOGIN);
        assertEquals(LOGIN_TITLE, driver.getTitle());
        driver.get(baseURL + URL_SIGNUP);
        assertEquals(SIGN_UP_TITLE, driver.getTitle());
    }

    @Test
    @Order(2)
    void authorizedUserCanLogInAndAccessTheHomePage() {
        driver.get(baseURL + URL_SIGNUP);
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);

        driver.get(baseURL + URL_LOGIN);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get(baseURL + URL_HOME);
        assertEquals(HOME_TITLE, driver.getTitle());

        HomePage homePage = new HomePage(driver);
        homePage.logout();

        driver.get(baseURL + URL_HOME);
        assertNotEquals(HOME_TITLE, driver.getTitle());
    }

    @Test
    @Order(3)
    void loggedInUserCanCreateANoteAndItIsDisplayed() {
        String noteTitle = NOTE_TITLE;
        String noteDescription = NOTE_DESCRIPTION;

        driver.get(baseURL + URL_LOGIN);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(USERNAME, PASSWORD);

        driver.get(baseURL + URL_HOME);
        assertEquals(HOME_TITLE, driver.getTitle());

        HomePage homePage = new HomePage(driver);
        homePage.addNote(noteTitle, noteDescription);

        Note addedNote = homePage.getDisplayedNote();
        assertEquals(noteTitle, addedNote.getNoteTitle());
        assertEquals(noteDescription, addedNote.getNoteDescription());
    }

    @Test
    @Order(4)
    void addedNoteCanBeUpdatedAndChangesAreDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.updateNote(UPDATED_TITLE, UPDATED_DESCRIPTION);

        Note updatedNote = homePage.getDisplayedNote();
        assertEquals(UPDATED_TITLE, updatedNote.getNoteTitle());
        assertEquals(UPDATED_DESCRIPTION, updatedNote.getNoteDescription());
    }


    @Test
    @Order(5)
    void addedNoteCanBeDeletedAndItIsNoLongerDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.deleteAddedNote();

        assertThrows(NoSuchElementException.class, () -> homePage.getDisplayedNote());
    }

    @Test
    @Order(6)
    void loggedInUserCanCreateACredentialAndItIsDisplayedAndDisplayedPasswordIsEncrypted() {
        driver.get(baseURL + URL_HOME);
        HomePage homePage = new HomePage(driver);
        homePage.addCredential(URL, USERNAME, PASSWORD);

        Credential credential = homePage.getDisplayedCredential();

        assertEquals(URL, credential.getUrl());
        assertEquals(USERNAME, credential.getUsername());
        assertNotEquals(PASSWORD, credential.getPassword());
    }

    @Test
    @Order(7)
    void addedCredentialCanBeViewedAndPasswordIsDecryptedAndCanBeUpdatedAndChangesAreDisplayed() {
        HomePage homePage = new HomePage(driver);
        Credential credential = homePage.getViewedCredential();

        assertEquals(URL, credential.getUrl());
        assertEquals(USERNAME, credential.getUsername());
        assertEquals(PASSWORD, credential.getPassword());

        homePage.updateCredential(NEW_URL, NEW_USERNAME, NEW_PASSWORD);

        credential = homePage.getDisplayedCredential();

        assertEquals(NEW_URL, credential.getUrl());
        assertEquals(NEW_USERNAME, credential.getUsername());
        assertNotEquals(NEW_PASSWORD, credential.getPassword());
    }

    @Test
    @Order(8)
    void addedCredentialCanBeDeletedAndItIsNoLongerDisplayed() {
        HomePage homePage = new HomePage(driver);
        homePage.deleteAddedCredential();

        assertThrows(NoSuchElementException.class, () -> homePage.getDisplayedCredential());
    }
}

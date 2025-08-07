package io.qameta.allure;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@Layer("rest")
@Owner("baev")
@Feature("Issues")
public class IssuesRestTest {

    private static final String OWNER = "allure-framework";
    private static final String REPO = "allure2";

    private final RestSteps steps = new RestSteps();

    @TM4J("AE-T1")
    @Story("Create new issue")
    @Microservice("Billing")
    @Tags({@Tag("api"), @Tag("smoke")})
    @ParameterizedTest(name = "Create issue via api")
    @ValueSource(strings = {"First Note", "Second Note"})
    public void shouldCreateUserNote(@Param(value = "Title") String title) {
        steps.createIssueWithTitle(OWNER, REPO, title);
        steps.shouldSeeIssueWithTitle(OWNER, REPO, title);
    }

    @TM4J("AE-T2")
    @Story("Close existing issue")
    @Microservice("Repository")
    @Tags({@Tag("web"), @Tag("regress")})
    @JiraIssues({@JiraIssue("P1-1")})
    @ParameterizedTest(name = "Close issue via api")
    @ValueSource(strings = {"First Note", "Second Note"})
    public void shouldDeleteUserNote(@Param(value = "Title") String title) {
        steps.createIssueWithTitle(OWNER, REPO, title);
        steps.closeIssueWithTitle(OWNER, REPO, title);
    }

    // --- New Feature: Authentication ---
    @TM4J("AE-T3")
    @Feature("Authentication")
    @Story("User login with valid credentials")
    @Microservice("Auth")
    @Tags({@Tag("api"), @Tag("auth"), @Tag("smoke")})
    @ParameterizedTest(name = "Login with username: {0}")
    @ValueSource(strings = {"user1", "user2"})
    public void shouldAuthenticateUser(@Param("Username") String username) {
        String password = "securePassword"; // placeholder, replace with secure logic
        steps.authenticateUser(username, password);
        steps.shouldSeeUserLoggedIn(username);
    }
}

import org.junit.Assert;
import org.junit.Test;

import mysql.UserDao;

public class UserTest {

    @Test
    public void createUserShouldRemoveTheDotsInUsername() {

        UserDao user = new UserDao("an","bb","an.bb");
        Assert.assertEquals(user.getUsername(),"anbb");
    }
}


// Piramida nivelelor de testare - Test Pyramid

//- unit testing - easy to write/scoped/mocking (izolare cod)/fast
//- integration testing - fairly easy/focus on integration, focus on communication with other
//comp, systems/ API Testing
//- acceptance testing / e2e end-to-end testing - as less as possible:
// Google Search: input Samsung S20, click button, return results
// FrontEnd - unit teste (merge butonul? e activ search-ul? se afiseaza logoul?)
// BackEnd - unit teste (validare searchInput), integrationtesting-> callSpreDB, checkREsponse
// DB - can I duplicate data? (No)

// Homework: integration tests using DB interaction
// integration tests H2 in-memoryDB
// mysql/mariadb/postgress/oracle - integration testing (integrare intre 2 sist 'fizice'))


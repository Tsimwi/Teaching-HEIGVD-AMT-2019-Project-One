# Teaching-HEIGVD-AMT-2019-Project-One

### Part 3. Testing strategy

We had to set up several ways to test our different components, such as DAO, models and servlets. To do this we used severals tool as Mockito, Arquillian and JUnit.

#### Mockito tests

We used Mockito to test servlets. The main part of servlet testing to to be sure that we reach the point that we want with behaviors that we defined. For this, we need to mock all objects that we will need, as `request` or `RequestDispatcher` for example.

![mockito_variables](./img/mockito_variables.png)

In this project we tested almost all servlet with all case. For example for login page we tested that if username is missing we have the corresponding message in the request. 

![mockito_test](./img/mockito_test.png)

We can see that we define the return values of each object, when `request.getParameter("username")` is called we want that the return value is empty and then we verify that `request.setAttribut("error", errors)` has been called at least on time.

So for summary Mockito tests is a part where we defined behaviors and then test that we reach the right destination.

#### Arquillian tests

Arquillian is used to test object that is normally managed by the application server. Arquillian will package our tests, send them on the application server and execute them. To do thet we will need that the application server and the database containers are up and ready. We need to import the ssl certificate of the application server in our JDK keystore so that Arquillian is able to upload tests.

![arquillian_test](./img/arquillian_test.png)

We can see that with  Arquillian we use the EJB as usually. We can set the transactionMode to `ROLLBACK` or`COMMIT`.

#### JUnit tests

We used JUnit to make tests on the models tier. 

![junit_test](./img/junit_test.png)

The tests are creating object and test that values match with assertion test. 






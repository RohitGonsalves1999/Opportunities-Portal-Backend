Feature: The User can Verify his/her session
   
Scenario: the user attempts to verify session 

When client calls /verifySession

Then the client gets a session object and receives a response status code 200
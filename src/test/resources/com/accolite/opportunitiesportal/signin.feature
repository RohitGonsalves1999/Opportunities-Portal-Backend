Feature: The User can sign in
   
Scenario: the user attempts to sign in

When client calls /signin/google

Then the client gets a SessionUser object and receives a response status code 200
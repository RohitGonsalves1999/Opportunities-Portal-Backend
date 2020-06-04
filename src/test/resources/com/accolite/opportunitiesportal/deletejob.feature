Feature: A JobDescription Can Be deleted
   
Scenario: Delete JobDescription

When client calls /delete/9

Then the job is deleted and client receives a response status code 200
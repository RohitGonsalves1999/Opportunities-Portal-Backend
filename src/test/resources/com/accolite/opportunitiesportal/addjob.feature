Feature: A JobDescription Can Be Added
   
Scenario: update JobDescription

When client calls Post /JobDescription 

Then the job is added and client receives a response status code 200
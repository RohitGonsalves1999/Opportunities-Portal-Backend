Feature: A JobDescription Can Be updated
   
Scenario: update JobDescription

When client calls UPDATE /JobDescription

Then the job is updated and client receives a response status code 200
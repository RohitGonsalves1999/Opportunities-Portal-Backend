Feature: A JobDescription Can Be resolved
   
Scenario: resolve JobDescription

When client calls /resolve/9

Then the job is resolved and client receives a response status code 200
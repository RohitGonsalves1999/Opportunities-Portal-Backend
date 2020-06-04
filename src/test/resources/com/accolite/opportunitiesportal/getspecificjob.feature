Feature: specific JobDescriptionWithSkills can be retrieved
   
Scenario: get specific JobDescriptionWithSkills

When client calls /JobDescription/31

Then the client receives JobDescription with a response status code 200
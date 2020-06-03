
Feature: the  JobDescriptionWithSkills version list can be retrieved
Scenario:  client makes call to GET /JobVersions/10
When client calls /JobVersions/10
Then the client receives JobDescriptionWithSkills with response status code 200
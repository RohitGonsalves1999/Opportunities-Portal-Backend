Feature: the specific JobDescriptionWithSkills version object can be retrieved
Scenario:  client makes call to GET /JobVersions/version/10
When client calls /JobVersions/version/10
Then the client receives JobDescriptionWithSkills object with response status code 200
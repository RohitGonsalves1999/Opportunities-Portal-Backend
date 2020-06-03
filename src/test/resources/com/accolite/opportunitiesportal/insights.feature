Feature: The insight Map can be retrieved
   
Scenario: client makes GET call to /jobInsights

When client calls /jobInsights

Then the InsightMap is received and client receives a response status code 200
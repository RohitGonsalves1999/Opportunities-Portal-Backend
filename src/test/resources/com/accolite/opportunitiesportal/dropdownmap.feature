Feature: The Dropdown MapMap Can Be resolved
   
Scenario: receive Dropdown MapMap

When client calls /DropDownMap

Then the MapMap is received and client receives a response status code 200
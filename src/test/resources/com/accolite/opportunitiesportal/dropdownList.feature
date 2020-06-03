Feature: The Dropdown ListMap Can Be resolved
   
Scenario: receive Dropdown ListMap

When client calls /DropDownItems

Then the ListMap is received and client receives a response status code 200
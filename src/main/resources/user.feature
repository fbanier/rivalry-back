
Feature: User management

  as a user i want to create and manage an account

  Scenario: Creation of an account
    Given Player "Tom" who wants to login to his account
    When Player register email : "tom@gmail.com", username : "tom", age : "12-04-1990", and password : "tom123"
    Then Check if email "tom@gmail.com" already exist and raise an error if it does
    And Player receive an confirmation email

  Scenario: Login to account
    Given Player "Tom" who wants to login to his account
    When Player input email : "tom@gmail.com" and password : "tom123"
    Then search the email in database and raise an error if not found
    Then compare the password with the one stored and raise an error if it doesn't match
    Then the player is logged

  Scenario: Account Management
    Given Player "Tom" who want to edit his username
    When Player got to profile form and edit his username to "tomcat"
    And Check if player "tomcat" already exist and raise an error if it does
    Then Player receive an validation message

  Scenario: Register to tournament when logged
    Given Player "Jerry" who want compete in tournament
    When player go to the tournament's page and is logged
    And player validate his registration
    Then player receive an validation message

  Scenario: Register to tournament when not logged
    Given Player "Jerry" who want compete in tournament
    When player go to the tournament's page and he is not logged
    Then player is redirect to the sign in page
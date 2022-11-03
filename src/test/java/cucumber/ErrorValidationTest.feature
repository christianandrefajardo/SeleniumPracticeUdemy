
@tag
Feature: Error Validation
  I want to use this template for my feature file

  @ErrorValidation
  Scenario Outline: Title of your scenario outline
    Given I landed on Login Page
   	When Logged in with username <username> and password <password>
    Then "Incorrect email or password." is displayed

    Examples: 
      | username  														| password 		  |
      | christianandre@rahulshettyacademy.com | Password1234! | 
      | shetty@gmail.com											| Iamking@0000  |

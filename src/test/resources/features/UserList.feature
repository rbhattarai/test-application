@UITest
Feature: Manage Users

  @HealthCheck
  Scenario: Check Status
    Given I am in Landing Page
    Then I verify Landing Page opens successfully

  @SmokeTest @AddUser
  Scenario Outline: Add User and Verify User List
    Given I am in Landing Page
    And I click Add New User
    And I enter Username as "<username>" and Email as "<email>"
    When I click Add User button
    Then I verify Landing Page opens successfully
    And I verify Username as "<username>" and Email as "<email>" are added successfully

    Examples:
      | username | email           |
      | test1    | test1@gmail.com |
      | test2    | test2@gmail.com |


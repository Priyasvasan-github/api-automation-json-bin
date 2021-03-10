Feature: Verify Delete Bin is working as expected


  Scenario Outline: Verify a user with invalid token can't delete a bin
    Given A inValid user is accessing the Json portal
    When he delete a Bin for data of file <fileName>
    Then Bin is not deleted successfully
    Examples:
      |   fileName               |
      | HelloWorldData |

  @smoke
  Scenario Outline: Verify a user with valid token can delete a bin
    Given A valid user is accessing the Json portal
    When he delete a Bin for data of file <fileName>
    Then Bin is deleted successfully
    And Bin is not read successfully
    Examples:
      |   fileName               |
      | HelloWorldData |
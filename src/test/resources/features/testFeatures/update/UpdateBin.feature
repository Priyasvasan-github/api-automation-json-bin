Feature: Verify Update Bin is working as expected

  @smoke
  Scenario Outline: Verify a user with valid token is able to update a bin successfully
    Given A valid user is accessing the Json portal
    When he update a Bin from its previous value <fileName> to new value <newFileName>
    Then Bin is updated successfully as expected for the <newFileName>
    And Retrieved record is matching the data from <newFileName>
    Examples:
      |   fileName               | newFileName|
      | ValidUserDataCreate |    ValidUserDataCreate |
      | EmptyStringData |        ValidUserDataCreate |


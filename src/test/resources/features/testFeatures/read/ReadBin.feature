Feature:  Verify Read end point is working as expected


  Scenario Outline: Verify a user with invalid token can't  read a bin
    Given A inValid user is accessing the Json portal
    When he read a Bin for data of file <fileName>
    Then Bin is not read successfully
    Examples:
      |   fileName               |
      | ValidUserDataCreate |


  Scenario Outline: Verify a user with valid token can  read a bin
    Given A valid user is accessing the Json portal
    When he read a Bin for data of file <fileName>
    Then Bin is read successfully
    And Retrieved record is matching the data from <fileName>
    Examples:
      |   fileName               |
      | ValidUserDataCreate |
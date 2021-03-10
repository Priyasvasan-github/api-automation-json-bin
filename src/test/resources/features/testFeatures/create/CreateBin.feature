Feature: Verify creation of BIN is working as expected

  @smoke
  Scenario Outline: Verify a user with valid token is able to create A Bin
    Given A valid user is accessing the Json portal
    When he create a Bin for data of file <fileName>
    Then Bin is crated successfully as expected for the <fileName>
    And Retrieved record is matching the data from <fileName>
    Examples:
      |   fileName               |
      | ValidUserDataCreate |
      | HelloWorldData |
      | EmptyStringData |

  Scenario Outline: Verify a user with valid token is able to create A Bin
    Given A valid user is accessing the Json portal
    When he create a Bin for data of file <fileName>
    Then Bin is not crated successfully as expected for the <fileName>
    And <ErrorMessage> is displayed as expected
    Examples:
      |   fileName | ErrorMessage|
      | EmptyData   |  Bin cannot be blank           |

  Scenario Outline: Verify a user with invalid token cant  create A Bin
    Given A inValid user is accessing the Json portal
    When he create a Bin for data of file <fileName>
    Then Bin is not crated successfully as expected for the <fileName>
    Examples:
      |   fileName               |
      | ValidUserDataCreate |

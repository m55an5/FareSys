Feature: Transit Management System 

Scenario Outline: A Incomplete Trip - max cost to customer
Given There are three bus stop Stop1, Stop2 and Stop3
And Trip Cost from Stop1 to Stop2 is $3.25
And Trip Cost from Stop2 to Stop3 is $5.50
And Trip Cost from Stop1 to Stop3 is $7.30
And I perform a tap "<taptype>" at "<stopID>" with "<pan>"
And I create the csv file with above record 
When I run the transit management app
Then The trips csv is created 
And I read the trips csv file
And The trip status is "INCOMPLETE"
And The trip charge is "<cost>"

Examples:
    | taptype | stopID | pan              | cost   |
    | ON      | Stop1  | 5500005555550000 | $7.3   |
    | ON      | Stop2  | 5500005555550000 | $5.5   |
    | ON      | Stop3  | 5500005555550000 | $7.3   |
    
Scenario: A Completed Trip - Stop1 to Stop2 
Given There are three bus stop Stop1, Stop2 and Stop3
And Trip Cost from Stop1 to Stop2 is $3.25
And Trip Cost from Stop2 to Stop3 is $5.50
And Trip Cost from Stop1 to Stop3 is $7.30
And I perform a tap "ON" at "Stop1" with "5500005555550005"
And I again perform a tap "OFF" at "Stop2" with "5500005555550005"
And I create the csv file with above record 
When I run the transit management app
Then The trips csv is created 
And I read the trips csv file
And The trip status is "COMPLETED"
And The trip charge is "$3.25"

Scenario: A Completed Trip - Stop3 to Stop2 
Given There are three bus stop Stop1, Stop2 and Stop3
And Trip Cost from Stop1 to Stop2 is $3.25
And Trip Cost from Stop2 to Stop3 is $5.50
And Trip Cost from Stop1 to Stop3 is $7.30
And I perform a tap "ON" at "Stop3" with "5500005555550005"
And I again perform a tap "OFF" at "Stop2" with "5500005555550005"
And I create the csv file with above record 
When I run the transit management app
Then The trips csv is created 
And I read the trips csv file
And The trip status is "COMPLETED"
And The trip charge is "$5.5"

Scenario: A Completed Trip - Stop3 to Stop1
Given There are three bus stop Stop1, Stop2 and Stop3
And Trip Cost from Stop1 to Stop2 is $3.25
And Trip Cost from Stop2 to Stop3 is $5.50
And Trip Cost from Stop1 to Stop3 is $7.30
And I perform a tap "ON" at "Stop3" with "5500005555550005"
And I again perform a tap "OFF" at "Stop1" with "5500005555550005"
And I create the csv file with above record 
When I run the transit management app
Then The trips csv is created 
And I read the trips csv file
And The trip status is "COMPLETED"
And The trip charge is "$7.3"

Scenario: A Cancelled Trip - no cost to customer
Given There are three bus stop Stop1, Stop2 and Stop3
And Trip Cost from Stop1 to Stop2 is $3.25
And Trip Cost from Stop2 to Stop3 is $5.50
And Trip Cost from Stop1 to Stop3 is $7.30
And I perform a tap "ON" at "Stop1" with "5500005555550005"
And I again perform a tap "OFF" at "Stop1" with "5500005555550005"
And I create the csv file with above record 
When I run the transit management app
Then The trips csv is created 
And I read the trips csv file
And The trip status is "CANCELLED"
And The trip charge is "$0.0"
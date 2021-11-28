# Zendesk Coding Challenge
This Command Line Interface (CLI) operated project connects to the ZenDesk ticketing API,
fetches batch open tickets, and displays them in groups of 25.

## Technologies Used
- Main Language: Java
- Testing: JUnit4
- Other: [JSON-Simple.jar](https://jar-download.com/artifacts/com.github.cliftonlabs/json-simple/2.3.0/source-code)

## Requirements
- Have a [JDK installed](https://www.oracle.com/java/technologies/downloads/)
- Have JSON-Simple.jar installed
- Have ZenDesk API credentials available

## How To Run
1. Download the ZendeskCodingChallenge.jar file located in /out/artifacts/ZendeskCodingChallenge_jar/ directory
2. Run the jar file with the following command in CLI: 
```bash
java -jar ZendeskCodingChallenge.jar
```
3. You will then be prompted to enter your username, this will most likely be your email used to log onto ZenDesk.
4. You will then be prompted to enter your token. Instructions to make your token can be found [here](https://developer.zendesk.com/api-reference/ticketing/tickets/ticket-requests/)
5. You will then be prompted to enter your subdomain. This can be found here `https://<subdomain>.zendesk.com`
6. Once you enter all of those, you will be able to access the Zendesk Ticket Viewer and see all your open tickets.

## How It Works
1. The program will connect to the ZenDesk API and fetch all open tickets.
2. If option 1 is selected, the program will then group the tickets into groups of 25.
3. If option 2 is selected, the program will then prompt you to choose a specific ticket number to look at. The program will then fetch that ticket and display its content to you
4. If option 3 is selected, the program will terminate
5. When option 1 is selected and there is more than 25 tickets, the program will ask you to pick option 5, which will take you to the next page of tickets.
6. While on the next page of tickets, there will be a prompt to choose option 4 or 5, 4 taking you to the previous page of tickets and 5 taking you to the next page.

## How to Test
1. To run the test cases, have Junit4, Hamcrest, and JSON-Simple.jar installed. 
2. Copy the src directory to your local IDE.
3. Once copied, run the test cases in your IDE.

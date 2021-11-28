import java.util.*;
import java.io.*;
import org.json.simple.parser.*;

public class Main {
    TicketReader ticketReader;
    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter Zendesk Username");
        String username = scanner.nextLine();

        System.out.println("Please enter Zendesk Token");
        String token = scanner.nextLine();

        System.out.println("Please enter Zendesk Subdomain");
        String subdomain = scanner.nextLine();

        APICaller caller = new APICaller(username, token, subdomain);
        TicketReader ticketReader = new TicketReader(caller);

        int choice;
        HashMap<String, Boolean> direction = new HashMap<String, Boolean>();
        direction.put("previous", false);
        direction.put("next", false);

        do {
            System.out.println("Welcome to the Zendesk Ticket Reader");
            System.out.println("Please select an option");

            String choice1 = "1. Get All Tickets \n";
            if (direction.get("previous") != null && direction.get("next") != null) {
                if (direction.get("previous") && direction.get("next")) {
                    choice1 = "4) Get Previous Page Of Tickets \n5) Get Next Page Of Tickets \n";
                } else if (direction.get("previous")) {
                    choice1 = "4) Get Previous Page Of Tickets \n";
                } else if (direction.get("next")) {
                    choice1 = "5) Get Next Page Of Tickets \n";
                }
            }

            String options = choice1 + "2. Get Ticket By ID \n3. Exit";
            System.out.println(options);
            choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1, 5, 4 -> direction = ticketReader.getTickets(choice);
                    case 2 -> {
                        System.out.println("Please enter the ticket ID");
                        int id = scanner.nextInt();
                        ticketReader.getTicketById(id);
                    }
                    case 3 -> {
                        System.out.println("Thanks For Using The Zendesk Ticket Reader");
                        System.out.println("Exiting...");
                    }
                }
            } catch (IOException | ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (choice != 3);
    }
}
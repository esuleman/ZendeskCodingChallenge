import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;

public class TicketReader {
    APICaller caller;
    public TicketReader(APICaller caller) {
        this.caller = caller;
    }

    public HashMap<String, Boolean> getTickets(int pageDir) throws IOException, ParseException {
        HashMap<String, Boolean> direction = new HashMap<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(caller.getBuffer(pageDir, 0).toString());

            JSONArray tickets = (JSONArray) json.get("tickets");
            JSONObject meta = (JSONObject) json.get("meta");
            JSONObject links = (JSONObject) json.get("links");

            Boolean hasNext = (boolean) meta.get("has_more");

            caller.setPaginatedZendeskURL((String) links.get("next"));
            caller.setPaginatedZendeskURL((String) links.get("previous"));

            if (caller.getPageCounter() > 1) {
                direction.put("prev", true);
            } else {
                direction.put("prev", false);
            }

            direction.put("next", hasNext);

            displayTickets(tickets);
        } catch (Exception e) {
            System.out.println("\nNo tickets found.");
        }

        return direction;
    }

    public static void displayTickets(JSONArray tickets) {
        System.out.println("\nTickets:");
        System.out.println("ID\tStatus\tDate Created\t\tDate Updated\t\tSubject");
        for (Object ticket : tickets) {
            JSONObject ticketObj = (JSONObject) ticket;
            System.out.println(ticketObj.get("id") +
                    "\t" + ticketObj.get("status") +
                    "\t" + ticketObj.get("created_at") +
                    "\t" + ticketObj.get("updated_at") +
                    "\t" + ticketObj.get("subject"));
        }
    }

    public void getTicketById(int id) throws IOException, ParseException {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(caller.getBuffer(0, id).toString());
            JSONObject ticket = (JSONObject) json.get("ticket");

            System.out.println("\nTicket Details Below:");
            System.out.println(" ID: " + ticket.get("id") +
                    "\n Status: " + ticket.get("status") +
                    "\n Subject: " + ticket.get("subject") +
                    "\n Date Created: " + ticket.get("created_at") +
                    "\n Updated: " + ticket.get("updated_at") +
                    "\n Description: " + ticket.get("description"));
        } catch (Exception e) {
            System.out.println("\nTicket not found.");
        }
    }
}

import java.io.*;
import java.net.*;
import java.util.*;

public class APICaller {
    String username;
    String token;
    String subdomain;
    String zendeskURL;
    String paginatedZendeskURL;
    int pageCounter = 1;

    public APICaller(String username, String token, String subdomain) {
        this.username = username;
        this.token = token;
        this.subdomain = subdomain;
        zendeskURL = "https://" + subdomain + ".zendesk.com";
        paginatedZendeskURL = null;
    }

    public void setPaginatedZendeskURL(String paginatedZendeskURL) {
        this.paginatedZendeskURL = paginatedZendeskURL;
    }

    public void setPageCounter(int pageCounter) {
        this.pageCounter = pageCounter;
    }

    public String getPaginatedZendeskURL() {
        return paginatedZendeskURL;
    }

    public String getZendeskURL() {
        return zendeskURL;
    }

    public int getPageCounter() {
        return pageCounter;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public StringBuffer getBuffer(int direction, int id) throws IOException, MalformedURLException{
        if (id == 0) {
            if (paginatedZendeskURL == null) {
                paginatedZendeskURL = zendeskURL + "/api/v2/tickets.json?page[size]=25";
            } else {
                if (direction == 4) {
                    paginatedZendeskURL = zendeskURL + "/api/v2/tickets.json?page[size]=25&page[number]=" + pageCounter;
                } else if (direction == 5) {
                    pageCounter++;
                    paginatedZendeskURL = zendeskURL + "/api/v2/tickets.json?page[size]=25&page[number]=" + pageCounter;
                }
            }
        } else {
            paginatedZendeskURL = zendeskURL + "/api/v2/tickets/" + id + ".json";
        }

        StringBuffer response = new StringBuffer();

        if (!token.isEmpty() && !username.isEmpty()) {
            try {
                String auth = username + "/token:" + token;
                URL url = new URL(paginatedZendeskURL);
                String base64EncodedCredentials = Base64.getEncoder().encodeToString(auth.getBytes());

                HttpURLConnection zendeskConnect = (HttpURLConnection) url.openConnection();
                zendeskConnect.setRequestMethod("GET");

                zendeskConnect.setRequestProperty("Authorization", "Basic " + base64EncodedCredentials);
                zendeskConnect.setRequestProperty("Accept", "application/json");
                zendeskConnect.connect();

                int responseCode = zendeskConnect.getResponseCode();

                if (responseCode >= 500 && responseCode < 600) {
                    System.out.println("Server Unavailable");
                } else if (responseCode == 404) {
                    throw new IOException("Ticket Does Not Exist");
                } else if (responseCode == 401) {
                    throw new IOException("Username or Password is incorrect");
                } else if (responseCode != 200) {
                    throw new IOException("Unexpected Error From Zendesk API");
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(zendeskConnect.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return response;
    }
}
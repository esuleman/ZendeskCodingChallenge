import java.io.*;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class TicketReaderTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    String test = "test";
    APICaller apiCaller = new APICaller(test, test, test);
    TicketReader ticketReader = new TicketReader(apiCaller);

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
        System.out.flush();
    }

    @Test
    public void testGetTickets() throws Exception {
        HashMap<String, Boolean> tickets = ticketReader.getTickets(0);
        assertEquals(0, tickets.size());
        assertEquals(true, tickets instanceof HashMap);
    }
}

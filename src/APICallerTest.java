import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;

public class APICallerTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

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
    public void testGetBuffer() throws Exception {
        String test = "test";
        APICaller apiCaller = new APICaller(test, test, test);
        StringBuffer res = apiCaller.getBuffer(0, 1);
        assertEquals(true, res instanceof StringBuffer);
    }


}

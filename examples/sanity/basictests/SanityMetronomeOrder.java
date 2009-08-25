package sanity.basictests;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class SanityMetronomeOrder extends MultithreadedTestCase
{
    String s;

    @Before
    public void initialize()
    {
        s = "";
    }

    @Threaded
    public void thread1()
    {
        waitForTick(1);
        s += "A";

        waitForTick(3);
        s += "C";

        waitForTick(6);
        s += "F";
    }

    @Threaded
    public void thread2()
    {
        waitForTick(2);
        s += "B";

        waitForTick(5);
        s += "E";

        waitForTick(8);
        s += "H";
    }

    @Threaded
    public void thread3()
    {
        waitForTick(4);
        s += "D";

        waitForTick(7);
        s += "G";

        waitForTick(9);
        s += "I";
    }

    @Test
    public void finish()
    {
        assertEquals("Threads were not called in correct order",
                s, "ABCDEFGHI");
    }
}

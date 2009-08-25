package sanity.timing;

import edu.umd.cs.mtc.Threaded;
import edu.umd.cs.mtc.*;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class TUnitTestClockDoesNotAdvanceWhenFrozen extends MultithreadedTestCase
{
    String s;

    @Before
    public void initialize()
    {
        s = "A";
    }

    @Threaded("first")
    public void thread1() throws InterruptedException
    {
        freezeClock();
        Thread.sleep(200);
        assertEquals("Clock advanced while thread was sleeping", s, "A");
        unfreezeClock();
    }

    @Threaded("second")
    public void thread2()
    {
        waitForTick(1);
        s = "B";
    }

    @Test
    public void finish()
    {
        assertEquals(s, "B");
    }
}

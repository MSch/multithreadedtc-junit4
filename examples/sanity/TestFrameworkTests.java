package sanity;

import edu.umd.cs.mtc.Threaded;
import edu.umd.cs.mtc.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestFrameworkTests extends MultithreadedTestCase
{

    int i = 0;

    @Threaded("1")
    public void thisWillRunInThread1()
    {
        i++;
    }

    @Threaded("2")
    public void thisWillRunInThread2()
    {
        waitForTick(1);
        i++;
    }

    @Test
    @MultithreadedTest(times = 3)
    public void testRunThreeTimes() throws Throwable
    {
        assertEquals(i, 6);
    }

}

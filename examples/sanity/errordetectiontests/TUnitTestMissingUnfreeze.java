package sanity.errordetectiontests;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;
import org.junit.Test;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class TUnitTestMissingUnfreeze extends MultithreadedTestCase
{
    @Threaded
    public void thread1() throws InterruptedException
    {
        freezeClock();
        Thread.sleep(200);
    }

    @Threaded
    public void thread2()
    {
        waitForTick(1);
    }

    @Test(expected = IllegalStateException.class)
    public void test()
    {
    }
}

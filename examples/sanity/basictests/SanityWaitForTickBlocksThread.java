package sanity.basictests;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class SanityWaitForTickBlocksThread extends MultithreadedTestCase
{
    Thread t;

    @Threaded
    public void thread1()
    {
        t = Thread.currentThread();
        waitForTick(2);
    }

    @Threaded
    public void thread2()
    {
        waitForTick(1);
        assertEquals(Thread.State.WAITING, t.getState());
    }

    @Test
    public void test()
    {
    }
}

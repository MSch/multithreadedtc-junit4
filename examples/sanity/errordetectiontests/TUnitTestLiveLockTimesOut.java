package sanity.errordetectiontests;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;

import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class TUnitTestLiveLockTimesOut extends MultithreadedTestCase
{
    AtomicInteger ai;

    @Before
    public void initialize()
    {
        ai = new AtomicInteger(1);
    }

    @Threaded
    public void thread1()
    {
        while (!ai.compareAndSet(2, 3)) Thread.yield();
    }

    @Threaded
    public void thread2()
    {
        while (!ai.compareAndSet(3, 2)) Thread.yield();
    }

    @Test(expected = IllegalStateException.class)
    public void finish()
    {
        assertTrue(ai.get() == 2 || ai.get() == 3);
    }
}

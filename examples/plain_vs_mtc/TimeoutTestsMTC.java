package plain_vs_mtc;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * In this test, the first offer is allowed to timeout, the second offer is interrupted. Use `freezeClock` to prevent the clock from
 * advancing during the first offer.
 */
public class TimeoutTestsMTC extends MultithreadedTestCase
{
    ArrayBlockingQueue<Object> q;

    @Before
    public void initialize()
    {
        q = new ArrayBlockingQueue<Object>(2);
    }

    @Threaded("1")
    public void thread1()
    {
        try
        {
            q.put(new Object());
            q.put(new Object());

            freezeClock();
            assertFalse(q.offer(new Object(), 25, TimeUnit.MILLISECONDS));
            unfreezeClock();

            q.offer(new Object(), 2500, TimeUnit.MILLISECONDS);
            fail("should throw exception");
        } catch (InterruptedException success)
        {
            assertTick(1);
        }
    }

    @Threaded
    public void thread2()
    {
        waitForTick(1);
        getThread(1).interrupt();
    }

    @Test
    public void test()
    {
    }
}

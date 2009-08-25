package plain_vs_mtc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

/**
 * Timed offer times out if ArrayBlockingQueue is full and elements are not taken
 */
public class TimeoutTestsPlain
{

    /* NOTES
      * - Uses freezeClock to prevent clock from advancing
      * - This also guarantees that interrupt is on second offer
      */

    // Plain Version

    volatile boolean threadFailed;

    public void threadShouldThrow()
    {
        threadFailed = true;
        fail("should throw exception");
    }

    public void threadAssertFalse(boolean b)
    {
        if (b)
        {
            threadFailed = true;
            assertFalse(b);
        }
    }

    @Before
    public void setUp() throws Exception
    {
        threadFailed = false;
    }

    @Test
    public void testTimedOffer()
    {
        final ArrayBlockingQueue<Object> q = new ArrayBlockingQueue<Object>(2);
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    q.put(new Object());
                    q.put(new Object());
                    threadAssertFalse(q.offer(new Object(), 25, TimeUnit.MILLISECONDS));
                    q.offer(new Object(), 2500, TimeUnit.MILLISECONDS);
                    threadShouldThrow();
                } catch (InterruptedException success)
                {
                }
            }
        });

        try
        {
            t.start();
            Thread.sleep(50);
            t.interrupt();
            t.join();
        } catch (Exception e)
        {
            fail("Unexpected exception");
        }
    }

    @After
    public void tearDown() throws Exception
    {
        assertFalse(threadFailed);
    }

}

package plain_vs_mtc;

import java.util.concurrent.Semaphore;

import static junit.framework.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.fail;

/**
 * Test that a waiting acquire blocks interruptibly
 */
public class InterruptBlockedTestsPlain
{

    /* NOTES
      * - Failures in threads require additional work in setup and teardown
      * - Relies on Threaded.sleep to ensure acquire has blocked
      * - Does not ensure that exceptions are definitely caused by interrupt
      * - More verbose
      * - Requires a join at the end
      * - In MTC version, get reference to a thread using getThread(1)
      */

    volatile boolean threadFailed;

    public void threadShouldThrow()
    {
        threadFailed = true;
        fail("should throw exception");
    }

    @Before
    public void setUp() throws Exception
    {
        threadFailed = false;
    }

    @Test
    public void testInterruptedAcquire()
    {
        final Semaphore s = new Semaphore(0);
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    s.acquire();
                    threadShouldThrow();
                } catch (InterruptedException success)
                {
                }
            }
        });
        t.start();
        try
        {
            Thread.sleep(50);
            t.interrupt();
            t.join();
        } catch (InterruptedException e)
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

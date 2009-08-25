package plain_vs_mtc;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * compareAndSet in one thread enables another waiting for value to succeed
 */
public class CompareAndSetTestsPlain
{

    /* NOTES
      * - Plain version requires a join before final asserts
      * - MTC version does not need to check if thread is alive
      */

    @Test
    public void testCompareAndSet() throws InterruptedException
    {
        final AtomicInteger ai = new AtomicInteger(1);
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                while (!ai.compareAndSet(2, 3)) Thread.yield();
            }
        });

        t.start();
        assertTrue(ai.compareAndSet(1, 2));
        t.join(2500);
        assertFalse(t.isAlive());
        assertEquals(ai.get(), 3);
    }


}

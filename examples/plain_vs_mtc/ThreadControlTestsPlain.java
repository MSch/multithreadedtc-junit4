package plain_vs_mtc;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controlling the order in which threads are called
 */
public class ThreadControlTestsPlain
{

    volatile boolean threadFailed;

    @Before
    public void setUp() throws Exception
    {
        threadFailed = false;
    }

    @After
    public void tearDown() throws Exception
    {
        assertFalse(threadFailed);
    }

    public void unexpectedException()
    {
        threadFailed = true;
        fail("Unexpected exception");
    }

    @Test
    public void testLatchBasedThreadOrdering() throws InterruptedException
    {
        final CountDownLatch c1 = new CountDownLatch(1);
        final CountDownLatch c2 = new CountDownLatch(1);
        final CountDownLatch c3 = new CountDownLatch(1);
        final AtomicInteger ai = new AtomicInteger(0);

        Thread t1 = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    assertTrue(ai.compareAndSet(0, 1)); // S1
                    c1.countDown();
                    c3.await();
                    assertEquals(ai.get(), 3);            // S4
                } catch (Exception e)
                {  // Can't simply catch InterruptedException because we might miss some RuntimeException
                    unexpectedException();
                }
            }
        });

        Thread t2 = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    c1.await();
                    assertTrue(ai.compareAndSet(1, 2)); // S2
                    c2.countDown();
                    c3.await();
                    assertEquals(ai.get(), 3);            // S4
                } catch (Exception e)
                {
                    unexpectedException();
                }
            }
        });

        t1.start();
        t2.start();

        c2.await();
        assertTrue(ai.compareAndSet(2, 3)); // S3
        c3.countDown();

        t1.join();
        t2.join();
    }

}

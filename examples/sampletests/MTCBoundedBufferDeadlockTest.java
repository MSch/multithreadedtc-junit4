package sampletests;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Can we implement the Bounded Buffer using CountDownLatch? Nope, this causes a deadlock! But MTC can detect deadlocks. So we'll use the
 * CountDownLatch version to demonstrate MTC's deadlock detection capabilities.
 */
public class MTCBoundedBufferDeadlockTest extends MultithreadedTestCase
{
    ArrayBlockingQueue<Integer> buf;
    CountDownLatch c;

    @Before
    public void initialize()
    {
        buf = new ArrayBlockingQueue<Integer>(1);
        c = new CountDownLatch(1);
    }

    @Threaded
    public void threadPutPut() throws InterruptedException
    {
        buf.put(42);
        buf.put(17);
        c.countDown();
    }

    @Threaded
    public void thread2() throws InterruptedException
    {
        c.await();
        assertEquals(Integer.valueOf(42), buf.take());
        assertEquals(Integer.valueOf(17), buf.take());
    }

    @Test(expected = IllegalStateException.class)
    public void test()
    {
    }
}

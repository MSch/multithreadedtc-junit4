package plain_vs_mtc;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class ThreadControlTestsMTC extends MultithreadedTestCase
{

    AtomicInteger ai;

    @Before
    public void initialize()
    {
        ai = new AtomicInteger(0);
    }

    @Threaded
    public void thread1()
    {
        assertTrue(ai.compareAndSet(0, 1)); // S1
        waitForTick(3);
        assertEquals(ai.get(), 3);          // S4
    }

    @Threaded
    public void thread2()
    {
        waitForTick(1);
        assertTrue(ai.compareAndSet(1, 2)); // S2
        waitForTick(3);
        assertEquals(ai.get(), 3);          // S4
    }

    @Threaded
    public void thread3()
    {
        waitForTick(2);
        assertTrue(ai.compareAndSet(2, 3)); // S3
    }

    @Test
    public void test()
    {
    }
}

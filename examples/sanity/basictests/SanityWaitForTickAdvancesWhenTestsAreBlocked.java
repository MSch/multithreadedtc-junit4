package sanity.basictests;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;

import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
*/
public class SanityWaitForTickAdvancesWhenTestsAreBlocked extends MultithreadedTestCase
{
    CountDownLatch c;

    @Before
    public void initialize() {
        c = new CountDownLatch(3);
    }

    @Threaded
    public void thread1() throws InterruptedException {
        c.countDown();
        c.await();
    }

    @Threaded
    public void thread2() throws InterruptedException {
        c.countDown();
        c.await();
    }

    @Threaded
    public void thread3() {
        waitForTick(1);
        assertEquals(1, c.getCount());
        waitForTick(2); // advances quickly
        assertEquals(1, c.getCount());
        c.countDown();
    }

    @Test
    public void finish() {
        assertEquals(0, c.getCount());
    }
}

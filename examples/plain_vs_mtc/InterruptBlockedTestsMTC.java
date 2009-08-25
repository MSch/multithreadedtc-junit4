package plain_vs_mtc;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;

import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class InterruptBlockedTestsMTC extends MultithreadedTestCase
{
    Semaphore s;

    @Before
    public void initialize()
    {
        s = new Semaphore(0);
    }

    @Threaded("1")
    public void thread1()
    {
        try
        {
            s.acquire();
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

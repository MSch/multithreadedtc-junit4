package sanity.basictests;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;
import static org.junit.Assert.assertSame;
import org.junit.Test;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class SanityGetThreadReturnsCorrectThread extends MultithreadedTestCase
{
    Thread t;

    @Threaded("1")
    public void thread1()
    {
        t = Thread.currentThread();
        waitForTick(2);
    }

    @Threaded("2")
    public void thread2()
    {
        waitForTick(1);
        assertSame(getThread(1), t);
    }

    @Test
    public void test()
    {
    }
}

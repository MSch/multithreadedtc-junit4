package sanity.basictests;

import edu.umd.cs.mtc.Threaded;
import edu.umd.cs.mtc.*;
import org.junit.Test;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class SanityGetThreadByNameReturnsCorrectThread extends MultithreadedTestCase
{
    Thread t;

    @Threaded("Fooey")
    public void threadFooey()
    {
        t = Thread.currentThread();

        waitForTick(2);
    }

    @Threaded
    public void threadBooey()
    {
        waitForTick(1);
        assertEquals("threadBooey", Thread.currentThread().getName());
        assertSame(getThreadByName("Fooey"), t);
    }

    @Test
    public void test()
    {
        assertEquals(t.getName(), "Fooey");
    }

}

package sanity.basictests;

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.Threaded;
import org.junit.Test;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class SanityThreadTerminatesBeforeFinishIsCalled extends MultithreadedTestCase
{
    Thread t1, t2;

    @Threaded
    public void thread1()
    {
        t1 = Thread.currentThread();
    }

    @Threaded
    public void thread2()
    {
        t2 = Thread.currentThread();
    }

    @Test
    public void finish()
    {
        org.junit.Assert.assertEquals(Thread.State.TERMINATED, t1.getState());
        org.junit.Assert.assertEquals(Thread.State.TERMINATED, t2.getState());
    }
}

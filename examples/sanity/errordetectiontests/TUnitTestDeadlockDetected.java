package sanity.errordetectiontests;

import edu.umd.cs.mtc.Threaded;
import edu.umd.cs.mtc.*;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class TUnitTestDeadlockDetected extends MultithreadedTestCase
{
    ReentrantLock lockA;
    ReentrantLock lockB;

    @Before
    public void initialize()
    {
        lockA = new ReentrantLock();
        lockB = new ReentrantLock();
    }

    @Threaded
    public void threadA()
    {
        lockA.lock();
        waitForTick(1);
        lockB.lock();
    }

    @Threaded
    public void threadB()
    {
        lockB.lock();
        waitForTick(1);
        lockA.lock();
    }

    @Test(expected = IllegalStateException.class)
    public void test()
    {
    }
}
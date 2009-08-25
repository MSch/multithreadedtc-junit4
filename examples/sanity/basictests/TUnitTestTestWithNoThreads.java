package sanity.basictests;

import edu.umd.cs.mtc.MultithreadedTestCase;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class TUnitTestTestWithNoThreads extends MultithreadedTestCase
{
    private AtomicInteger v1;

    @Test
    public void initialize()
    {
        v1 = new AtomicInteger(0);
        assertTrue(v1.compareAndSet(0, 1));
    }

    @After
    public void finish()
    {
        assertTrue(v1.compareAndSet(1, 2));
    }
}

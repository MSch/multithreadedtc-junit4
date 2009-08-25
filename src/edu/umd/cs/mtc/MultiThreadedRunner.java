package edu.umd.cs.mtc;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * A junit runner which runs the threaded methods before the actual junit test method.
 *
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
public class MultiThreadedRunner extends BlockJUnit4ClassRunner
{
    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @throws org.junit.runners.model.InitializationError
     *          if the test class is malformed.
     */
    public MultiThreadedRunner(Class<?> klass)
            throws InitializationError
    {
        super(klass);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test)
    {
        final MultithreadedTest multithreadedTestAnnotation = method.getAnnotation(MultithreadedTest.class);
        if (multithreadedTestAnnotation != null)
            return new RunThreadsThenInvokeMethod(method, test, multithreadedTestAnnotation.times());
        else
            return new RunThreadsThenInvokeMethod(method, test, 1);
    }
}

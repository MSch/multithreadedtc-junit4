package edu.umd.cs.mtc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a method in a {@link MultithreadedTestCase}
 * should be run in a separate thread.
 * 
 * @author <a href="mailto:jvb@newtec.eu">Jan Van Besien</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD })
public @interface Threaded {
    /**
     * name of the thread, if empty the method name is used
     */
    String value() default "";
}

package pl.polidea.robospock;

import android.net.Uri__FromAndroid;
import com.xtremelabs.robolectric.RobolectricConfig;
import com.xtremelabs.robolectric.bytecode.RobolectricClassLoader;
import com.xtremelabs.robolectric.bytecode.ShadowWrangler;
import com.xtremelabs.robolectric.internal.RealObject;
import com.xtremelabs.robolectric.util.DatabaseConfig;
import org.codehaus.groovy.runtime.ArrayUtil;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.*;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.spockframework.runtime.Sputnik;

import java.lang.reflect.Constructor;

public class RoboSputnik extends Runner implements Filterable, Sortable {

    // we're using interface, because using Sputnik by class would throw
    // "cannot cast from Sputnik to Sputnik"
    private Runner sputnikRunner;

    private static RobolectricClassLoader classLoader;

    public RoboSputnik(final Class<?> clazz) throws InitializationError {

        //Reuse classloader to decrease perm usage and speed up tests
        if (classLoader == null) {
            classLoader = createClassLoader();
            // this line prevents overloading class loader ? LOL
            classLoader.delegateLoadingOf(ArrayUtil.class.getName());
        }

        final Class<?> delegateClass = classLoader.bootstrap(Sputnik.class);
        try {
            // that's the only reason for new runner - to delegate to sputnik,
            // which is loaded with Robolectric classloader
            final Constructor<?> constructorForDelegate = delegateClass.getConstructor(Class.class);
            this.sputnikRunner = (Runner) constructorForDelegate.newInstance(classLoader.bootstrap(clazz));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
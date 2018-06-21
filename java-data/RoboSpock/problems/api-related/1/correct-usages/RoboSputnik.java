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

    private static RobolectricClassLoader classLoader;

    public RoboSputnik(final Class<?> clazz) throws InitializationError {

        //Reuse classloader to decrease perm usage and speed up tests
        if (classLoader == null) {
            classLoader = createClassLoader();
            classLoader.delegateLoadingOf(ArrayUtil.class.getName());
        }
    }
}

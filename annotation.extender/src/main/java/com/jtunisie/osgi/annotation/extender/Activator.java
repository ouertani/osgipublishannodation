/*
 * @name: Slim OUERTANI
 * @site : http://jroller.com/ouertani
 * @mail : ouertani@gmail.com
 */
package com.jtunisie.osgi.annotation.extender;

import com.jtunisie.osgi.annotation.Prop;
import com.jtunisie.osgi.annotation.RegisterService;
import com.jtunisie.osgi.annotation.NamedInterface;
import java.net.URL;
import java.util.Enumeration;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 *
 * @author slim
 */
public class Activator implements BundleActivator, BundleListener {

    private BundleContext context;

    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;
        System.out.println("Adding bundle listener");
        context.addBundleListener(this);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        //TODO
    }

    @Override
    public void bundleChanged(BundleEvent event) {
        if (event.getBundle().equals(this.context.getBundle())) {
            return;
        }

        if (event.getType() == BundleEvent.STARTED) {
            Bundle b = event.getBundle();
            System.out.println("New bundle: " + b);
            Enumeration<?> entrs = b.findEntries("/", "*.class", true);

            if (entrs == null || !entrs.hasMoreElements()) {
                return;
            }

            while (entrs.hasMoreElements()) {
                URL e = (URL) entrs.nextElement();
                String file = e.getFile();
                String path = e.getPath();

                System.out.println("file: " + file + "path : " + path);
                String c = file.replaceAll("/", ".").replaceAll(".class", "").replaceFirst(".", "");
                System.out.println("c: " + c);
                loadClass(b, c);
            }
        }
    }


    private void loadClass(Bundle b, String clazz) {
        try {
            Class<?> loaded = b.loadClass(clazz);

            System.out.println("Loaded class [" + loaded + "]");
            boolean annotationPresent = loaded.isAnnotationPresent(RegisterService.class);
            System.out.println("" + annotationPresent);
            if (annotationPresent) {
                publishService(loaded, b);

            }

        } catch (InstantiationException ex) {
            ex.printStackTrace();
            Logger.getLogger(Activator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
            Logger.getLogger(Activator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void publishService(Class<?> loaded, Bundle bundle) throws IllegalAccessException, InstantiationException {
        RegisterService annotation = loaded.getAnnotation(RegisterService.class);
        String id = annotation.id();
        NamedInterface[] interfaces = annotation.NamedInterfaces();
        Prop[] with = annotation.Props();
        Properties p = new Properties();
        for (Prop props : with) {
            p.put(props.key(), props.value());
        }
        Object newInstance = loaded.newInstance();
        for (NamedInterface object : interfaces) {
            //register service
            System.out.println("[ " + id + " ] registring service ..." + object.value().getName());
            bundle.getBundleContext().registerService(object.value().getName(), newInstance, p);
        }
    }
}

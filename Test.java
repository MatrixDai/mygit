package com.cateyes.xcar;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.cateyes.xcar.config.Router;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class AppLifecycleBean {
    private static final Logger LOGGER = Logger.getLogger(AppLifecycleBean.class);

    // do NOT change manually, will update by cicd script.
    private String VERSION="";
    private String GITHUB_SHA="";

    @Inject
    MeterRegistry meterRegistry;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("\n" +
                "  __  __         __     __          __   _______          _____  \n" +
                " |  \\/  |        \\ \\   / /          \\ \\ / / ____|   /\\   |  __ \\ \n" +
                " | \\  / | __ _  __\\ \\_/ /_ _ _ __    \\ V / |       /  \\  | |__) |\n" +
                " | |\\/| |/ _` |/ _ \\   / _` | '_ \\    > <| |      / /\\ \\ |  _  / \n" +
                " | |  | | (_| | (_) | | (_| | | | |  / . \\ |____ / ____ \\| | \\ \\ \n" +
                " |_|  |_|\\__,_|\\___/|_|\\__,_|_| |_| /_/ \\_\\_____/_/    \\_\\_|  \\_\\\n");
        System.out.println("Xcar Release: " + VERSION + ", GITHUB_SHA: " + GITHUB_SHA);

        // Monitor the thread queue size
        meterRegistry.gaugeCollectionSize("xcar.queue.size", Tags.empty(), Router.XCAR_THREAD_POOL.getQueue());
    }
}

package es.gob.afirma.android;

import android.content.Context;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Tarea para ejecutar la eliminacion del certificado de cache
 */
public class DeleteCertCacheService {

    private static ScheduledExecutorService scheduler;
    private Runnable task;
    private boolean isRunning;
    private static boolean isInitialized;
    private static Context context;

    public DeleteCertCacheService() { }

    private void init(Context ctx) {
        this.context = ctx;
        this.task = new Runnable() {
            @Override
            public void run() {
                StickySignatureManager.setStickyKeyEntry(null, context);
                stop();
            }
        };
        this.isRunning = false;
        this.isInitialized = true;
    }

    public void start(final long intervalMinutes, final Context ctx) {
        if (!this.isInitialized) {
            init(ctx);
        }
        if (!this.isRunning) {
            this.scheduler = Executors.newSingleThreadScheduledExecutor();
            this.scheduler.scheduleWithFixedDelay(this.task, intervalMinutes, intervalMinutes, TimeUnit.MINUTES);
            this.isRunning = true;
        }
    }

    public void stop() {
        if (this.isRunning) {
            this.scheduler.shutdown();
            this.isRunning = false;
        }
    }

}

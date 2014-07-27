package org.wahtod.attwifiblacklister;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AttBlacklisterService extends IntentService {

    public AttBlacklisterService() {
        super("AttBlacklisterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
             /*
             * Disable component if necessary
            */
            Context context =this.getApplicationContext();
            if (BlacklistHelper.isBlacklisted(context)) {
                int network = intent.getIntExtra(BlacklistHelper.NETWORK_ID, -1);
                BlacklistHelper.blacklist(context, network, true);
            }
        }
    }
}

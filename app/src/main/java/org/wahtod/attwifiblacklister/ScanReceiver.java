package org.wahtod.attwifiblacklister;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScanReceiver extends BroadcastReceiver {


    public ScanReceiver() {
    }

    @Override
    public void onReceive(Context c, Intent intent) {
        Context context = c.getApplicationContext();
        if (BlacklistHelper.isBlacklisted(context)) {
            int network = BlacklistHelper.shouldBlackList(context);
            if (network > 0) {
                Intent i = new Intent(context, AttBlacklisterService.class);
                i.putExtra(BlacklistHelper.NETWORK_ID, network);
                context.startService(i);
            }
        }
    }
}

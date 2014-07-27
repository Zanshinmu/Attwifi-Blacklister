/*
 * Attwifi Blacklister for Android
 * Copyright (C) 2014  David Van de Ven
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses
 */

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

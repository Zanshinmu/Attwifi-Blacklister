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

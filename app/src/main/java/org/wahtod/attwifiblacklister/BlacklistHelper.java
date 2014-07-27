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

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zanshin on 7/26/14.
 */
public class BlacklistHelper {
    public static final String ATTWIFI = "attwifi";
    public static final String EMPTYSTRING = " ";
    public static final String NETWORK_ID = "NET_ID";
    public static final String BLACKLIST_KEY = "BLACKLISTED";
    private static WifiManager _wifiManager;
    private static SharedPreferences _prefs;

    public static WifiManager getWifiManager(Context context) {
        if (_wifiManager == null)
            _wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return _wifiManager;
    }

    public static void blacklist(Context context, int network, boolean b) {
        if (b) {
            getWifiManager(context).disableNetwork(network);
        } else {
            getWifiManager(context).enableNetwork(network, false);
        }
    }

    public static String removeQuotes(String ssid) {
        if (ssid == null)
            return EMPTYSTRING;
        else if (ssid.endsWith("\"") && ssid.startsWith("\"")) {
            try {
                ssid = ssid.substring(1, ssid.length() - 1);
            } catch (IndexOutOfBoundsException e) {
                return EMPTYSTRING;
            }
        }
        return ssid;
    }

    public static SharedPreferences getSharedPreferences(Context c) {
        if (_prefs == null)
            _prefs = PreferenceManager.getDefaultSharedPreferences(c
                    .getApplicationContext());
        return _prefs;
    }

    public static boolean isBlacklisted(Context context) {
        return getSharedPreferences(context).getBoolean(BLACKLIST_KEY, false);
    }

    public static void showToast(Context context, int stringId) {
        Toast toast = Toast.makeText(context, context.getString(stringId), Toast.LENGTH_LONG);
        toast.show();
    }

    public static void setScanReceiverEnabled(Context context,
                                            Boolean state) {
        PackageManager pm = context.getPackageManager();
        ComponentName service = new ComponentName(context, ScanReceiver.class);
        if (state)
            pm.setComponentEnabledSetting(service,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        else {
            pm.setComponentEnabledSetting(service,
                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }

    public static int shouldBlackList(Context context) {
        WifiManager wifi = BlacklistHelper.getWifiManager(context);
        if (!wifi.isWifiEnabled())
            return -1;
        List<WifiConfiguration> configs = wifi.getConfiguredNetworks();
        for (WifiConfiguration config : configs) {
            if (BlacklistHelper.removeQuotes(config.SSID).equals(BlacklistHelper.ATTWIFI)) {
                return config.networkId;
            }
        }
        return -1;
    }
}

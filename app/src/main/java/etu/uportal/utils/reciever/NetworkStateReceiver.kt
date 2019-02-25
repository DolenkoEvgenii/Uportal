package etu.uportal.utils.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import etu.uportal.model.event.GpsSettingsChangedEvent
import org.greenrobot.eventbus.EventBus

class NetworkStateReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.extras != null) {
            EventBus.getDefault().post(GpsSettingsChangedEvent())
        }
    }
}
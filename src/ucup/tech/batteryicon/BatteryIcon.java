package ucup.tech.batteryicon;

import ucup.tech.batteryicon.utils.uTheme;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.provider.Settings.System;
import android.util.AttributeSet;
import android.widget.ImageView;

public class BatteryIcon extends ImageView{
	/*private Drawable sNormal, sCharge;
	private uTheme mTheme;*/
	private IntentFilter mFilter;
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			int level = arg1.getIntExtra("level", 0);
			int type = arg1.getIntExtra("status", 0);
			if (type==2)
				BatteryIcon.this.setImageResource(R.drawable.batt_charge);
			else
				BatteryIcon.this.setImageResource(R.drawable.batt);
			BatteryIcon.this.setImageLevel(level);
		}
	};
	public BatteryIcon(Context context, AttributeSet attr) {
		super(context, attr);
		
		/*mTheme = new uTheme(context);*/
		
		mFilter = new IntentFilter();
		mFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
		context.registerReceiver(mReceiver, mFilter);
		
		/*String settings = System.getString(context.getContentResolver(), "ucup_batt_normal");
		if(settings == null){
			settings = "batt";
		}
		sNormal = mTheme.getIcon(settings);
		
		settings = System.getString(context.getContentResolver(), "ucup_batt_charge");
		if(settings == null){
			settings = "batt_charge";
		}
		sCharge = mTheme.getIcon(settings);*/
	}
}

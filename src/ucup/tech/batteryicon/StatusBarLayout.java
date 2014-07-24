package ucup.tech.batteryicon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class StatusBarLayout extends RelativeLayout{
	private Context mContext;
	private WidgetObserver mObs;
	private BatteryIcon mBatt;
	private LinearLayout mLinear;
	
	private RelativeLayout.LayoutParams leftBattParam = new RelativeLayout.LayoutParams(19, 19);
	private RelativeLayout.LayoutParams rightBattParam = new RelativeLayout.LayoutParams(19, 19);

	private RelativeLayout.LayoutParams leftLinearParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
			RelativeLayout.LayoutParams.FILL_PARENT);
	private RelativeLayout.LayoutParams rightLinearParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
			RelativeLayout.LayoutParams.FILL_PARENT);
	public StatusBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		leftBattParam.addRule(RelativeLayout.RIGHT_OF, getID("signalIcon", mContext));
		rightBattParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		leftLinearParam.addRule(RelativeLayout.LEFT_OF, getID("battIcon", mContext));
		rightLinearParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	}
	@Override
	protected void onFinishInflate(){
		mBatt = (BatteryIcon)findViewById(getID("battIcon", mContext));
		mLinear = (LinearLayout)findViewById(getID("statusIcons", mContext));
		changePos(getBool(mContext));
		
		mObs = new WidgetObserver();
		mContext.registerReceiver(mObs, new IntentFilter("ucup.tech.battery"));
	}
	private int getID(String s, Context c){
		return c.getResources().getIdentifier(s, "id", c.getPackageName());
	}
	private void changePos(String s){
		if(s.equals("right")){
			Log.i("UcupTech", "Battery Pos: Right");
			mBatt.setPadding(0, 0, dipToPx(mContext, "6"), 0);
			mBatt.setLayoutParams(rightBattParam);
			
			mLinear.setPadding(0, 0, 0, 0);
			mLinear.setLayoutParams(leftLinearParam);
			return;
		}
		Log.i("UcupTech", "Battery Pos: Left");
		mBatt.setLayoutParams(leftBattParam);

		mLinear.setPadding(0, 0, dipToPx(mContext, "6"), 0);
		mLinear.setLayoutParams(rightLinearParam);

	}
	private String getBool(Context c){
		String mResult = Settings.System.getString(c.getContentResolver(), "ucup_batt_pos");
		if(mResult == null){
			mResult = "right";
		}
		Log.i("UcupTech", "Battery Pos: " + mResult);
		return mResult;
	}
	private class WidgetObserver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			changePos(getBool(context));
		}
		
	}
	private int dipToPx(Context c, String v){
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Float.valueOf(v), c.getResources().getDisplayMetrics());
	}
}

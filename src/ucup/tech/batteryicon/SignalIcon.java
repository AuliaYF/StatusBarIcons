package ucup.tech.batteryicon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;

public class SignalIcon extends ImageView{
	private ServiceState mState;
	private Context mContext;
	private int mStrength;
	public SignalIcon(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		SignalStrengthListener mListener = new SignalStrengthListener();
	    ((TelephonyManager)context.getSystemService("phone")).listen(mListener, PhoneStateListener.LISTEN_SERVICE_STATE | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
	    

	}
	private void updateSignal(){
		int signal;
		
		if (mState == null || (!hasService() && !mState.isEmergencyOnly()) ) {
            //Slog.d(TAG, "updateSignalStrength: no service");
            if (Settings.System.getInt(mContext.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) == 1) {
            	SignalIcon.this.setImageResource(getIdentifier("stat_sys_signal_flightmode", mContext));
            } else {
            	SignalIcon.this.setImageResource(getIdentifier("stat_sys_signal_null", mContext));
            }
            return;
        }
		
		if (mStrength <= 0)
			signal = 0;
		else if (mStrength >= 1 && mStrength <= 4)
			signal = 1;
		else if (mStrength >= 5 && mStrength <= 7)
			signal = 2;
		else if (mStrength >= 8 && mStrength <= 11)
			signal = 3;
		else if (mStrength >= 12)
			signal = 4;
		else
			signal = 0;
		SignalIcon.this.setImageLevel(signal);
		SignalIcon.this.setImageResource(getIdentifier("stat_sys_signal", mContext));
	}
	private boolean hasService() {
        if (mState != null) {
            switch (mState.getState()) {
                case ServiceState.STATE_OUT_OF_SERVICE:
                case ServiceState.STATE_POWER_OFF:
                    return false;
                default:
                    return true;
            }
        } else {
            return false;
        }
    }
	private int getIdentifier(String s, Context c){
		return c.getResources().getIdentifier(s, "drawable", c.getPackageName());
	}
	private class SignalStrengthListener extends PhoneStateListener
	{
		private SignalStrengthListener() {}
		
		@Override
        public void onServiceStateChanged(ServiceState state) {
            mState = state;
            updateSignal();
        }
		
		@Override
		public void onSignalStrengthsChanged(SignalStrength paramSignalStrength)
		{
			mStrength = paramSignalStrength.getGsmSignalStrength();
			updateSignal();
		}
	}
}

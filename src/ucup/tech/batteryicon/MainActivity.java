package ucup.tech.batteryicon;

import android.os.Bundle;
import android.provider.Settings.System;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {
	Button x;
	String state = "left";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		x = (Button)findViewById(R.id.button);
		x.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(state.equals("left"))
					state = "right";
				else
					state = "left";
				System.putString(getContentResolver(), "ucup_batt_pos", state);
				Intent i = new Intent("ucup.tech.battery");
				MainActivity.this.sendBroadcast(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

package ucup.tech.batteryicon.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class uTheme {
	private PackageManager pm;

	private String themePackage = "ucup.tech.batteryicon";
	
	public String battery_normal = "batt";
	public String battery_charging = "batt_charge";
	public uTheme(Context context){
		pm = context.getPackageManager();
	}
	public Drawable getIcon(String iconName) {
		Resources themeResources = null;
		Drawable d = null;
		try {

			themeResources = pm.getResourcesForApplication(themePackage);
		} catch (NameNotFoundException e) {
		} catch (NullPointerException e) {
		}

		if (themeResources != null) {
			int resource_id = themeResources.getIdentifier(iconName,
					"drawable", themePackage);
			if (resource_id != 0) {
				try {
					d = themeResources.getDrawable(resource_id);
				} catch (Resources.NotFoundException e) {
				}
			}
		}
		return d;
	}
}
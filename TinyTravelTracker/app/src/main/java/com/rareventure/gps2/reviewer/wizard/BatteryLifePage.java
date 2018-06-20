/** 
    Copyright 2015 Tim Engler, Rareventure LLC

    This file is part of Tiny Travel Tracker.

    Tiny Travel Tracker is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Tiny Travel Tracker is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Tiny Travel Tracker.  If not, see <http://www.gnu.org/licenses/>.

*/
package com.rareventure.gps2.reviewer.wizard;

import com.rareventure.gps2.R;
import com.rareventure.android.widget.SeekBarWithText;
import com.rareventure.gps2.GTG;
import com.rareventure.gps2.GTGActivity;
import com.rareventure.gps2.GpsTrailerGpsStrategy;
import com.rareventure.gps2.GTG.Requirement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BatteryLifePage extends GTGActivity {

	private SeekBarWithText timeToUseGpsPercSB;
	private SeekBarWithText minBatteryLifeSB;

	public BatteryLifePage()
	{
	}

	@Override
	public void doOnCreate(Bundle savedInstanceState) {
		super.doOnCreate(savedInstanceState);
		setContentView(R.layout.wizard_battery_life);

		//prevents the initial setup screens from showing when the system is already set up
		if(GTG.prefs.initialSetupCompleted)
			finish();

		timeToUseGpsPercSB = (SeekBarWithText)findViewById(R.id.seekbarwt_perc_time_gps_used);
		minBatteryLifeSB = (SeekBarWithText)findViewById(R.id.seekbarwt_min_battery_life);
		
		timeToUseGpsPercSB.setValue(GpsTrailerGpsStrategy.prefs.batteryGpsOnTimePercentage*100);
		minBatteryLifeSB.setValue(GTG.prefs.minBatteryPerc*100);
	}

	@Override
	public void doOnResume()
	{
		super.doOnResume();
		//prevents the initial setup screens from showing when the system is already set up
		if(GTG.prefs.initialSetupCompleted)
			finish();
	}

	public void onPrev(View target) {
		finish();
	}

	public void onNext(View target) {
		GpsTrailerGpsStrategy.prefs.batteryGpsOnTimePercentage = timeToUseGpsPercSB.getValue()/100;
		GTG.prefs.minBatteryPerc = minBatteryLifeSB.getValue()/100;

		GTG.savePreferences(this);
		
		startInternalActivity(new Intent(this, HelpDeveloperPage.class));		
	}

	@Override
	public int getRequirements() {
		return GTG.REQUIREMENTS_WIZARD;
	}

}

package com.garrivi.apps.arkhamhorrorhelper;

import com.garrivi.apps.arkhamhorrorhelper.fragments.CharacterSelectionFragment;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

public class CharacterSelectionActivity extends FragmentActivity {

	private static final String TAG_FRAGMENT_CHARACTER_SELECTION = "FRAGMENT_CHARACTER_SELECTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_selection);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(new CharacterSelectionFragment(),
							TAG_FRAGMENT_CHARACTER_SELECTION).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.character_selection, menu);
		return true;
	}

}

package com.garrivi.apps.arkhamhorrorhelper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.garrivi.apps.arkhamhorrorhelper.fragments.InvestigatorSelectionFragment;

public class InvestigatorSelectionActivity extends FragmentActivity {

	private static final String TAG_FRAGMENT_CHARACTER_SELECTION = "FRAGMENT_CHARACTER_SELECTION";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_investigator_selection);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.main_content,new InvestigatorSelectionFragment(),
							TAG_FRAGMENT_CHARACTER_SELECTION).commit();
		}
		
		Button btnRandomize = (Button) findViewById(R.id.btnRandomize);
		btnRandomize.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				InvestigatorSelectionFragment fragment = (InvestigatorSelectionFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_CHARACTER_SELECTION);
				if (fragment!=null){
					fragment.showRandomInvestigators(2);
				}
			}
		});
		
		Button btnAll = (Button)findViewById(R.id.btnAll);
		btnAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				InvestigatorSelectionFragment fragment = (InvestigatorSelectionFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_CHARACTER_SELECTION);
				if (fragment!=null){
					fragment.showAllInvestigators();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.investigator_selection, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Implementar
		return super.onMenuItemSelected(featureId, item);
	}

}

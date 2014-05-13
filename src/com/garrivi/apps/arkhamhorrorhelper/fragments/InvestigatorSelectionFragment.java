package com.garrivi.apps.arkhamhorrorhelper.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.garrivi.apps.arkhamhorrorhelper.common.Investigator;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class InvestigatorSelectionFragment extends ListFragment {

	private static Investigator[] sInvestigatorArray = null;
	private static Random sRandom = new Random();
	private static Investigator[] createInvestigatorArray(Context context) {
		ArrayList<Investigator> investigatorList = new ArrayList<Investigator>();
		try {
			InputStream isInvestigators = context.getAssets().open(
					"characters.json");
			JsonReader jrInvestigators = new JsonReader(new InputStreamReader(
					isInvestigators));
	
			Gson gson = new Gson();
			jrInvestigators.beginArray();
			while (jrInvestigators.peek() != JsonToken.END_ARRAY) {
				investigatorList.add((Investigator) gson.fromJson(jrInvestigators,
						Investigator.class));
			}
			jrInvestigators.endArray();
			jrInvestigators.close();
	
		} catch (IOException e) {
			// TODO Controlar correctamente
			e.printStackTrace();
		}
		
		// Sorts the list by name
		Collections.sort((List<Investigator>)investigatorList, new Comparator<Investigator>(){
			@Override
			public int compare(Investigator arg0, Investigator arg1) {
				return arg0.getNombre().compareTo(arg1.getNombre());
			}
		});
		
		return investigatorList.toArray(new Investigator[investigatorList.size()]);
	}

	private Investigator[] mShownInvestigatorArray = null;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new BaseAdapter() {
			
			@Override
			public int getCount() {
				return mShownInvestigatorArray.length;
			}
			
			@Override
			public Object getItem(int position) {
				return mShownInvestigatorArray[position];
			}

			@Override
			public long getItemId(int position) {
				return mShownInvestigatorArray[position].getId();
			}
			
			@Override
			public View getView(int position, View contentView, ViewGroup container) {
				
				View view = contentView;
				
				if (view == null){
					view = getActivity().getLayoutInflater().inflate(
						android.R.layout.simple_list_item_2, container, false);
				}

				updateData((Investigator) getItem(position), view);

				return view;
			}
			
			private void updateData(Investigator item, View view) {
				TextView text1 = (TextView) view
						.findViewById(android.R.id.text1);
				text1.setText(item.getNombre());
				TextView text2 = (TextView) view
						.findViewById(android.R.id.text2);
				text2.setText(item.getProfesion());
				
			}
		});
		
		//((BaseAdapter)getListAdapter()).notifyDataSetChanged();
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (sInvestigatorArray == null) {
			sInvestigatorArray = createInvestigatorArray(getActivity()
					.getApplicationContext());
		}
		updateShownInvestigatorArray(sInvestigatorArray);
	}

	private void updateShownInvestigatorArray(Investigator[] investigatorArray) {
		
		final Investigator[] newArray =  investigatorArray != null?investigatorArray:new Investigator[0];
		
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {				
				mShownInvestigatorArray = newArray;
				
				if (getListAdapter()!=null)
					((BaseAdapter)getListAdapter()).notifyDataSetChanged();
			}
		});
		
		
	}

	public void showRandomInvestigators(int count) {
		Investigator[] investigatorArray = new Investigator[count];
		if (count > 0 && count < sInvestigatorArray.length){
			int ceil = sInvestigatorArray.length - (count - 1);
			int i = 0, position = 0;
			while(i<count && position < ceil){
				position = position + sRandom.nextInt(ceil-position);
				investigatorArray[i] = sInvestigatorArray[position];
				i++;position++;ceil++;
			}
			
			while(i<count){
				investigatorArray[i] = sInvestigatorArray[position];
				i++;position++;
			}
			
		}
		else if (count == sInvestigatorArray.length){
			investigatorArray = sInvestigatorArray;
		}
		
		updateShownInvestigatorArray(investigatorArray);
	}

	public void showAllInvestigators() {
		showRandomInvestigators(sInvestigatorArray.length);
	}
}

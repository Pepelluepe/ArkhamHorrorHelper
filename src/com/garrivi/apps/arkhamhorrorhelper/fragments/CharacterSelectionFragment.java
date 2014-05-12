package com.garrivi.apps.arkhamhorrorhelper.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

public class CharacterSelectionFragment extends ListFragment {

	public static Investigator[] sInvestigatorArray = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (sInvestigatorArray == null) {
			sInvestigatorArray = createInvestigatorArray(getActivity()
					.getApplicationContext());
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View contentView, ViewGroup container) {
				
				View view = contentView;
				
				if (view == null){
					view = getActivity().getLayoutInflater().inflate(
						android.R.layout.simple_list_item_1, container, false);
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

			@Override
			public long getItemId(int position) {
				return sInvestigatorArray[position].getId();
			}
			
			@Override
			public Object getItem(int position) {
				return sInvestigatorArray[position];
			}
			
			@Override
			public int getCount() {
				return sInvestigatorArray.length;
			}
		});
		
		
	}

	private static Investigator[] createInvestigatorArray(Context context) {
		ArrayList<Investigator> investigatorArray = new ArrayList<Investigator>();
		try {
			InputStream isInvestigators = context.getAssets().open(
					"characters.json");
			JsonReader jrInvestigators = new JsonReader(new InputStreamReader(
					isInvestigators));

			Gson gson = new Gson();
			jrInvestigators.beginArray();
			while (jrInvestigators.peek() != JsonToken.END_ARRAY) {
				investigatorArray.add((Investigator) gson.fromJson(jrInvestigators,
						Investigator.class));
			}
			jrInvestigators.endArray();
			jrInvestigators.close();

		} catch (IOException e) {
			// TODO Controlar correctamente
			e.printStackTrace();
		}

		return investigatorArray.toArray(new Investigator[investigatorArray.size()]);
	}

	public void showRandomCharacters(int count) {

	}
}

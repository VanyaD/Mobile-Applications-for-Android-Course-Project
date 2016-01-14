package com.example.android.careandshare;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DetailsFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this.getContext(), R.array.category_array,R.layout.support_simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) v.findViewById(R.id.sp_category);
        spinner.setAdapter(adapterCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(1);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

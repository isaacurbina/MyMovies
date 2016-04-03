package com.mobileappsco.training.mymovies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mobileappsco.training.mymovies.R;

public class SearchFormFragment extends Fragment implements View.OnClickListener {

    private FormFragmentListener mListener;
    EditText formTitle, formYear;
    Button formButton;

    public SearchFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_searchform, container, false);
        formTitle = (EditText) v.findViewById(R.id.form_title);
        formYear = (EditText) v.findViewById(R.id.form_year);
        formButton = (Button) v.findViewById(R.id.form_button);
        formButton.setOnClickListener(this);
        formTitle.clearFocus();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorites:
                mListener.displayFavorites();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FormFragmentListener) {
            mListener = (FormFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FormFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        String title, year;
        title = formTitle.getText().toString();
        year = formYear.getText().toString();
        mListener.bridgeWithForm(title, year);
        formTitle.clearFocus();
    }

    public interface FormFragmentListener {
        void bridgeWithForm(String title, String year);
        void displayFavorites();
    }
}

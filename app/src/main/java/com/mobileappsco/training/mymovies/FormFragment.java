package com.mobileappsco.training.mymovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.net.URLEncoder;

public class FormFragment extends Fragment implements View.OnClickListener {

    private FormFragmentListener mListener;
    EditText formTitle, formYear;
    Button formButton;

    public FormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_form, container, false);
        formTitle = (EditText) v.findViewById(R.id.form_title);
        formYear = (EditText) v.findViewById(R.id.form_year);
        formButton = (Button) v.findViewById(R.id.form_button);
        formButton.setOnClickListener(this);
        return v;
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
        String q = "";
        String title, year;
        title = formTitle.getText().toString();
        year = formYear.getText().toString();
        q = "/search/movie?";
        if (title.length()>0)
            q += "query="+URLEncoder.encode(title);
        if (year.length()>0)
            q += "&year="+URLEncoder.encode(year);
        mListener.bridgeWithForm(q);
    }

    public interface FormFragmentListener {
        void bridgeWithForm(String q);
    }
}

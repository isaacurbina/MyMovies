package com.mobileappsco.training.mymovies;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    SearchFragmentListener searchListener;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    List<Cinematic> cinematics;
    RVAdapter adapter;
    Context context;
    StaggeredGridLayoutManager gaggeredGridLayoutManager;
    boolean loading = false;
    String API_JSON_URL, API_IMAGES_URL, API_KEY;

    public SearchFragment() {
        // Required empty public constructor
        API_KEY = getResources().getString(R.string.API_KEY);
        API_JSON_URL = getResources().getString(R.string.API_JSON_URL);
        API_IMAGES_URL = getResources().getString(R.string.API_IMAGES_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = inflater.getContext();
        cinematics = new ArrayList<>();
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        // Recycler View in SearchFragment
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_list_results);
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(inflater.getContext());
        //recyclerView.setLayoutManager(llm);
        //GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        //recyclerView.setLayoutManager(manager);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        adapter = new RVAdapter(context, cinematics);
        recyclerView.setAdapter(adapter);
        //makeJsonObjectRequest("", "");
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener) {
            searchListener = (SearchFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        searchListener = null;
    }

    public interface SearchFragmentListener {
        void bridgeWithSearch(String q);
    }

}

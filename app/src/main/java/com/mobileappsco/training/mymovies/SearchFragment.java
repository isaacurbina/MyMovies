package com.mobileappsco.training.mymovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        cinematics = new ArrayList<>();
        populateDB();
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        // Recycler View in SearchFragment
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_list_results);
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(inflater.getContext());
        recyclerView.setLayoutManager(llm);
        adapter = new RVAdapter(inflater.getContext(), cinematics);
        recyclerView.setAdapter(adapter);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (searchListener != null) {
            searchListener.sendQueryURL(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SearchFragmentListener {
        // TODO: Update argument type and name
        void sendQueryURL(Uri uri);
    }

    public void populateDB() {
        /*Cinematic c = new Cinematic();
        c.id = 194662;
        c.title = "Birdman";
        c.poster_path = "/rSZs93P0LLxqlVEbI001UKoeCQC.jpg";
        c.overview = "A fading actor best known for his portrayal of a popular superhero attempts to mount a comeback by appearing in a Broadway play. As opening night approaches, his attempts to become more altruistic, rebuild his career, and reconnect with friends and family prove more difficult than expected.";
        c.original_language = "en";
        c.vote_average = 7.34;
        c.adult = false;
        c.video_path = "false";
        c.save();
        cinematics.add(c);

        c = new Cinematic();
        c.id = 177572;
        c.title = "Big Hero 6";
        c.poster_path = "/3zQvuSAUdC3mrx9vnSEpkFX0968.jpg";
        c.overview = "The special bond that develops between plus-sized inflatable robot Baymax, and prodigy Hiro Hamada, who team up with a group of friends to form a band of high-tech heroes.";
        c.original_language = "en";
        c.vote_average = 7.84;
        c.adult = false;
        c.video_path = "false";
        c.save();
        cinematics.add(c);*/
    }
}

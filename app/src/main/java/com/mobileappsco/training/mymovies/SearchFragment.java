package com.mobileappsco.training.mymovies;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public SearchFragment() {
        // Required empty public constructor
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
        makeJsonObjectRequest("");
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

    public void makeJsonObjectRequest(String query) {

        String request = AppController.apiUrl;
        if (query.length()<1) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String now = df.format(new Date());
            request += "/discover/movie?primary_release_date.lte=" + now;
        } else {
            request += query;
            cinematics.removeAll(cinematics);
        }
        request += "&api_key=" + AppController.apiKey;

        AppController.helper.logAndToast(context, "request volley: " + request, Log.INFO);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                request, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                //AppController.helper.logAndToast(getContext(), response.toString(), Log.INFO);

                try {

                    JSONArray jsonArray = response.getJSONArray("results");
                    cinematics.removeAll(cinematics);

                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        Cinematic c;
                        List<Cinematic> res;
                        //c = Cinematic.findById(Cinematic.class, item.getInt("id"));
                        res = Cinematic.find(Cinematic.class, "mid = ?", item.getString("id"));
                        if (res.size()==0)
                            c = new Cinematic();
                        else
                            c = res.get(0);
                        if (c == null)
                            c = new Cinematic();
                        if (item.has("id"))
                            c.mid = item.getInt("id");
                        if (item.has("title") && item.getString("title")!=null)
                            c.title = item.getString("title");
                        if (item.has("overview") && item.getString("overview")!=null)
                            c.overview = item.getString("overview");
                        if (item.has("release_date") && item.getString("release_date")!=null)
                            c.release_date = item.getString("release_date");
                        if (item.has("poster_path") && item.getString("poster_path")!=null)
                            c.poster_path = item.getString("poster_path");
                        if (item.has("adult"))
                            c.adult = item.getBoolean("adult");
                        if (item.has("vote_average"))
                            c.vote_average = item.getDouble("vote_average");
                        if (item.has("video_path") && item.getString("video_path")!=null)
                            c.video_path = item.getString("video_path");
                        if (item.has("original_language") && item.getString("original_language")!=null)
                            c.original_language = item.getString("original_language");

                        //AppController.helper.logAndToast(getApplicationContext(), "Cinematic ID: " + c.mid + " Title: " + c.title, Log.INFO);
                        c.save();
                        cinematics.add(c);
                        adapter.notifyDataSetChanged();
                    }

                    AppController.helper.logAndToast(context, "Number of Registries in Cinematic: "+Cinematic.count(Cinematic.class), Log.INFO);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AppController.helper.logAndToast(context, "VOLLEY ERROR: " + error.getMessage(), Log.ERROR);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public interface SearchFragmentListener {
        void bridgeWithSearch(String q);
    }

}

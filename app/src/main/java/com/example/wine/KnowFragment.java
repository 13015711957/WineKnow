package com.example.wine;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KnowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KnowFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SHOW_TEXT = "text";

    private String mContentText;


    public KnowFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment BlankFragment.
     */
    public static KnowFragment newInstance(String param1) {
        KnowFragment fragment = new KnowFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_know, container, false);

        return rootView;
    }

}
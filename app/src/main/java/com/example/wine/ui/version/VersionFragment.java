package com.example.wine.ui.version;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.wine.R;

public class VersionFragment extends Fragment {

    private VersionViewModel versionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        versionViewModel =
                ViewModelProviders.of(this).get(VersionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_version, container, false);
        return root;
    }
}
package com.unimelb.lecmore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

public class FragmentView extends Fragment {

    static String LAYOUT_TYPE = "type";
    private int layout;

    @Nullable
    @Override
    /**
     *
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (this.getArguments() != null)
            this.layout = getArguments().getInt(LAYOUT_TYPE);

        View view = inflater.inflate(layout, container, false);

        return view;
    }

    static Fragment studentFeedback(int layout, String lecId, String lecNumber) {
        Fragment fragment = new FragmentView();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE, layout);
        fragment.setArguments(bundle);

        return fragment;
    }
}

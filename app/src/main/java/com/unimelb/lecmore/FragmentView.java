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
     *method for creating view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (this.getArguments() != null)
            this.layout = getArguments().getInt(LAYOUT_TYPE);

        View view = inflater.inflate(layout, container, false);

        return view;
    }

    /**
     * Method for creating the fragments according to the feedback of students
     * @param layout the int for storing the layout number
     * @param lecId the String stores lecture id
     * @param lecNumber the string stores lecture number
     * @return the fragment produced
     */
    static Fragment studentFeedback(int layout, String lecId, String lecNumber) {
        Fragment fragment = new FragmentView();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT_TYPE, layout);
        fragment.setArguments(bundle);

        return fragment;
    }
}

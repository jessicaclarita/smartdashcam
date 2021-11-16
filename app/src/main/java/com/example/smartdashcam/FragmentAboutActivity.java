package com.example.smartdashcam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAboutActivity extends Fragment {

    private Button mPrivacyBtn, mTermsBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_about, container, false);

        mPrivacyBtn = view.findViewById(R.id.privacyBtn);
        mPrivacyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentAboutPrivacyPolicy dialog = new DialogFragmentAboutPrivacyPolicy();
                dialog.show(getParentFragmentManager(), "DialogFragmentAboutPrivacyPolicy");
            }
        });

        mTermsBtn = view.findViewById(R.id.termsBtn);
        mTermsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentAboutTermsCondition dialog = new DialogFragmentAboutTermsCondition();
                dialog.show(getParentFragmentManager(), "DialogFragmentAboutTermsCondition");
            }
        });


        return view;

    }
}
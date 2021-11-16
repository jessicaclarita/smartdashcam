package com.example.smartdashcam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentAboutTermsCondition extends DialogFragment {
    private static final String TAG = "DialogFragmentAboutTermsCondition";

    public WebView mWebView;
    private Button mOkBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_about_dialogtermscondition, container, false);

        String fileName = "terms.html";
        mWebView = view.findViewById(R.id.termsWeb);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/" + fileName);

        mOkBtn = view.findViewById(R.id.okBtn);
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();
            }
        });

        return view;
    }
}

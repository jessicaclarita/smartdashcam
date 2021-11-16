package com.example.smartdashcam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;



public class FragmentSettingActivity extends Fragment {

    private SwitchCompat mThemeSwitch;
    private SharedPreferences mPreferences;
    private static final String ISNIGHTMODE = "isNightMode";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_setting,container,false);

        mPreferences = getContext().getSharedPreferences("NightModePref", Context.MODE_PRIVATE);
        mThemeSwitch = view.findViewById(R.id.themeSwitch);

        checkNightModeState();

        mThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    NightModeState(true);
                    getActivity().recreate();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    NightModeState(false);
                    getActivity().recreate();
                }

            }
        });


        return view;
    }

    private void NightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = mPreferences.edit();

        editor.putBoolean(ISNIGHTMODE, nightMode);
        editor.apply();

    }
    public void checkNightModeState(){
        if(mPreferences.getBoolean(ISNIGHTMODE, false)){
            mThemeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            mThemeSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}

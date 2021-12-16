package com.toniel.githubuser.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.toniel.githubuser.SettingPreferences;

public class SettingViewModel extends ViewModel {
    private final SettingPreferences preferences;


    public SettingViewModel(SettingPreferences preferences) {
        this.preferences = preferences;
    }

    public LiveData<Boolean> getThemeSettings(){
        return LiveDataReactiveStreams.fromPublisher(preferences.getThemeSetting());
    }

    public void saveThemeSetting(Boolean isDarkMode){
        preferences.saveThemeSetting(isDarkMode);
    }
}

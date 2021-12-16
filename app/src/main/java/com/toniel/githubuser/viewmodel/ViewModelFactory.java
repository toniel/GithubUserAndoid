package com.toniel.githubuser.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.toniel.githubuser.SettingPreferences;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    RxDataStore<Preferences> dataStore;
    private final Application application;

    private SettingPreferences preferences;

    public ViewModelFactory(Application application) {

        this.application = application;
        this.dataStore = new RxPreferenceDataStoreBuilder(application,"settings").build();
    }

    public static ViewModelFactory getInstance(Application application){
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(application);
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SettingViewModel.class)){
            preferences = SettingPreferences.getInstance(dataStore);
            return (T) new SettingViewModel(preferences);
        }else if(modelClass.isAssignableFrom(FavouriteUserViewModel.class)){
            return (T) new FavouriteUserViewModel(application);
        }else if(modelClass.isAssignableFrom(UserDetailViewModel.class)){
            return (T) new UserDetailViewModel(application);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}

package com.example.appporcino.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.appporcino.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    //private View root;
    private BottomNavigationView navView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navView = getActivity().findViewById(R.id.nav_view_buttom);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_home);
        NavigationUI.setupWithNavController(navView, navController);
        //navView.setOnNavigationItemSelectedListener(navListener);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        //navView.setSelectedItemId(R.id.navigation_todos);
    }
}
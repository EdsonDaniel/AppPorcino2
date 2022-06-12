package com.example.appporcino.ui.home;

import android.content.Intent;
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

import com.example.appporcino.AgregarGanado;
import com.example.appporcino.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeGanadoFragment extends Fragment {

    private HomeGanadoViewModel homeViewModel;
    private BottomNavigationView navView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeGanadoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_ganado, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navView = getActivity().findViewById(R.id.nav_view_buttom_ganado);
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_ganado);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        FloatingActionButton fab = getActivity().findViewById(R.id.add_ganado);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AgregarGanado.class);
                startActivity(intent);
            }
        });
    }
}

/*
Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
 */
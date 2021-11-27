package com.example.mybookkeeper.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout compBtn = view.findViewById(R.id.image_btn);
        RelativeLayout compBtn1 = view.findViewById(R.id.image_btn1);
        RelativeLayout compBtn2 = view.findViewById(R.id.image_btn2);
        RelativeLayout compBtn3 = view.findViewById(R.id.image_btn3);
        RelativeLayout compBtn4 = view.findViewById(R.id.image_btn4);

        compBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Administrator");

/*                Intent intent = new Intent(getActivity(), MainActivity2.class);
                intent.putExtra("One", page);// One is your argument
                startActivity(intent);*/
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_home_to_ManagersFragment);
            }
        });

        compBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Login Page");
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_home_to_CreateAccount);
            }
        });

        compBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Registration Page");
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_home_to_loginPage);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    void showToast(String msg){
        Toast.makeText(getActivity(),"This is the " + msg,Toast.LENGTH_LONG).show();
    }
}
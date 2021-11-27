package com.example.mybookkeeper.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mybookkeeper.R;

public class CreateAccount extends Fragment {

    private EditText eRegName;
    private EditText eRegPassword;
    private Button eRegister;
    private TextView eAttemptsInfo;

    public  static Credentials credentials;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    public CreateAccount() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_account, container, false);
        eRegName=v.findViewById(R.id.etRegName);
        eRegPassword=v.findViewById(R.id.etRegPassword);
        eRegister=v.findViewById(R.id.btnRegister);

        sharedPreferences = getActivity().getSharedPreferences("CredentialsDB", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.apply();

        eRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regUserame = eRegName.getText().toString();
                String regPassword = eRegPassword.getText().toString();

                if(validate(regUserame, regPassword)){
                    credentials = new Credentials(regUserame, regPassword);

                    /*Store the credentials*/
                    sharedPreferencesEditor.putString("Username", regUserame);
                    sharedPreferencesEditor.putString("Password", regPassword);
                    /*Add the changes to the file and commit*/
                    sharedPreferencesEditor.apply();

                    Bundle args = new Bundle();
                    args.putString("Credentials", credentials.getGetUsername());
                    NavHostFragment.findNavController(CreateAccount.this)
                            .navigate(R.id.action_CreateAccount_to_LoginPage, args);
                    Toast.makeText( getActivity(), "Registration successful", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private  boolean validate(String username, String password) {
        if (username.isEmpty() && password.length() < 4) {
            Toast.makeText( getActivity(), "Wrong credentials. Password should be 4 characters", Toast.LENGTH_SHORT).show();
            return false;
        }else{

        }
        return true;
    }
}
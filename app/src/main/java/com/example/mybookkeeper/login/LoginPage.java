package com.example.mybookkeeper.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mybookkeeper.R;

public class LoginPage extends Fragment {

    private EditText eName;
    private EditText ePassword;
    private Button eLogin;
    private TextView eAttemptsInfo;
    private CheckBox eRememberMe;

    private int counter = 5;

    Credentials credentials =new Credentials("admin", "admin");
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;
    public LoginPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login_page, container, false);
        eName=v.findViewById(R.id.edName);
        ePassword=v.findViewById(R.id.edPassword);
        eLogin=v.findViewById(R.id.btnLogin);
        eAttemptsInfo=v.findViewById(R.id.tvAttemptsInfo);
        TextView eRegister = v.findViewById(R.id.tvRegister);
        eRememberMe=v.findViewById(R.id.cbRememberMe);

        sharedPreferences = getActivity().getSharedPreferences("CredentialsDB", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        if(sharedPreferences != null){
            String savedUserName = sharedPreferences.getString("Username", "");
            String savedPassword = sharedPreferences.getString("Password", "");

            CreateAccount.credentials = new Credentials(savedUserName, savedPassword);

            if(sharedPreferences.getBoolean("RememberMeCheckbox", false)){
                eName.setText(savedUserName);
                ePassword.setText(savedPassword);
                eRememberMe.setChecked(true);
            }
            eRememberMe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreferencesEditor.putBoolean("RememberMeCheckbox", eRememberMe.isChecked());
                    sharedPreferencesEditor.apply();
                }
            });
            eRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("Credentials", credentials.getGetUsername());
                    NavHostFragment.findNavController(LoginPage.this)
                            .navigate(R.id.action_loginPage_to_CreateAccount, args);
                }
            });
            eLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isValid = false;
                    String inputName = eName.getText().toString();
                    String inputPassword = ePassword.getText().toString();

                    if(inputName.isEmpty() && inputPassword.isEmpty()) {
                        Toast.makeText(getActivity(), "Please enter the credentials!", Toast.LENGTH_SHORT).show();
                    }else{
                        isValid = validate(inputName, inputPassword);
                        if(!isValid) {
                            counter--;
                            Toast.makeText(getActivity(), "Incorrect credentials entered!", Toast.LENGTH_SHORT).show();
                            eAttemptsInfo.setText(counter + "  " + "attempts remaining");

                            if (counter == 0) {
                                eLogin.setEnabled(false);
                            }

                        }else{
                            Toast.makeText(getActivity(), "Login successful!", Toast.LENGTH_SHORT).show();
/*                        sharedPreferencesEditor.putBoolean("RememberMeCheckbox", eRememberMe.isChecked());
                        sharedPreferencesEditor.apply();*/
                            Bundle args = new Bundle();
                            args.putString("Credentials", credentials.getGetUsername());
                            NavHostFragment.findNavController(LoginPage.this)
                                    .navigate(R.id.action_loginPage_to_myAccounts, args);
/*                            Intent intent = new Intent(getActivity(), FeedFragment.class);
                            startActivity(intent);*/
                        }
                    }
                }
            });
        }
        return  v;
    }
    private  boolean validate(String name, String password) {
        if(CreateAccount.credentials != null){
            if (name.equals(CreateAccount.credentials.getGetUsername()) && password.equals(CreateAccount.credentials.getGetPassword())) {
                return true;
            }
        }
        return false;
    }
}
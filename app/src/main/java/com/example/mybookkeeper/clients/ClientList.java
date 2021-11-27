package com.example.mybookkeeper.clients;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.databinding.FragmentClientListBinding;

import java.util.Arrays;
import java.util.List;

public class ClientList extends Fragment {

    int preSelectedIndex = -1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    private List<Client> homes = Arrays.asList(
            new Client("1", "Add An Account", "false"),
            new Client("2", "Add A Subaccount", "false"),
            new Client("3", "Add A Member", "false"),
            new Client("4", "Receive Payment", "false"),
            new Client("5", "Add An Expense", "false")
    );

    private FragmentClientListBinding binding;
    private RecyclerView mRecyclerList = null;
    private ClientAdapter adapter = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentClientListBinding.inflate(inflater, container, false);
        if (preSelectedIndex == -1) {
            preSelectedIndex = 1;
        }else if (preSelectedIndex ==1)
            preSelectedIndex = -1;

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        binding.lstClients.setLayoutManager(new LinearLayoutManager(context));
          binding.lstClients.setAdapter(new ClientAdapter(homes,
                new DialogInterface() {
                    @Override
                  public void openDialog(Client home) {
/*                        Bundle args = new Bundle();
                        args.putString("ClientName", home.getClientName());
//                        args.putString("ClientID", home.getClientID());*/
 /*                       NavHostFragment.findNavController(ClientList.this)
                                .navigate(R.id.action_ClientList_to_HomeDialog, args);*/
                    }
               }
          )
       );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static interface DialogInterface {
        public void openDialog(Client client);
    }
}
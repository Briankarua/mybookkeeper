package com.example.mybookkeeper.managers;

import com.example.mybookkeeper.accounts.Account;
import com.example.mybookkeeper.clients.Client;
import com.example.mybookkeeper.subaccounts.SubAccount;

public interface RefreshableFragment {
    public void refresh();

    void navigateToManagers(Manager manager);

    void navigateToCreaateAccount();

    void navigateToClients(SubAccount subaccounts);

    void navigateToSubAccountDialog(SubAccount subaccounts);

    void navigateToAccounts(Manager manager);

    void navigateToSubAccounts(Account account);

    void navigateToClientsDialog(Client client);

    void navigateToManagerDialog(Manager manager);

    void navigateToAccountReceiptDialog(Account account);

    void navigateToAccountReceiptDFragment(Account account);
}
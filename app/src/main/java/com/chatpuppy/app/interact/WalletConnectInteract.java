package com.chatpuppy.app.interact;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.chatpuppy.app.entity.WalletConnectActions;
import com.chatpuppy.app.entity.walletconnect.WalletConnectSessionItem;
import com.chatpuppy.app.entity.walletconnect.WalletConnectV2SessionItem;
import com.chatpuppy.app.repository.entity.RealmWCSession;
import com.chatpuppy.app.service.RealmManager;
import com.chatpuppy.app.service.WalletConnectService;
import com.chatpuppy.app.viewmodel.WalletConnectViewModel;
import com.chatpuppy.app.walletconnect.WCClient;
import com.chatpuppy.app.walletconnect.entity.WCUtils;
import com.walletconnect.sign.client.Sign;
import com.walletconnect.sign.client.SignClient;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import timber.log.Timber;

public class WalletConnectInteract
{
    private final RealmManager realmManager;

    @Inject
    public WalletConnectInteract(RealmManager realmManager)
    {
        this.realmManager = realmManager;
    }

    public int getSessionsCount()
    {
        return getSessions().size();
    }

    public List<WalletConnectSessionItem> getSessions()
    {
        List<WalletConnectSessionItem> result = new ArrayList<>();
        result.addAll(getWalletConnectV1SessionItems());
        result.addAll(getWalletConnectV2SessionItems());
        return result;
    }

    public void fetchSessions(Context context, SessionFetchCallback sessionFetchCallback)
    {
        ServiceConnection connection = new ServiceConnection()
        {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service)
            {
                WalletConnectService walletConnectService = ((WalletConnectService.LocalBinder) service).getService();
                fetch(walletConnectService, sessionFetchCallback);
            }

            @Override
            public void onServiceDisconnected(ComponentName name)
            {
            }
        };

        WCUtils.startServiceLocal(context, connection, WalletConnectActions.CONNECT);
    }

    private void fetch(WalletConnectService walletConnectService, SessionFetchCallback sessionFetchCallback)
    {
        List<WalletConnectSessionItem> result = new ArrayList<>();
        List<WalletConnectSessionItem> sessionItems = getWalletConnectV1SessionItems();
        for (WalletConnectSessionItem item : sessionItems)
        {
            WCClient wcClient = walletConnectService.getClient(item.sessionId);
            if (wcClient != null && wcClient.isConnected())
            {
                result.add(item);
            }
        }

        result.addAll(getWalletConnectV2SessionItems());
        sessionFetchCallback.onFetched(result);
    }

    private List<WalletConnectSessionItem> getWalletConnectV1SessionItems()
    {
        List<WalletConnectSessionItem> sessions = new ArrayList<>();
        try (Realm realm = realmManager.getRealmInstance(WalletConnectViewModel.WC_SESSION_DB))
        {
            RealmResults<RealmWCSession> items = realm.where(RealmWCSession.class)
                    .sort("lastUsageTime", Sort.DESCENDING)
                    .findAll();

            for (RealmWCSession r : items)
            {
                sessions.add(new WalletConnectSessionItem(r));
            }
        }

        return sessions;
    }

    private List<WalletConnectSessionItem> getWalletConnectV2SessionItems()
    {
        List<WalletConnectSessionItem> result = new ArrayList<>();
        try
        {
            List<Sign.Model.Session> listOfSettledSessions = SignClient.INSTANCE.getListOfSettledSessions();
            for (Sign.Model.Session session : listOfSettledSessions)
            {
                result.add(new WalletConnectV2SessionItem(session));
            }
        }
        catch (IllegalStateException e)
        {
            Timber.e(e);
        }
        return result;
    }

    public interface SessionFetchCallback
    {
        void onFetched(List<WalletConnectSessionItem> sessions);
    }
}


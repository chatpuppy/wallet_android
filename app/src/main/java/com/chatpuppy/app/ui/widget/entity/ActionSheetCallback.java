package com.chatpuppy.app.ui.widget.entity;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

import com.chatpuppy.app.entity.SignAuthenticationCallback;
import com.chatpuppy.app.entity.cryptokeys.SignatureFromKey;
import com.chatpuppy.app.entity.tokens.Token;
import com.chatpuppy.app.web3.entity.Web3Transaction;
import com.chatpuppy.token.entity.Signable;

/**
 * Created by JB on 27/11/2020.
 */
public interface ActionSheetCallback
{
    void getAuthorisation(SignAuthenticationCallback callback);

    void sendTransaction(Web3Transaction tx);

    void dismissed(String txHash, long callbackId, boolean actionCompleted);

    void notifyConfirm(String mode);

    ActivityResultLauncher<Intent> gasSelectLauncher();

    default void signTransaction(Web3Transaction tx)
    {
    } // only WalletConnect uses this so far

    default void buttonClick(long callbackId, Token baseToken)
    {
    }

    default void notifyWalletConnectApproval(long chainId)
    {
    }

    default void denyWalletConnect()
    {
    }

    default void openChainSelection()
    {
    }

    default void signingComplete(SignatureFromKey signature, Signable message)
    {
    }

    default void signingFailed(Throwable error, Signable message)
    {
    }
}

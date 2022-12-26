package com.chatpuppy.app.entity;

import com.chatpuppy.token.entity.TSAction;

import java.math.BigInteger;
import java.util.List;

public interface StandardFunctionInterface
{
    default void selectRedeemTokens(List<BigInteger> selection) { }

    default void sellTicketRouter(List<BigInteger> selection) { }

    default void showTransferToken(List<BigInteger> selection) { }

    default void showSend() { }

    default void showReceive() { }

    default void updateAmount() { }

    default void displayTokenSelectionError(TSAction action) { }

    default void handleClick(String action, int actionId) { }

    default void handleTokenScriptFunction(String function, List<BigInteger> selection) { }

    default void showWaitSpinner(boolean show) { }

    default void handleFunctionDenied(String denialMessage) { }
}

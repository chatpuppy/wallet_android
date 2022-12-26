package com.chatpuppy.app;

import static com.chatpuppy.app.assertions.Should.shouldSee;
import static com.chatpuppy.app.steps.Steps.createNewWallet;
import static com.chatpuppy.app.steps.Steps.gotoWalletPage;
import static com.chatpuppy.app.steps.Steps.selectCurrency;

import org.junit.Test;

public class CurrencyTest extends BaseE2ETest
{

    @Test
    public void should_switch_currency()
    {
        createNewWallet();

        selectCurrency("CNY");
        gotoWalletPage();
        shouldSee("¥");

        selectCurrency("IDR");
        gotoWalletPage();
        shouldSee("Rp");
    }

}

package com.chatpuppy.app;

import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static com.chatpuppy.app.assertions.Should.shouldSee;
import static com.chatpuppy.app.steps.Steps.createNewWallet;
import static com.chatpuppy.app.steps.Steps.gotoSettingsPage;
import static com.chatpuppy.app.steps.Steps.selectMenu;
import static com.chatpuppy.app.util.Helper.clickListItem;

import org.junit.Test;

public class SelectNetworkTest extends BaseE2ETest
{
    @Test
    public void title_should_update_count()
    {
        createNewWallet();
        gotoSettingsPage();
        selectMenu("Select Active Networks");
        shouldSee("Enabled Networks (1)");
        clickListItem(R.id.test_list, withSubstring("Gnosis"));
        shouldSee("Enabled Networks (2)");
    }
}

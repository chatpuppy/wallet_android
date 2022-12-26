package com.chatpuppy.app.ui.widget.holder;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.ViewGroup;

import com.chatpuppy.app.entity.tokens.Token;
import com.chatpuppy.token.entity.TicketRange;
import com.chatpuppy.app.service.AssetDefinitionService;

/**
 * Created by James on 9/02/2018.
 */
public class TicketHolder extends BaseTicketHolder
{
    public static final int VIEW_TYPE = 1066;

    @Deprecated
    public TicketHolder(int resId, ViewGroup parent, Token ticket, AssetDefinitionService service)
    {
        super(resId, parent, ticket, service);
    }

    @Override
    public void bind(@Nullable TicketRange data, @NonNull Bundle addition)
    {
        super.bind(data, addition);

        ticketLayout.setOnClickListener(v -> {

        });
    }
}

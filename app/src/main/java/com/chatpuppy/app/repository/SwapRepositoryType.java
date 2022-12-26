package com.chatpuppy.app.repository;

import com.chatpuppy.app.entity.lifi.SwapProvider;

import java.util.List;

public interface SwapRepositoryType
{
    List<SwapProvider> getProviders();
}

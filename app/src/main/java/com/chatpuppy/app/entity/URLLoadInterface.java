package com.chatpuppy.app.entity;

public interface URLLoadInterface
{
    void onWebpageLoaded(String url, String title);
    void onWebpageLoadComplete();
}

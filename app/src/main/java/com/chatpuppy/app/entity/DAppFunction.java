package com.chatpuppy.app.entity;

import com.chatpuppy.token.entity.Signable;

public interface DAppFunction
{
    void DAppError(Throwable error, Signable message);
    void DAppReturn(byte[] data, Signable message);
}

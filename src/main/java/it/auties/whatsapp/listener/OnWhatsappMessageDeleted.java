package it.auties.whatsapp.listener;

import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.model.info.MessageInfo;

public interface OnWhatsappMessageDeleted extends QrDiscardingListener {
    /**
     * Called when a message is deleted
     *
     * @param info     the message that was deleted
     * @param everyone whether this message was deleted by you only for yourself or whether the message was permanently removed
     */
    void onMessageDeleted(Whatsapp whatsapp, MessageInfo info, boolean everyone);
}
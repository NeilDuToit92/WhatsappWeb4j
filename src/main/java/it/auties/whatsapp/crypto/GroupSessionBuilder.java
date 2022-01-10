package it.auties.whatsapp.crypto;

import it.auties.whatsapp.manager.WhatsappKeys;
import it.auties.whatsapp.protobuf.signal.keypair.SignalKeyPair;
import it.auties.whatsapp.protobuf.signal.message.SignalDistributionMessage;
import it.auties.whatsapp.protobuf.signal.sender.SenderKeyName;
import it.auties.whatsapp.protobuf.signal.sender.SenderKeyRecord;
import lombok.NonNull;

public record GroupSessionBuilder(@NonNull WhatsappKeys keys) {
    public void process(SenderKeyName name, SignalDistributionMessage message) {
        var senderKeyRecord = keys.findSenderKeyByName(name)
                .orElseGet(() -> createRecord(name));
        senderKeyRecord.addState(message.id(), message.iteration(),
                message.chainKey(), message.signingKey());
        keys.addSenderKey(name, senderKeyRecord);
    }

    public SignalDistributionMessage createMessage(SenderKeyName name) {
        var senderKeyRecord = keys.findSenderKeyByName(name)
                .orElseGet(() -> createRecord(name));
        if (senderKeyRecord.isEmpty()) {
            var keyId = SignalHelper.randomSenderKeyId();
            var senderKey = SignalHelper.randomSenderKey();
            var signingKey = SignalKeyPair.random();
            senderKeyRecord.addState(keyId, 0, senderKey, signingKey.publicKey(), signingKey.privateKey());
            keys.addSenderKey(name, senderKeyRecord);
        }


        var state = senderKeyRecord.currentState();
        return new SignalDistributionMessage(state.id(), state.chainKey().iteration(),
                state.chainKey().seed(), state.signingKeyPublic());
    }

    private SenderKeyRecord createRecord(SenderKeyName name) {
        var record = new SenderKeyRecord();
        keys.addSenderKey(name, record);
        return record;
    }
}

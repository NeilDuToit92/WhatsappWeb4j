package it.auties.whatsapp.model.signal.message;

import it.auties.bytes.Bytes;
import it.auties.protobuf.api.model.ProtobufProperty;
import it.auties.protobuf.api.model.ProtobufSchema;
import it.auties.whatsapp.util.BytesHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

import java.util.Objects;

import static it.auties.protobuf.api.model.ProtobufProperty.Type.BYTES;
import static it.auties.protobuf.api.model.ProtobufProperty.Type.UINT32;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
@Accessors(fluent = true)
public final class SignalPreKeyMessage implements SignalProtocolMessage {
    private int version;

    @ProtobufProperty(index = 1, type = UINT32)
    private int preKeyId;

    @ProtobufProperty(index = 2, type = BYTES)
    private byte[] baseKey;

    @ProtobufProperty(index = 3, type = BYTES)
    private byte[] identityKey;

    @ProtobufProperty(index = 4, type = BYTES)
    private byte[] serializedSignalMessage;

    @ProtobufProperty(index = 5, type = UINT32)
    private int registrationId;

    @ProtobufProperty(index = 6, type = UINT32)
    private int signedPreKeyId;

    private byte[] serialized;

    @SneakyThrows
    public SignalPreKeyMessage(int preKeyId, byte[] baseKey, byte[] identityKey, byte[] serializedSignalMessage,
                               int registrationId, int signedPreKeyId) {
        this.version = CURRENT_VERSION;
        this.preKeyId = preKeyId;
        this.baseKey = baseKey;
        this.identityKey = identityKey;
        this.serializedSignalMessage = serializedSignalMessage;
        this.registrationId = registrationId;
        this.signedPreKeyId = signedPreKeyId;
        this.serialized = Bytes.of(serializedVersion())
                .append(PROTOBUF.writeValueAsBytes(this))
                .toByteArray();
    }

    @SneakyThrows
    public static SignalPreKeyMessage ofSerialized(byte[] serialized) {
        return PROTOBUF.reader()
                .with(ProtobufSchema.of(SignalPreKeyMessage.class))
                .readValue(Bytes.of(serialized).slice(1).toByteArray(), SignalPreKeyMessage.class)
                .version(BytesHelper.bytesToVersion(serialized[0]))
                .serialized(serialized);
    }

    public SignalMessage signalMessage(){
        return SignalMessage.ofSerialized(serializedSignalMessage);
    }
}

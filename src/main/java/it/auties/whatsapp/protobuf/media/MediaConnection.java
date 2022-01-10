package it.auties.whatsapp.protobuf.media;

import it.auties.whatsapp.exchange.Node;
import lombok.NonNull;

import java.util.List;

import static java.util.Objects.requireNonNullElse;

public record MediaConnection(@NonNull String auth, int ttl, int maxBuckets, long timestamp, @NonNull List<@NonNull String> hosts) {
    public static MediaConnection ofNode(Node node){
        var mediaConnection = requireNonNullElse(node.findNode("media_conn"), node);
        var auth = mediaConnection.attributes()
                .getString("auth");
        var ttl = mediaConnection.attributes()
                .getInt("ttl");
        var maxBuckets = mediaConnection.attributes()
                .getInt("max_buckets");
        var timestamp = System.currentTimeMillis();
        var hosts = mediaConnection.findNodes("host")
                .stream()
                .map(Node::attributes)
                .map(attributes -> attributes.getString("hostname"))
                .toList();
        return new MediaConnection(auth, ttl, maxBuckets, timestamp, hosts);
    }
}

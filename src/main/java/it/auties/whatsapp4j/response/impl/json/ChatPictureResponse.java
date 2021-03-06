package it.auties.whatsapp4j.response.impl.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.auties.whatsapp4j.response.model.json.JsonResponseModel;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record ChatPictureResponse(int status, @Nullable String url, @Nullable String tag) implements JsonResponseModel {
    @JsonCreator
    public ChatPictureResponse(@Nullable @JsonProperty("eurl") String url, @Nullable @JsonProperty("tag") String tag, @JsonProperty("status") Integer status){
        this(Objects.requireNonNullElse(status, 200), url, tag);
    }
}

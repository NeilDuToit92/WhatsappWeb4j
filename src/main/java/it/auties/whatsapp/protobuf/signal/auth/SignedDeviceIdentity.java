package it.auties.whatsapp.protobuf.signal.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class SignedDeviceIdentity {
  @JsonProperty("1")
  @JsonPropertyDescription("bytes")
  private byte[] details;

  @JsonProperty("2")
  @JsonPropertyDescription("bytes")
  private byte[] accountSignatureKey;

  @JsonProperty("3")
  @JsonPropertyDescription("bytes")
  private byte[] accountSignature;

  @JsonProperty("4")
  @JsonPropertyDescription("bytes")
  private byte[] deviceSignature;
}

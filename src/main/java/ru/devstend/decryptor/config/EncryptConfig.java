package ru.devstend.decryptor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "encrypt")
public class EncryptConfig {

  private String recipientId;
  private String merchantPublicKey;
  private String signingEcV1PrivateKey;

}

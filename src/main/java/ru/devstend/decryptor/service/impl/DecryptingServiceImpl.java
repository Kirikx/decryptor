package ru.devstend.decryptor.service.impl;

import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.devstend.decryptor.config.DecryptConfig;
import ru.devstend.decryptor.dto.DecryptedDataDto;
import ru.devstend.decryptor.dto.EncryptedDataDto;
import ru.devstend.decryptor.exception.DecryptException;
import ru.devstend.decryptor.service.DecryptingService;

@Slf4j(topic = "decrypting-service")
@Service
@EnableConfigurationProperties(DecryptConfig.class)
@RequiredArgsConstructor
public class DecryptingServiceImpl implements DecryptingService {

  private final DecryptConfig config;

  @PostConstruct
  public void init() {
    log.info("decrypting-service start");
  }

  @Override
  public DecryptedDataDto decryptData(EncryptedDataDto request) {

    String decodeEncryptData = new String(Base64.decodeBase64(
        request.getEncryptedData()
    ));

    String decryptedMessage;
    try {
      PaymentMethodTokenRecipient recipient =
          new PaymentMethodTokenRecipient.Builder()
              .senderVerifyingKeys(
                  config.getVerifyingPublicKeysJson()
              ) // GOOGLE_VERIFYING_PUBLIC_KEYS_JSON
              .recipientId(
                  config.getRecipientId()
              ) // RECIPIENT_ID
              .addRecipientPrivateKey(
                  config.getAlternateMerchantPrivateKey()
              ) // ALTERNATE_MERCHANT_PRIVATE_KEY_PKCS8_BASE64
              .addRecipientPrivateKey(
                  config.getMerchantPrivateKey()
              ) // MERCHANT_PRIVATE_KEY_PKCS8_BASE64
              .build();

      decryptedMessage = recipient.unseal(decodeEncryptData);
    } catch (Exception ex) {
      log.error("Error decrypt message");

      throw new DecryptException("Decrypt error", ex);
    }

    return DecryptedDataDto.builder()
        .messageId(request.getMessageId())
        .decryptedData(decryptedMessage)
        .build();
  }
}

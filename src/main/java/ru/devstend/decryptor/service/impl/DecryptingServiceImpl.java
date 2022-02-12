package ru.devstend.decryptor.service.impl;

import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenRecipient;
import java.security.GeneralSecurityException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
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
  public Mono<DecryptedDataDto> decryptData(EncryptedDataDto request) {

    return Mono.just(request)
        .map(this::decodeEncryptData)
        .map(encrData -> {
          try {
            return unsealMessage(encrData);
          } catch (GeneralSecurityException ex) {
            log.error("Error build recipient");

            throw new DecryptException("Decrypt error", ex);
          } catch (Exception ex) {
            log.error("Error decrypt message");

            throw new DecryptException("Decrypt error", ex);
          }
        })
        .map(decrData -> DecryptedDataDto.builder()
            .messageId(request.getMessageId())
            .decryptedData(decrData)
            .build());
  }

  private String unsealMessage(String decodeEncryptData) throws GeneralSecurityException {
    return new PaymentMethodTokenRecipient.Builder()
        .senderVerifyingKeys(config.getVerifyingPublicKeysJson())
        .recipientId(config.getRecipientId())
        .addRecipientPrivateKey(config.getAlternateMerchantPrivateKey())
        .addRecipientPrivateKey(config.getMerchantPrivateKey())
        .build()
        .unseal(decodeEncryptData);
  }

  private String decodeEncryptData(EncryptedDataDto request) {
    return new String(Base64.decodeBase64(
        request.getEncryptedData()
    ));
  }
}

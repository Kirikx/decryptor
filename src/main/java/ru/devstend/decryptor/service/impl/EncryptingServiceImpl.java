package ru.devstend.decryptor.service.impl;

import com.google.crypto.tink.apps.paymentmethodtoken.PaymentMethodTokenSender;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.devstend.decryptor.config.EncryptConfig;
import ru.devstend.decryptor.dto.EncryptedDataDto;
import ru.devstend.decryptor.dto.PayDataDto;
import ru.devstend.decryptor.exception.DecryptException;
import ru.devstend.decryptor.service.EncryptingService;

@Slf4j(topic = "encrypting-service")
@Service
@EnableConfigurationProperties(EncryptConfig.class)
@RequiredArgsConstructor
public class EncryptingServiceImpl implements EncryptingService {

  private final EncryptConfig config;

  @PostConstruct
  public void init() {
    log.info("encrypting-service start");
  }

  @Override
  public Mono<EncryptedDataDto> encryptData(PayDataDto request) {

    return Mono.just(request)
        .map(req -> {
          try {
            return sealData(req);
          } catch (GeneralSecurityException ex) {
            log.error("Error build sender");

            throw new DecryptException("Encrypt error", ex);
          } catch (Exception ex) {
            log.error("Error encrypt message");

            throw new DecryptException("Encrypt error", ex);
          }
        })
        .map(this::encodeBase64)
        .map(encrDataBase64 -> EncryptedDataDto.builder()
            .messageId(request.getMessageId())
            .encryptedData(encrDataBase64)
            .build());
  }

  private String encodeBase64(String encryptMessage) {
    return Base64.encodeBase64String(
        encryptMessage.getBytes(StandardCharsets.UTF_8)
    );
  }

  private String sealData(PayDataDto request) throws GeneralSecurityException {
    return new PaymentMethodTokenSender.Builder()
        .senderSigningKey(config.getSigningEcV1PrivateKey())
        .recipientId(config.getRecipientId())
        .rawUncompressedRecipientPublicKey(config.getMerchantPublicKey())
        .build()
        .seal(request.getMessage());
  }
}

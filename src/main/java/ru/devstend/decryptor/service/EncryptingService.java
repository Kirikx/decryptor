package ru.devstend.decryptor.service;

import reactor.core.publisher.Mono;
import ru.devstend.decryptor.dto.EncryptedDataDto;
import ru.devstend.decryptor.dto.PayDataDto;

public interface EncryptingService {

  Mono<EncryptedDataDto> encryptData(PayDataDto request);

}

package ru.devstend.decryptor.service;

import reactor.core.publisher.Mono;
import ru.devstend.decryptor.dto.DecryptedDataDto;
import ru.devstend.decryptor.dto.EncryptedDataDto;

public interface DecryptingService {

  Mono<DecryptedDataDto> decryptData(EncryptedDataDto request);

}

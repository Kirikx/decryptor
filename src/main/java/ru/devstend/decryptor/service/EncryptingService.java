package ru.devstend.decryptor.service;

import ru.devstend.decryptor.dto.EncryptedDataDto;
import ru.devstend.decryptor.dto.PayDataDto;

public interface EncryptingService {

  EncryptedDataDto encryptData(PayDataDto request);

}

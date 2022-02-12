package ru.devstend.decryptor.service;

import ru.devstend.decryptor.dto.DecryptedDataDto;
import ru.devstend.decryptor.dto.EncryptedDataDto;

public interface DecryptingService {

  DecryptedDataDto decryptData(EncryptedDataDto request);

}

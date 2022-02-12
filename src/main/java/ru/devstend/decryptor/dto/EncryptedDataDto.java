package ru.devstend.decryptor.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncryptedDataDto {

  private UUID messageId;
  private String encryptedData;

}

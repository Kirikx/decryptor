package ru.devstend.decryptor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.devstend.decryptor.dto.DecryptedDataDto;
import ru.devstend.decryptor.dto.EncryptedDataDto;
import ru.devstend.decryptor.service.DecryptingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/decrypt")
public class DecryptController {

  private final DecryptingService decryptingService;

  @PostMapping
  ResponseEntity<DecryptedDataDto> decryptMessage(@RequestBody EncryptedDataDto request) {

    return ResponseEntity.ok(
        decryptingService.decryptData(request)
    );
  }

}

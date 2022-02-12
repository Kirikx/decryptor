package ru.devstend.decryptor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ru.devstend.decryptor.dto.EncryptedDataDto;
import ru.devstend.decryptor.dto.PayDataDto;
import ru.devstend.decryptor.service.EncryptingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/encrypt")
public class EncryptController {

  private final EncryptingService encryptingService;

  @PostMapping
  public Mono<ResponseEntity<EncryptedDataDto>> encryptMessage(@RequestBody PayDataDto request) {

    return encryptingService.encryptData(request)
        .map(ResponseEntity::ok);
  }

}

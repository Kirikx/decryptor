package ru.devstend.decryptor.exception;

public class DecryptException extends RuntimeException{

  public DecryptException(Throwable cause) {
    super(cause);
  }

  public DecryptException(String message) {
    super(message);
  }

  public DecryptException(String message, Throwable cause) {
    super(message, cause);
  }

}

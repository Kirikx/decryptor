package ru.devstend.decryptor.config;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "decrypt")
public class DecryptConfig {

    private static final String GOOGLE_VERIFYING_PUBLIC_KEYS_JSON =
        "{\n"
            + "  \"keys\": [\n"
            + "    {\n"
            + "      \"keyValue\": \"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEPYnHwS8uegWAewQtlxizmLFynw"
            + "HcxRT1PK07cDA6/C4sXrVI1SzZCUx8U8S0LjMrT6ird/VW7be3Mz6t/srtRQ==\",\n"
            + "      \"protocolVersion\": \"ECv1\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"keyValue\": \"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE/1+3HBVSbdv+j7NaArdgMyoSAM"
            + "43yRydzqdg1TxodSzA96Dj4Mc1EiKroxxunavVIvdxGnJeFViTzFvzFRxyCw==\",\n"
            + "      \"keyExpiration\": \""
            + Instant.now().plus(Duration.standardDays(1000)).getMillis()
            + "\",\n"
            + "      \"protocolVersion\": \"ECv2\"\n"
            + "    },\n"
            + "    {\n"
            + "      \"keyValue\": \"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAENXvYqxD5WayKYhuXQevdGdLA8i"
            + "fV4LsRS2uKvFo8wwyiwgQHB9DiKzG6T/P1Fu9Bl7zWy/se5Dy4wk1mJoPuxg==\",\n"
            + "      \"keyExpiration\": \""
            + Instant.now().plus(Duration.standardDays(1000)).getMillis()
            + "\",\n"
            + "      \"protocolVersion\": \"ECv2SigningOnly\"\n"
            + "    }\n"
            + "  ]\n"
            + "}";

    private String recipientId;
    private String verifyingPublicKeysJson;
    private String merchantPrivateKey;
    private String alternateMerchantPrivateKey;

    public void setVerifyingPublicKeysJson(String verifyingPublicKeysJson) {
//        System.out.println("TEST");
//        System.out.println(new String(Base64.encodeBase64(GOOGLE_VERIFYING_PUBLIC_KEYS_JSON.getBytes(StandardCharsets.UTF_8))));
        this.verifyingPublicKeysJson = Optional.ofNullable(verifyingPublicKeysJson)
            .filter(StringUtils::isNoneBlank)
            .filter(Base64::isBase64)
            .map(Base64::decodeBase64)
            .map(String::new)
            .orElseThrow(() -> new RuntimeException("VerifyingPublicKeysJson not decode Base64"));
    }
}

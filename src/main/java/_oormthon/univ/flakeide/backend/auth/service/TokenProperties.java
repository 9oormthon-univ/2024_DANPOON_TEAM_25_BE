package _oormthon.univ.flakeide.backend.auth.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "access")
public class TokenProperties {

    private String secretKey;
    private long tokenValidityTime;

}
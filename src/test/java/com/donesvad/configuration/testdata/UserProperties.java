package com.donesvad.configuration.testdata;

import com.donesvad.rest.dto.User;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "user")
public class UserProperties {
  private List<User> list;
}

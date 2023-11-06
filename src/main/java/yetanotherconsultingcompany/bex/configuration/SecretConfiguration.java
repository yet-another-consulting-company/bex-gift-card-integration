package yetanotherconsultingcompany.bex.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yetanotherconsultingcompany.bex.configuration.exception.ConfigException;
import yetanotherconsultingcompany.bex.configuration.model.BabySecret;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SecretConfiguration {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecretConfiguration.class);

  public static final String BSG_CONFIG_PATH = "bsg.config.path";

  @Bean
  public BabySecret configurationPath(final Environment environment, final ObjectMapper objectMapper) {
    try {
      final String configPath = environment.getRequiredProperty(BSG_CONFIG_PATH).trim();
      LOGGER.info("Mounting config from {}", configPath);
      final String fileContentAsString = getFileContent(configPath);
      return objectMapper.readValue(fileContentAsString, BabySecret.class);
    } catch (final Exception e) {
      throw new ConfigException("Could not start application due to configuration issues for path [" + BSG_CONFIG_PATH + "]", e);
    }
  }


  @Bean
  public String applicationName(final BabySecret secret) {
    return secret.applicationName();
  }

  public String getFileContent(final String filePath) throws IOException {
    try (final BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      final StringBuilder sb = new StringBuilder();
      String line = br.readLine();

      while (line != null) {
        sb.append(line);
        sb.append(System.lineSeparator());
        line = br.readLine();
      }
      return sb.toString();
    }
  }
}

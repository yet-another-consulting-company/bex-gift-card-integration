package yetanotherconsultingcompany.bex.configuration.model;
import java.util.Map;

public record BabySecret(
    String applicationName,
    Map<String, UrlConfig> url) {

}

package yetanotherconsultingcompany.bex.configuration.model;

import java.util.Map;

public record UrlConfig(
    String url,
    String host,
    Map<String, String> paths,
    String token,
    String user,
    String password) {}

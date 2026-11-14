package com.hyao.config.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
@ConfigurationProperties(prefix = "my-app")
public class MyAppProperties {
    private String name;

    @NotBlank(message = "环境配置不能为空")
    @Pattern(regexp = "dev|st|uat|prod", message = "环境配置必须是 dev|st|uat|prod 中的一个")
    private String env;

    private String lastDeploy;

    private Tag tag;

    @Data
    public static class Tag {
        private String id;

        private String name;

        private List<String> author;
    }
}

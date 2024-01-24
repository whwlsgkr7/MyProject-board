package com.myproject.board.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

// 클래스에 @Configuration을 사용하면, 이 클래스 내의 @Bean 어노테이션이 붙은 메소드들이 빈으로 등록된다.
// 이렇게 생성된 빈들은 Spring 컨테이너에서 관리되며, 애플리케이션 전반에서 필요한 의존성 주입에 사용될 수 있다.
@Configuration
public class ThymeleafConfig {

        @Bean
        public SpringResourceTemplateResolver thymeleafTemplateResolver(
                SpringResourceTemplateResolver defaultTemplateResolver,
                Thymeleaf3Properties thymeleaf3Properties
        ) {
            defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.decoupledLogic());

            return defaultTemplateResolver;
        }

        @ConfigurationProperties("spring.thymeleaf3")
        public record Thymeleaf3Properties(boolean decoupledLogic) {

        }


}

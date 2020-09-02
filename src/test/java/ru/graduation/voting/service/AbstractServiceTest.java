package ru.graduation.voting.service;

import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
public abstract class AbstractServiceTest {
}
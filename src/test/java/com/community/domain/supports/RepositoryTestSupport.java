package com.community.domain.supports;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.community.global.config.property.QueryDslConfig;

@DataJpaTest
@Import(QueryDslConfig.class)
public abstract class RepositoryTestSupport {
}

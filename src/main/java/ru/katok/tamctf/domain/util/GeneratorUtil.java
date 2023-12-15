package ru.katok.tamctf.domain.util;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class GeneratorUtil implements IdentifierGenerator {
    public static final String randomValueGenerator = "randomValueGenerator";

    public static String generateCleanUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return GeneratorUtil.generateCleanUuid();
    }
}

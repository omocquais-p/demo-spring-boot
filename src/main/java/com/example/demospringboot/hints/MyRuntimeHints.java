package com.example.demospringboot.hints;

import org.hibernate.id.factory.spi.CustomIdGeneratorCreationContext;
import org.springframework.aot.hint.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;

public class MyRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {

        try {
            Constructor<org.hibernate.id.uuid.UuidGenerator> uuidGeneratorConstructor = ReflectionUtils.accessibleConstructor(org.hibernate.id.uuid.UuidGenerator.class, org.hibernate.annotations.UuidGenerator.class, Member.class, CustomIdGeneratorCreationContext.class);
            hints.reflection().registerConstructor(uuidGeneratorConstructor, ExecutableMode.INVOKE);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        hints.reflection().registerType(TypeReference.of("java.util.UUID[]"),
                MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
    }
}

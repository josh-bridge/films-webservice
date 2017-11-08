package com.jbridgiee.films.server.controller;

import java.util.Collections;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.jbridgiee.films.server.data.Film;

/**
 *
 * @author josh.bridge
 */
@Component
public class FilmToStringConverter implements ConditionalGenericConverter {

    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Film.class, String.class));
    }

    public boolean matches(@NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        return sourceType.getObjectType().equals(Film.class);
    }

    @Nullable
    public Object convert(@Nullable Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        return source != null ? source.toString() : null;
    }

}
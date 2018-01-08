package com.jbridgiee.web.response.converters;

import java.util.Collections;
import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.jbridgiee.web.response.Results;

/**
 *
 * @author josh.bridge
 */
@Component
public class ResultsToStringConverter implements ConditionalGenericConverter {

    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Results.class, String.class));
    }

    public boolean matches(@NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        return sourceType.getObjectType().equals(Results.class);
    }

    @Nullable
    public Object convert(@Nullable Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        return source != null ? source.toString() : null;
    }

}

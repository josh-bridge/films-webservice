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
 * Allows conversion of Results objects to text
 *
 * <p>
 *     An implementation of a Spring MVC class which is loaded at startup in the spring web config.
 *     It's not clear why this is needed, and certainly wasn't mentioned in any documentation, so it could be a bug.
 * </p>
 *
 * @author josh.bridge
 */
@Component
public class ResultsToStringConverter implements ConditionalGenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Results.class, String.class));
    }

    @Override
    public boolean matches(@NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        return sourceType.getObjectType().equals(Results.class);
    }

    @Override
    @Nullable
    public Object convert(@Nullable Object source, @NonNull TypeDescriptor sourceType, @NonNull TypeDescriptor targetType) {
        return source != null ? source.toString() : null;
    }

}

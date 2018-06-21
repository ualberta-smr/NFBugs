package com.instagram.common.json.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Apply this to a field in a class annotated with {@link JsonType}.  This tells the annotation
 * processor which fields exist, and how they may to/from the json object.
 */
@Retention(SOURCE) @Target(FIELD)
public @interface JsonField {
  // ...
}

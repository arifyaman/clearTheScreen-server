package com.xlipstudio.cleanthescreen.server.annotations;

import com.xlipstudio.cleanthescreen.communication.request.RequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HandleRequest {
    RequestType type();
}

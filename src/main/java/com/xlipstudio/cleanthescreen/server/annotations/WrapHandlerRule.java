package com.xlipstudio.cleanthescreen.server.annotations;

import com.xlipstudio.cleanthescreen.communication.request.RequestType;
import com.xlipstudio.cleanthescreen.server.server.room.rule.BaseWrapHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WrapHandlerRule {
    Class<? extends BaseWrapHandler> ruleClass() default BaseWrapHandler.class;

    RequestType[] requestTypes() default {RequestType.GO};
}

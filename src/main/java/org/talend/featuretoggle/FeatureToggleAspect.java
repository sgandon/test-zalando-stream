// ============================================================================
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// https://github.com/Talend/data-prep/blob/master/LICENSE
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.featuretoggle;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.talend.featuretoggle.annotation.FeatureToggle;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;

@Aspect
@Component
public class FeatureToggleAspect {
    @Around("@annotation(org.talend.featuretoggle.annotation.FeatureToggle)")
    public Object skipWhenFeatureNotPresent(ProceedingJoinPoint joinPoint) throws Throwable {
        FeatureToggle featureToggle = getAnnotation(joinPoint, FeatureToggle.class);
        if (canProceed(featureToggle)) {
            return joinPoint.proceed();
        }else return computeReturn(featureToggle);
    }

    private Object computeReturn(FeatureToggle featureToggle) {
        switch (featureToggle.defaultReturns()) {
        case NULL:
            return null;
        case EMPTY_LIST:
            return Collections.emptyList();
        default: throw new RuntimeException("Enum value shall be handled here:"+featureToggle.defaultReturns());
        }
    }

    private boolean canProceed(FeatureToggle featureToggle) {
        //here should be the code for calling the feature toggle service to check the feature is authorised
        return Arrays.stream(featureToggle.value()).parallel().anyMatch("authorized-feature"::contains);
    }

    public static <T> T getAnnotation(ProceedingJoinPoint pjp, Class<T> annotationClass) {
        final MethodSignature methodSignature = ((MethodSignature) pjp.getSignature());
        final Annotation[] annotations = methodSignature.getMethod().getAnnotations();
        T lookupAnnotation = null;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(annotationClass)) {
                lookupAnnotation = (T) annotation;
                break;
            }
        }
        return lookupAnnotation;
    }
}

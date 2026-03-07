package org.kindness.coremodule.util;

import com.google.protobuf.GeneratedMessage;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcServiceUtil {

    /*
    AI-generated, my idea was to remove redundant code by creating static method
    using gRPC generic class invoking methods
     */
    public static  <B extends GeneratedMessage.Builder<B>, T> void handleRequest(
            B responseBuilder,
            StreamObserver<T> observer,
            Runnable action
    ) {
        if (observer == null) {
            throw new IllegalArgumentException("StreamObserver cannot be null!");
        }

        try {
            action.run();
            tryInvoke(responseBuilder, "setSuccess", true);
        } catch (IllegalStateException e) {
            tryInvoke(responseBuilder, "setSuccess", false);
            tryInvoke(responseBuilder, "setMessage", e.getMessage());
            log.debug(e.getMessage());
        } catch (Exception e) {
            tryInvoke(responseBuilder, "setSuccess", false);
            tryInvoke(responseBuilder, "setMessage", "Internal server error");
            log.error("Internal server error occurred: {}", e.getMessage(), e);
        }

        observer.onNext((T) responseBuilder.build());
        observer.onCompleted();
    }

    private static void tryInvoke(Object builder, String methodName, Object value) {
        if (value == null) return;

        try {
            for (java.lang.reflect.Method method : builder.getClass().getMethods()) {
                if (method.getName().equals(methodName) && method.getParameterCount() == 1) {
                    method.invoke(builder, value);
                    return;
                }
            }
            log.warn("Method {} not found on {}", methodName, builder.getClass().getName());
        } catch (Exception e) {
            log.error("Failed to invoke {} with value {}", methodName, value, e);
        }
    }
}

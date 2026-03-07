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
        try {
            var method = builder.getClass().getMethod(methodName, value.getClass());
            method.invoke(builder, value);
        } catch (Exception ignored) { }
    }
}

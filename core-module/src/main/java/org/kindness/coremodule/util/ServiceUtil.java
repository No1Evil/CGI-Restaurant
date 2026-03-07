package org.kindness.coremodule.util;

import com.google.protobuf.GeneratedMessage;
import io.grpc.stub.StreamObserver;

import java.util.function.Consumer;

public class ServiceUtil {

    // TODO: remove redundant code with invoking the methods in gRPC MessageBuilder
    public static <V extends GeneratedMessage.Builder<?>> void test(StreamObserver<V> observer, Consumer<Void> test){

    }
}

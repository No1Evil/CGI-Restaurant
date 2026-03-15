package org.kindness.webapp;

import org.kindness.common.grpc.reservation.ReservationServiceGrpc;
import org.kindness.common.grpc.restaraunt.RestaurantServiceGrpc;
import org.kindness.common.grpc.table.TableServiceGrpc;
import org.kindness.common.grpc.user.AuthenticationServiceGrpc;
import org.kindness.common.grpc.user.RegistrationServiceGrpc;
import org.kindness.common.grpc.user.UserServiceGrpc;
import org.kindness.common.grpc.zone.ZoneServiceGrpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.grpc.client.ImportGrpcClients;

@SpringBootApplication(scanBasePackages = {"org.kindness.common", "org.kindness"})
@ImportGrpcClients(
        types = {
                UserServiceGrpc.UserServiceBlockingStub.class,
                AuthenticationServiceGrpc.AuthenticationServiceBlockingStub.class,
                RegistrationServiceGrpc.RegistrationServiceBlockingStub.class,
                ReservationServiceGrpc.ReservationServiceBlockingStub.class,
                RestaurantServiceGrpc.RestaurantServiceBlockingStub.class,
                ZoneServiceGrpc.ZoneServiceBlockingStub.class,
                TableServiceGrpc.TableServiceBlockingStub.class
        }
)
public class WebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppApplication.class, args);
    }

}

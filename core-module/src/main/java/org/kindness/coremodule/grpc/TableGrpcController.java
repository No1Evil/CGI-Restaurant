package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.table.*;
import org.kindness.common.model.impl.Table;
import org.kindness.coremodule.domain.table.TableService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.kindness.common.model.util.TimestampConverter;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public final class TableGrpcController extends TableServiceGrpc.TableServiceImplBase {
    private final TableService tableService;

    @Override
    public void getTable(RequestTable request, StreamObserver<TableResponse> observer) {
        var responseBuilder = TableResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            Table table = tableService.getTable(request.getTableId());
            responseBuilder.setData(convert(table));
        });
    }

    @Override
    public void getTableCollection(RequestTableCollection request, StreamObserver<TableCollectionResponse> observer) {
        var responseBuilder = TableCollectionResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            List<Table> tables = tableService.getAllTables();
            responseBuilder.addAllData(convert(tables));
        });
    }

    @Override
    public void getAvailableTables(RequestAvailableTables request, StreamObserver<TableCollectionResponse> observer) {
        var responseBuilder = TableCollectionResponse.newBuilder();

        Long zoneId = request.hasZoneId() ? request.getZoneId() : null;
        Long restaurantId = request.hasRestaurantId() ? request.getRestaurantId() : null;
        Integer capacity = request.hasCapacity() ? request.getCapacity() : null;

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            var startTime = TimestampConverter.toInstant(request.getStartTime());
            var endTime = TimestampConverter.toInstant(request.getEndTime());

            List<Table> tables = tableService.getAvailableTables(
                    zoneId, restaurantId, capacity,
                    startTime, endTime);

            responseBuilder.addAllData(convert(tables));
        });
    }

    private static List<TableData> convert(List<Table> tables){
        return tables.stream().map(TableGrpcController::convert).toList();
    }

    private static TableData convert(Table tb){
        return TableData.newBuilder()
                .setTableId(tb.getId())
                .setRestarauntId(tb.getRestaurantId())
                .setCapacity(tb.getCapacity())
                .setZoneId(tb.getZoneId())
                .setPosX(tb.getPosX())
                .setPosY(tb.getPosY())
                .build();
    }
}

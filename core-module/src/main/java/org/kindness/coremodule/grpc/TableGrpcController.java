package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.table.*;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public final class TableGrpcController extends TableServiceGrpc.TableServiceImplBase {
    @Override
    public void getTableData(RequestTableData request, StreamObserver<TableDataResponse> responseObserver) {
        super.getTableData(request, responseObserver);
    }

    @Override
    public void getAllTablesData(RequestAllTableData request, StreamObserver<AllTableDataResponse> responseObserver) {
        super.getAllTablesData(request, responseObserver);
    }
}

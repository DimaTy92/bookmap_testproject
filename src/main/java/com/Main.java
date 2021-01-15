package com;

import com.service.OperationService;
import com.service.QueryService;
import com.service.impl.OperationServiceImpl;
import com.service.impl.QueryServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        QueryService queryService = new QueryServiceImpl();
        OperationService operationService = new OperationServiceImpl();
        FileParser fileParser = new FileParser(queryService, operationService);
        fileParser.parseFile("src\\main\\resources\\input.txt");
    }
}

package com;

import com.entity.OrderBook;
import com.entity.OrderBookRow;
import com.entity.type.Type;
import com.entity.type.FileRowType;
import com.entity.type.OperationType;
import com.entity.type.QueryType;
import com.exception.FileRowTypeParsingException;
import com.service.OperationService;
import com.service.QueryService;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileParser {
    private static final int ROW_TYPE_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final int QUERY_TYPE_INDEX = 1;
    private static final int OPERATION_TYPE_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final int QUERY_PRICE_INDEX = 2;
    private static final int OPERATION_SIZE_INDEX = 2;
    private static final int TYPE_INDEX = 3;
    private static final String OUTPUT_FILE_PATH = "./output.txt";

    private final QueryService queryService;
    private final OperationService operationService;
    private OrderBook orderBook;
    private FileWriter fileWriter;

    public FileParser(QueryService queryService, OperationService operationService) {
        this.queryService = queryService;
        this.operationService = operationService;
    }

    public void parseFile(String inputFilePath) throws IOException {
        orderBook = new OrderBook();
        fileWriter = new FileWriter(OUTPUT_FILE_PATH);

        List<String> allLines = Files.readAllLines(Paths.get(inputFilePath));

        allLines.forEach(line -> {
            FileRowType fileRowType = getFileRowType(line);
            operateRowByType(fileRowType, line);
        });

        fileWriter.close();
    }

    private void operateRowByType(FileRowType type, String line) {
        switch (type) {
            case UPDATE:
                processUpdateEvent(line, orderBook);
                break;
            case QUERY:
                processQueryEvent(line);
                break;
            case OPERATION:
                processOperation(line, orderBook);
                break;
            default:
                throw new FileRowTypeParsingException("Row type not supported");
        }
    }

    private void processOperation(String line, OrderBook orderBook) {
        String[] split = line.split(",");
        String stringOperationType = split[OPERATION_TYPE_INDEX];
        Integer operationSize = Integer.valueOf(split[OPERATION_SIZE_INDEX]);
        OperationType operationType = OperationType.getTypeFromString(stringOperationType);
        if (operationType == OperationType.BUY) {
            operationService.processBuy(orderBook.getOrderBookRowMap(), operationSize);
        } else if (operationType == OperationType.SELL) {
            operationService.processSell(orderBook.getOrderBookRowMap(), operationSize);
        }
    }

    private void processQueryEvent(String line) {
        String[] split = line.split(",");
        String stringType = split[QUERY_TYPE_INDEX];
        QueryType queryType = QueryType.getQueryTypeFromString(stringType);
        switch (queryType) {
            case BEST_ASK:
                OrderBookRow askRow = queryService.countMaxAsk(orderBook.getOrderBookRowList());
                printValue(askRow.getPrice(), askRow.getSize());
                break;
            case BEST_BID:
                OrderBookRow bidRow = queryService.countMaxBuy(orderBook.getOrderBookRowList());
                printValue(bidRow.getPrice(), bidRow.getSize());
                break;
            case SIZE:
                String stringPrice = split[QUERY_PRICE_INDEX];
                Integer value = queryService.countAmount(orderBook.getOrderBookRowMap(), Long.valueOf(stringPrice));
                printValue(value);
                break;
            default:
                throw new FileRowTypeParsingException("Query type not supported");
        }

    }

    private void processUpdateEvent(String line, OrderBook orderBook) {
        String[] split = line.split(",");
        String stringPrice = split[PRICE_INDEX];
        String stringAmount = split[AMOUNT_INDEX];
        String stringType = split[TYPE_INDEX];

        OrderBookRow orderBookRow =
                new OrderBookRow(Long.valueOf(stringPrice), Integer.valueOf(stringAmount),
                        Type.getTypeFromString(stringType));

        orderBook.addRow(orderBookRow);
    }

    private FileRowType getFileRowType(String line) {
        char fileRowTypeChar = line.charAt(ROW_TYPE_INDEX);
        return FileRowType.getFileRowTypeFromChar(fileRowTypeChar);
    }

    private void printValue(Long price, Integer size) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(price).append(",").append(size).append("\n");
        try {
            fileWriter.append(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printValue(Integer size) {
        try {
            fileWriter.append(String.valueOf(size)).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

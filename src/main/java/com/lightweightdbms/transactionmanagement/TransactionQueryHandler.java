package com.lightweightdbms.transactionmanagement;

import com.lightweightdbms.queryoperation.QueryOperationUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class TransactionQueryHandler {
    int commitLine = 0;
    QueryOperationUtil queryOperationUtil = new QueryOperationUtil();

    public void transactionHandler(String query, String userId, String databaseName) {
        ArrayList<String> queryList = new ArrayList<>(Arrays.asList(query.split("\n")));
        for (String q : queryList) {
            System.out.println(q);
            if (q.equals("commit")) {
                completeTransaction(commitLine + 1, queryList.indexOf(q) - 1, userId, databaseName, queryList);
                commitLine = queryList.indexOf(q);
                System.out.println("Queries before commit executed");
            }
        }
        if (!queryList.contains("commit")) {
            System.out.println("no queries commited");
        }
    }

    public void completeTransaction(int startLine, int endLine, String userId, String databaseName, ArrayList<String> queryList) {
        for (int i = startLine; i <= endLine; i++) {
            System.out.println(queryList.get(i));
            queryOperationUtil.queryMapper(queryList.get(i) + " ", userId, databaseName);
        }
    }
}

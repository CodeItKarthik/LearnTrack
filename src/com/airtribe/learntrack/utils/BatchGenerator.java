package com.airtribe.learntrack.utils;

import com.airtribe.learntrack.constants.AppConstants;

public class BatchGenerator {

    private static int batchNumber = 0;

    public static String generateBatchNumber() {
        int nextBatchNum = ++batchNumber;
        return AppConstants.BATCH + nextBatchNum;
    }

}

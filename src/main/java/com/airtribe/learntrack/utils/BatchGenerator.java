package main.java.com.airtribe.learntrack.utils;

public class BatchGenerator {

    private static int batchNumber = 0;

    public static String generateBatchNumber() {
        int nextBatchNum = ++batchNumber;
        return "batch" + nextBatchNum;
    }

}

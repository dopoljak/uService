package com.ilirium.webservice.generator;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigInteger;

@ApplicationScoped
public class CorrelationIdGenerator {

    private static final long COUNTER_BITS = 10;
    private static final long NODEID_BITS = 6; // max 0 - 63

    private long lastUsedCurrentTime = -1;
    private long counter = 0;

    public synchronized String getNextId(long nodeId) {
        long currentTime = System.currentTimeMillis();
        if (currentTime == lastUsedCurrentTime) {
            counter++;
            if (counter >= 1024) {
                currentTime++;
                counter = 0;
            }
        } else {
            counter = 0;
        }
        lastUsedCurrentTime = currentTime;

        long transactionId = currentTime;
        transactionId = transactionId << (COUNTER_BITS + NODEID_BITS);
        transactionId |= counter << NODEID_BITS;
        transactionId |= nodeId;
        return format(transactionId);
    }

    private String format(long transactionId) {
        return new BigInteger(1, longToBytes(transactionId)).toString(36).toUpperCase();
    }

    private byte[] longToBytes(long value) {
        return new byte[]{
                (byte) (value >> 56),
                (byte) (value >> 48),
                (byte) (value >> 40),
                (byte) (value >> 32),
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }
}

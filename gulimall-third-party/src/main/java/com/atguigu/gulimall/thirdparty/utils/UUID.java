package com.atguigu.gulimall.thirdparty.utils;

import java.security.SecureRandom;

public class UUID {

    private static final SecureRandom NUMBER_GENERATOR = new SecureRandom();
    private final long mostSigBits;

    private final long leastSigBits;

    private UUID(byte[] data) {
        long msb = 0;
        long lsb = 0;
        assert data.length == 16 : "data must be 16 bytes in length";
        for (int i = 0; i < 8; i++) {
            msb = (msb << 8) | (data[i] & 0xff);
        }
        for (int i = 8; i < 16; i++) {
            lsb = (lsb << 8) | (data[i] & 0xff);
        }
        this.mostSigBits = msb;
        this.leastSigBits = lsb;
    }

    public static String randomUUID() {
        final byte[] randomBytes = new byte[16];
        NUMBER_GENERATOR.nextBytes(randomBytes);

        randomBytes[6] &= 0x0f;
        randomBytes[6] |= 0x40;
        randomBytes[8] &= 0x3f;
        randomBytes[8] |= 0x80;

        UUID uuid = new UUID(randomBytes);
        return uuid.toString();
    }

    /**
     * 返回指定数字对应的hex值
     *
     * @param val    值
     * @param digits 位
     * @return 值
     */
    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }

    @Override
    public String toString() {
        return digits(mostSigBits >> 32, 8) +
                digits(mostSigBits >> 16, 4) +
                digits(mostSigBits, 4) +
                digits(leastSigBits >> 48, 4) +
                digits(leastSigBits, 12);
    }
}

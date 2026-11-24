package com.bush.skygate.booking.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class BookingReferenceGeneratorNanoIdUtils implements BookingReferenceGenerator{

    private static final char[] ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray();
    private static final int LENGTH = 6;

    private final SecureRandom random = new SecureRandom();

    @Override
    public String generate(){
        return NanoIdUtils.randomNanoId(random, ALPHABET, LENGTH);
    }
}

package com.hbm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @project: HotelBookingManager
 * @description:
 * @author: Mabel.Chen
 * @create: 2023-03-31
 **/
public class UtilsTest {

    @Test
    public void formatTest() {
        String dateStr = Utils.format(new Date());
        Assertions.assertNotNull(dateStr);
        Assertions.assertEquals(8, dateStr.length());

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> Utils.format(null));
        Assertions.assertEquals("Invalid date", runtimeException.getMessage());
    }

    @Test
    public void isBeforeDateTest() {
        Date today = new Date();
        Date past = new Date(today.getTime() - 24 * 1000 * 60 * 60);
        Date future = new Date(today.getTime() + 24 * 1000 * 60 * 60);
        Assertions.assertTrue(Utils.isBeforeDate(today, past));
        Assertions.assertFalse(Utils.isBeforeDate(today, new Date(today.getTime())));
        Assertions.assertFalse(Utils.isBeforeDate(today, future));
    }
}
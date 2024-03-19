package io.hhplus.tdd;

import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PointSyncronizedTest {

    @Autowired
    private PointService pointService;

    @Test
    void syncTest1(){
        pointService.chargePoint(1L, 100L);
        Runnable runnable1 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println( "* "+i);
                pointService.chargePoint(1L, 1L);
            }
        };
        Runnable runnable2 = () -> {
            for (int i = 0; i < 100; i++) {
                System.out.println( "# "+i);

                pointService.usePoint(1L, 1L);
            }
        };

        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        UserPoint userPoint = pointService.lookUpOneUserPointById(1L);
        System.out.println(userPoint); // 200
    }


}

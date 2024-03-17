package io.hhplus.tdd;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserPointTest {

    private PointService pointService;
    private UserPointTable userPointTable;
    private PointHistoryService pointHistoryService = Mockito.mock(PointHistoryService.class);

    @BeforeEach
    void setUp(){
        userPointTable = new UserPointTable();
        pointService = new PointService(userPointTable, pointHistoryService);
    }

    @Test
    @DisplayName("잔고가 없다면 포인트 사용을 실패한다.")
    void test1(){
        assertThrows(NotEnoughtPointException.class, () -> {
            pointService.usePoint(1L, 5L);
        });
    }

    @Test
    @DisplayName("잔고가 있다면 포인트를 사용한다")
    void test2(){

        UserPoint userPoint = pointService.chargePoint(1L, 10L);
        UserPoint resultUserPoint = pointService.usePoint(userPoint.getId(), 10L);
        assertEquals(0L,resultUserPoint.getPoint());
    }
}

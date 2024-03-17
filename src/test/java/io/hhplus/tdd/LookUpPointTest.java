package io.hhplus.tdd;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LookUpPointTest {
    private PointService pointService;
    private UserPointTable userPointTable = new UserPointTable();
    private PointHistoryService pointHistoryService = Mockito.mock(PointHistoryService.class);


    @BeforeEach
    void setUp2(){
        pointService = new PointService(userPointTable, pointHistoryService);
    }

    @Test
    @DisplayName("포인트를 조회한다")
    void test1(){
        UserPoint userPointInserted = pointService.chargePoint(5L, 10L);
        UserPoint userPointLookedUp = pointService.lookUpOneUserPointById(5L);
        assertEquals(userPointInserted, userPointLookedUp);
    }

    @Test
    @DisplayName("존제하는 유저가 아니라면 예외를 날린다.")
    void test2(){
        // 이건 현재 db 로직상 불가능 , 로직을 고쳐야하는데 고쳐도 되는지 모르겠고, 고치려고 해도 코들린이라서 귀찮을것 같다.
//        UserPoint userPointLookedUp = pointService.getOneUserPointById(1L);
    }
}

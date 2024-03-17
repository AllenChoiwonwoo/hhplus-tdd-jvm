package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.UserPoint;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Assignment1 {
//    private PointHistoryTable pointHistoryTable = new PointHistoryTable();
//    private UserPointTable userPointTable = new UserPointTable();
    private PointService pointService;
    private UserPointTable userPointTable = new UserPointTable();
    private UserPointTable mockUserPointTable = Mockito.mock(UserPointTable.class);

    @BeforeEach
    void setUp2(){
        pointService = new PointService(userPointTable);
    }


    @Test
    @DisplayName("포인트를 충전한다.")
    void chargePoint(){
        UserPoint userPoint = pointService.chargePoint(1, 10);
        assertEquals(1, userPoint.getId());
        assertEquals(10, userPoint.getPoint());
    }
    @Test
    @DisplayName("충전할 포일트가 0이면 충전하지 않는다.")
    void chargePointZero(){
        pointService.setUserPointTable(mockUserPointTable);

        BDDMockito.given(mockUserPointTable.selectById(Mockito.anyLong())).willReturn(new UserPoint(1L, 0L, System.currentTimeMillis()));
        pointService.chargePoint(1L, 0L);
        BDDMockito.then(mockUserPointTable)
                .should(Mockito.never())
                .insertOrUpdate(Mockito.anyLong(), Mockito.anyLong());

    }
}

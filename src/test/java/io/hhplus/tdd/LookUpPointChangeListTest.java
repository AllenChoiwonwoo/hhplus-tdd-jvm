package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LookUpPointChangeListTest {

    private PointService pointService;
    private PointHistoryService pointHistoryService;
    private UserPointTable userPointTable = new UserPointTable();
    private PointHistoryTable pointHistoryTable = new PointHistoryTable();

    @BeforeEach
    void setUp() {
        pointHistoryService = new PointHistoryService(pointHistoryTable);
        pointService = new PointService(userPointTable, pointHistoryService);
    }

    @Test
    @DisplayName("포인트 히스토리 넣기")
    void test1() {
        pointService.chargePoint(1L, 10L);

        PointHistory pointHistory = pointHistoryService.getPointUsageHistory(1L).stream().max(Comparator.comparingLong(PointHistory::getId)).orElseThrow();
        assertEquals(1L, pointHistory.getId());
    }

    @Test
    @DisplayName("포인트 히스토리 리스트 조회")
    void test2() {
        pointService.chargePoint(1L, 10L);
        pointService.usePoint(1L, 5L);
        pointService.chargePoint(1L, 10L);
        pointService.usePoint(1L, 5L);

        List<PointHistory> pointHistoryList = pointHistoryService.getPointUsageHistory(1L);
        assertEquals(4, pointHistoryList.size());
        assertEquals(10L, pointHistoryList.get(0).getAmount());
        assertEquals(5L, pointHistoryList.get(1).getAmount());
        assertEquals(10L, pointHistoryList.get(2).getAmount());
        assertEquals(5L, pointHistoryList.get(3).getAmount());
    }

}

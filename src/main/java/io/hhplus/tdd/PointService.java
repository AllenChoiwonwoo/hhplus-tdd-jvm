package io.hhplus.tdd;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    @Autowired
    private UserPointTable userPointTable;
    @Autowired
    private PointHistoryService pointHistoryService;
    public PointService(UserPointTable userPointTable, PointHistoryService pointHistoryService){
        this.userPointTable = userPointTable;
        this.pointHistoryService = pointHistoryService;
    }

    public void setUserPointTable(UserPointTable userPointTable) {
        this.userPointTable = userPointTable;
    }
    public void setPointHistoryService(PointHistoryService pointHistoryService){
        this.pointHistoryService = pointHistoryService;
    }

    public UserPoint chargePoint(long id, long point){
        UserPoint userPoint = userPointTable.selectById(id);
         if (point == 0L)
             return userPoint;
        long pointCharged = userPoint.getPoint() + point;
        UserPoint userPointCharged = userPointTable.insertOrUpdate(id, pointCharged);
        pointHistoryService.addHistory(TransactionType.CHARGE, userPointCharged);
        return userPointCharged;
    }

    public UserPoint lookUpOneUserPointById(long id) {
        return userPointTable.selectById(id);
    }

    public UserPoint usePoint(long id, Long point) {
        UserPoint userPoint = lookUpOneUserPointById(id);
        if (userPoint.getPoint() < point) throw new NotEnoughtPointException();
        UserPoint userPointUsed = userPointTable.insertOrUpdate(id, userPoint.getPoint() - point);
        pointHistoryService.addHistory(TransactionType.USE, userPointUsed);
        return userPointUsed;
    }
}

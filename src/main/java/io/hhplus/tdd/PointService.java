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



    public UserPoint lookUpOneUserPointById(long id) {
        return userPointTable.selectById(id);
    }
    public UserPoint chargePoint(long id, long point){
        return changePoint(id, point);

    }

    public UserPoint usePoint(long id, Long point) {
        return changePoint(id, point * -1L);
    }

    private synchronized UserPoint changePoint(Long id, Long point){
        UserPoint userPoint = lookUpOneUserPointById(id);
        if (point == 0L){
            return userPoint;
        }
        long calculatedPoint = userPoint.getPoint() + point;
        if (calculatedPoint < 0){
            throw new NotEnoughtPointException();
        }
        UserPoint userPointChanged = userPointTable.insertOrUpdate(id, calculatedPoint);
        TransactionType type = point > 0L ? TransactionType.CHARGE : TransactionType.USE;
        pointHistoryService.addHistory(type, userPointChanged);
        return userPointChanged;
    }
}

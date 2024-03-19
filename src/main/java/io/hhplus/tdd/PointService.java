package io.hhplus.tdd;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import kotlin.jvm.Synchronized;
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
        return changePoint(id, point, TransactionType.CHARGE);

    }

    public UserPoint usePoint(long id, long point) {
        return changePoint(id, point ,TransactionType.USE);
    }


    private synchronized UserPoint changePoint(Long id, Long point, TransactionType type){
        UserPoint userPoint = lookUpOneUserPointById(id);
        if (point == 0L){
            return userPoint; // todo : 충전/사용 할때 point가 0 이면 어떻게 해야하는지? 예외? 얼리 리턴?
        }
        long sign = type.equals(TransactionType.USE) ? -1L : 1L;
        long calculatedPoint = userPoint.getPoint() + point * sign;
        if (calculatedPoint < 0){
            throw new NotEnoughtPointException();
        }
        UserPoint userPointChanged = userPointTable.insertOrUpdate(id, calculatedPoint);
        pointHistoryService.addHistory(type, id, point);
        return userPointChanged;
    }
}

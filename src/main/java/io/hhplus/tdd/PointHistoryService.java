package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointHistoryService {
    private PointHistoryTable pointHistoryTable;

    public PointHistoryService(PointHistoryTable pointHistoryTable){
        this.pointHistoryTable = pointHistoryTable;
    }

    public Long addHistory(TransactionType type, UserPoint userPoint) {
        PointHistory pointHistory = pointHistoryTable.insert(userPoint.getId(), userPoint.getPoint(), type, userPoint.getUpdateMillis());
        return pointHistory.getId();

    }

    public List<PointHistory> getPointUsageHistory(long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }
}

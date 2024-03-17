package io.hhplus.tdd;

import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TempApplicationTest {

    private UserPointTable userPointTable = new UserPointTable();

    @Test
    void test1(){
        UserPoint userPoint = userPointTable.selectById(1);
        System.out.println(userPoint);
        System.out.println(userPointTable.getTable());
    }

    @Test
    @DisplayName("UserPointTable 에 포인트 정보 추가")
    void chargePoint(){
        UserPoint userPointInserted = userPointTable.insertOrUpdate(1, 10);
        UserPoint userPointSelected = userPointTable.selectById(1);

        assertEquals(1,userPointSelected.getId());
        assertEquals(10, userPointSelected.getPoint());

        UserPoint userPointSelected2 = userPointTable.insertOrUpdate(1,10);
        assertEquals(10L, userPointSelected2.getPoint());
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}

package com.example.aop.exam;

import com.example.aop.exam.annotation.Retry;
import com.example.aop.exam.annotation.Trace;
import org.springframework.stereotype.Repository;

@Repository
public class ExamRepository {
    private static int seq = 0;

    /**
     * 5번에 1번 실패하는 요청
     */
    @Retry(4)
    @Trace
    public String save(String itemId) {
        seq++;

        if (seq % 5 == 0) {
            throw new IllegalStateException("save 실패");
        }

        return "ok";
    }
}

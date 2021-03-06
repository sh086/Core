package com.module.utils;

import com.Application;
import com.common.utils.MySqlUtils;
import com.module.modal.SqlModal;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class UtilsTest {

    /**
     * 解析SQL中的Where过滤条件
     * */
    @Test
    public void resolveWhere(){
        String sql = "select* from (SELECT * from tc_goods_qualification where merchantCode = '1001' and sourceId =6 and verifyStatus =2 and startDate >'2019-02-28' and endDate >'2019-02-28') a";
        SqlModal vo = MySqlUtils.resolveWhere(sql, SqlModal.class);
        log.info("{}",vo);
    }
}

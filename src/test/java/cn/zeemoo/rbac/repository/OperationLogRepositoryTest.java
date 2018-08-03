package cn.zeemoo.rbac.repository;

import cn.zeemoo.rbac.domain.OperationLog;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OperationLogRepositoryTest {

    @Autowired
    private OperationLogRepository repository;

    @Test
    @Ignore
    public void findAllByUserId() throws Exception {
        Page<OperationLog> allByUserId = repository.findAllByUserId(4L, PageRequest.of(0, 10));
        System.out.println(allByUserId.getTotalElements());
        System.out.println(allByUserId.getTotalPages());
    }

}
package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.ydglExternalApi.entity.ExternalDepartment;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

/**
 * @Auther: hao
 * @Date: 2020/4/14 11:02
 * @Description:
 */
@Api(description = "公共接口")
@Slf4j
@RestController
@RequestMapping("/ydgl/cm")
public class CmMockController {

    Faker faker = Faker.instance(new Locale("zh", "CN"));

    @ApiOperation(value = "根据节点id获取所有下级组织机构树(当节点id为空时为root节点)", notes = "根据节点id获取所有下级组织机构树(当节点id为空时为root节点)")
    @GetMapping("/dept/loadAllDisDeptTreeByDeptId")
    public Rv loadAllDisDeptListByDeptId(String deptId) {
        List<ExternalDepartment> result = Lists.newArrayList();
        ExternalDepartment department = new ExternalDepartment();
        department.setDeptId(CheckUtil.strIsEmpty(deptId) ? faker.idNumber().toString() : deptId);
        department.setDeptName(faker.company().name());
        department.setParentDeptId(CheckUtil.strIsEmpty(deptId) ? "" : faker.idNumber().toString());
        ExternalDepartment child1 = new ExternalDepartment();
        child1.setParentDeptId(department.getDeptId());
        child1.setDeptName(faker.company().name());
        child1.setDeptId(faker.idNumber().toString());
        department.setChildren(child1);
        ExternalDepartment child2 = new ExternalDepartment();
        child2.setParentDeptId(child1.getDeptId());
        child2.setDeptName(faker.company().name());
        child2.setDeptId(faker.idNumber().toString());
        child1.setChildren(child2);
        result.add(department);
        return Rv.getInstance(result);
    }
}

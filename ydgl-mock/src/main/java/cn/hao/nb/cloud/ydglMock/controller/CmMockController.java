package cn.hao.nb.cloud.ydglMock.controller;

import cn.hao.nb.cloud.common.entity.Qd;
import cn.hao.nb.cloud.common.entity.Rv;
import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.IDUtil;
import cn.hao.nb.cloud.ydglExternalApi.entity.ExternalDepartment;
import cn.hao.nb.cloud.ydglMock.ElecDesc;
import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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

    @Autowired
    IDUtil idUtil;

    Faker faker = Faker.instance(new Locale("zh", "CN"));

    @ApiOperation(value = "根据节点id获取所有下级组织机构树(当节点id为空时为root节点)", notes = "根据节点id获取所有下级组织机构树(当节点id为空时为root节点)\n涉及实体:ExternalDepartment")
    @GetMapping("/dept/loadAllDisDeptTreeByDeptId")
    public Rv loadAllDisDeptListByDeptId(
            @ApiParam(value = "组织机构id", name = "deptId", required = false) @RequestParam(required = false) String deptId) {
        List<ExternalDepartment> result = Lists.newArrayList();
        ExternalDepartment department = new ExternalDepartment();
        department.setDeptId(CheckUtil.strIsEmpty(deptId) ? idUtil.nextId().toString() : deptId);
        department.setDeptName("公司名称");
        department.setParentDeptId(CheckUtil.strIsEmpty(deptId) ? "" : idUtil.nextId().toString());
        ExternalDepartment child1 = new ExternalDepartment();
        child1.setParentDeptId(department.getDeptId());
        child1.setDeptName("公司名称");
        child1.setDeptId(idUtil.nextId().toString());
        department.setChildren(child1);
        ExternalDepartment child2 = new ExternalDepartment();
        child2.setParentDeptId(child1.getDeptId());
        child2.setDeptName("公司名称");
        child2.setDeptId(idUtil.nextId().toString());
        child1.setChildren(child2);
        return Rv.getInstance(department);
    }

    @ApiOperation(value = "根据组织机构id获取设备列表", notes = "根据组织机构id获取设备列表")
    @GetMapping("/device/listByDeptId")
    public Rv listDeviceByDeptId(@ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId) {
        return Rv.getInstance(Lists.newArrayList(
                Qd.create().add("deviceId", "设备Id").add("deviceName", "设备名称"),
                Qd.create().add("deviceId", "设备Id").add("deviceName", "设备名称"),
                Qd.create().add("deviceId", "设备Id").add("deviceName", "设备名称"),
                Qd.create().add("deviceId", "设备Id").add("deviceName", "设备名称"),
                Qd.create().add("deviceId", "设备Id").add("deviceName", "设备名称")
        ));
    }

    @ApiOperation(value = "按天统计概览数据", notes = "按天统计概览数据")
    @GetMapping("/totalStatisticsByDate")
    public Rv totalStatisticsByDate(
            @ApiParam(value = "统计时间", name = "statisticsDate", required = true) @RequestParam Date statisticsDate,
            @ApiParam(value = "组织机构Id", name = "deptId", required = true) @RequestParam String deptId
    ) {
        return Rv.getInstance(
                Qd.create()
                        .add("todayConsumption", ElecDesc.kWhDesc("今日"))
                        .add("yesterdayConsumption", ElecDesc.kWhDesc("昨日"))
                        .add("currentLoad", ElecDesc.kWDesc("当前"))
                        .add("currentLoadCollectionTime", ElecDesc.timeDesc("当前负荷"))
                        .add("todayMaxLoad", ElecDesc.kWDesc("今日最高"))
                        .add("todayMaxLoadCollectionTime", ElecDesc.timeDesc("今日最高"))
                        .add("todayMinLoad", ElecDesc.kWDesc("今日最低"))
                        .add("todayMinLoadCollectionTime", ElecDesc.timeDesc("今日最低"))

        );
    }

}

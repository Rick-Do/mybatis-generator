package com.dx.generator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dx.generator.enums.ConfigTypeEnum;
import com.dx.generator.model.DataConfigModel;
import com.dx.generator.model.ResultVO;
import com.dx.generator.query.DataConfigQuery;
import com.dx.generator.service.DataConfigModelService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dongy
 * @version 1.0
 * @date 2023/3/28 16:53:55
 * @since jdk1.8_202
 */
@RestController
@RequestMapping("api")
public class DataConfigModelController {

    @Resource
    private DataConfigModelService configService;



    /**
     * 添加信息
     * @param configModel 要添加的信息
     * @return  添加成功后的信息
     */
    @PostMapping("add")
    public ResultVO<DataConfigModel> add(@RequestBody DataConfigModel configModel){
        DataConfigModel block = configService.save(configModel);
        return ResultVO.success(block);
    }

    /**
     * 更新信息
     * @param configModel 更新之后的信息
     * @return 更新后的信息
     */
    @PostMapping("edit")
    public ResultVO<DataConfigModel> edit(@RequestBody DataConfigModel configModel) {
        return ResultVO.success(configService.update(configModel));
    }

    /**
     * 根据主键删除信息
     * @param id 主键
     * @return 已经删除的信息
     */
    @DeleteMapping("deleteById/{configType}/{id}")
    public ResultVO<DataConfigModel> deleteById(@PathVariable("id") Integer id,@PathVariable("configType") ConfigTypeEnum configType){
        return ResultVO.success(configService.delete(id, configType));
    }

    /**
     * 根据主键查询信息
     * @param id 主键
     * @return 配置详情
     */
    @GetMapping("selectById/{configType}/{id}")
    public ResultVO<DataConfigModel> selectById(@PathVariable("id") Integer id, @PathVariable("configType") ConfigTypeEnum configType) {
        return ResultVO.success(configService.selectById(id, configType));
    }


    @PostMapping("selectPage/{pageIndex}/{pageSize}")
    public ResultVO<Page<DataConfigModel>> selectPage(@PathVariable("pageIndex") Integer pageIndex,
                                                      @PathVariable("pageSize") Integer pageSize,
                                                      @RequestBody DataConfigQuery query){
        Page<DataConfigModel> modelPage = configService.selectPage(pageIndex, pageSize, query);
        return ResultVO.success(modelPage);
    }

    @PostMapping("selectList")
    public ResultVO<List<DataConfigModel>> selectList(@RequestBody DataConfigQuery query) {
        List<DataConfigModel> list = configService.selectList(query);
        return ResultVO.success(list);
    }
}

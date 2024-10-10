package com.dx.generator.controller;

import com.dx.generator.annotation.RequireDataId;
import com.dx.generator.model.DataBaseModel;
import com.dx.generator.model.ResultVO;
import com.dx.generator.model.TableModel;
import com.dx.generator.query.DataBaseQuery;
import com.dx.generator.service.DataBaseService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据库控制器
 */
@RestController
@RequestMapping("database")
public class DataSourceController {

    @Resource
    private DataBaseService dataBaseService;


    /**
     * 获取数据实例所拥有的数据库
     * @return 数据库信息
     */
    @PostMapping("getDataBaseList")
    public ResultVO<List<DataBaseModel>> getDataBaseList(@RequestBody DataBaseQuery query){
        List<DataBaseModel> dataList = dataBaseService.getDataBaseList(query);
        return ResultVO.success(dataList);
    }

    @GetMapping("getTableList/{catId}")
    @RequireDataId
    public ResultVO<List<TableModel>> getTableList(@PathVariable("catId") String catId){
        List<TableModel> list = dataBaseService.getTableList(catId);
        return ResultVO.success(list);
    }

    @GetMapping("/generateCode/{catId}/{templateId}/{table}")
    public void generateCode(@PathVariable("catId") String catId,
                             @PathVariable("table") String tableName,
                             @PathVariable("templateId") Integer templateId,
                             HttpServletResponse response) throws Exception {
        dataBaseService.generateCode(catId, tableName, templateId, response.getOutputStream());
    }
}

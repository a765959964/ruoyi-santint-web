package com.ruoyi.web.controller.santint;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.santint.domain.Foodtable;
import com.ruoyi.santint.service.IFoodtableService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 菜品Controller
 * zf111
 * @author zf
 * @date 2019-08-19
 */
@Controller
@RequestMapping("/santint/foodtable")
public class FoodtableController extends BaseController {
    private String prefix = "santint/foodtable";

    @Autowired
    private IFoodtableService foodtableService;

    @RequiresPermissions("santint:foodtable:view")
    @GetMapping()
    public String foodtable() {
        return prefix + "/foodtable";
    }

    /**
     * 查询菜品列表
     */
//    @RequiresPermissions("santint:foodtable:list")
//    @PostMapping("/list")
//    @ResponseBody
//    public TableDataInfo list(Foodtable foodtable)
//    {
//        startPage();
//        List<Foodtable> list = foodtableService.selectFoodtableList(foodtable);
//        return getDataTable(list);
//    }
    @RequiresPermissions("santint:foodtable:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Map map, HttpServletRequest req) {
        startPage();

        selWhere(map, req);

        List list = foodtableService.getFoodtableList(map);
        return getDataTable(list);
    }

    /**
     * 查询导出  公共条件
     *
     * @param map
     * @param req
     * @return
     */
    private Map selWhere(Map map, HttpServletRequest req) {
        String foodtypeField = req.getParameter("foodtypeField");
        String foodname = req.getParameter("foodname");
        String beginTime = req.getParameter("beginTime");
        String endTime = req.getParameter("endTime");


        if (foodname != null && foodname != "") {
            map.put("foodname" , foodname);
        }

        if (foodtypeField != null && foodtypeField != "") {
            map.put("foodtypeField" , foodtypeField);
        }

        if (beginTime != null && beginTime != "") {
            map.put("beginTime" , beginTime);
        }

        if (endTime != null && endTime != "") {
            map.put("endTime" , endTime);
        }
        return map;
    }


    @RequiresPermissions("santint:foodtable:exports")
    @PostMapping("/exports")
    @ResponseBody
    public AjaxResult exports(Foodtable foodtable) {
        List<Foodtable> list = foodtableService.selectFoodtableList(foodtable);
        ExcelUtil<Foodtable> util = new ExcelUtil<Foodtable>(Foodtable.class);
        return util.exportExcel(list, "foodtable");
    }

    /**
     * 新增菜品
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存菜品
     */
    @RequiresPermissions("santint:foodtable:add")
    @Log(title = "菜品" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Foodtable foodtable) {
        return toAjax(foodtableService.insertFoodtable(foodtable));
    }

    /**
     * 修改菜品
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Foodtable foodtable = foodtableService.selectFoodtableById(id);
        mmap.put("foodtable" , foodtable);
        return prefix + "/edit";
    }

    /**
     * 修改保存菜品
     */
    @RequiresPermissions("santint:foodtable:edit")
    @Log(title = "菜品" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Foodtable foodtable) {
        return toAjax(foodtableService.updateFoodtable(foodtable));
    }

    /**
     * 删除菜品
     */
    @RequiresPermissions("santint:foodtable:remove")
    @Log(title = "菜品" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(foodtableService.deleteFoodtableByIds(ids));
    }
}

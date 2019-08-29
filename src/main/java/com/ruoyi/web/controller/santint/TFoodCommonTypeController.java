package com.ruoyi.web.controller.santint;

import java.util.List;

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
import com.ruoyi.santint.domain.TFoodCommonType;
import com.ruoyi.santint.service.ITFoodCommonTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.domain.Ztree;

/**
 * 菜系食材味型分类Controller
 *
 * @author ruoyi
 * @date 2019-08-19
 */
@Controller
@RequestMapping("/santint/foodCommonType")
public class TFoodCommonTypeController extends BaseController {
    private String prefix = "santint/foodCommonType";

    @Autowired
    private ITFoodCommonTypeService tFoodCommonTypeService;

    @RequiresPermissions("santint:foodCommonType:view")
    @GetMapping()
    public String foodCommonType() {
        return prefix + "/foodCommonType";
    }

    /**
     * 查询菜系食材味型分类树列表
     */
    @RequiresPermissions("santint:foodCommonType:list")
    @PostMapping("/list")
    @ResponseBody
    public List<TFoodCommonType> list(TFoodCommonType tFoodCommonType) {
        if (tFoodCommonType.getStatus() == null) {
            tFoodCommonType.setStatus(1l);
        }
        List<TFoodCommonType> list = tFoodCommonTypeService.selectTFoodCommonTypeList(tFoodCommonType);
        return list;
    }

    /**
     * 导出菜系食材味型分类列表
     */
    @RequiresPermissions("santint:foodCommonType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TFoodCommonType tFoodCommonType) {
        List<TFoodCommonType> list = tFoodCommonTypeService.selectTFoodCommonTypeList(tFoodCommonType);
        ExcelUtil<TFoodCommonType> util = new ExcelUtil<TFoodCommonType>(TFoodCommonType.class);
        return util.exportExcel(list, "foodCommonType");
    }

    /**
     * 新增菜系食材味型分类
     */
    @GetMapping(value = {"/add/{id}" , "/add/"})
    public String add(@PathVariable(value = "id" , required = false) Long id, ModelMap mmap) {
        if (StringUtils.isNotNull(id)) {
            mmap.put("tFoodCommonType" , tFoodCommonTypeService.selectTFoodCommonTypeById(id));
        }
        return prefix + "/add";
    }

    /**
     * 新增保存菜系食材味型分类
     */
    @RequiresPermissions("santint:foodCommonType:add")
    @Log(title = "菜系食材味型分类" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TFoodCommonType tFoodCommonType) {
        return toAjax(tFoodCommonTypeService.insertTFoodCommonType(tFoodCommonType));
    }

    /**
     * 修改菜系食材味型分类
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        TFoodCommonType tFoodCommonType = tFoodCommonTypeService.selectTFoodCommonTypeById(id);
        mmap.put("tFoodCommonType" , tFoodCommonType);
        return prefix + "/edit";
    }

    /**
     * 修改保存菜系食材味型分类
     */
    @RequiresPermissions("santint:foodCommonType:edit")
    @Log(title = "菜系食材味型分类" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TFoodCommonType tFoodCommonType) {
        return toAjax(tFoodCommonTypeService.updateTFoodCommonType(tFoodCommonType));
    }

    /**
     * 删除
     */
    @RequiresPermissions("santint:foodCommonType:remove")
    @Log(title = "菜系食材味型分类" , businessType = BusinessType.DELETE)
    @GetMapping("/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("id") Long id) {
        return toAjax(tFoodCommonTypeService.deleteTFoodCommonTypeById(id));
    }

    /**
     * 选择菜系食材味型分类树
     */
    @GetMapping(value = {"/selectFoodCommonTypeTree/{id}" , "/selectFoodCommonTypeTree/"})
    public String selectFoodCommonTypeTree(@PathVariable(value = "id" , required = false) Long id, ModelMap mmap) {
        if (StringUtils.isNotNull(id)) {
            mmap.put("tFoodCommonType" , tFoodCommonTypeService.selectTFoodCommonTypeById(id));
        }
        return prefix + "/tree";
    }

    /**
     * 加载菜系食材味型分类树列表
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = tFoodCommonTypeService.selectTFoodCommonTypeTree();
        return ztrees;
    }
}

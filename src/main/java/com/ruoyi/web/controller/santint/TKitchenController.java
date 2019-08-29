package com.ruoyi.web.controller.santint;

import java.util.List;

import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
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
import com.ruoyi.santint.domain.TKitchen;
import com.ruoyi.santint.service.ITKitchenService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 门店管理Controller
 *
 * @author ruoyi
 * @date 2019-08-27
 */
@Controller
@RequestMapping("/santint/kitchen")
public class TKitchenController extends BaseController {
    private String prefix = "santint/kitchen";

    @Autowired
    private ITKitchenService tKitchenService;

    @RequiresPermissions("santint:kitchen:view")
    @GetMapping()
    public String kitchen() {
        return prefix + "/kitchen";
    }

    /**
     * 查询门店管理列表
     */
    @RequiresPermissions("santint:kitchen:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TKitchen tKitchen) {
        startPage();
        List<TKitchen> list = tKitchenService.selectTKitchenList(tKitchen);
        return getDataTable(list);
    }

    @Log(title = "门店管理" , businessType = BusinessType.IMPORT)
    @RequiresPermissions("santint:kitchen:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<TKitchen> util = new ExcelUtil<TKitchen>(TKitchen.class);
        List<TKitchen> kitchenList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = tKitchenService.importKitchen(kitchenList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("santint:kitchen:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<TKitchen> util = new ExcelUtil<TKitchen>(TKitchen.class);
        return util.importTemplateExcel("门店数据");
    }


    /**
     * 导出门店管理列表
     */
    @RequiresPermissions("santint:kitchen:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TKitchen tKitchen) {
        List<TKitchen> list = tKitchenService.selectTKitchenList(tKitchen);
        ExcelUtil<TKitchen> util = new ExcelUtil<TKitchen>(TKitchen.class);
        return util.exportExcel(list, "kitchen");
    }

    /**
     * 新增门店管理
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存门店管理
     */
    @RequiresPermissions("santint:kitchen:add")
    @Log(title = "门店管理" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TKitchen tKitchen) {
        return toAjax(tKitchenService.insertTKitchen(tKitchen));
    }

    /**
     * 修改门店管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        TKitchen tKitchen = tKitchenService.selectTKitchenById(id);
        mmap.put("tKitchen" , tKitchen);
        return prefix + "/edit";
    }

    /**
     * 修改保存门店管理
     */
    @RequiresPermissions("santint:kitchen:edit")
    @Log(title = "门店管理" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TKitchen tKitchen) {
        return toAjax(tKitchenService.updateTKitchen(tKitchen));
    }

    /**
     * 删除门店管理
     */
    @RequiresPermissions("santint:kitchen:remove")
    @Log(title = "门店管理" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tKitchenService.deleteTKitchenByIds(ids));
    }

    @Log(title = "门店管理" , businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(Long id, Long status) {
        TKitchen tKitchen = tKitchenService.selectTKitchenById(id);
        tKitchen.setStatus(status);
        return toAjax(tKitchenService.updateTKitchen(tKitchen));
    }


    /**
     * 校验门店名称
     */
    @PostMapping("/checkKitNameUnique")
    @ResponseBody
    public String checkKitNameUnique(TKitchen kitchen) {
        return tKitchenService.checkKitNameUnique(kitchen.getName()) + "";
    }

    @PostMapping("/checkTkitchenUnique")
    @ResponseBody
    public String checkTkitchenUnique(TKitchen kitchen) {
        return tKitchenService.checkTkitchenUnique(kitchen);
    }


}

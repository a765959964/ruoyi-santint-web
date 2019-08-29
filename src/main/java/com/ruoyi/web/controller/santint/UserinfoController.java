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
import com.ruoyi.santint.domain.Userinfo;
import com.ruoyi.santint.service.IUserinfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个人资料Controller
 *
 * @author ruoyi
 * @date 2019-08-19
 */
@Controller
@RequestMapping("/santint/userinfo")
public class UserinfoController extends BaseController {
    private String prefix = "santint/userinfo";

    @Autowired
    private IUserinfoService userinfoService;

    @RequiresPermissions("santint:userinfo:view")
    @GetMapping()
    public String userinfo() {
        return prefix + "/userinfo";
    }

    /**
     * 查询个人资料列表
     */
    @RequiresPermissions("santint:userinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Userinfo userinfo) {
        startPage();
        List<Userinfo> list = userinfoService.selectUserinfoList(userinfo);
        return getDataTable(list);
    }

    /**
     * 导出个人资料列表
     */
    @RequiresPermissions("santint:userinfo:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Userinfo userinfo) {
        List<Userinfo> list = userinfoService.selectUserinfoList(userinfo);
        ExcelUtil<Userinfo> util = new ExcelUtil<Userinfo>(Userinfo.class);
        return util.exportExcel(list, "userinfo");
    }

    /**
     * 新增个人资料
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存个人资料
     */
    @RequiresPermissions("santint:userinfo:add")
    @Log(title = "个人资料" , businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Userinfo userinfo) {
        return toAjax(userinfoService.insertUserinfo(userinfo));
    }

    /**
     * 修改个人资料
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Userinfo userinfo = userinfoService.selectUserinfoById(id);
        mmap.put("userinfo" , userinfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存个人资料
     */
    @RequiresPermissions("santint:userinfo:edit")
    @Log(title = "个人资料" , businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Userinfo userinfo) {
        return toAjax(userinfoService.updateUserinfo(userinfo));
    }

    /**
     * 删除个人资料
     */
    @RequiresPermissions("santint:userinfo:remove")
    @Log(title = "个人资料" , businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(userinfoService.deleteUserinfoByIds(ids));
    }
}

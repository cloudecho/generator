package ${controllerPackage};

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zheng.common.base.MyBaseController;
import com.zheng.common.base.MyBaseService;
import ${modelJavaType};
import ${modelJavaType}Dto;
import ${servicePackage}.${modelJavaName}Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "${tableRemark}", description = "${tableDescription}")
@RequestMapping("/${simpleModuleName}/${simpleEntityName}")
public class ${modelJavaName}Controller extends MyBaseController<${modelJavaName}, ${idJavaType}> {

    private static final Logger LOGGER = LoggerFactory.getLogger(${modelJavaName}Controller.class);

    @Reference(mock = "true")
    private ${modelJavaName}Service ${modelJavaNameFirstLower}Service;

    @Override
    protected MyBaseService<${modelJavaName}, ${idJavaType}> myBaseService() {
        return ${modelJavaNameFirstLower}Service;
    }

    @Override
    protected ${modelJavaName}Dto toCondition(String search) {
        return new ${modelJavaName}Dto();
    }

    @Override
    protected ${idJavaType} convertId(String id) {
        return ${idJavaType}.valueOf(id);
    }

    @Override
    protected void setId(${idJavaType} id, ${modelJavaName} entityDto) {
        entityDto.set${idFieldFirstUpper}(id);
    }

    @ApiOperation(value = "${tableRemark}首页")
    @RequiresPermissions("${simpleModuleName}:${simpleEntityName}:read")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @Override
    public String index() {
        return super.index();
    }

    @ApiOperation(value = "${tableRemark}列表")
    @RequiresPermissions("${simpleModuleName}:${simpleEntityName}:read")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int pageNum,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, defaultValue = "asc", value = "order") String order) {
        return super.list(pageNum, limit, search, sort, order);
    }

    @ApiOperation(value = "新增${tableRemark}")
    @RequiresPermissions("${simpleModuleName}:${simpleEntityName}:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @Override
    public String create() {
        return super.create();
    }

    @ApiOperation(value = "新增${tableRemark}")
    @RequiresPermissions("${simpleModuleName}:${simpleEntityName}:create")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Override
    public Object create(${modelJavaName} ${modelJavaNameFirstLower}) {
        return super.create(${modelJavaNameFirstLower});
    }

    @ApiOperation(value = "删除${tableRemark}")
    @RequiresPermissions("${simpleModuleName}:${simpleEntityName}:delete")
    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Object delete(@PathVariable("ids") String ids) {
        return super.delete(ids);
    }

    @ApiOperation(value = "修改${tableRemark}")
    @RequiresPermissions("${simpleModuleName}:${simpleEntityName}:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @Override
    public String update(@PathVariable("id") Integer id, ModelMap modelMap) {
        return super.update(id, modelMap);
    }

    @ApiOperation(value = "修改${tableRemark}")
    @RequiresPermissions("${simpleModuleName}:${simpleEntityName}:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public Object update(@PathVariable("id") Integer id, ${modelJavaName} ${modelJavaNameFirstLower}) {
        return super.update(id, ${modelJavaNameFirstLower});
    }
}

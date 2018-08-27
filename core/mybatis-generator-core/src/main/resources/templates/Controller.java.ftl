package ${controllerPackage};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.dubbo.config.annotation.Reference;
import gallantry.core.web.BaseFormController;
import gallantry.core.service.BaseService;
import ${modelJavaType};
import ${servicePackage}.${domainObjectName}Service;

@Controller
@RequestMapping("/${simpleModuleName}/${simpleEntityName}")
public class ${domainObjectName}Controller extends BaseController<${modelJavaName}, ${idJavaType}> {

    private static final Logger LOGGER = LoggerFactory.getLogger(${domainObjectName}Controller.class);

    @Reference
    private ${domainObjectName}Service ${domainObjectNameFirstLower}Service;

    @Override
    protected BaseService<${modelJavaName}, ${idJavaType}> getBusiService() {
        return ${domainObjectNameFirstLower}Service;
    }

    @Override
    protected Class<${modelJavaName}> getModelType() {
        return ${modelJavaName}.class;
    }
}

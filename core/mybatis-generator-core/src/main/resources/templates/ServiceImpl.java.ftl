package ${serviceImplPackage};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.stereotype.Service;

import gallantry.core.service.AbstractBaseService;
import ${modelJavaType};
import ${repositoryJavaType};

@Service
@Lazy
public class ${domainObjectName}ServiceImpl extends AbstractBaseService<${modelJavaName}, ${idJavaType}>
        implements ${domainObjectName}Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(${modelJavaName}ServiceImpl.class);

    @Autowired
    private ${domainObjectName}Repository ${domainObjectNameFirstLower}Repository;

    @Override
    public MybatisRepository<${modelJavaName}, ${idJavaType}> repository() {
        return ${domainObjectNameFirstLower}Repository;
    }
}

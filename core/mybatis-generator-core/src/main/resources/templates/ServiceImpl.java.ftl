package ${serviceImplPackage};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.stereotype.Service;

import com.zheng.common.base.MyBaseServiceImpl;
import ${modelJavaType};
import ${repositoryJavaType};

@Service
@Lazy
public class ${modelJavaName}ServiceImpl
        extends MyBaseServiceImpl<${modelJavaName}, ${idJavaType}>
        implements ${servicePackage}.${modelJavaName}Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(${modelJavaName}ServiceImpl.class);

    @Autowired
    private ${modelJavaName}Repository ${modelJavaNameFirstLower}Repository;

    @Override
    public MybatisRepository<${modelJavaName}, ${idJavaType}> repository() {
        return ${modelJavaNameFirstLower}Repository;
    }
}
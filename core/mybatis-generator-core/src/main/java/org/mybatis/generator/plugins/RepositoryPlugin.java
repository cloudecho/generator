/**
 * Copyright 2006-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.generator.plugins;

import java.util.Arrays;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * This plugin generates XxxRepository.java for spring-data-mybatis.
 *
 * @author yong.ma
 */
public class RepositoryPlugin extends PluginAdapter {
    static final String REPOSITORY_SUFFIX = "Repository";

    public boolean validate(List<String> warnings) {
        // this plugin is always valid
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
        IntrospectedTable introspectedTable) {
        GeneratedJavaFile gjf = new GeneratedJavaFile(
            compilationUnit(introspectedTable),
            context.getJavaModelGeneratorConfiguration().getTargetProject(),
            context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
            context.getJavaFormatter());

        return Arrays.asList(gjf);
    }

    static String fullTypeSpecification(IntrospectedTable introspectedTable) {
        return introspectedTable.getBaseRecordType()
            .replaceAll("\\.(domain|model)", "." + REPOSITORY_SUFFIX.toLowerCase())
            + REPOSITORY_SUFFIX;
    }

    private CompilationUnit compilationUnit(IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType type =
            new FullyQualifiedJavaType(fullTypeSpecification(introspectedTable));
        Interface repoInterface = new Interface(type);
        repoInterface.setVisibility(JavaVisibility.PUBLIC);

        // TODO composite primary key
        FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(
            "MybatisRepository<"
                + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + ", "
                + idType(introspectedTable) + ">");
        repoInterface.addSuperInterface(superInterface);
        repoInterface.addImportedType(new FullyQualifiedJavaType(
            "org.springframework.data.mybatis.repository.support.MybatisRepository"));
        repoInterface
            .addImportedType(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        return repoInterface;
    }

    private Object idType(IntrospectedTable introspectedTable) {
        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
            return introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "Key";
        } else {
            return introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType();
        }
    }
}

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * This plugin generates Service & Controller (SpringMVC) java files.
 *
 * @author yong.ma
 */
public class ServiceAndControllerPlugin extends AbstractTextPlugin {
    static final String CONTROLLER_SUFFIX = "Controller";
    static final String SERVICE_SUFFIX = "Service";
    static final String IMPL_PKG_SUFFIX = ".impl";


    @Override
    protected List<String> getTemplateNames() {
        return Arrays.asList(
            "Service.java.ftl",
            "ServiceImpl.java.ftl",
            "ServiceMock.java.ftl",
            "Controller.java.ftl"
        );
    }

    @Override
    protected Map<String, Object> getTemplateData(IntrospectedTable introspectedTable) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("servicePackage", getTargetPackage(SERVICE_SUFFIX, introspectedTable));
        map.put("serviceImplPackage",
            getTargetPackage(SERVICE_SUFFIX, introspectedTable) + IMPL_PKG_SUFFIX);
        map.put("domainObjectName", domainObjectName(introspectedTable));
        map.put("domainObjectNameFirstLower",
            StringUtility.firstLowerCase(domainObjectName(introspectedTable)));
        map.put("modelJavaType", modelJavaType(introspectedTable));
        map.put("modelJavaName", modelJavaName(introspectedTable));
        map.put("modelJavaNameFirstLower",
            StringUtility.firstLowerCase(modelJavaName(introspectedTable)));
        map.put("idJavaType", idJavaType(introspectedTable));
        map.put("repositoryJavaType", repositoryJavaType(introspectedTable));

        map.put("controllerPackage", getTargetPackage(CONTROLLER_SUFFIX, introspectedTable));
        map.put("tableRemark", remark(introspectedTable));
        map.put("tableDescription", description(introspectedTable));
        map.put("idFieldFirstUpper", StringUtility
            .firstUpperCase(introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty()));
        map.put("simpleEntityName", simpleEntityName(introspectedTable));
        map.put("simpleModuleName", simpleModuleName(introspectedTable));

        return map;
    }

    @Override
    protected String getFileName(String ftl, IntrospectedTable introspectedTable) {
        return domainObjectName(introspectedTable) + ftl.replaceAll("\\.ftl$", "");
    }

    @Override
    protected String getTargetPackage(String ftl, IntrospectedTable introspectedTable) {
        String replacement = ftl.contains(CONTROLLER_SUFFIX) ?
            CONTROLLER_SUFFIX.toLowerCase() : ftl.contains("Impl") ?
            SERVICE_SUFFIX.toLowerCase() + IMPL_PKG_SUFFIX : SERVICE_SUFFIX.toLowerCase();
        return context.getJavaModelGeneratorConfiguration().getTargetPackage()
            .replaceAll("\\.(domain|model)", "." + replacement);
    }

    private String domainObjectName(IntrospectedTable introspectedTable) {
        return introspectedTable.getFullyQualifiedTable().getDomainObjectName();
    }

    private String modelJavaType(IntrospectedTable introspectedTable) {
        return introspectedTable.getBaseRecordType() + "Dto";
    }

    private String modelJavaName(IntrospectedTable introspectedTable) {
        return domainObjectName(introspectedTable) + "Dto";
    }

    private String idJavaType(IntrospectedTable introspectedTable) {
        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
            return domainObjectName(introspectedTable) + "Key";
        } else {
            return introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType()
                .getShortName();
        }
    }

    private String repositoryJavaType(IntrospectedTable introspectedTable) {
        return RepositoryPlugin.fullTypeSpecification(introspectedTable);
    }

    private String remark(IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        if (!StringUtility.stringHasValue(remarks)) {
            remarks = simpleEntityName(introspectedTable);
        }
        return remarks.split("\n")[0].toUpperCase();
    }

    private String description(IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        if (!StringUtility.stringHasValue(remarks)) {
            remarks = simpleEntityName(introspectedTable).toUpperCase();
        }
        return remarks;
    }

    private String simpleEntityName(IntrospectedTable introspectedTable) {
        String remarks = String.valueOf(introspectedTable.getFullyQualifiedTable());
        int k = remarks.indexOf('_');
        return (k > 0 ? remarks.substring(k + 1) : remarks).toLowerCase();
    }

    private String simpleModuleName(IntrospectedTable introspectedTable) {
        String remarks = String.valueOf(introspectedTable.getFullyQualifiedTable());
        int k = remarks.indexOf('_');
        return (k > 0 ? remarks.substring(0, k) : remarks).toLowerCase();
    }
}

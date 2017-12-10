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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * This plugin generates CRUD html.
 *
 * @author yong.ma
 */
public class CrudHtmlPlugin extends AbstractTextPlugin {

    @Override
    protected List<String> getTemplateNames() {
        return Arrays.asList("index.html.ftl", "create.html.ftl", "update.html.ftl");
    }

    @Override
    protected Map<String, Object> getTemplateData(IntrospectedTable introspectedTable) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableRemark", remark(introspectedTable));
        map.put("idField", introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty());
        map.put("simpleEntityName", simpleEntityName(introspectedTable));
        map.put("columns", columnInfos(introspectedTable));

        return map;
    }

    @Override
    protected String getFileName(String ftl, IntrospectedTable introspectedTable) {
        return ftl.replaceAll("\\.ftl$", "");
    }

    protected String getTargetPackage(String ftl, IntrospectedTable introspectedTable) {
        return context.getJavaModelGeneratorConfiguration().getTargetPackage()
                .replaceAll("\\.(domain|model)", ".html")
                + "." + simpleEntityName(introspectedTable);
    }

    private String remark(IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        if (!StringUtility.stringHasValue(remarks)) {
            remarks = simpleEntityName(introspectedTable);
        }
        return remarks.split("\n")[0].toUpperCase();
    }

    private String remark(IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if (!StringUtility.stringHasValue(remarks)) {
            remarks = introspectedColumn.getActualColumnName();
        }
        return remarks.split("\n")[0];
    }

    private String simpleEntityName(IntrospectedTable introspectedTable) {
        String[] remarks = String.valueOf(introspectedTable.getFullyQualifiedTable()).split("_");
        return remarks[remarks.length - 1].toLowerCase();
    }


    private List<ColumnInfo> columnInfos(IntrospectedTable introspectedTable) {
        List<ColumnInfo> result = new ArrayList<ColumnInfo>();
        List<IntrospectedColumn> primaryKeyColumns = introspectedTable.getPrimaryKeyColumns();
        for (IntrospectedColumn column : introspectedTable.getAllColumns()) {
            result.add(new ColumnInfo(column.getJavaProperty(), remark(column), primaryKeyColumns.contains(column)));
        }
        return result;
    }


    public static class ColumnInfo {
        final String name;
        final String remark;
        final boolean pk;

        public ColumnInfo(String name, String remark, boolean pk) {
            this.name = name;
            this.remark = remark;
            this.pk = pk;
        }

        public String getName() {
            return name;
        }

        public String getRemark() {
            return remark;
        }

        public boolean isPk() {
            return pk;
        }
    }
}

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
package org.mybatis.generator.internal;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * The Class MyCommentGenerator which extends from {@link DefaultCommentGenerator}.
 *
 * @author yong.ma
 */
public class MyCommentGenerator extends DefaultCommentGenerator implements CommentGenerator {
    public MyCommentGenerator() {
        super();
        addRemarkComments = true;
    }

    private String remarks(IntrospectedTable introspectedTable) {
        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && !StringUtility.stringHasValue(remarks)) {
            remarks = String.valueOf(introspectedTable.getFullyQualifiedTable());
        }
        return remarks;
    }


    private String remarks(IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if (addRemarkComments && !StringUtility.stringHasValue(remarks)) {
            remarks = introspectedColumn.getActualColumnName();
        }
        return remarks;
    }

    private void addJavaElementComment(JavaElement element, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        element.addJavaDocLine("/**");
        for (String remarkLine : remarks(introspectedColumn).split("\n")) {
            element.addJavaDocLine(" * " + remarkLine);
        }
        addJavadocTag(element, false);
        element.addJavaDocLine(" */");
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }

        topLevelClass.addJavaDocLine("/**");
        for (String remarkLine : remarks(introspectedTable).split("\n")) {
            topLevelClass.addJavaDocLine(" * " + remarkLine);
        }
        addJavadocTag(topLevelClass, true);
        topLevelClass.addJavaDocLine(" */");

        //Annotation
        topLevelClass.addImportedType("org.springframework.data.mybatis.annotations.*");
        topLevelClass.addAnnotation("@Entity(table = \"" + introspectedTable.getFullyQualifiedTable() + "\")");
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        this.addJavaElementComment(field, introspectedColumn);
        field.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
    }

    public void addGeneralMethodComment(Method method,
            IntrospectedTable introspectedTable) {
        method.addJavaDocLine("/**");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        this.addJavaElementComment(method, introspectedColumn);
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        this.addJavaElementComment(method, introspectedColumn);
    }
}

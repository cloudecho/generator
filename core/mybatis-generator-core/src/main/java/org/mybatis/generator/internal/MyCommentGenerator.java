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
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * The Class MyCommentGenerator which extends from {@link DefaultCommentGenerator}, for spring-data-mybatis.
 *
 * @author yong.ma
 */
public class MyCommentGenerator extends DefaultCommentGenerator implements CommentGenerator {
    static enum ReservedColumn {
        Version,
        CreatedDate,
        LastModifiedDate,
        CreatedBy,
        LastModifiedBy
    }

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
    public void addModelClassComment(TopLevelClass topLevelClass,
        IntrospectedTable introspectedTable) {
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
        topLevelClass.addImportedType("org.springframework.data.annotation.*");
        topLevelClass.addImportedType("org.springframework.data.mybatis.annotations.Column");
        topLevelClass.addImportedType("org.springframework.data.mybatis.annotations.Entity");
        topLevelClass.addImportedType("org.springframework.data.mybatis.annotations.Id");
        topLevelClass
            .addStaticImport("org.springframework.data.mybatis.annotations.Id.GenerationType.AUTO");
        topLevelClass.addAnnotation(
            "@Entity(table = \"" + introspectedTable.getFullyQualifiedTable() + "\")");

        if (isCompositeKey(introspectedTable)) {
            //keyClass
            final FullyQualifiedJavaType keyClass =
                new FullyQualifiedJavaType(topLevelClass.getType().getShortName() + "Key");
            //keyField
            Field keyField =
                new Field(StringUtility.firstLowerCase(keyClass.getShortName()), keyClass);
            keyField.setVisibility(JavaVisibility.PRIVATE);
            keyField.addAnnotation("@Id(strategy = AUTO, composite = true)");
            keyField.setInitializationString("new " + keyClass + "()");
            topLevelClass.addField(keyField);
            //key getter
            Method keyGetter = new Method("get" + keyClass);
            keyGetter.setReturnType(keyClass);
            keyGetter.setVisibility(JavaVisibility.PUBLIC);
            keyGetter.addBodyLine("return this." + keyField.getName() + ";");
            topLevelClass.addMethod(keyGetter);
            //key setter
            Method keySetter = new Method("set" + keyClass);
            keySetter.setVisibility(JavaVisibility.PUBLIC);
            keySetter.addParameter(new Parameter(keyClass, keyField.getName()));
            keySetter.addBodyLine("this." + keyField.getName() + " = " + keyField.getName() + ";");
            topLevelClass.addMethod(keySetter);
        }
    }

    private boolean isCompositeKey(IntrospectedTable introspectedTable) {
        return introspectedTable.getPrimaryKeyColumns().size() > 1;
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
        IntrospectedColumn introspectedColumn) {
        this.addJavaElementComment(field, introspectedColumn);

        //CompositeKey
        if (isCompositeKey(introspectedTable) &&
            introspectedTable.getPrimaryKeyColumns().contains(introspectedColumn)) {
            field.setVisibility(JavaVisibility.PROTECTED);
        }

        //@Reserved
        for (ReservedColumn reservedColumn : ReservedColumn.values()) {
            if (introspectedColumn.getJavaProperty().equalsIgnoreCase(reservedColumn.name())) {
                field.addAnnotation("@" + reservedColumn.name());
            }
        }

        //@Column
        field.addAnnotation("@Column(name = \"" + introspectedColumn.getActualColumnName() + "\")");
    }

    @Override
    public void addGeneralMethodComment(Method method,
        IntrospectedTable introspectedTable) {
        method.addJavaDocLine("/**");
        addJavadocTag(method, false);
        method.addJavaDocLine(" */");
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable,
        IntrospectedColumn introspectedColumn) {
        this.addJavaElementComment(method, introspectedColumn);
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable,
        IntrospectedColumn introspectedColumn) {
        this.addJavaElementComment(method, introspectedColumn);
    }
}

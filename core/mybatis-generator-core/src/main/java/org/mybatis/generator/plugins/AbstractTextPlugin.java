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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.GeneratedTextFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * This plugin generates text files from freemarker Templates
 *
 * @author yong.ma
 */
public abstract class AbstractTextPlugin extends PluginAdapter {
    protected abstract List<String> getTemplateNames();

    protected abstract Map<String, Object> getTemplateData(IntrospectedTable introspectedTable);

    protected abstract String getFileName(String ftl, IntrospectedTable introspectedTable);

    protected abstract String getTargetPackage(String ftl, IntrospectedTable introspectedTable);

    public boolean validate(List<String> warnings) {
        // this plugin is always valid
        return true;
    }

    @Override
    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles(IntrospectedTable introspectedTable) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setClassForTemplateLoading(AbstractTextPlugin.class, "/templates");

        Map<String, Object> map = getTemplateData(introspectedTable);
        List<GeneratedXmlFile> result = new ArrayList<GeneratedXmlFile>();
        for (final String ftl : getTemplateNames()) {
            try {
                Template template = configuration.getTemplate(ftl);
                StringWriter stringWriter = new StringWriter();
                template.process(map, stringWriter);

                GeneratedTextFile generatedTextFile = new GeneratedTextFile(
                        stringWriter.toString(),
                        getFileName(ftl, introspectedTable),
                        getTargetPackage(ftl, introspectedTable),
                        context.getJavaModelGeneratorConfiguration().getTargetProject());
                result.add(generatedTextFile);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        return result;
    }
}

/**
 * Copyright 2006-2016 the original author or authors.
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
package org.mybatis.generator.api;

/**
 * The Class GeneratedTextFile.
 *
 * @author yong.ma
 */
public class GeneratedTextFile extends GeneratedXmlFile {
    private final String formattedContent;

    /**
     * Instantiates a new generated text file.
     *
     * @param formattedContent the text content
     * @param fileName         the file name
     * @param targetPackage    the target package
     * @param targetProject    the target project
     */
    public GeneratedTextFile(String formattedContent, String fileName, String targetPackage, String targetProject) {
        super(null, fileName, targetPackage, targetProject, false, null);
        this.formattedContent = formattedContent;
    }

    @Override
    public String getFormattedContent() {
        return this.formattedContent;
    }
}

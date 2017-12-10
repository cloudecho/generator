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
package org.mybatis.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GallantryMyBatisGeneratorTest {
    static Set<String> CONTEXT_SET = new HashSet<String>();

    static {
        //    CONTEXT_SET.add("test");
        CONTEXT_SET.add("gallantry");
    }

    @Before
    public void createDatabase() throws Exception {
        SqlScriptRunner scriptRunner = new SqlScriptRunner(getClass().getResourceAsStream("/scripts/CreateDB.sql"), "org.hsqldb.jdbcDriver", "jdbc:hsqldb:mem:aname", "sa", "");
        scriptRunner.executeScript();
    }

    @Test
    public void testGenerate() throws Exception {
        List<String> warnings = new ArrayList<String>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(getClass().getResourceAsStream("/gallantry/generatorConfig.xml"));
        DefaultShellCallback shellCallback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
        myBatisGenerator.generate(null, CONTEXT_SET, null, true);

        for (String w : warnings) {
            System.out.println("WARN: " + w);
        }
    }
}

/*
 * Copyright 2021 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.apicurio.perftest.agg;

import java.io.File;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author eric.wittmann@gmail.com
 */
@ApplicationScoped
public class AggregatorConfiguration {
    
    public static final String GATLING_HOME = "GATLING_HOME";
    public static final String LOGS_DIR = "LOGS_DIR";
    public static final String RESULTS_DIR = "RESULTS_DIR";
    public static final String HTML_DIR = "HTML_DIR";
    public static final String SHELL_PATH = "SHELL_PATH";
    public static final String PROCESS_SH_PATH = "PROCESS_SH_PATH";
    
    private File gatlingHome = null;
    private File logsDir = null;
    private File htmlDir = null;
    private File resultsDir = null;
    
    public File getGatlingHome() {
        if (gatlingHome == null) {
            String gatlingPath = "/apps/gatling";
            String ev = System.getenv(GATLING_HOME);
            if (ev != null && !ev.trim().isEmpty()) {
                gatlingPath = ev;
            }
            File file = new File(gatlingPath);
            if (!file.isDirectory()) {
                throw new RuntimeException("Invalid GATLING_HOME: " + file);
            }
            gatlingHome = file;
            System.out.println("Gatling home dir: " + gatlingHome);
        }
        return gatlingHome;

    }

    public File getLogsDirectory() {
        if (logsDir == null) {
            String logsPath = "/tmp/logs";
            String ev = System.getenv(LOGS_DIR);
            if (ev != null && !ev.trim().isEmpty()) {
                logsPath = ev;
            }
            File file = new File(logsPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!file.isDirectory()) {
                throw new RuntimeException("Invalid logs directory path: " + file);
            }
            logsDir = file;
            System.out.println("Logs directory: " + logsDir);
        }
        return logsDir;
    }

    public File getHtmlDirectory() {
        if (htmlDir == null) {
            String htmlPath = "/tmp/html";
            String ev = System.getenv(HTML_DIR);
            if (ev != null && !ev.trim().isEmpty()) {
                htmlPath = ev;
            }
            File file = new File(htmlPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!file.isDirectory()) {
                throw new RuntimeException("Invalid html directory path: " + file);
            }
            htmlDir = file;
            System.out.println("Html directory: " + htmlDir);
        }
        return htmlDir;
    }
    
    public File getResultsDirectory() {
        if (resultsDir == null) {
            String resultsPath = "/tmp/results";
            String ev = System.getenv(RESULTS_DIR);
            if (ev != null && !ev.trim().isEmpty()) {
                resultsPath = ev;
            } else {
                resultsPath = new File(getGatlingHome(), "results").getAbsolutePath();
            }
            File file = new File(resultsPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!file.isDirectory()) {
                throw new RuntimeException("Invalid results directory path: " + file);
            }
            resultsDir = file;
            System.out.println("Results directory: " + resultsDir);
        }
        return resultsDir;
    }
    
    public String getShell() {
        String shell = "/bin/bash";
        String ev = System.getenv(SHELL_PATH);
        if (ev != null && !ev.trim().isEmpty()) {
            shell = ev;
        }
        System.out.println("Location of shell: " + shell);
        return shell;
    }
    
    public String getProcessSh() {
        String path = "/apps/bin/process.sh";
        String ev = System.getenv(PROCESS_SH_PATH);
        if (ev != null && !ev.trim().isEmpty()) {
            path = ev;
        }
        System.out.println("Location of process.sh: " + path);
        return path;
    }

}

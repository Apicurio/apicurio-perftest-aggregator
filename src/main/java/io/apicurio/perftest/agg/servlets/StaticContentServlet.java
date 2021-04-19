/*
 * Copyright 2020 Red Hat
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

package io.apicurio.perftest.agg.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import io.apicurio.perftest.agg.AggregatorConfiguration;

/**
 * @author eric.wittmann@gmail.com
 */
@ApplicationScoped
public class StaticContentServlet extends GenericServlet {

    private static final long serialVersionUID = 4259630009438256847L;
    
    @Inject
    AggregatorConfiguration config;

    /**
     * @see javax.servlet.GenericServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null) {
            pathInfo = "/index.html";
        }
        if (pathInfo.contains("..")) {
            throw new UnsupportedOperationException();
        }

        String relPath = pathInfo;
        if (relPath.startsWith("/")) {
            relPath = relPath.substring(1);
        }
        if (relPath.isEmpty() || relPath.endsWith("/")) {
            relPath += "index.html";
        }
        
        File file = new File(config.getHtmlDirectory(), relPath);
        
        System.out.println("Attempting to serve file: " + file);

        HttpServletResponse response = (HttpServletResponse) res;
        if (!file.isFile()) {
            response.sendError(404);
        } else {
            response.setContentType(detectContentType(file));
            response.setStatus(200);
            try (InputStream from = new FileInputStream(file) ; OutputStream to = response.getOutputStream()) {
                IOUtils.copy(from, to);
            }
        }
    }

    private static String detectContentType(File file) {
        String name = file.getName();
        if (name.endsWith(".html")) {
            return "text/html";
        }
        if (name.endsWith(".css")) {
            return "text/css";
        }
        if (name.endsWith(".js")) {
            return "text/javascript";
        }
        if (name.endsWith(".json")) {
            return "application/json";
        }
        if (name.endsWith(".xml")) {
            return "application/xml";
        }
        if (name.endsWith(".svg")) {
            return "image/svg+xml";
        }
        if (name.endsWith(".png")) {
            return "image/png";
        }
        if (name.endsWith(".jpg")) {
            return "image/jpg";
        }
        if (name.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (name.endsWith(".gif")) {
            return "image/gif";
        }
        
        System.out.println("Unknown type for file: " + name);
        
        return "text/plain";
    }

}

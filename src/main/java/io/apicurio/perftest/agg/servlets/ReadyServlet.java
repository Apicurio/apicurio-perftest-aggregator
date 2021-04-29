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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * @author eric.wittmann@gmail.com
 */
@ApplicationScoped
public class ReadyServlet extends GenericServlet {

    private static final long serialVersionUID = 4259630009438256847L;

    private static final String READY_HTML = "<html><head><title>Registry Performance Test Ready Page</title></head><body>OK</body></html>";
    
    /**
     * @see javax.servlet.GenericServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setContentType("text/html");
        response.setStatus(200);

        try (InputStream from = new ByteArrayInputStream(READY_HTML.getBytes(StandardCharsets.UTF_8)) ; 
             OutputStream to = response.getOutputStream()) 
        {
            IOUtils.copy(from, to);
        }
    }

}

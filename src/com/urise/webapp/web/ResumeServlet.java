package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        printResume(response);
    }

    private void printResume(HttpServletResponse response) throws IOException {
        List<Resume> resumes = storage.getAllSorted();
        response.getWriter().write(
                "<html>\n" +
                        "<body>\n" +
                        "<table border=\"1\"\n" +
                        "<tr>\n" +
                        "<th>Uuid</th>\n" +
                        "<th>FullName</th>\n" +
                        "</tr>\n");
        for (Resume resume : resumes) {
            response.getWriter().write(
                    "<tr>\n" +
                            "<th>" + resume.getUuid() + "</th>\n" +
                            "<th>" + resume.getFullName() + "</th>\n" +
                            "</tr>\n");
        }
        response.getWriter().write(
                "</table>\n" +
                        "</body>\n" +
                        "</html>\n");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
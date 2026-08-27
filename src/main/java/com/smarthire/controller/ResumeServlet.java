package com.smarthire.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.smarthire.dao.ResumeDAO;
import com.smarthire.model.Resume;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/resume")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class ResumeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ResumeDAO resumeDAO;

    // =========================================================
    // INITIALIZE
    // =========================================================

    @Override
    public void init() {

        resumeDAO =
                new ResumeDAO();
    }


    // =========================================================
    // GET
    // =========================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String action =
                request.getParameter("action");


        // =====================================================
        // VIEW / DOWNLOAD RESUME
        // =====================================================

        if ("download".equals(action) ||
            "view".equals(action)) {

            serveResume(
                    request,
                    response
            );

            return;
        }


        // =====================================================
        // JOB SEEKER RESUME PAGE
        // =====================================================

        int userId =
                (Integer) session.getAttribute(
                        "userId"
                );

        String role =
                (String) session.getAttribute(
                        "role"
                );

        if (!"JOB_SEEKER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        Resume resume =
                resumeDAO.getResumeByUserId(
                        userId
                );

        request.setAttribute(
                "resume",
                resume
        );

        request.getRequestDispatcher(
                "/resume.jsp"
        ).forward(
                request,
                response
        );
    }


    // =========================================================
    // POST - UPLOAD RESUME
    // =========================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String role =
                (String) session.getAttribute(
                        "role"
                );

        if (!"JOB_SEEKER".equals(role)) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        int userId =
                (Integer) session.getAttribute(
                        "userId"
                );

        // =====================================================
        // GET FILE
        // =====================================================

        Part filePart =
                request.getPart("resumeFile");

        if (filePart == null ||
            filePart.getSize() == 0) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/resume?error=nofile"
            );

            return;
        }

        // =====================================================
        // GET ORIGINAL FILE NAME
        // =====================================================

        String originalFileName =
                Paths.get(
                        filePart.getSubmittedFileName()
                )
                .getFileName()
                .toString();

        String lowerName =
                originalFileName.toLowerCase();


        // =====================================================
        // CHECK FILE TYPE
        // =====================================================

        if (!lowerName.endsWith(".pdf") &&
            !lowerName.endsWith(".doc") &&
            !lowerName.endsWith(".docx")) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/resume?error=type"
            );

            return;
        }


        // =====================================================
        // UPLOAD DIRECTORY
        // =====================================================

        String uploadFolder =
                System.getProperty("user.home")
                        + java.io.File.separator
                        + "SmartHireUploads"
                        + java.io.File.separator
                        + "resumes";

        Path uploadPath =
                Paths.get(uploadFolder);

        Files.createDirectories(
                uploadPath
        );


        // =====================================================
        // CREATE UNIQUE FILE NAME
        // =====================================================

        String storedFileName =
                userId
                        + "_"
                        + System.currentTimeMillis()
                        + "_"
                        + originalFileName;

        Path targetPath =
                uploadPath.resolve(
                        storedFileName
                );


        // =====================================================
        // SAVE FILE
        // =====================================================

        try (java.io.InputStream input =
                     filePart.getInputStream()) {

            Files.copy(
                    input,
                    targetPath,
                    java.nio.file.StandardCopyOption
                            .REPLACE_EXISTING
            );
        }


        // =====================================================
        // DELETE OLD RESUME
        // =====================================================

        Resume oldResume =
                resumeDAO.getResumeByUserId(
                        userId
                );

        if (oldResume != null) {

            try {

                Files.deleteIfExists(
                        Paths.get(
                                oldResume.getFilePath()
                        )
                );

            } catch (Exception e) {

                e.printStackTrace();
            }

            resumeDAO.deleteResume(
                    oldResume.getId()
            );
        }


        // =====================================================
        // SAVE DATABASE RECORD
        // =====================================================

        Resume resume =
                new Resume(
                        userId,
                        originalFileName,
                        targetPath.toString()
                );

        boolean saved =
                resumeDAO.addResume(
                        resume
                );


        // =====================================================
        // RESULT
        // =====================================================

        if (saved) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/resume"
            );

        } else {

            /*
             * Database save failed, therefore remove
             * the physical file that was just uploaded.
             */

            try {

                Files.deleteIfExists(
                        targetPath
                );

            } catch (Exception e) {

                e.printStackTrace();
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/resume?error=database"
            );
        }
    }


    // =========================================================
    // VIEW / DOWNLOAD RESUME
    // =========================================================

    private void serveResume(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        String role =
                (String) session.getAttribute(
                        "role"
                );

        if (role == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login.jsp"
            );

            return;
        }

        String idParameter =
                request.getParameter("id");

        if (idParameter == null ||
            idParameter.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Resume ID is required."
            );

            return;
        }

        int resumeId;

        try {

            resumeId =
                    Integer.parseInt(
                            idParameter
                    );

        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid Resume ID."
            );

            return;
        }

        int currentUserId =
                (Integer) session.getAttribute(
                        "userId"
                );

        Resume resume = null;


        // =====================================================
        // JOB SEEKER CAN VIEW OWN RESUME
        // =====================================================

        if ("JOB_SEEKER".equals(role)) {

            resume =
                    resumeDAO.getResumeById(
                            resumeId
                    );

            if (resume == null ||
                resume.getUserId() != currentUserId) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "You cannot access this resume."
                );

                return;
            }
        }


        // =====================================================
        // RECRUITER CAN VIEW RESUMES OF THEIR APPLICANTS
        // =====================================================

        else if ("RECRUITER".equals(role)) {

            resume =
                    resumeDAO.getResumeForRecruiter(
                            resumeId,
                            currentUserId
                    );

            if (resume == null) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "You cannot access this resume."
                );

                return;
            }
        }


        // =====================================================
        // OTHER ROLES
        // =====================================================

        else {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access denied."
            );

            return;
        }


        // =====================================================
        // CHECK PHYSICAL FILE
        // =====================================================

        Path filePath =
                Paths.get(
                        resume.getFilePath()
                );

        if (!Files.exists(filePath) ||
            !Files.isRegularFile(filePath)) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Resume file not found."
            );

            return;
        }


        // =====================================================
        // DETERMINE CONTENT TYPE
        // =====================================================

        String contentType =
                Files.probeContentType(
                        filePath
                );

        if (contentType == null) {

            String name =
                    resume.getFileName()
                            .toLowerCase();

            if (name.endsWith(".pdf")) {

                contentType =
                        "application/pdf";

            } else if (name.endsWith(".doc")) {

                contentType =
                        "application/msword";

            } else if (name.endsWith(".docx")) {

                contentType =
                        "application/vnd.openxmlformats-officedocument"
                        + ".wordprocessingml.document";

            } else {

                contentType =
                        "application/octet-stream";
            }
        }

        response.setContentType(
                contentType
        );

        response.setContentLengthLong(
                Files.size(filePath)
        );


        // =====================================================
        // VIEW OR DOWNLOAD
        // =====================================================

        String action =
                request.getParameter(
                        "action"
                );

        if ("view".equals(action) &&
            contentType.equals("application/pdf")) {

            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=\"" +
                    resume.getFileName() +
                    "\""
            );

        } else {

            response.setHeader(
                    "Content-Disposition",
                    "attachment; filename=\"" +
                    resume.getFileName() +
                    "\""
            );
        }


        // =====================================================
        // SEND FILE
        // =====================================================

        try (java.io.InputStream input =
                     Files.newInputStream(filePath);
             OutputStream output =
                     response.getOutputStream()) {

            byte[] buffer =
                    new byte[8192];

            int bytesRead;

            while ((bytesRead =
                    input.read(buffer)) != -1) {

                output.write(
                        buffer,
                        0,
                        bytesRead
                );
            }
        }
    }
}
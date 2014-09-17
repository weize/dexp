/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umass.ciir.dexp;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ggf.drmaa.DrmaaException;
import org.ggf.drmaa.JobTemplate;
import org.ggf.drmaa.Session;
import org.ggf.drmaa.SessionFactory;

/**
 *
 * @author wkong
 */
public class DRMAAExecutor {

    Session session;

//    public String command = System.getenv("JAVA_HOME") + File.separator
//          + "bin/java";
//    public String classPath = System.getProperty("java.class.path");
    public DRMAAExecutor() {
        try {
            session = SessionFactory.getFactory().getSession();
            session.init("");
        } catch (DrmaaException ex) {
            Logger.getLogger(DRMAAExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String submit(Job job) {
        String id = "";
        try {
            JobTemplate template = session.createJobTemplate();
            template.setJobName(job.name);
            template.setWorkingDirectory((new File(".")).getCanonicalPath());
            template.setRemoteCommand(job.command);
            template.setArgs(job.arguments);
            template.setNativeSpecification(job.nativeSpecification);
            template.setOutputPath(job.stdoutPath);
            template.setErrorPath(job.stderrPath);

            // run the job
            id = session.runJob(template);
            int status = session.getJobProgramStatus(id);
            if (status == Session.FAILED) {
                System.err.println("ERROR: Job failed! [" + job.name +"]");
            }
            
            session.deleteJobTemplate(template);

        } catch (Exception ex) {
            System.err.println("Problems submitting jobs: " + ex.getMessage());
            return null;
        }
        System.err.println("job-launched: " + job.name);
        System.err.println("jobid: " + id);
        return id;
    }

    public void shutdown() {
        try {
            session.exit();
        } catch (DrmaaException ex) {
            Logger.getLogger(DRMAAExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

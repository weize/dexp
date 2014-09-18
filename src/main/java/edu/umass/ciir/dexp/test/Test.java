/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umass.ciir.dexp.test;

import edu.umass.ciir.dexp.DRMAAExecutor;
import edu.umass.ciir.dexp.Job;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author wkong
 */
public class Test {

    public static final String DEFAULT_ENCODING = System.getProperty("file.encoding", "UTF-8");

    public static void main(String[] args) throws IOException {
        String classPath = System.getProperty("java.class.path");
        String memory_x = "-Xmx200m";
        String memory_s = "-Xmx200m";
        String temporary = new File(".").getCanonicalPath();
        String className = TestJob.class.getName();
        DRMAAExecutor executor = new DRMAAExecutor();
        Job job = new Job();
        job.name = "test";
        job.command = System.getenv("JAVA_HOME") + File.separator + "bin/java";
        job.arguments = new String[]{"-ea", memory_x, memory_s,
            "-Dfile.encoding=" + DEFAULT_ENCODING,
            "-cp", classPath, className};
        job.nativeSpecification = "-w n -q all.q -l mem_free=1G -l mem_token=1G";
        job.stdoutPath = ":" + temporary + File.separator + "stdout";
        job.stderrPath = ":" + temporary + File.separator + "stderr";
        executor.submit(job);
        executor.shutdown();
    }

}

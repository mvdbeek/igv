/*
 * Copyright (c) 2007-2012 The Broad Institute, Inc.
 * SOFTWARE COPYRIGHT NOTICE
 * This software and its documentation are the copyright of the Broad Institute, Inc. All rights are reserved.
 *
 * This software is supplied without any warranty or guaranteed support whatsoever. The Broad Institute is not responsible for its use, misuse, or functionality.
 *
 * This software is licensed under the terms of the GNU Lesser General Public License (LGPL),
 * Version 2.1 which is available at http://www.opensource.org/licenses/lgpl-2.1.php.
 */

package org.broad.igv.gs.dm;

import org.broad.igv.Globals;
import org.broad.igv.PreferenceManager;
import org.broad.igv.gs.GSUtils;
import org.broad.igv.util.HttpUtils;
import org.broad.igv.util.TestUtils;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * @author Jim Robinson
 * @date 1/16/12
 */
public class DMUtilsTest {


    private static final String IGV_TEST_DIR = "/Home/igvtest/";
    private URL defaultURL;
    private URL personaldirectoryURL;
    private URL fileURL;

    @Before
    public void setUp() {
        Globals.setTesting(true);
        GSUtils.logout();
        HttpUtils.getInstance().setAuthenticator(new GSTestAuthenticator());

        try {
            String defaultURLStr = PreferenceManager.getInstance().get(PreferenceManager.GENOME_SPACE_DM_SERVER);
            defaultURL = new URL(defaultURLStr);
            personaldirectoryURL = new URL(defaultURLStr + DMUtils.PERSONAL_DIRECTORY);
            fileURL = new URL(defaultURL + "file");
            System.out.println("Genome space URL: " + defaultURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Assume.assumeTrue(false);
        }
    }

    @After
    public void tearDown() {
        HttpUtils.getInstance().resetAuthenticator();
    }

    @Test
    public void testGetDirectoryListing() throws Exception {
        final String testFileName = "Broad.080528.subtypes.seg.gz";
        boolean found = dirContainsFile(personaldirectoryURL, testFileName);
        assertTrue("Test file not found: " + testFileName, found);

    }

    private boolean dirContainsFile(URL dirURL, String testFileName) throws Exception{
        GSDirectoryListing dirListing = DMUtils.getDirectoryListing(dirURL);

        assertNotNull("Directory listing", dirListing);

        List<GSFileMetadata> gsFiles = dirListing.getContents();

        //Search for known test file
        boolean found = false;
        for (GSFileMetadata fileMetadata : gsFiles) {
            if (fileMetadata.getName().equals(testFileName)) {
                found = true;
                String path = IGV_TEST_DIR + testFileName;
                assertEquals("Test file path not expected", path, fileMetadata.getPath());
            }
        }
        return found;
    }

    /**
     * Upload a file, check it got uploaded, delete it, check it was deleted
     * Not really ideal, but since we need to check that the file doesn't exist before
     * uploading and delete it afterwards anyway, figured we might as well combine these.
     * @throws Exception
     */
    @Test
    public void testUploadDeleteFile() throws Exception {

        String locName = "test2.bed";
        File localFile = new File(TestUtils.DATA_DIR + "bed", locName);
        String remPath = IGV_TEST_DIR + locName;

        assertFileStatus(locName, false);

        DMUtils.uploadFile(localFile, remPath);

        assertFileStatus(locName, true);

        DMUtils.deleteFileOrDirectory(fileURL + remPath);

        assertFileStatus(locName, false);
    }

    @Test
    public void testCreateDeleteDirectory() throws Exception {
        String dirname = "testdir_deleteme";
        String fullPath = IGV_TEST_DIR + dirname;

        assertFileStatus(dirname, false);

        DMUtils.createDirectory(fileURL + fullPath);

        assertFileStatus(dirname, true);

        DMUtils.deleteFileOrDirectory(fileURL + fullPath);

        assertFileStatus(dirname, false);
    }

    private void assertFileStatus(String objName, boolean expExists) throws Exception{
        boolean found = dirContainsFile(personaldirectoryURL, objName);
        if(expExists){
            assertEquals("Object not found: " + objName, expExists, found);
        }else{
            assertEquals("Object exists, but it shouldn't: " + objName, expExists, found);
        }
    }


    static class GSTestAuthenticator extends Authenticator {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("igvtest", "igvtest".toCharArray());
        }
    }

}

/*
 *  Copyright (c) 2011 Tamas Cservenak. All rights reserved.
 *
 *  <tamas@cservenak.com>
 *  http://www.cservenak.com/
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.cservenak.streams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CoderTest
    extends TestCase
{
    public void testSomeNastyClass()
        throws IOException
    {
        File baseDir = new File( "target/test-classes" );
        File inputFile = new File( baseDir, "input.txt" );
        File middleFile = new File( baseDir, "middle.txt" );
        File outputFile = new File( baseDir, "output.txt" );

        InputStream is = null;
        OutputStream os = null;

        // copy original - middle
        is = new FileInputStream( inputFile );
        os = new DummyOutputStream( new FileOutputStream( middleFile ), 'a' );

        IOUtils.copy( is, os );
        os.flush();
        IOUtils.closeQuietly( os );
        IOUtils.closeQuietly( is );

        // copy middle - output
        is = new DummyInputStream( new FileInputStream( middleFile ), 'b' );
        os = new FileOutputStream( outputFile );

        IOUtils.copy( is, os );
        os.flush();
        IOUtils.closeQuietly( os );
        IOUtils.closeQuietly( is );

        // assertions
        // inputFile is 8 byte length, some random content
        Assert.assertEquals( "input file is 8 bytes!", 8, inputFile.length() );

        // middleFile is also 8 byte length, and is not equal with inputFile since it's all 'a's.
        Assert.assertEquals( "middle file is 8 bytes!", 8, middleFile.length() );
        Assert.assertFalse( "input and middle file should differ!", FileUtils.contentEquals( inputFile, middleFile ) );
        
        // outputFile is also 8 byte length, and is not equal with input and with middle file neither, since it's all 'b's
        Assert.assertEquals( "middle file is 8 bytes!", 8, outputFile.length() );
        Assert.assertFalse( "input and output file should differ!", FileUtils.contentEquals( inputFile, outputFile ) );
        Assert.assertFalse( "middle and output file should differ!", FileUtils.contentEquals( middleFile, outputFile ) );
    }

}

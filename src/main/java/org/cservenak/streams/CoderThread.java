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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class CoderThread
    extends Thread
{
    private final PipedInputStream inSink;

    private final PipedOutputStream outSink;

    private final Runnable workhorse;

    private Throwable throwable;

    public CoderThread( final Coder coder, final InputStream in )
        throws IOException
    {
        this.inSink = new PipedInputStream();
        this.outSink = new PipedOutputStream( inSink );
        this.workhorse = new Runnable()
        {
            public void run()
            {
                try
                {
                    coder.code( in, outSink );

                    outSink.flush();
                    
                    outSink.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();

                    throwable = e;
                }
            }
        };
    }

    public CoderThread( final Coder coder, final OutputStream out )
        throws IOException
    {
        this.outSink = new PipedOutputStream();
        this.inSink = new PipedInputStream( outSink );
        this.workhorse = new Runnable()
        {
            public void run()
            {
                try
                {
                    coder.code( inSink, out );

                    out.flush();
                    
                    inSink.close();
                }
                catch ( IOException e )
                {
                    e.printStackTrace();

                    throwable = e;
                }
            }
        };
    }

    public void run()
    {
        workhorse.run();
    }

    // ==

    public Throwable getThrowable()
    {
        return throwable;
    }

    public PipedInputStream getInputStreamSink()
    {
        return inSink;
    }

    public PipedOutputStream getOutputStreamSink()
    {
        return outSink;
    }
}

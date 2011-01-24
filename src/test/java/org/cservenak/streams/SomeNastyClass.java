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
import java.util.Arrays;

/**
 * This is a nasty class that does not "adheres" to Java Filter stream specs, but we do need it as. Honestly, I could
 * not come up with anything "nastier" than a code that simple swaps out read bytes with some preselected char.
 * 
 * @author cstamas
 */
public class SomeNastyClass
{
    private final char c;

    public SomeNastyClass( final char c )
    {
        this.c = c;
    }

    public void beNasty( InputStream in, OutputStream out )
        throws IOException
    {
        int nread = 0;
        final byte[] buffer = new byte[1024];
        while ( ( nread = in.read( buffer ) ) != -1 )
        {
            Arrays.fill( buffer, (byte) ( c & 0xFF ) );
            out.write( buffer, 0, nread );
        }
    }
}

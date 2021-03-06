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

/**
 * This is a wrapper around the SomeNastyClass that makes it implement Coder iface.
 * 
 * @author cstamas
 */
public class SomeNastyClassWrapper
    implements Coder
{
    private final SomeNastyClass someNastyClass;

    public SomeNastyClassWrapper( final char c )
    {
        this.someNastyClass = new SomeNastyClass( c );
    }

    public void code( InputStream in, OutputStream out )
        throws IOException
    {
        someNastyClass.beNasty( in, out );
    }
}

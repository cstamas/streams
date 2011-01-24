Streams
=======

Simple reusable utilities for Java IO Streams.


Problem
=======

You have some utility that is needed for your job to be done, and is about to be used as FilterStream but the utility is "nasty" and does not provide you such classes? Or, you need the exact opposites of GZIPInputStream (that _ungzips_ from input stream) or GZIPOutputStream (that _gzips_ to output stream)?

No problem here.

Solution
========

Steps needed:

 1. Wrap your nasty class with Coder interface
 2. Optionally subclass CoderInputStream and/or CoderOutputStream
 3. Use your nasty utility as FilterStream!

Look at unit tests for an example.

Have fun,  
~t~
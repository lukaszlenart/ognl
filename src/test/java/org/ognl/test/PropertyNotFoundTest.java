//--------------------------------------------------------------------------
//  Copyright (c) 2004, Drew Davidson and Luke Blanshard
//  All rights reserved.
//
//  Redistribution and use in source and binary forms, with or without
//  modification, are permitted provided that the following conditions are
//  met:
//
//  Redistributions of source code must retain the above copyright notice,
//  this list of conditions and the following disclaimer.
//  Redistributions in binary form must reproduce the above copyright
//  notice, this list of conditions and the following disclaimer in the
//  documentation and/or other materials provided with the distribution.
//  Neither the name of the Drew Davidson nor the names of its contributors
//  may be used to endorse or promote products derived from this software
//  without specific prior written permission.
//
//  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
//  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
//  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
//  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
//  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
//  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
//  OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED
//  AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
//  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
//  THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
//  DAMAGE.
//--------------------------------------------------------------------------
package org.ognl.test;

import junit.framework.TestSuite;
import ognl.*;

import java.util.Map;

public class PropertyNotFoundTest extends OgnlTestCase {

    private static final Blah BLAH = new Blah();

    private static final Object[][] TESTS = {
            {BLAH, "webwork.token.name", OgnlException.class, "W value", OgnlException.class},
    };

    public static class Blah {
        String x;
        String y;

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }
    }

    public static class BlahPropertyAccessor implements PropertyAccessor {
        public void setProperty(Map context, Object target, Object name, Object value) throws OgnlException {
        }

        public Object getProperty(Map context, Object target, Object name) throws OgnlException {
            if ("x".equals(name) || "y".equals(name)) {
                return OgnlRuntime.getProperty((OgnlContext) context, target, name);
            }
            return null;
        }

        public String getSourceAccessor(OgnlContext context, Object target, Object index) {
            return index.toString();
        }

        public String getSourceSetter(OgnlContext context, Object target, Object index) {
            return index.toString();
        }
    }

    public static TestSuite suite() {
        TestSuite result = new TestSuite();

        for (Object[] test : TESTS) {
            result.addTest(
                    new PropertyNotFoundTest(
                            (String) test[1],
                            test[0],
                            (String) test[1],
                            test[2],
                            test[3],
                            test[4]
                    )
            );
        }
        return result;
    }

    public PropertyNotFoundTest() {
        super();
    }

    public PropertyNotFoundTest(String name) {
        super(name);
    }

    public PropertyNotFoundTest(String name, Object root, String expressionString, Object expectedResult, Object setValue, Object expectedAfterSetResult) {
        super(name, root, expressionString, expectedResult, setValue, expectedAfterSetResult);
    }

    public PropertyNotFoundTest(String name, Object root, String expressionString, Object expectedResult, Object setValue) {
        super(name, root, expressionString, expectedResult, setValue);
    }

    public PropertyNotFoundTest(String name, Object root, String expressionString, Object expectedResult) {
        super(name, root, expressionString, expectedResult);
    }

    protected void setUp() {
        super.setUp();
        OgnlRuntime.setPropertyAccessor(Blah.class, new BlahPropertyAccessor());
    }
}

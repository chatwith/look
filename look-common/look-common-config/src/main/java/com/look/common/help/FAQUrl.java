/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.look.common.help;

/**
 * 记录一些问题对应的解决方案，减少答疑工作量
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 */
public class FAQUrl {

    //
    // FAQ: 未收录异常处理办法
    //
    public static final String UNEXPECTED_EXCEPTION_URL = //
            "https://github.com/zxj058/look/issues/48";

    // FAQ: 主机名不存在
    public static final String UNKNOWN_HOST_EXCEPTION = //
            "https://github.com/zxj058/look/issues/64";

    private static final String TipStringBegin = "\nSee ";
    private static final String TipStringEnd = " for further details.";


    public static String suggestTodo(final String url) {
        StringBuilder sb = new StringBuilder();
        sb.append(TipStringBegin);
        sb.append(url);
        sb.append(TipStringEnd);
        return sb.toString();
    }


    /**
     * 对于没有未异常原因指定FAQ的情况，追加默认FAQ
     */
    public static String attachDefaultURL(final String errorMessage) {
        if (errorMessage != null) {
            int index = errorMessage.indexOf(TipStringBegin);
            if (-1 == index) {
                StringBuilder sb = new StringBuilder();
                sb.append(errorMessage);
                sb.append("\n");
                sb.append("For more information, please visit the url, ");
                sb.append(UNEXPECTED_EXCEPTION_URL);
                return sb.toString();
            }
        }

        return errorMessage;
    }
}

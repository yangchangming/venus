/*
 *  Copyright 2015-2018 DataVens, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package venus.config.factor;

import venus.config.Config;

/**
 * <p> Default XML Configuration </p>
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2018-05-03 11:06
 */
public class DefaultXMLConfig implements Config {

    private String type;

    private Object data;

    public void refreshData(Object data) {
        if (data != null && data instanceof venus.config.URL){
            this.data = data;
        }
    }

    public Object getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

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
package venus.oa.checkcode.bs.impl;

import org.springframework.stereotype.Service;
import venus.oa.checkcode.bs.ICheckCodeBs;

/**
 * <p> Check code service implements </p>
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2019-03-02 17:27
 */
@Service
public class CheckCodeBs implements ICheckCodeBs {

    @Override
    public byte[] buildCheckCode() {
        return new byte[0];
    }
}

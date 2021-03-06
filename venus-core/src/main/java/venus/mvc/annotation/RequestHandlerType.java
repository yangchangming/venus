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
package venus.mvc.annotation;

/**
 * <p> Request handler type </p>
 * 1. common, will be execute by all handler
 * 2. customize, just be execute by the handler for annotation @RequestChain
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2019-06-01 15:07
 */
public enum RequestHandlerType {
    COMMON, CUSTOMIZE
}

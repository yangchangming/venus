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
package venus.mvc.chain;

import venus.core.Context;
import venus.ioc.Beans;
import venus.mvc.Mvcs;
import venus.mvc.RequestPath;
import venus.mvc.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> Request chain factory </p>
 * 1. build handler chain for a request
 * 2. diff request corresponding to diff chain
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2019-05-30 16:26
 */
public class RequestHandlerChainFactory {

    private static Map<RequestPath, RequestHandlerChain> chains = new ConcurrentHashMap<>();

    public static void initialChain(){
        Set<Class<?>> mappingClz = Beans.of().loadClassByAnnotation(RequestMapping.class);
        



    }

    /**
     * fetch request handler chain
     *
     * @param context
     * @return
     */
    public static RequestHandlerChain chain(Context context){
        RequestPath path = Mvcs.buildPath(context);
        if (chains.containsKey(path)){
            return chains.get(path);
        }else {
            chains.putIfAbsent(path, RequestHandlerChainFactory.buildChain(context));
        }
        return chains.get(path);
    }

    /**
     * build chain for every http request
     *
     * @param context
     * @return
     */
    protected static RequestHandlerChain buildChain(Context context){
        RequestHandlerChain chain = new RequestHandlerChain(context);

        // TODO: 19/5/31 build chain

        return chain;
    }




}
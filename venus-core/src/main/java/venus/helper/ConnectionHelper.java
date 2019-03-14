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
package venus.helper;

import venus.common.VenusConstants;
import venus.datasource.GenericDataSource;
import venus.exception.VenusFrameworkException;
import venus.springsupport.BeanFactoryHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p> Connection of database helper </p>
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2019-03-14 12:20
 */
public class ConnectionHelper {

    public static Connection getConnection(){
        DataSource dataSource = (GenericDataSource)BeanFactoryHelper.getBean(VenusConstants.BEAN_ID_DATASOURCE);
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new VenusFrameworkException("Fetch connection failure.[" + e.getMessage() + "]");
        }
    }

}

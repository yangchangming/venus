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
package venus.user.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import venus.frames.base.dao.BaseTemplateDao;
import venus.user.dao.IUserDao;
import venus.user.model.User;
import venus.util.PopulateUtil;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p> User Dao implements </p>
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2018-05-29 12:08
 */
@Repository("userDao")
public class UserDao extends BaseTemplateDao implements IUserDao {

    @Autowired
    private DataSource dataSource;

    public List<User> queryAll(String sql) {

        List result = query(sql.trim(), new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User();
                PopulateUtil.populate(user, rs);
                return user;
            }
        });

        return result;
    }
}

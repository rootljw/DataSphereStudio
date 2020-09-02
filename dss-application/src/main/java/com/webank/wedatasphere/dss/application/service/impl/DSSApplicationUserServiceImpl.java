/*
 * Copyright 2019 WeBank
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.webank.wedatasphere.dss.application.service.impl;

import com.webank.wedatasphere.dss.application.dao.DSSApplicationUserMapper;
import com.webank.wedatasphere.dss.application.entity.DSSUser;
import com.webank.wedatasphere.dss.application.entity.WorkOrder;
import com.webank.wedatasphere.dss.application.service.DSSApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by chaogefeng on 2019/10/11.
 */
@Service
public class DSSApplicationUserServiceImpl implements DSSApplicationUserService {

    @Autowired
    private DSSApplicationUserMapper dssApplicationUserMapper;

    @Override
    public DSSUser getUserByName(String username) {
        DSSUser user = dssApplicationUserMapper.getUserByName(username);
        WorkOrder order = dssApplicationUserMapper.getUserOrderInfoByName(1, user.getUsername());
        if (order.getSuccess() == 2) {
            user.setStatus(3);
        } else if (order.getSuccess() == 1) {
            user.setStatus(2);
        } else if (order.getSuccess() == 0 && order.getProgress() == 0) {
            user.setStatus(0);
        } else {
            user.setStatus(1);
        }
        return user;
    }

    @Override
    public void registerDssUser(DSSUser userDb) {
        dssApplicationUserMapper.registerDssUser( userDb);
    }

    @Override
    public void updateUserFirstLogin(Long id) {
        dssApplicationUserMapper.updateUserFirstLogin(id);
    }
}

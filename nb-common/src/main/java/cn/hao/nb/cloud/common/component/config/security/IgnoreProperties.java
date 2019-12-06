/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package cn.hao.nb.cloud.common.component.config.security;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lengleng
 * @date 2018/1/9
 */
//@Data
//@Configuration
//@RefreshScope
//@ConditionalOnExpression("!'${ignore}'.isEmpty()")
//@ConfigurationProperties(prefix = "ignore")
public class IgnoreProperties {
    private List<String> urls = new ArrayList<>();

    private List<String> clients = new ArrayList<>();
}

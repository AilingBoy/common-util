package com.cn.stardust.tool.jdk.diagram;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author stardust
 * @date 2020/11/3 13:57
 */
@Data
public class Clazz {

    private String name;

    private String packageName;

    private Set<Clazz> parents;

    private List<String> marks;

    private Set<Clazz> children;
}

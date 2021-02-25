package net.mhgoi.blog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形数据工具类(ElementUI Tree控件)
 * 使用该工具的实体类必须实现 接口Tree 里的方法
 */
public class TreeUtil {
    public static <T extends Tree<T>> List<T> getTreeList(String topId, List<T> entityList, List<String> conditions) {
        List<T> resultList = new ArrayList<>(); //存储顶层的数据
        Map<Object, T> treeMap = new HashMap<>();
        T itemTree;

        for (int i = 0; i < entityList.size(); i++) {
            itemTree = entityList.get(i);
            treeMap.put(itemTree.getId(), itemTree); //把所有的数据放到Map中，id为Key
            if ((topId == null && itemTree.getParentId() == null) || (itemTree.getParentId() != null && itemTree.getParentId().equals(topId))) {   //把顶层数据放到集合中
                //添加条件
                if (conditions == null || conditions.contains(itemTree.getId())) {
                    resultList.add(itemTree);
                }
            }
        }

        //循环数据，把数据放到上一级的children属性中
        for (int i = 0; i < entityList.size(); i++) {
            itemTree = entityList.get(i);

            //添加条件
            if (conditions == null || conditions.contains(itemTree.getId())) {
                T data = treeMap.get((itemTree.getParentId())); //在map集合中寻找父亲
                if (data != null) { //判断父亲有没有
                    if (data.getChildren() == null) {
                        data.setChildren(new ArrayList<>());
                    }
                    data.getChildren().add(itemTree); //把子节点 放到父节点childList当中
                    treeMap.put(itemTree.getParentId(), data);
                }
            }
        }

        return resultList;
    }
}

package net.mhgoi.blog.util;

import java.util.List;

public interface Tree<T> {

    String getId();

    String getParentId();

    List<T> getChildren();

    void setChildren(List<T> children);

}

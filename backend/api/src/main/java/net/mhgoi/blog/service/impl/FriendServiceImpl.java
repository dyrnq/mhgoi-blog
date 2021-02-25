package net.mhgoi.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.mhgoi.blog.entity.Friend;
import net.mhgoi.blog.mapper.FriendMapper;
import net.mhgoi.blog.service.FriendService;
import org.springframework.stereotype.Service;

/**
 * (Friend)表服务实现类
 *
 * @author 少年
 * @since 2020-08-02 20:10:04
 */
@Service("friendService")
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements FriendService {

}